package org.cybercrowd.mvp.util;

public class RequestResult {

	private int statusCode;

	private String Result;

	private String message;

	public RequestResult() {
	}

	public RequestResult(int statusCode, String result, String message) {
		this.statusCode = statusCode;
		Result = result;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getResult() {
		return Result;
	}

	public void setResult(String result) {
		Result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
