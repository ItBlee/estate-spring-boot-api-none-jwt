package com.itblee.repository.impl;

import com.itblee.entity.Building;
import com.itblee.exception.ErrorRepositoryException;
import com.itblee.repository.BuildingRepository;
import com.itblee.sqlbuilder.SqlBuilder;
import com.itblee.sqlbuilder.SqlMap;
import com.itblee.sqlbuilder.impl.SqlBuilderFactory;
import com.itblee.sqlbuilder.SqlExecutor;
import org.springframework.stereotype.Repository;

import java.sql.SQLSyntaxErrorException;
import java.util.List;
import java.util.Map;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {

    @Override
    public List<Building> findByCondition(Map<?, ?> conditions) {
        SqlMap<?> statements = (SqlMap<?>) conditions;
        SqlBuilderFactory factory = new SqlBuilderFactory(statements);
        SqlBuilder builder = factory.getInstance("query");
        try {
            String sql = builder.build();
            return SqlExecutor.query(sql, Building.class);
        } catch (SQLSyntaxErrorException e) {
            throw new ErrorRepositoryException(e);
        }
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

    /*@Override
    public Building findOne(Long id) {
        StringBuilder sql = getQuerySQL();
        sql.append("WHERE building.id = ? ");
        List<Building> results = query(sql.toString(), buildingMapper, id);
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    public List<Building> findAll() {
        StringBuilder sql = getQuerySQL();
        return query(sql.toString(), buildingMapper);
    }

    @Override
    public List<Building> findByConditions(SqlConditions conditions) {
        StringBuilder sql = getQuerySQL();
        sql.append(conditions.generateWhereClauseSQL());
        return query(sql.toString(), buildingMapper);
    }*/

    /*private StringBuilder getQuerySQL() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT building.*, ");
        sql.append("district.code AS \"districtCode\", district.name AS \"districtName\", ");
        sql.append("rentarea.id AS \"rentareaID\", rentarea.value AS \"rentareaValue\", ");
        sql.append("renttype.id AS \"renttypeID\", renttype.code AS \"renttypeCode\", renttype.name AS \"renttypeName\", ");
        sql.append("ur.id AS \"userID\", ur.fullname AS \"userFullName\" ");
        sql.append("FROM building ");
        sql.append("LEFT JOIN district ON building.districtid = district.id ");
        sql.append("LEFT JOIN rentarea ON building.id = rentarea.buildingid ");
        sql.append("LEFT JOIN buildingrenttype ON building.id = buildingrenttype.buildingid ");
        sql.append("LEFT JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
        sql.append("LEFT JOIN assignmentbuilding ON building.id = assignmentbuilding.buildingid ");
        sql.append("LEFT JOIN ( ");
        sql.append("SELECT user.id, user.fullname ");
        sql.append("FROM user ");
        sql.append("INNER JOIN user_role ON user.id = user_role.userid ");
        sql.append("INNER JOIN role ON role.id = user_role.roleid ");
        sql.append("WHERE role.code = \"staff\" ");
        sql.append(") AS ur ON ur.id = assignmentbuilding.staffid ");
        return sql;
    }*/

}
