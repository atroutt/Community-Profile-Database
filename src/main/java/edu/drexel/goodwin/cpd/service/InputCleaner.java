package edu.drexel.goodwin.cpd.service;

import org.owasp.validator.html.CleanResults;

public interface InputCleaner {

	CleanResults scan(String firstName);

	String getCleanHTML(String website);

}
