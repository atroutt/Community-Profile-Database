package edu.drexel.goodwin.cpd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.dto.ContactRequest;
import edu.drexel.goodwin.cpd.service.MessageSender;

@Service
public class MessageSenderImpl implements MessageSender {

    @Autowired
    private transient MailSender mailTemplate;

    @Value(value="${email.adminEmail}")
    private String[] adminEmailAddresses;

    @Value(value="${email.from}")
	private String fromAddress;

    @Override
    public void sendMessage(String subject, String mailTo, String message) {
    	SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    	simpleMailMessage.setFrom(fromAddress);
    	simpleMailMessage.setTo(mailTo);
    	simpleMailMessage.setSubject(subject);
    	simpleMailMessage.setText(message);
    	mailTemplate.send(simpleMailMessage);
    }

	@Override
	public void handleContactRequest(ContactRequest request, BindingResult result) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(request.getFromEmail());
		simpleMailMessage.setTo(adminEmailAddresses);
		simpleMailMessage.setSubject(request.getSubject());
		simpleMailMessage.setText(request.getEmailBody());
		mailTemplate.send(simpleMailMessage);
	}
}
