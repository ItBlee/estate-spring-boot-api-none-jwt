package com.itblee.api;

import com.itblee.exception.NoContentException;
import com.itblee.model.BuildingModel;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<BuildingSearchResponse> getBuilding(
            @RequestParam(required = false) Map<String, Object> params,
            @RequestParam(required = false) Object[] types) {
        if (types != null)
            params.put("types", types);
        List<BuildingSearchResponse> responses = buildingService.findByCondition(params);
        if (responses.isEmpty())
            throw new NoContentException("No buildings found.");
        return responses;
    }

	/*@GetMapping
	public List<BuildingSearchResponse> getBuilding(BuildingSearchRequest request) {
		List<BuildingSearchResponse> responses = buildingService.findAll(request);
		if (responses.isEmpty())
			throw new NoContentException();
		return responses;
	}*/

    @GetMapping("/{buildingid}")
    public BuildingModel getDetail(@PathVariable("buildingid") Long id) {
        Optional<BuildingModel> building = buildingService.findOne(id);
        return building.orElseThrow(() -> new NoContentException("No building found."));
    }

    @PostMapping
    public Object createBuilding(BuildingModel building) {
        throw new UnsupportedOperationException();
    }

    @PutMapping
    public Object updateBuilding(BuildingModel building) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping
    public void deleteBuilding(Long[] ids) {
        throw new UnsupportedOperationException();
    }
}
