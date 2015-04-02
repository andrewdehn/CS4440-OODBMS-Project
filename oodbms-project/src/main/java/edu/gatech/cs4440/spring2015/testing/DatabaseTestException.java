package edu.gatech.cs4440.spring2015.testing;

public class DatabaseTestException extends Exception {

	private static final long serialVersionUID = 1315667748425387332L;

	public DatabaseTestException() {
	}

	public DatabaseTestException(String message) {
		super(message);
	}

	public DatabaseTestException(Throwable cause) {
		super(cause);
	}

	public DatabaseTestException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseTestException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
