package com.sst.generico10.utils.exceptions;

public class AppConfigurationException extends Exception{

	private static final long serialVersionUID = 1L;

	public AppConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AppConfigurationException(String message) {
		super(message);
	}
}
