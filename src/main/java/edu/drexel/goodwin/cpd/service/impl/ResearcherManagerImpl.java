package edu.drexel.goodwin.cpd.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import edu.drexel.goodwin.cpd.domain.ProfilePicture;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.dto.ForgotPasswordRequest;
import edu.drexel.goodwin.cpd.dto.ResearcherDto;
import edu.drexel.goodwin.cpd.dto.ResearcherDtoAssembler;
import edu.drexel.goodwin.cpd.service.InputCleaner;
import edu.drexel.goodwin.cpd.service.MessageSender;
import edu.drexel.goodwin.cpd.service.ResearcherManager;

@Service
@Transactional
public class ResearcherManagerImpl implements ResearcherManager {

	private final Random random;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private InputCleaner inputCleaner;

	@Autowired
	private ReCaptchaImpl recaptcha;

	private final ResearcherDtoAssembler researcherDtoAssembler;

	@Value(value="${researcher.passwordReset}")
	private String resetPasswordMessage;

	@Value(value="${researcher.accountUpdated}")
	private String accountUpdatedMessage;

	@Value(value="${researcher.accountCreated}")
	private String accountCreatedMessage;

	@Autowired
	private String applicationUrl;


	public ResearcherManagerImpl() {
		random = new Random();
		researcherDtoAssembler = new ResearcherDtoAssembler();
	}

	private void checkAllUserInputsForInvalidHtml(ResearcherDto researcher, BindingResult result) {
		if (researcher.getFirstName() != null) {
			researcher.setFirstName(inputCleaner.getCleanHTML(researcher.getFirstName()));
		}
		if (researcher.getLastName() != null) {
			researcher.setLastName(inputCleaner.getCleanHTML(researcher.getLastName()));
		}
		if (researcher.getJobTitle() != null) {
			researcher.setJobTitle(inputCleaner.getCleanHTML(researcher.getJobTitle()));
		}
		if (researcher.getOrganization() != null) {
			researcher.setOrganization(inputCleaner.getCleanHTML(researcher.getOrganization()));
		}
		if (researcher.getBibliography() != null) {
			researcher.setBibliography(inputCleaner.getCleanHTML(researcher.getBibliography()));
		}
		if (researcher.getEmail() != null) {
			researcher.setEmail(inputCleaner.getCleanHTML(researcher.getEmail()));
		}
		if (researcher.getWebsite() != null) {
			researcher.setWebsite(inputCleaner.getCleanHTML(researcher.getWebsite()));
		}
		// don't check password because that is checked elsewhere
	}

	private boolean isPasswordValidAndMatchesConfirm(ResearcherDto researcher, BindingResult result) {
		boolean isPasswordOkay = true;
		if (researcher.getPassword() != null) {
			researcher.setPassword(inputCleaner.getCleanHTML(researcher.getPassword()));
		}

		if (researcher.getPassword().length() < 5) {
			// note: must check length here because once it is hashed/salted it will be much longer
			result.rejectValue("password", "", "Your password must be at least 5 characters long");
			isPasswordOkay = false;
		}

		if (!researcher.getPassword().equals(researcher.getPasswordConfirm())) {
			result.rejectValue("passwordConfirm", "", "The password confirmation does not match the password you entered.");
			isPasswordOkay = false;
		}

		return isPasswordOkay;
	}

	@Override
	public void create(ResearcherDto dto, BindingResult result) {
		checkAllUserInputsForInvalidHtml(dto, result);
		isPasswordValidAndMatchesConfirm(dto, result);

		ReCaptchaResponse response = recaptcha.checkAnswer("http://WHERETHEAPPLICATIONWILLBEHOSTED/cpd/researcher/create", (String) result.getRawFieldValue("recaptcha_challenge_field"), (String) result.getRawFieldValue("recaptcha_response_field"));
		if (!response.isValid()) {
			result.addError(new ObjectError("captcha", "The two words you entered did not match the picture. Please try again."));
		}

		if (Researcher.findResearchersByEmailEquals(dto.getEmail()).getResultList().size() != 0) {
			result.rejectValue("email", "researcher.error.email", "There is already an account under that email address. If you forgot your password use the forgot password link from the login page to get help.");
		}
		
		Researcher researcher = copyFormDataFromDtoToResearcher(dto, new Researcher());
		researcher.setPassword(dto.getPassword());

		Set<ConstraintViolation<Researcher>> violatedConstraints = Validation.buildDefaultValidatorFactory().getValidator().validate(researcher);
		for (ConstraintViolation<Researcher> constraint : violatedConstraints) {
			result.rejectValue(constraint.getPropertyPath().toString(), "researcher.error." + constraint.getPropertyPath(), constraint.getMessage());
		}

		if (!result.hasErrors()) {
			uploadProfilePictureIfEntered(dto, researcher);

			researcher.setSalt(getRandomSalt());
			researcher.setPassword(getHashedPassword(dto.getPassword(), researcher.getSalt()));
			researcher.persist();
			
			String message = generateMessage(accountCreatedMessage, new Object[] {researcher.getEmail(), applicationUrl});
			messageSender.sendMessage("Community Profile Database Account Created", researcher.getEmail(), message);
		}
	}

	private String getRandomSalt() {
		return "" + random.nextLong();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Override
	public void deleteResearcher(Long id) {
		Researcher researcher = Researcher.findResearcher(id);
		researcher.setDeleted(true);
		researcher.merge();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
	@Override
	public Researcher getResearcherForEditing(Long id) {
		return Researcher.findResearcher(id);
	}

	@Override
	public ResearcherDto getResearcherDtoForEditing(Long id) {
		return researcherDtoAssembler.assemble(getResearcherForEditing(id));
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or (#dto.id == principal.id)")
	@Override
	public void update(ResearcherDto dto, BindingResult result) {
		checkAllUserInputsForInvalidHtml(dto, result);

		Researcher researcher = copyFormDataFromDtoToResearcher(dto, getResearcherForEditing(dto.getId()));

		for (ConstraintViolation<Researcher> constraint : Validation.buildDefaultValidatorFactory().getValidator().validate(researcher)) {
			if ("password".equals(constraint.getPropertyPath().toString())) {
				continue; // We do not validate the password on updates
			}
			result.rejectValue(constraint.getPropertyPath().toString(), "researcher.error." + constraint.getPropertyPath(), constraint.getMessage());
		}

		if (!result.hasErrors()) {
			uploadProfilePictureIfEntered(dto, researcher);

			researcher.merge();
			String message = generateMessage(accountUpdatedMessage, new Object[] {applicationUrl});
			messageSender.sendMessage("Community Profile Database Account Updated", researcher.getEmail(), message);
		}
	}

	private void uploadProfilePictureIfEntered(ResearcherDto dto, Researcher researcher) {
		if (dto.getUploadedPicture() == null || dto.getUploadedPicture().isEmpty() || dto.getUploadedPicture().getSize() == 0) {
			return;
		}

		MultipartFile uploadedPicture = dto.getUploadedPicture();
		try {
			int maxSize = 200;

			BufferedImage uploadedRescaledImage = GraphicsUtilities.loadCompatibleImage(uploadedPicture.getInputStream());
			if (uploadedRescaledImage.getWidth() > maxSize || uploadedRescaledImage.getHeight() > maxSize) {
				uploadedRescaledImage = GraphicsUtilities.createThumbnail(uploadedRescaledImage, maxSize);
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(uploadedRescaledImage, "png", baos);

			ProfilePicture profilePicture = new ProfilePicture();
			profilePicture.setMimeType(uploadedPicture.getContentType());
			profilePicture.setBytes(baos.toByteArray());
			profilePicture.setWidth(uploadedRescaledImage.getWidth());
			profilePicture.setHeight(uploadedRescaledImage.getHeight());
			researcher.setProfilePicture(profilePicture);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Researcher copyFormDataFromDtoToResearcher(ResearcherDto dto, Researcher researcher) {
		researcher.setFirstName(dto.getFirstName());
		researcher.setLastName(dto.getLastName());
		researcher.setJobTitle(dto.getJobTitle());
		researcher.setOrganization(dto.getOrganization());
		researcher.setWebsite(dto.getWebsite());
		researcher.setEmail(dto.getEmail());
		researcher.setInterests(dto.getInterests());
		researcher.setSkills(dto.getSkills());
		researcher.setBibliography(dto.getBibliography());
		return researcher;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or #dto.Id == principal.Id")
	@Override
	public void updatePassword(ResearcherDto dto, BindingResult result) {
		if (isPasswordValidAndMatchesConfirm(dto, result)) {
			Researcher researcher = getResearcherForEditing(dto.getId());
			researcher.setPassword(getHashedPassword(dto.getPassword(), researcher.getSalt()));
			researcher.merge();
		}
	}

	private String getHashedPassword(String rawPassword, String salt) {
		return passwordEncoder.encodePassword(rawPassword, salt);
	}

	@Override
	public void resetPassword(ForgotPasswordRequest resetRequest, BindingResult result) {
		ReCaptchaResponse response = recaptcha.checkAnswer("http://WHERETHEAPPLICATIONWILLBEHOSTED/cpd/researcher/forgotPassword", (String) result.getRawFieldValue("recaptcha_challenge_field"), (String) result.getRawFieldValue("recaptcha_response_field"));
		if (!response.isValid()) {
			result.addError(new ObjectError("captcha", "The two words you entered did not match the picture. Please try again."));
			return;
		}

		Query queryResult = Researcher.findResearchersByEmailEquals(resetRequest.getEmailAddress());

		if (queryResult.getResultList().size() == 0) {
			// Email address does not exist in database
			return;
		}

		Researcher researcher = (Researcher) queryResult.getSingleResult();

		String temporaryPassword = RandomStringUtils.randomAlphanumeric(8);
		researcher.setPassword(getHashedPassword(temporaryPassword, researcher.getSalt()));

		String message = generateMessage(resetPasswordMessage, new Object[] {temporaryPassword, applicationUrl});
		messageSender.sendMessage("Community Profile Database Forgot Password", researcher.getEmail(), message);
	}

	@Override
	public ResearcherDto getCurrentlyLoggedInResearcherDto() {
		String currentlyLoggedInReseacher = ((Researcher) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		Researcher researcher = (Researcher) Researcher.findResearchersByEmailEquals(currentlyLoggedInReseacher).getSingleResult();

		return researcherDtoAssembler.assemble(researcher);
	}

	@Override
	public ResearcherDto getResearcherDto(Long id) {
		return researcherDtoAssembler.assemble(Researcher.findResearcher(id));
	}

	@Override
	public ProfilePicture getProfilePictureForThisResearcher(Long researcherId) {
		return Researcher.findResearcher(researcherId).getProfilePicture();
	}
	
	private String generateMessage(String messageBody, Object[] parms) {
		return String.format(messageBody, parms);
	}
}
