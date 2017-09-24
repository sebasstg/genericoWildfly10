package com.sst.generico10.utils.exceptions;

public class UsuarioException extends GeneralAppException{

	private static final long serialVersionUID = 1L;

	public UsuarioException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioException(String message) {
		super(message);
	}
}
