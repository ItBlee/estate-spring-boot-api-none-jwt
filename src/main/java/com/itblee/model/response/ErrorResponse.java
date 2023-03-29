package com.itblee.model.response;

import java.util.ArrayList;

public class ErrorResponseBean {
	private String error;
	private ArrayList<String> details;
	
	public ErrorResponseBean() {
		super();
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ArrayList<String> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<String> details) {
		this.details = details;
	}
	
}
