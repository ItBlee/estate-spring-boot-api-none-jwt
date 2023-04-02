package com.itblee.api;

import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;

	@GetMapping
	public List<BuildingSearchResponse> getBuilding() {
		return buildingService.findAll();
	}
	
	@GetMapping("/{buildingid}")
	public BuildingDTO getDetail(@PathVariable("buildingid") Long id) {
		return buildingService.findOne(id);
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
