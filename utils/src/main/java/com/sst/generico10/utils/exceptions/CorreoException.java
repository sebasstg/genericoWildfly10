package com.sst.generico10.utils.exceptions;

public class CorreoException extends GeneralAppException{

	private static final long serialVersionUID = 1L;

	public CorreoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CorreoException(String message) {
		super(message);
	}
}
