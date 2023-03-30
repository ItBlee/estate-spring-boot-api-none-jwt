package com.itblee.converter.impl;

import com.itblee.converter.BuildingConverter;
import com.itblee.converter.ObjectConverter;
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
public class BuildingConverterImpl extends ObjectConverter implements BuildingConverter {

    @Override
    public BuildingEntity convertToEntity(ResultSet resultSet) {
        try {
            BuildingEntity building = new BuildingEntity();
            building.setId(resultSet.getLong("id"));
            building.setName(resultSet.getString("name"));
            building.setStreet(resultSet.getString("street"));
            building.setWard(resultSet.getString("ward"));
            return building;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public BuildingSearchResponse convertToResponse(BuildingFilter filter) {
        BuildingSearchResponse searchResponse = new BuildingSearchResponse();
        searchResponse.setId(filter.getId());
        searchResponse.setName(filter.getName());
        String address = filter.getStreet() + ", " + filter.getWard();
        try {
            address = address + ", " + filter.getDistrict().getName();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        searchResponse.setAddress(address);
        return searchResponse;
    }

    @Override
    public List<BuildingSearchResponse> convertToResponse(List<BuildingFilter> filters) {
        List<BuildingSearchResponse> list = new ArrayList<>();
        for (BuildingFilter filter : filters)
            list.add(convertToResponse(filter));
        return list;
    }

    @Override
    public BuildingDTO convertToDto(BuildingFilter filter) {
        return convertObject(filter, BuildingDTO.class);
    }

    @Override
    public List<BuildingDTO> convertToDto(List<BuildingFilter> filters) {
        return convertObject(filters, BuildingDTO.class);
    }

    @Override
    public <T> BuildingFilter convertToFilter(T o) {
        return convertObject(o, BuildingFilter.class);
    }

    @Override
    public <T> List<BuildingFilter> convertToFilter(List<T> os) {
        return convertObject(os, BuildingFilter.class);
    }

    @Override
    public BuildingEntity convertToEntity(BuildingFilter filter) {
        return convertObject(filter, BuildingEntity.class);
    }

    @Override
    public List<BuildingEntity> convertToEntity(List<BuildingFilter> filters) {
        return convertObject(filters, BuildingEntity.class);
    }
}
