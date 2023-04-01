package com.itblee.api;

import java.util.List;

import com.itblee.converter.BuildingMapper;
import com.itblee.filter.BuildingFilter;
import com.itblee.model.response.BuildingSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itblee.model.dto.BuildingDTO;
import com.itblee.service.BuildingService;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@Autowired
	private BuildingMapper buildingConverter;

	@GetMapping
	public List<BuildingSearchResponse> getBuilding() {
		List<BuildingFilter> list = buildingService.findAll();
		return buildingConverter.mapToResponse(list);
	}
	
	@GetMapping("/{buildingid}")
	public BuildingDTO getDetail(@PathVariable("buildingid") Long id) {
		BuildingFilter buildingFilter = buildingService.findOne(id);
		return buildingConverter.mapToDto(buildingFilter);
	}
	
	@PostMapping
	public Object createBuilding(@RequestBody BuildingDTO building) {
		return null;
	}
	
	@PutMapping
	public Object updateBuilding(@RequestBody BuildingDTO building) {
		return null;
	}
	
	@DeleteMapping
	public void deleteBuilding(@RequestBody Long[] ids) {
		System.out.println("");
	}
}
