package com.api.gamescore.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 8922489579381100836L;

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
