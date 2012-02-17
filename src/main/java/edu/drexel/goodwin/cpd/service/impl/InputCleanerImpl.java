package edu.drexel.goodwin.cpd.service.impl;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import edu.drexel.goodwin.cpd.exception.InputValidationException;
import edu.drexel.goodwin.cpd.service.InputCleaner;

@Service
public class InputCleanerImpl implements InputCleaner {

	private final String LIMITED_HTML_POLICY_FILE_LOCATION = "/META-INF/limitedHtmlPolicy.xml";
	private final AntiSamy antiSamy;
	
	public InputCleanerImpl() {
		Policy policy;
		try {
			ClassPathResource resource = new ClassPathResource(LIMITED_HTML_POLICY_FILE_LOCATION);
			policy = Policy.getInstance(resource.getInputStream());
		} catch (Exception e) {
			throw new InputValidationException("Cannot create required AntiSamy policy object. Hint: make sure a policy file is located at " + LIMITED_HTML_POLICY_FILE_LOCATION, e);
		}
		antiSamy = new AntiSamy(policy);
	}

	@Override
	public CleanResults scan(String taintedInput) {
		try {
			return antiSamy.scan(taintedInput);
		} catch (PolicyException pe) {
			pe.printStackTrace();
			throw new InputValidationException("There was an error with the AntiSamy policy for validating user input");
		} catch (ScanException se) {
			se.printStackTrace();
			throw new InputValidationException("There was an error validating your submission. Please check your input and try again.");
		}
	}

	@Override
	public String getCleanHTML(String taintedInput) {
		return scan(taintedInput).getCleanHTML();
	}
	
}
