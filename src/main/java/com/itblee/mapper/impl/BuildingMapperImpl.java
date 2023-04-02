package com.itblee.mapper.impl;

import com.itblee.entity.*;
import com.itblee.mapper.BuildingMapper;
import com.itblee.mapper.ModelMapper;
import com.itblee.model.dto.*;
import com.itblee.model.response.BuildingSearchResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BuildingMapperImpl implements BuildingMapper {

    @Override
    public List<Building> processResultSet(ResultSet resultSet) {
        Map<Long, Building> map = new HashMap<>();
        try {
            while (resultSet.next()) {
                Long buildingID = resultSet.getLong("id");
                Building building = map.get(buildingID);
                if (building == null) {
                    building = mapRow(resultSet);
                }
                building = mergeRow(resultSet, building);
                /*if map or merge row fail return null result(building)
                 * ignore null result*/
                if (building != null) {
                    map.put(building.getId(), building);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public Building mapRow(ResultSet resultSet) {
        try {
            Building building = new Building();
            building.setId(resultSet.getLong("id"));
            building.setName(resultSet.getString("name"));
            building.setStreet(resultSet.getString("street"));
            building.setWard(resultSet.getString("ward"));
            building.setDistrictID(resultSet.getLong("districtid"));
            building.setStructure(resultSet.getString("structure"));
            building.setNumberOfBasement(resultSet.getInt("numberofbasement"));
            building.setFloorArea(resultSet.getInt("floorarea"));
            building.setDirection(resultSet.getString("direction"));
            building.setLevel(resultSet.getString("level"));
            building.setRentPrice(resultSet.getInt("rentprice"));
            building.setRentPriceDescription(resultSet.getString("rentpricedescription"));
            building.setServiceFee(resultSet.getString("servicefee"));
            building.setCarFee(resultSet.getString("carfee"));
            building.setMotorbikeFee(resultSet.getString("motorbikefee"));
            building.setOvertimeFee(resultSet.getString("overtimefee"));
            building.setWaterFee(resultSet.getString("waterfee"));
            building.setElectricityFee(resultSet.getString("electricityfee"));
            building.setDeposit(resultSet.getString("deposit"));
            building.setPayment(resultSet.getString("payment"));
            building.setRentTime(resultSet.getString("renttime"));
            building.setDecorationTime(resultSet.getString("decorationtime"));
            building.setBrokerageFee(resultSet.getDouble("brokeragefee"));
            building.setManagerName(resultSet.getString("managername"));
            building.setManagerPhone(resultSet.getString("managerphone"));
            building.setNote(resultSet.getString("note"));
            building.setLinkOfBuilding(resultSet.getString("linkofbuilding"));
            building.setMap(resultSet.getString("map"));
            building.setImage(resultSet.getString("image"));
            building.setCreatedDate(resultSet.getTimestamp("createddate"));
            building.setCreatedBy(resultSet.getString("createdby"));
            building.setModifiedDate(resultSet.getTimestamp("modifieddate"));
            building.setModifiedBy(resultSet.getString("modifiedby"));

            District district = new District();
            district.setId(resultSet.getLong("districtid"));
            if (district.getId() != null) {
                district.setName(resultSet.getString("districtName"));
                district.setCode(resultSet.getString("districtCode"));
                building.setDistrict(district);
            }
            return building;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Building mergeRow(ResultSet resultSet, Building building) {
        try {
            RentArea rentArea = new RentArea();
            rentArea.setId(resultSet.getLong("rentareaID"));
            if (rentArea.getId() != null) {
                rentArea.setValue(resultSet.getInt("rentareaValue"));
                rentArea.setBuildingID(building.getId());
                building.getRentAreas().add(rentArea);
            }

            RentType rentType = new RentType();
            rentType.setId(resultSet.getLong("renttypeID"));
            if (rentType.getId() != null) {
                rentType.setCode(resultSet.getString("renttypeCode"));
                rentType.setName(resultSet.getString("renttypeName"));
                building.getRentTypes().add(rentType);
            }

            User user = new User();
            user.setId(resultSet.getLong("userID"));
            if (user.getId() != null) {
                user.setFullName(resultSet.getString("userFullName"));
                building.getAssignUsers().add(user);
            }
            return building;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BuildingSearchResponse mapToResponse(Building entity) {
        ModelMapper mapper = ModelMapper.getInstance();
        BuildingSearchResponse response = mapper.convertModel(entity, BuildingSearchResponse.class);
        String address = Stream.of(entity.getStreet(), entity.getWard(), entity.getDistrict().getName())
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(", "));
        response.setAddress(address);
        List<AssignUserDTO> assignUserDTOS = mapper.convertModel(entity.getAssignUsers(), AssignUserDTO.class);
        response.setAssignUsers(assignUserDTOS);
        return response;
    }

    @Override
    public List<BuildingSearchResponse> mapToResponse(Collection<Building> entities) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        entities.forEach(e -> list.add(mapToResponse(e)));
        return list;
    }

    @Override
    public BuildingDTO mapToDto(Building entity) {
        ModelMapper mapper = ModelMapper.getInstance();
        BuildingDTO buildingDTO = mapper.convertModel(entity, BuildingDTO.class);
        {
            DistrictDTO districtDTO = mapper.convertModel(entity.getDistrict(), DistrictDTO.class);
            List<RentAreaDTO> rentAreaDTOS = mapper.convertModel(entity.getRentAreas(), RentAreaDTO.class);
            List<RentTypeDTO> rentTypeDTOS = mapper.convertModel(entity.getRentTypes(), RentTypeDTO.class);
            List<AssignUserDTO> assignUserDTOS = mapper.convertModel(entity.getAssignUsers(), AssignUserDTO.class);
            buildingDTO.setDistrict(districtDTO);
            buildingDTO.setRentAreas(rentAreaDTOS);
            buildingDTO.setRentTypes(rentTypeDTOS);
            buildingDTO.setAssignUsers(assignUserDTOS);
        }
        return buildingDTO;
    }

    @Override
    public List<BuildingDTO> mapToDto(Collection<Building> entities) {
        List<BuildingDTO> list = new ArrayList<>();
        entities.forEach(e -> list.add(mapToDto(e)));
        return list;
    }

    @Override
    public Building mapToEntity(BuildingDTO dto) {
        ModelMapper mapper = ModelMapper.getInstance();
        Building building = mapper.convertModel(dto, Building.class);
        {
            District district = mapper.convertModel(dto.getDistrict(), District.class);
            List<RentArea> rentAreas = mapper.convertModel(dto.getRentAreas(), RentArea.class);
            rentAreas.forEach(ra -> ra.setBuildingID(dto.getId()));
            List<RentType> rentTypes = mapper.convertModel(dto.getRentTypes(), RentType.class);
            List<User> assignUsers = mapper.convertModel(dto.getAssignUsers(), User.class);
            building.setDistrictID(district.getId());
            building.setDistrict(district);
            building.setRentAreas(new HashSet<>(rentAreas));
            building.setRentTypes(new HashSet<>(rentTypes));
            building.setAssignUsers(new HashSet<>(assignUsers));
        }
        return building;
    }

    @Override
    public List<Building> mapToEntity(Collection<BuildingDTO> dtos) {
        List<Building> list = new ArrayList<>();
        dtos.forEach(d -> list.add(mapToEntity(d)));
        return list;
    }
}
