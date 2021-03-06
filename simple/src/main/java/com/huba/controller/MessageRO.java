package com.huba.controller;

/**
 * A middle object between the database model and UI/other services.
 * 
 * @author Huba Albu {ha@viabill.com}
 **/
public class MessageRO {

	private String message;

	public MessageRO() {
	}

	public MessageRO(String message) {
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
