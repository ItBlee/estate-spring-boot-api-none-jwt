package com.itblee.converter.impl;

import com.itblee.converter.BuildingMapper;
import com.itblee.converter.ModelMapper;
import com.itblee.entity.BuildingEntity;
import com.itblee.filter.BuildingFilter;
import com.itblee.model.dto.BuildingDTO;
import com.itblee.model.response.BuildingSearchResponse;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuildingMapperImpl extends ModelMapper implements BuildingMapper {

    @Override
    public BuildingEntity mapRow(ResultSet resultSet) {
        try {
            BuildingEntity building = new BuildingEntity();
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
            building.setNote(resultSet.getString("note"));
            building.setLinkOfBuilding(resultSet.getString("linkofbuilding"));
            building.setMap(resultSet.getString("map"));
            building.setImage(resultSet.getString("image"));
            return building;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public BuildingSearchResponse mapToResponse(BuildingFilter filter) {
        try {
            BuildingSearchResponse searchResponse = new BuildingSearchResponse();
            searchResponse.setId(filter.getId());
            searchResponse.setName(filter.getName());
            String address = filter.getStreet()
                            + ", " + filter.getWard()
                            + ", " + filter.getDistrict().getName();
            searchResponse.setAddress(address);
            return searchResponse;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BuildingSearchResponse> mapToResponse(List<BuildingFilter> filters) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        filters.forEach(f -> list.add(mapToResponse(f)));
        return list;
    }

    @Override
    public BuildingDTO mapToDto(BuildingFilter filter) {
        return convertModel(filter, BuildingDTO.class);
    }

    @Override
    public List<BuildingDTO> mapToDto(List<BuildingFilter> filters) {
        return convertModel(filters, BuildingDTO.class);
    }

    @Override
    public <T> BuildingFilter mapToFilter(T o) {
        return convertModel(o, BuildingFilter.class);
    }

    @Override
    public <T> List<BuildingFilter> mapToFilter(List<T> os) {
        return convertModel(os, BuildingFilter.class);
    }

    @Override
    public BuildingEntity mapToEntity(BuildingFilter filter) {
        return convertModel(filter, BuildingEntity.class);
    }

    @Override
    public List<BuildingEntity> mapToEntity(List<BuildingFilter> filters) {
        return convertModel(filters, BuildingEntity.class);
    }
}
