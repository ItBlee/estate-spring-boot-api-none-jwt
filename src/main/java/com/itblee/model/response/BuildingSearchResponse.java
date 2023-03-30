package com.itblee.model.response;

import java.io.Serializable;

public class BuildingSearchResponse implements Serializable {
	private Long id;
	private String name;
	private String address;

	public BuildingSearchResponse() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
