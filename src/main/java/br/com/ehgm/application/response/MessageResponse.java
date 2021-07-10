package br.com.ehgm.application.response;

import java.sql.Timestamp;

public class MessageResponse {

	private String message;
	private String exception;
	private String timestamp;

	public MessageResponse(Exception ex) {
		this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
		this.exception = ex.getClass().getSimpleName();
		this.message = ex.getMessage();
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getException() {
		return exception;
	}
}
