package com.itblee.mapper.impl;

import com.itblee.entity.*;
import com.itblee.exception.BadRequestException;
import com.itblee.mapper.BuildingMapper;
import com.itblee.model.dto.*;
import com.itblee.model.response.BuildingSearchResponse;
import com.itblee.repository.condition.SqlConditionMap;
import com.itblee.repository.condition.key.BuildingKey;
import com.itblee.utils.CastUtils;
import com.itblee.utils.MapUtils;
import com.itblee.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class BuildingMapperImpl extends AbstractMapper<Building> implements BuildingMapper {

    @Override
    public List<Building> extractData(ResultSet resultSet) throws SQLException {
        Map<Long, Building> buildings = new HashMap<>();
        for (Map<String, Object> row : getRow(resultSet)) {
            Long id = cast(MapUtils.get(row, "id"), Long.class);
            if (id == null)
                continue;
            Building building = buildings.getOrDefault(id, null);
            if (building == null)
                building = mapRow(row);
            try {
                //merge duplicate building row if any
                building = mergeRow(row, building);
                //if map or merge fail -> throw exception
                buildings.put(building.getId(), building);
            } catch (IllegalArgumentException ignored) {}
        }
        return new ArrayList<>(buildings.values());
    }

    private <T> T cast(Object o, Class<T> t) {
        return CastUtils.cast(o, t).orElse(null);
    }

    @Override
    public Building mapRow(Map<String, Object> row) {
        Building building = new Building();
        {
            building.setId(cast(MapUtils.get(row, "id"), Long.class));
            building.setName(cast(MapUtils.get(row, "name"), String.class));
            building.setStreet(cast(MapUtils.get(row, "street"), String.class));
            building.setWard(cast(MapUtils.get(row, "ward"), String.class));
            building.setDistrictID(cast(MapUtils.get(row, "districtid"), Long.class));
            building.setStructure(cast(MapUtils.get(row, "structure"), String.class));
            building.setNumberOfBasement(cast(MapUtils.get(row, "numberofbasement"), Integer.class));
            building.setFloorArea(cast(MapUtils.get(row, "floorarea"), Integer.class));
            building.setDirection(cast(MapUtils.get(row, "direction"), String.class));
            building.setLevel(cast(MapUtils.get(row, "level"), String.class));
            building.setRentPrice(cast(MapUtils.get(row, "rentprice"), Integer.class));
            building.setRentPriceDescription(cast(MapUtils.get(row, "rentpricedescription"), String.class));
            building.setServiceFee(cast(MapUtils.get(row, "servicefee"), String.class));
            building.setCarFee(cast(MapUtils.get(row, "carfee"), String.class));
            building.setMotorbikeFee(cast(MapUtils.get(row, "motorbikefee"), String.class));
            building.setOvertimeFee(cast(MapUtils.get(row, "overtimefee"), String.class));
            building.setWaterFee(cast(MapUtils.get(row, "waterfee"), String.class));
            building.setElectricityFee(cast(MapUtils.get(row, "electricityfee"), String.class));
            building.setDeposit(cast(MapUtils.get(row, "deposit"), String.class));
            building.setPayment(cast(MapUtils.get(row, "payment"), String.class));
            building.setRentTime(cast(MapUtils.get(row, "renttime"), String.class));
            building.setDecorationTime(cast(MapUtils.get(row, "decorationtime"), String.class));
            building.setBrokerageFee(cast(MapUtils.get(row, "brokeragefee"), Double.class));
            building.setManagerName(cast(MapUtils.get(row, "managername"), String.class));
            building.setManagerPhone(cast(MapUtils.get(row, "managerphone"), String.class));
            building.setNote(cast(MapUtils.get(row, "note"), String.class));
            building.setLinkOfBuilding(cast(MapUtils.get(row, "linkofbuilding"), String.class));
            building.setMap(cast(MapUtils.get(row, "map"), String.class));
            building.setImage(cast(MapUtils.get(row, "image"), String.class));
            building.setCreatedDate(cast(MapUtils.get(row, "createddate"), Timestamp.class));
            building.setCreatedBy(cast(MapUtils.get(row, "createdby"), String.class));
            building.setModifiedDate(cast(MapUtils.get(row, "modifieddate"), Timestamp.class));
            building.setModifiedBy(cast(MapUtils.get(row, "modifiedby"), String.class));
        }

        if (building.getDistrictID() != null) {
            District district = new District();
            district.setId(building.getDistrictID());
            district.setName(cast(MapUtils.get(row, "districtName"), String.class));
            district.setCode(cast(MapUtils.get(row, "districtCode"), String.class));
            building.setDistrict(district);
        }
        return building;
    }

    @Override
    public Building mergeRow(Map<String, Object> row, Building building) {
        if (building == null)
            throw new IllegalStateException();
        if (row.containsKey("rentareaID")) {
            RentArea rentArea = new RentArea();
            rentArea.setId(cast(MapUtils.get(row, "rentareaID"), Long.class));
            if (rentArea.getId() != null) {
                if (row.containsKey("rentareaValue")) {
                    rentArea.setValue(cast(MapUtils.get(row, "rentareaValue"), Integer.class));
                    rentArea.setBuildingID(building.getId());
                    building.getRentAreas().add(rentArea);
                }
            }
        }

        if (row.containsKey("renttypeID")) {
            RentType rentType = new RentType();
            rentType.setId(cast(MapUtils.get(row, "renttypeID"), Long.class));
            if (rentType.getId() != null) {
                if (row.containsKey("renttypeCode") && row.containsKey("renttypeName")) {
                    rentType.setCode(cast(MapUtils.get(row, "renttypeCode"), String.class));
                    rentType.setName(cast(MapUtils.get(row, "renttypeName"), String.class));
                    building.getRentTypes().add(rentType);
                }
            }
        }

        if (row.containsKey("userID")) {
            User user = new User();
            user.setId(cast(MapUtils.get(row, "userID"), Long.class));
            if (user.getId() != null) {
                if (row.containsKey("userFullName")) {
                    user.setFullName(cast(MapUtils.get(row, "userFullName"), String.class));
                    building.getAssignUsers().add(user);
                }
            }
        }
        return building;
    }

    @Override
    public BuildingSearchResponse toResponse(Building entity) {
        if (entity == null)
            throw new IllegalArgumentException();
        BuildingSearchResponse response = convert(entity, BuildingSearchResponse.class);
        List<RentAreaDTO> rentAreas = convert(entity.getRentAreas(), RentAreaDTO.class);
        List<AssignUserDTO> assignUsers = convert(entity.getAssignUsers(), AssignUserDTO.class);
        List<String> address = new LinkedList<>();
        if (StringUtils.isBlank(entity.getStreet()))
            address.add(entity.getStreet());
        if (StringUtils.isBlank(entity.getWard()))
            address.add(entity.getWard());
        if (entity.getDistrict() != null && StringUtils.isBlank(entity.getDistrict().getName()))
            address.add(entity.getDistrict().getName());
        response.setAddress(String.join(", ", address));
        response.setRentAreas(rentAreas);
        response.setAssignUsers(assignUsers);
        return response;
    }

    @Override
    public List<BuildingSearchResponse> toResponse(Collection<Building> entities) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        entities.forEach(entity -> list.add(toResponse(entity)));
        return list;
    }

    @Override
    public SqlConditionMap toCondition(Map<String, Object> params) {
        if (params == null)
            throw new IllegalArgumentException();
        SqlConditionMap conditions = new SqlConditionMap();
        if (params.isEmpty())
            return conditions;
        try {
            conditions.put(BuildingKey.ID, MapUtils.get(params, "id"));
            conditions.put(BuildingKey.NAME, MapUtils.get(params, "name"));
            conditions.put(BuildingKey.WARD, MapUtils.get(params, "ward"));
            conditions.put(BuildingKey.LEVEL, MapUtils.get(params, "level"));
            conditions.put(BuildingKey.STAFF, MapUtils.get(params, "staffid"));
            conditions.put(BuildingKey.STREET, MapUtils.get(params, "street"));
            conditions.put(BuildingKey.DISTRICT, MapUtils.get(params, "districtcode"));
            conditions.put(BuildingKey.DIRECTION, MapUtils.get(params, "direction"));
            conditions.put(BuildingKey.RENT_TYPES, MapUtils.get(params, "types"));
            conditions.put(BuildingKey.FLOOR_AREA, MapUtils.get(params, "floorarea"));
            conditions.put(BuildingKey.MANAGER_NAME, MapUtils.get(params, "managername"));
            conditions.put(BuildingKey.MANAGER_PHONE, MapUtils.get(params, "managerphone"));
            conditions.put(BuildingKey.NUMBER_OF_BASEMENT, MapUtils.get(params, "numberofbasement"));
            conditions.put(BuildingKey.RENT_AREA,
                    MapUtils.get(params, "rentareafrom"),
                    MapUtils.get(params, "rentareato"));
            conditions.put(BuildingKey.COST_RENT,
                    MapUtils.get(params, "costrentfrom"),
                    MapUtils.get(params, "costrentto"));
        } catch (IllegalStateException e) {
            throw new BadRequestException(e);
        }
        return conditions;
    }

    @Override
    public BuildingDTO toDto(Building entity) {
        if (entity == null)
            throw new IllegalArgumentException();
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
            throw new IllegalArgumentException();
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
