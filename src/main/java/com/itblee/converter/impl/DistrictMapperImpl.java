package com.itblee.converter.impl;

import com.itblee.converter.DistrictMapper;
import com.itblee.converter.ModelMapper;
import com.itblee.entity.DistrictEntity;
import com.itblee.filter.DistrictFilter;
import com.itblee.model.dto.DistrictDTO;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DistrictMapperImpl extends ModelMapper implements DistrictMapper {

    @Override
    public DistrictEntity mapRow(ResultSet resultSet) {
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
    public DistrictDTO mapToDto(DistrictFilter filter) {
        return convertModel(filter, DistrictDTO.class);
    }

    @Override
    public List<DistrictDTO> mapToDto(List<DistrictFilter> filters) {
        return convertModel(filters, DistrictDTO.class);
    }

    @Override
    public <T> DistrictFilter mapToFilter(T o) {
        return convertModel(o, DistrictFilter.class);
    }

    @Override
    public <T> List<DistrictFilter> mapToFilter(List<T> os) {
        return convertModel(os, DistrictFilter.class);
    }

    @Override
    public DistrictEntity mapToEntity(DistrictFilter filter) {
        return convertModel(filter, DistrictEntity.class);
    }

    @Override
    public List<DistrictEntity> mapToEntity(List<DistrictFilter> filters) {
        return convertModel(filters, DistrictEntity.class);
    }
}
