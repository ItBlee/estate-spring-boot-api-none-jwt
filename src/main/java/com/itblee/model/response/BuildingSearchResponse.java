package com.itblee.model.response;

import com.itblee.model.dto.AssignUserDTO;
import com.itblee.model.dto.RentAreaDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BuildingSearchResponse {
	private Long id;
	private String name;
	private String address;
	private String managerName;
	private String managerPhone;
	private Integer floorArea;
	private Integer rentPrice;
	private List<RentAreaDTO> rentAreas = new ArrayList<>();
	private Double brokerageFee;
	private List<AssignUserDTO> assignUsers = new ArrayList<>();
	private Date createdDate;

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

	public List<AssignUserDTO> getAssignUsers() {
		return assignUsers;
	}

	public void setAssignUsers(List<AssignUserDTO> assignUsers) {
		this.assignUsers = assignUsers;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public Integer getFloorArea() {
		return floorArea;
	}

	public void setFloorArea(Integer floorArea) {
		this.floorArea = floorArea;
	}

	public List<RentAreaDTO> getRentAreas() {
		return rentAreas;
	}

	public void setRentAreas(List<RentAreaDTO> rentAreas) {
		this.rentAreas = rentAreas;
	}

	public Integer getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}

	public Double getBrokerageFee() {
		return brokerageFee;
	}

	public void setBrokerageFee(Double brokerageFee) {
		this.brokerageFee = brokerageFee;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
