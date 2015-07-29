package com.tempest.sentinelapi;

import java.util.List;

public class DomainException extends RuntimeException {
	
	private List<Error> errors;

	public DomainException(List<Error> errors) {
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

}
