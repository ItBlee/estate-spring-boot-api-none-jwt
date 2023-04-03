package com.itblee.mapper.impl;

import com.itblee.entity.*;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.*;
import com.itblee.model.response.BuildingSearchResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BuildingMapperImpl extends AbstractMapper<Building> implements BuildingMapper {

    @Override
    public List<Building> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, Building> map = new HashMap<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Building building = map.get(id);
            if (building == null) {
                building = mapRow(resultSet);
            }
            building = mergeRow(resultSet, building);
            /*if map or merge fail -> return null result (building)
             * ignore null result*/
            if (building != null) {
                map.put(building.getId(), building);
            }
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public Building mapRow(ResultSet resultSet) throws SQLException {
        Building building = new Building();
        {
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
        }
        District district = new District();
        district.setId(resultSet.getLong("districtid"));
        if (district.getId() != 0) {
            district.setName(resultSet.getString("districtName"));
            district.setCode(resultSet.getString("districtCode"));
            building.setDistrict(district);
        }
        return building;
    }

    @Override
    public Building mergeRow(ResultSet resultSet, Building building) throws SQLException {
        RentArea rentArea = new RentArea();
        rentArea.setId(resultSet.getLong("rentareaID"));
        if (rentArea.getId() != 0) {
            rentArea.setValue(resultSet.getInt("rentareaValue"));
            rentArea.setBuildingID(building.getId());
            building.getRentAreas().add(rentArea);
        }

        RentType rentType = new RentType();
        rentType.setId(resultSet.getLong("renttypeID"));
        if (rentType.getId() != 0) {
            rentType.setCode(resultSet.getString("renttypeCode"));
            rentType.setName(resultSet.getString("renttypeName"));
            building.getRentTypes().add(rentType);
        }

        User user = new User();
        user.setId(resultSet.getLong("userID"));
        if (user.getId() != 0) {
            user.setFullName(resultSet.getString("userFullName"));
            building.getAssignUsers().add(user);
        }
        return building;
    }

    @Override
    public BuildingSearchResponse toResponse(Building entity) {
        if (entity == null)
            return null;
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<AssignUserDTO> assignUserDTOS = convert(entity.getAssignUsers(), AssignUserDTO.class);
        response.setAssignUsers(assignUserDTOS);
        String address = Stream.of(entity.getStreet(), entity.getWard(), entity.getDistrict().getName())
                .filter(str -> str != null && !str.isEmpty())
                .collect(Collectors.joining(", "));
        response.setAddress(address);
        return response;
    }

    @Override
    public List<BuildingSearchResponse> toResponse(Collection<Building> entities) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toResponse(entity)));
        return list;
    }

    @Override
    public BuildingDTO toDto(Building entity) {
        if (entity == null)
            return null;
        BuildingDTO dto = convert(entity, BuildingDTO.class);
        {
            DistrictDTO district = convert(entity.getDistrict(), DistrictDTO.class);
            List<RentAreaDTO> rentAreas = convert(entity.getRentAreas(), RentAreaDTO.class);
            List<RentTypeDTO> rentTypes = convert(entity.getRentTypes(), RentTypeDTO.class);
            List<AssignUserDTO> assignUsers = convert(entity.getAssignUsers(), AssignUserDTO.class);
            dto.setDistrict(district);
            dto.setRentAreas(rentAreas);
            dto.setRentTypes(rentTypes);
            dto.setAssignUsers(assignUsers);
        }
        return dto;
    }

    @Override
    public List<BuildingDTO> toDto(Collection<Building> entities) {
        List<BuildingDTO> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toDto(entity)));
        return list;
    }

    @Override
    public Building toEntity(BuildingDTO dto) {
        if (dto == null)
            return null;
        Building entity = convert(dto, Building.class);
        {
            District district = convert(dto.getDistrict(), District.class);
            List<User> assignUsers = convert(dto.getAssignUsers(), User.class);
            List<RentType> rentTypes = convert(dto.getRentTypes(), RentType.class);
            List<RentArea> rentAreas = convert(dto.getRentAreas(), RentArea.class);
                rentAreas.forEach(area -> area.setBuildingID(dto.getId()));
            entity.setDistrictID(district.getId());
            entity.setDistrict(district);
            entity.setRentAreas(new HashSet<>(rentAreas));
            entity.setRentTypes(new HashSet<>(rentTypes));
            entity.setAssignUsers(new HashSet<>(assignUsers));
        }
        return entity;
    }

    @Override
    public List<Building> toEntity(Collection<BuildingDTO> dtos) {
        List<Building> list = new ArrayList<>();
        dtos.forEach(dto -> list.add(toEntity(dto)));
        return list;
    }
}
