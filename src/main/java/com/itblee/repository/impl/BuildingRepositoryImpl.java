package com.itblee.repository.impl;

import com.itblee.converter.BuildingMapper;
import com.itblee.entity.BuildingEntity;
import com.itblee.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BuildingRepositoryImpl extends AbstractRepository<BuildingEntity> implements BuildingRepository {

    @Autowired
    private BuildingMapper buildingConverter;

    @Override
    public BuildingEntity findOne(Long id) {
        String sql = "SELECT * FROM building WHERE id = ?";
        List<BuildingEntity> results = query(sql, buildingConverter, id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<BuildingEntity> findAll() {
        String sql = "SELECT * FROM building";
        return query(sql, buildingConverter);
    }

}
