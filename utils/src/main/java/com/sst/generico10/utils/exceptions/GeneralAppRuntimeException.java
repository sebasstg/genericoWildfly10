package com.sst.generico10.utils.exceptions;

public class GeneralAppRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public GeneralAppRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralAppRuntimeException(String message) {
		super(message);
	}
}
