package edu.drexel.goodwin.cpd.dto;

public class ForgotPasswordRequest {

	String emailAddress;
    private String recaptcha_challenge_field;
    private String recaptcha_response_field;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getRecaptcha_challenge_field() {
		return recaptcha_challenge_field;
	}

	public void setRecaptcha_challenge_field(String recaptchaChallengeField) {
		recaptcha_challenge_field = recaptchaChallengeField;
	}

	public String getRecaptcha_response_field() {
		return recaptcha_response_field;
	}

	public void setRecaptcha_response_field(String recaptchaResponseField) {
		recaptcha_response_field = recaptchaResponseField;
	}
}
