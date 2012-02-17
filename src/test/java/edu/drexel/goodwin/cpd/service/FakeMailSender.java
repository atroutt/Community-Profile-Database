package edu.drexel.goodwin.cpd.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class FakeMailSender implements MailSender {

	private SimpleMailMessage mostRecentlySendMessage;
	
	@Override
	public void send(SimpleMailMessage simpleMessage) throws MailException {
		mostRecentlySendMessage = simpleMessage;
	}

	@Override
	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		if (simpleMessages.length > 0) {
			mostRecentlySendMessage = simpleMessages[simpleMessages.length - 1];
		}
	}

	public SimpleMailMessage getMostRecentlySentMessage() {
		return mostRecentlySendMessage;
	}

}
