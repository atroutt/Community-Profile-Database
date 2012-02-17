package edu.drexel.goodwin.cpd.web;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.drexel.goodwin.cpd.domain.ContactUsSubject;
import edu.drexel.goodwin.cpd.domain.Researcher;
import edu.drexel.goodwin.cpd.dto.ContactRequest;
import edu.drexel.goodwin.cpd.service.InputCleaner;
import edu.drexel.goodwin.cpd.service.MessageSender;

@RequestMapping("/contact/**")
@Controller
public class ContactUsController {

	@Autowired
	private MessageSender messageSender;

	@Autowired
	private InputCleaner inputCleaner;

	@RequestMapping(value = "/contact", method = RequestMethod.POST)
	public String create(@ModelAttribute("contact") ContactRequest contact, BindingResult result, ModelMap modelMap) {
		if (contact == null) {
			throw new IllegalArgumentException("There was no contact information provided");
		}

		contact.setUserMessage(inputCleaner.getCleanHTML(contact.getUserMessage()));
		contact.setFromEmail(inputCleaner.getCleanHTML(contact.getFromEmail()));

		if (!contact.getFromEmail().matches(".+@.+\\.[a-z]+")) {
			result.rejectValue("fromEmail", "", "Please enter a valid email address");
			modelMap.addAllAttributes(result.getAllErrors());
			modelMap.addAttribute("contact", contact);
			modelMap.addAttribute("subjects", ContactUsSubject.findAllContactUsSubjects());
			return "contact/form";
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (!"anonymousUser".equals(authentication.getName())) {
			String currentlyLoggedInReseacher = ((Researcher) authentication.getPrincipal()).getUsername();
			contact.setLoggedInUserInfo(currentlyLoggedInReseacher);
		}

		messageSender.handleContactRequest(contact, result);

		return "contact/complete";
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String createForm(ModelMap modelMap) {
		ContactRequest contact = new ContactRequest();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority auth : authorities) {
			if ("ROLE_ANONYMOUS".equals(auth.getAuthority())) {
				
			}
		}
		if(!"anonymousUser".equals(authentication.getName())) {
			String currentlyLoggedInReseacher = ((Researcher) authentication.getPrincipal()).getUsername();
			contact.setFromEmail(currentlyLoggedInReseacher);
		}

		modelMap.addAttribute("contact", contact);
		modelMap.addAttribute("subjects", ContactUsSubject.findAllContactUsSubjects());
		return "contact/form";
	}

}
