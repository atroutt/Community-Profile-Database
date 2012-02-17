package edu.drexel.goodwin.cpd.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tanesha.recaptcha.ReCaptchaImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.drexel.goodwin.cpd.domain.Interest;
import edu.drexel.goodwin.cpd.domain.ProfilePicture;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.domain.Skill;
import edu.drexel.goodwin.cpd.dto.ForgotPasswordRequest;
import edu.drexel.goodwin.cpd.dto.ResearcherDto;
import edu.drexel.goodwin.cpd.service.ResearcherManager;

@RooWebScaffold(path = "researcher", automaticallyMaintainView = false, formBackingObject = ResearcherDto.class)
@RequestMapping("/researcher/**")
@Controller
public class ResearcherController {

	@Autowired
	private ResearcherManager researcherManager;

	@Autowired
	private ReCaptchaImpl recaptcha;

	private int BYTE_BUFFER_SIZE = 1024;

	@RequestMapping(value = "/researcher", method = RequestMethod.POST)
	public String create(@ModelAttribute("researcher") ResearcherDto researcher, BindingResult result, ModelMap modelMap) {
		if (researcher == null) {
			throw new IllegalArgumentException("A researcher is required");
		}

		researcherManager.create(researcher, result);

		if (result.hasErrors()) {
			modelMap.addAllAttributes(result.getAllErrors());
			modelMap.addAttribute("researcher", researcher);
			modelMap.addAttribute("interests", Interest.findAllInterests());
			modelMap.addAttribute("skills", Skill.findAllSkills());
			modelMap.addAttribute("captcha", recaptcha.createRecaptchaHtml(null, null));
			return "researcher/create";
		}

		modelMap.addAttribute("researcher", researcher);
		return "researcher/created";
	}

	@RequestMapping(value = "/researcher/form", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		modelMap.addAttribute("captcha", recaptcha.createRecaptchaHtml(null, null));
		modelMap.addAttribute("researcher", new ResearcherDto());
		modelMap.addAttribute("interests", Interest.findAllInterests());
		modelMap.addAttribute("skills", Skill.findAllSkills());
		return "researcher/create";
	}

	@RequestMapping(value = "/researcher/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		modelMap.addAttribute("researcher", researcherManager.getResearcherDto(id));
		return "researcher/show";
	}

	@RequestMapping(value = "/researcher", method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			modelMap.addAttribute("researchers", Researcher.findResearcherEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
			float nrOfPages = (float) Researcher.countResearchers() / sizeNo;
			modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			modelMap.addAttribute("researchers", Researcher.findAllResearchers());
		}
		return "researcher/list";
	}

	@RequestMapping(value = "find/BySkillsAndInterests", method = RequestMethod.GET)
	public String findResearchersBySkillsAndInterests(@RequestParam(value = "skills", required = false) Set<Skill> skills, @RequestParam(value = "interests", required = false) Set<Interest> interests, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, ModelMap modelMap) {
		if (interests == null && skills == null) {
			return "redirect:/researcher?page=1&size=10";
		}
		int sizeNo = size == null ? 10 : size.intValue();
		modelMap.addAttribute("researchers", Researcher.findResearchersBySkillsAndInterests(skills, interests, page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
		float nrOfPages = (float) Researcher.countResearchersBySkillsAndInterests(skills, interests) / sizeNo;
		modelMap.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		modelMap.addAttribute("skillSet", skills);
		modelMap.addAttribute("interestSet", interests);
		return "researcher/list";
	}

	@RequestMapping(value = "/researcher/{id}", method = RequestMethod.POST)
	public String update(@ModelAttribute("researcher") ResearcherDto researcher, BindingResult result, ModelMap modelMap) {
		if (researcher == null) {
			throw new IllegalArgumentException("A researcher is required");
		}

		researcherManager.update(researcher, result);

		if (result.hasErrors()) {
			modelMap.addAllAttributes(result.getAllErrors());
			modelMap.addAttribute("researcher", researcher);
			modelMap.addAttribute("interests", Interest.findAllInterests());
			modelMap.addAttribute("skills", Skill.findAllSkills());
			return "researcher/update";
		}

		return "redirect:/researcher/" + researcher.getId();
	}

	@RequestMapping(value = "/researcher/{id}/form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, ModelMap modelMap) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		modelMap.addAttribute("researcher", researcherManager.getResearcherDtoForEditing(id));
		modelMap.addAttribute("interests", Interest.findAllInterests());
		modelMap.addAttribute("skills", Skill.findAllSkills());

		return "researcher/update";
	}

	@RequestMapping(value = "/researcher/editMyProfile", method = RequestMethod.GET)
	public String updateForm(ModelMap modelMap) {
		ResearcherDto researcher = researcherManager.getCurrentlyLoggedInResearcherDto();
		return "redirect:/researcher/" + researcher.getId() + "/form";
	}

	@RequestMapping(value = "/researcher/editMyPassword", method = RequestMethod.PUT)
	public String updatePassword(@ModelAttribute("researcher") ResearcherDto researcher, BindingResult result, ModelMap modelMap) {
		if (researcher == null) {
			throw new IllegalArgumentException("A researcher is required");
		}

		researcherManager.updatePassword(researcher, result);

		if (result.hasErrors()) {
			modelMap.addAllAttributes(result.getAllErrors());
			modelMap.addAttribute("researcher", researcher);
			return "researcher/password";
		}

		return "redirect:/researcher/" + researcher.getId();
	}

	@RequestMapping(value = "/researcher/editMyPassword/form", method = RequestMethod.GET)
	public String updatePasswordForm(ModelMap modelMap) {
		ResearcherDto researcher = researcherManager.getCurrentlyLoggedInResearcherDto();
		modelMap.addAttribute("researcher", researcher);
		return "researcher/password";
	}

	@RequestMapping(value = "/researcher/forgotPassword", method = RequestMethod.PUT)
	public String resetPassword(@ModelAttribute("forgotPassword") ForgotPasswordRequest forgotPassword, BindingResult result, ModelMap modelMap) {
		if (forgotPassword == null) {
			throw new IllegalArgumentException("A forgotPassword form object is required");
		}

		researcherManager.resetPassword(forgotPassword, result);

		if (result.hasErrors()) {
			modelMap.addAllAttributes(result.getAllErrors());
			modelMap.addAttribute("forgotPassword", forgotPassword);
			return "researcher/forgotPassword/form";
		}

		return "forgotPassword/complete";
	}

	@RequestMapping(value = "/researcher/forgotPassword/form", method = RequestMethod.GET)
	public String resetPasswordForm(ModelMap modelMap) {
		modelMap.addAttribute("captcha", recaptcha.createRecaptchaHtml(null, null));
		modelMap.addAttribute("forgotPassword", new ForgotPasswordRequest());

		return "forgotPassword";
	}

	@RequestMapping(value = "/researcher/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}

		researcherManager.deleteResearcher(id);

		return "redirect:/researcher";
	}

	@RequestMapping(value = "find/BySkillsAndInterests/form", method = RequestMethod.GET)
	public String findResearchersBySkillsAndInterestsForm(ModelMap modelMap) {
		modelMap.addAttribute("skills", Skill.findAllSkills());
		modelMap.addAttribute("interests", Interest.findAllInterests());
		return "researcher/findResearchersBySkillsAndInterests";
	}

	@Transactional
	@RequestMapping(value = "/researcher/profilePicture/{id}", method = RequestMethod.GET)
	public void handleProfilePictureRequest(@PathVariable("id") Long id, HttpServletRequest req, HttpServletResponse resp) {
		if (id == null) {
			throw new IllegalArgumentException("An Identifier is required");
		}
		
		ProfilePicture researcherProfilePicture = researcherManager.getProfilePictureForThisResearcher(id);

		if (researcherProfilePicture == null) {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		resp.setContentType(researcherProfilePicture.getMimeType());

		InputStream in = null;
		OutputStream out = null;
		try {
			resp.setContentLength((int) researcherProfilePicture.getBytes().length);
			in = new ByteArrayInputStream(researcherProfilePicture.getBytes());
			out = resp.getOutputStream();

			byte[] buf = new byte[BYTE_BUFFER_SIZE];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
		} catch (Exception e) { // could be IOException or SqlException
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception ignore) {
				// ignore
			}
		}
	}
}
