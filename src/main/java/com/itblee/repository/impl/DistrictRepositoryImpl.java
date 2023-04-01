package com.itblee.repository.impl;

import com.itblee.converter.DistrictMapper;
import com.itblee.entity.DistrictEntity;
import com.itblee.repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DistrictRepositoryImpl extends AbstractRepository<DistrictEntity> implements DistrictRepository {

    @Autowired
    private DistrictMapper districtConverter;

    @Override
    public DistrictEntity findOne(Long id) {
        String sql = "SELECT * FROM district WHERE id = ?";
        List<DistrictEntity> results = query(sql, districtConverter, id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<DistrictEntity> findAll() {
        String sql = "SELECT * FROM district";
        return query(sql, districtConverter);
    }

}
