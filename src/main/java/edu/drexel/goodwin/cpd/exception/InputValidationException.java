package edu.drexel.goodwin.cpd.exception;

public class InputValidationException extends RuntimeException {

	private static final long serialVersionUID = 6243192389838643809L;
	
	public InputValidationException(String message) {
		super(message);
	}

	public InputValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
