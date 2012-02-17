package edu.drexel.goodwin.cpd.service;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Random;

import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.domain.ResearcherDataOnDemand;
import edu.drexel.goodwin.cpd.dto.ForgotPasswordRequest;
import edu.drexel.goodwin.cpd.dto.ResearcherDto;
import edu.drexel.goodwin.cpd.service.FakeMailSender;
import edu.drexel.goodwin.cpd.service.ResearcherManager;
import edu.drexel.goodwin.cpd.service.impl.ResearcherManagerImpl;

@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext-security.xml" })
@Configurable
@RooIntegrationTest(entity = ResearcherManagerImpl.class)
@Transactional
public class ResearcherManagerTest {

	@Autowired
	private ResearcherManager researcherManager;

	@Autowired
	private ResearcherDataOnDemand dod;

	@Autowired
	private FakeMailSender mailSender;
    
	@Autowired
	private PasswordEncoder passwordEncoder;

	private final Random random = new Random();

	@Test
	@Transactional
	public void testCreateShouldHaveErrors() {
		ResearcherDto dto = new ResearcherDto();
		int rand = random.nextInt();
		dto.setBibliography("biblio" + rand);
		String email = "audrey+TEST" + rand + "@mathforum.org";
		dto.setEmail(email);
		dto.setFirstName("firstName" + rand);
		dto.setLastName(null);
		dto.setJobTitle("jobtitle" + rand);
		dto.setOrganization("organization" + rand);
		dto.setPassword("password" + rand);
		dto.setPasswordConfirm("passwordfail" + rand);
		dto.setWebsite("http://mathforum.org" + rand);
		dto.setRecaptcha_challenge_field("test");
		dto.setRecaptcha_response_field("testfail");
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		researcherManager.create(dto, bindingResult);

		assertTrue("there should have been errors", bindingResult.hasErrors());
		assertEquals("there should have been 3 errors", 3, bindingResult.getAllErrors().size());
		assertEquals(0, Researcher.findResearchersByEmailEquals(email).getResultList().size());
	}

	@Test
	@Transactional
	public void testCreateHappyPath() {
		ResearcherDto dto = new ResearcherDto();
		int rand = random.nextInt();
		dto.setBibliography("biblio" + rand);
		String email = "audrey+TEST" + rand + "@mathforum.org";
		dto.setEmail(email);
		dto.setFirstName("firstName" + rand);
		dto.setLastName("lastname" + rand);
		dto.setJobTitle("jobtitle" + rand);
		dto.setOrganization("organization" + rand);
		dto.setPassword("password" + rand);
		dto.setPasswordConfirm("password" + rand);
		dto.setWebsite("http://mathforum.org" + rand);
		dto.setRecaptcha_challenge_field("test");
		dto.setRecaptcha_response_field("test");
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		researcherManager.create(dto, bindingResult);

		assertFalse("there should NOT have been errors", bindingResult.hasErrors());
		assertEquals(1, Researcher.findResearchersByEmailEquals(email).getResultList().size());
	}

	@Test
	@Transactional
	public void testCreateFailsIfEmailAddressAlreadyExists() {
		Researcher randomResearcher = dod.getRandomResearcher();

		ResearcherDto dto = new ResearcherDto();
		int rand = random.nextInt();
		dto.setBibliography("biblio" + rand);
		String email = randomResearcher.getEmail();
		dto.setEmail(email);
		dto.setFirstName("firstName" + rand);
		dto.setLastName("lastname" + rand);
		dto.setJobTitle("jobtitle" + rand);
		dto.setOrganization("organization" + rand);
		dto.setPassword("password" + rand);
		dto.setPasswordConfirm("password" + rand);
		dto.setWebsite("http://mathforum.org" + rand);
		dto.setRecaptcha_challenge_field("test");
		dto.setRecaptcha_response_field("test");
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		researcherManager.create(dto, bindingResult);

		assertTrue("there should have been errors", bindingResult.hasErrors());
		// there is only one researcher under this address still
		assertEquals(1, Researcher.findResearchersByEmailEquals(email).getResultList().size());
	}

	@Test
	@Transactional
	@ExpectedException(AccessDeniedException.class)
	public void testCannotDeleteResearcherIfNotAdmin() {

		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedIn(researcher);

		assertFalse("researcher should start out not deleted", researcher.isDeleted());
		researcherManager.deleteResearcher(researcher.getId());
		assertFalse("researcher should still not be deleted", researcher.isDeleted());
	}

	@Test
	@Transactional
	public void testDeleteResearcherAsAdmin() {

		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedInAndIsAdmin(researcher);

		assertFalse("researcher should start out not deleted", researcher.isDeleted());
		researcherManager.deleteResearcher(researcher.getId());
		assertTrue("researcher should have been flagged as deleted", researcher.isDeleted());
	}

	@Test
	@Transactional
	public void testUpdate() {
		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedIn(researcher);

		ResearcherDto dto = new ResearcherDto();
		int rand = random.nextInt();
		dto.setBibliography(researcher.getBibliography() + rand);
		String email = "audrey+TEST" + rand + "@mathforum.org";
		dto.setEmail(email);
		dto.setFirstName("firstName" + rand);
		dto.setLastName("lastname" + rand);
		dto.setJobTitle("jobtitle" + rand);
		dto.setOrganization("organization" + rand);
		dto.setPassword("password" + rand);
		dto.setPasswordConfirm("password" + rand);
		dto.setWebsite("http://mathforum.org" + rand);
		dto.setRecaptcha_challenge_field("test");
		dto.setRecaptcha_response_field("test");
		dto.setId(researcher.getId());
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		String oldPassword = researcher.getPassword();
		researcherManager.update(dto, bindingResult);

		assertFalse("there should NOT have been errors", bindingResult.hasErrors());
		Query queryResult = Researcher.findResearchersByEmailEquals(email);
		assertEquals(1, queryResult.getResultList().size());
		assertEquals("password should not have changed", oldPassword, ((Researcher) queryResult.getSingleResult()).getPassword());
		
		SimpleMailMessage sentMessage = mailSender.getMostRecentlySentMessage();
		assertNotNull(sentMessage);
	}

	@Test
	@Transactional
	public void testUpdatePasswordFailsIfConfirmDoesNotMatch() {
		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedIn(researcher);

		ResearcherDto dto = new ResearcherDto();
		String newPassword = "confirm";
		dto.setId(researcher.getId());
		dto.setPassword(newPassword);
		dto.setPasswordConfirm(newPassword + "FAIL");
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		String oldPassword = researcher.getPassword();
		researcherManager.updatePassword(dto, bindingResult);

		assertTrue("there should have been errors", bindingResult.hasErrors());
		Query queryResult = Researcher.findResearchersByEmailEquals(researcher.getEmail());
		assertEquals(1, queryResult.getResultList().size());
		assertEquals("password should NOT have changed", oldPassword, ((Researcher) queryResult.getSingleResult()).getPassword());
	}

	@Test
	@Transactional
	public void testUpdatePassword() {
		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedIn(researcher);

		ResearcherDto dto = new ResearcherDto();
		String newPassword = "confirm";
		dto.setId(researcher.getId());
		dto.setPassword(newPassword);
		dto.setPasswordConfirm(newPassword);
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		researcherManager.updatePassword(dto, bindingResult);

		assertFalse("there should NOT have been errors", bindingResult.hasErrors());
		Query queryResult = Researcher.findResearchersByEmailEquals(researcher.getEmail());
		assertEquals(1, queryResult.getResultList().size());

		String encodedNewPassword = passwordEncoder.encodePassword(newPassword, researcher.getSalt());
		assertEquals("password should have changed", encodedNewPassword, ((Researcher) queryResult.getSingleResult()).getPassword());
	}

	@Test
	@Transactional
	public void testCannotUpdatePasswordIfPasswordTooShort() {
		Researcher researcher = dod.getRandomResearcher();

		pretendThisResearcherIsLoggedIn(researcher);

		ResearcherDto dto = new ResearcherDto();
		String newPassword = "1234";
		dto.setId(researcher.getId());
		dto.setPassword(newPassword);
		dto.setPasswordConfirm(newPassword);
		BindingResult bindingResult = new BeanPropertyBindingResult(dto, "researcher");

		String oldPassword = researcher.getPassword();

		researcherManager.updatePassword(dto, bindingResult);

		assertTrue("there should have been errors", bindingResult.hasErrors());
		Query queryResult = Researcher.findResearchersByEmailEquals(researcher.getEmail());
		assertEquals(1, queryResult.getResultList().size());
		assertEquals("password should NOT have changed", oldPassword, ((Researcher) queryResult.getSingleResult()).getPassword());
	}

	@Test
	@Transactional
	public void testCanResetPasswordIfEmailAddressExists() throws Exception {
		Researcher researcher = dod.getRandomResearcher();

		ForgotPasswordRequest resetRequest = new ForgotPasswordRequest();
		resetRequest.setRecaptcha_challenge_field("test");
		resetRequest.setRecaptcha_response_field("test");
		resetRequest.setEmailAddress(researcher.getEmail());
		BindingResult bindingResult = new BeanPropertyBindingResult(resetRequest, "resetRequest");

		String oldPassword = researcher.getPassword();

		researcherManager.resetPassword(resetRequest, bindingResult);

		Query queryResult = Researcher.findResearchersByEmailEquals(researcher.getEmail());
		assertEquals(1, queryResult.getResultList().size());
		Researcher researcherAgain = (Researcher) queryResult.getSingleResult();
		assertTrue("password should have changed", !oldPassword.equals(researcherAgain.getPassword()));
	}

	@Test
	@Transactional
	public void testResetPasswordSaltsThePassword() throws Exception {
		Researcher researcher = dod.getRandomResearcher();

		ForgotPasswordRequest resetRequest = new ForgotPasswordRequest();
		resetRequest.setRecaptcha_challenge_field("test");
		resetRequest.setRecaptcha_response_field("test");
		resetRequest.setEmailAddress(researcher.getEmail());
		BindingResult bindingResult = new BeanPropertyBindingResult(resetRequest, "resetRequest");

		String oldPassword = researcher.getPassword();

		researcherManager.resetPassword(resetRequest, bindingResult);

		Query queryResult = Researcher.findResearchersByEmailEquals(researcher.getEmail());
		assertEquals(1, queryResult.getResultList().size());
		Researcher researcherAgain = (Researcher) queryResult.getSingleResult();
		assertTrue("password should have changed", !oldPassword.equals(researcherAgain.getPassword()));
	}
	
	private void pretendThisResearcherIsLoggedIn(Researcher researcher) {
		addResearcherToSecurityContext(researcher, false);
	}

	private void pretendThisResearcherIsLoggedInAndIsAdmin(Researcher researcher) {
		addResearcherToSecurityContext(researcher, true);
	}

	private void addResearcherToSecurityContext(Researcher researcher, boolean admin) {
		Collection<GrantedAuthority> authorities = researcher.getAuthorities();
		if (admin) {
			authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}
		TestingAuthenticationToken authentication = new TestingAuthenticationToken(researcher, researcher.getPassword(), authorities.toArray(new GrantedAuthority[authorities.size()]));
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(authentication);
	}

}
