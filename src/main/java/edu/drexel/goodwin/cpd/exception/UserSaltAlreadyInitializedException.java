package edu.drexel.goodwin.cpd.exception;

public class UserSaltAlreadyInitializedException extends RuntimeException {

	private static final long serialVersionUID = -4363281974948228385L;

	public UserSaltAlreadyInitializedException(String message) {
		super(message);
	}
	
}
