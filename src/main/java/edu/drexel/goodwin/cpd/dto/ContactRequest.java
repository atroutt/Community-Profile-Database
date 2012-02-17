package edu.drexel.goodwin.cpd.dto;

public class ContactRequest {

	private String subject;

	private String fromEmail;

	private String userMessage;

	private String loggedInUserInfo;

	public String getLoggedInUserInfo() {
		return loggedInUserInfo;
	}

	public void setLoggedInUserInfo(String loggedInUserInfo) {
		this.loggedInUserInfo = loggedInUserInfo;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public String getEmailBody() {
		return "From the Community Profile Database Contact Us Form:\n\n\n" 
				+ userMessage 
				+ "\n\n\n Logged-in user info: " 
				+ loggedInUserInfo;
	}
}
