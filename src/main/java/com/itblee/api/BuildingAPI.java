package com.itblee.api;

import com.itblee.exception.NoContentException;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.request.BuildingSearchRequest;
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
	public List<BuildingSearchResponse> getBuilding(BuildingSearchRequest request) {
		List<BuildingSearchResponse> responses = buildingService.findAll(request);
		if (responses.isEmpty()) {
			throw new NoContentException();
		}
		return responses;
	}

	@GetMapping("/{buildingid}")
	public BuildingDTO getDetail(@PathVariable("buildingid") Long id) {
		BuildingDTO dto = buildingService.findOne(id);
		if (dto == null) {
			throw new NoContentException();
		}
		return dto;
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
