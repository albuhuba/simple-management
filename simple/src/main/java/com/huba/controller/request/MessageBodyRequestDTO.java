package com.huba.controller.request;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A simple requestDTO which contains a body (message). The message cannot be empty, this is the contract betweend the endpoint and clients.
 * 
 * @author Huba Albu {ha@viabill.com}
 *
 **/
public class MessageBodyRequestDTO {

	@NotEmpty
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
