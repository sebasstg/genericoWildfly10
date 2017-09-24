package com.sst.generico10.utils.exceptions;

public class GeneralAppException extends Exception{

	private static final long serialVersionUID = 1L;

	public GeneralAppException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralAppException(String message) {
		super(message);
	}
}
