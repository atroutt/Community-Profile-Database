package edu.drexel.goodwin.cpd.service;

import java.util.Properties;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

public class MockCaptcha extends ReCaptchaImpl {

	/**
	 * Returns valid response if challenge equals response using String equals
	 */
	@Override
	public ReCaptchaResponse checkAnswer(String remoteAddr, String challenge, String response) {
		return new MockCaptchaResponse(challenge.equals(response));
	}

	@Override
	public String createRecaptchaHtml(String errorMessage, Properties options) {
		return null;
	}

	@Override
	public String createRecaptchaHtml(String errorMessage, String theme, Integer tabindex) {
		return null;
	}

	public class MockCaptchaResponse extends ReCaptchaResponse {

		public MockCaptchaResponse(boolean valid) {
			super(valid, "");
		}
		
		protected MockCaptchaResponse(boolean valid, String errorMessage) {
			super(valid, errorMessage);
		}
		
	}
}
