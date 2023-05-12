package com.itblee.repository.custom.impl;

import com.itblee.exception.ErrorRepositoryException;
import com.itblee.repository.custom.BuildingRepositoryCustom;
import com.itblee.repository.entity.Building;
import com.itblee.repository.sqlbuilder.SqlBuilder;
import com.itblee.repository.sqlbuilder.SqlMap;
import com.itblee.repository.sqlbuilder.impl.SqlBuilderFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Map;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Building> findByCondition(Map<?, ?> conditions) {
        SqlMap<?> statements = (SqlMap<?>) conditions;
        SqlBuilderFactory factory = new SqlBuilderFactory(statements);
        SqlBuilder builder = factory.getInstance("query");
        try {
            String sql = builder.build();
            Query query = entityManager.createNativeQuery(sql, Building.class);
            return query.getResultList();
        } catch (SQLSyntaxErrorException e) {
            throw new ErrorRepositoryException(e);
        }
    }

}
