package edu.drexel.goodwin.cpd.service;

import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.dto.ContactRequest;

public interface MessageSender {

	public void sendMessage(String subject, String mailTo, String message);
	
	public void handleContactRequest(ContactRequest contact, BindingResult result);

}
