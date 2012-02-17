package edu.drexel.goodwin.cpd.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import edu.drexel.goodwin.cpd.dto.ContactRequest;
import edu.drexel.goodwin.cpd.service.MessageSender;
import edu.drexel.goodwin.cpd.service.impl.MessageSenderImpl;

@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext.xml", "classpath:/META-INF/spring/test-applicationContext-security.xml" })
@Configurable
@RooIntegrationTest(entity = MessageSenderImpl.class)
@Transactional
public class MessageSenderTest {

	@Autowired
	private FakeMailSender mailSender;

	@Autowired
	private MessageSender messageSender;

	@Test
	public void testSendMessage() {
		String subject = "test subject";
		String mailTo = "to address";
		String message = "message text";
		messageSender.sendMessage(subject, mailTo, message);

		SimpleMailMessage sentMessage = mailSender.getMostRecentlySentMessage();
		assertEquals(subject, sentMessage.getSubject());
		assertEquals(mailTo, sentMessage.getTo()[0]);
		assertEquals(message, sentMessage.getText());
	}

	@Test
	public void testHandleContactRequest() {
		String subject = "test subject";
		ContactRequest contact = new ContactRequest();
		contact.setSubject(subject);
		String message = "message text";
		contact.setUserMessage(message);
		String fromEmail = "userEmail";
		contact.setFromEmail(fromEmail);
		String loggedInUserInfo = "loggedInUserInfo";
		contact.setLoggedInUserInfo(loggedInUserInfo);
		BindingResult result = new BeanPropertyBindingResult(contact, "contactRequest");
		
		messageSender.handleContactRequest(contact, result);

		SimpleMailMessage sentMessage = mailSender.getMostRecentlySentMessage();
		assertEquals(subject, sentMessage.getSubject());
		assertEquals(fromEmail, sentMessage.getFrom());
		assertTrue(sentMessage.getText().contains(message));
	}
	
	@Test
	public void testContactRequestSendingDoesNotChangeFromAddressForSendMessage() throws Exception {
		// First, send contact request
		String subject = "test subject";
		ContactRequest contact = new ContactRequest();
		contact.setSubject(subject);
		String message = "message text";
		contact.setUserMessage(message);
		String contactRequestFromEmail = "userEmail";
		contact.setFromEmail(contactRequestFromEmail);
		String loggedInUserInfo = "loggedInUserInfo";
		contact.setLoggedInUserInfo(loggedInUserInfo);
		BindingResult result = new BeanPropertyBindingResult(contact, "contactRequest");
		
		messageSender.handleContactRequest(contact, result);
		
		SimpleMailMessage sentContactRequestMessage = mailSender.getMostRecentlySentMessage();
		assertEquals(subject, sentContactRequestMessage.getSubject());
		assertEquals(contactRequestFromEmail, sentContactRequestMessage.getFrom());
		assertTrue(sentContactRequestMessage.getText().contains(message));
		
		// Now send message, as an account updated email would be sent
		messageSender.sendMessage("test subject", "to address", "message text");

		// Make sure the contact request from email is not still there as the from address
		SimpleMailMessage sentMessage = mailSender.getMostRecentlySentMessage();
		assertNotNull(sentMessage.getFrom());
		assertFalse("Email from address is contaminated from sending a contact request!", contactRequestFromEmail.equals(sentMessage.getFrom()));
	}
}
