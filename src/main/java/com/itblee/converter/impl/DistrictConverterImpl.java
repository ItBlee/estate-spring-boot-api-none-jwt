package com.itblee.converter.impl;

import com.itblee.converter.DistrictConverter;
import com.itblee.converter.ObjectConverter;
import com.itblee.entity.DistrictEntity;
import com.itblee.filter.DistrictFilter;
import com.itblee.model.dto.DistrictDTO;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DistrictConverterImpl extends ObjectConverter implements DistrictConverter {

    @Override
    public DistrictEntity convertRow(ResultSet resultSet) {
        try {
            DistrictEntity district = new DistrictEntity();
            district.setId(resultSet.getLong("id"));
            district.setName(resultSet.getString("name"));
            district.setCode(resultSet.getString("code"));
            return district;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public DistrictDTO convertToDto(DistrictFilter filter) {
        return convertObject(filter, DistrictDTO.class);
    }

    @Override
    public List<DistrictDTO> convertToDto(List<DistrictFilter> filters) {
        return convertObject(filters, DistrictDTO.class);
    }

    @Override
    public <T> DistrictFilter convertToFilter(T o) {
        return convertObject(o, DistrictFilter.class);
    }

    @Override
    public <T> List<DistrictFilter> convertToFilter(List<T> os) {
        return convertObject(os, DistrictFilter.class);
    }

    @Override
    public DistrictEntity convertToEntity(DistrictFilter filter) {
        return convertObject(filter, DistrictEntity.class);
    }

    @Override
    public List<DistrictEntity> convertToEntity(List<DistrictFilter> filters) {
        return convertObject(filters, DistrictEntity.class);
    }
}
