package com.itblee.repository.impl;

import com.itblee.entity.Building;
import com.itblee.mapper.BuildingMapper;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.SqlBuilder;
import com.itblee.repository.key.BuildingKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BuildingRepositoryImpl extends AbstractRepository<Building> implements BuildingRepository {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> findByCondition(SqlBuilder<BuildingKey> conditions) {
        StringBuilder sql = conditions.buildFinalQuery();
        return query(sql.toString(), buildingMapper);
    }

    @Override
    public Long save(Building entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Building entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException();
    }

}
