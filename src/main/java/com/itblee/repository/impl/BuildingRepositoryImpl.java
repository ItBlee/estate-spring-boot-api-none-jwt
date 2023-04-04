package com.itblee.repository.impl;

import com.itblee.entity.Building;
import com.itblee.mapper.BuildingMapper;
import com.itblee.repository.BuildingRepository;
import com.itblee.repository.conditions.SqlConditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BuildingRepositoryImpl extends AbstractRepository<Building> implements BuildingRepository {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
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
    }

    @Override
    public Long save(Building building) {
        return null;
    }

    @Override
    public void update(Building building) {

    }

    @Override
    public void delete(Long id) {

    }

    private StringBuilder getQuerySQL() {
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
        sql.append("LEFT JOIN user_role ON user.id = user_role.userid ");
        sql.append("LEFT JOIN role ON role.id = user_role.roleid ");
        sql.append("WHERE role.code = \"staff\" ");
        sql.append(") AS ur ON ur.id = assignmentbuilding.staffid ");
        return sql;
    }
}
