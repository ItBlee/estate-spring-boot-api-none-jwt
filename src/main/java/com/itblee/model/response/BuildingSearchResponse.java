package com.itblee.model.response;

import java.util.List;

import com.itblee.model.dto.BuildingDTO;

public class BuildingSearchResponse {
	private String message;
	private List<BuildingDTO> results;
	
	public BuildingSearchResponse() {
		super();
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public List<BuildingDTO> getResults() {
		return results;
	}
	
	public void setResults(List<BuildingDTO> results) {
		this.results = results;
	}
	
	
}
