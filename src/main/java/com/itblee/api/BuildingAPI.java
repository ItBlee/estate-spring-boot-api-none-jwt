package com.itblee.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itblee.bean.BuildingBean;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {

	@GetMapping
	public Object getBuilding(@RequestParam("id") Long id,
								@RequestParam("name") String name,
								@RequestParam("floorarea") Integer floorArea) {
		return null;
	}
	
	@GetMapping("/{buildingid}")
	public Object getDetail(@PathVariable("buildingid") Long id) {
		return null;
	}
	
	@PostMapping
	public Object createBuilding(@RequestBody BuildingBean building) {
		return null;
	}
	
	@PutMapping
	public Object updateBuilding(@RequestBody BuildingBean building) {
		return null;
	}
	
	@DeleteMapping
	public void deleteBuilding(@RequestBody Long[] ids) {
		System.out.println("");
	}
}
