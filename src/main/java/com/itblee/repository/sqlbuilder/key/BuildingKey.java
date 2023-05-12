package com.itblee.repository.sqlbuilder.key;

import com.itblee.repository.sqlbuilder.SqlKey;
import com.itblee.repository.sqlbuilder.SqlStatement;
import com.itblee.repository.sqlbuilder.model.SqlJoin;
import com.itblee.repository.sqlbuilder.model.SqlQuery;
import com.itblee.repository.sqlbuilder.model.Code;
import com.itblee.util.StringUtils;

import java.sql.Date;

public enum BuildingKey implements SqlKey {

    ID ("id", Long.class, SqlQuery.builder()
        .select("building.id")
        .from("building")
        .where("building.id").build()
    ),

    NAME ("name", String.class, SqlQuery.builder()
        .select("building.name")
        .from("building")
        .where("building.name").build()
    ),

    DISTRICT_CODE ("districtcode", Code.class, SqlQuery.builder()
        .select("district.code AS \"districtCode\"",
                "district.name AS \"districtName\"",
                "building.districtid")
        .from("building")
        .joinWith(SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join("district")
            .on("building.districtid = district.id").done())
        .where("district.code").build()
    ),

    WARD ("ward", String.class, SqlQuery.builder()
        .select("building.ward")
        .from("building")
        .where("building.ward").build()
    ),

    LEVEL ("level", String.class, SqlQuery.builder()
        .select("building.level")
        .from("building")
        .where("building.level").build()
    ),

    STREET ("street", String.class, SqlQuery.builder()
        .select("building.street")
        .from("building")
        .where("building.street").build()
    ),

    DIRECTION ("direction", String.class, SqlQuery.builder()
        .select("building.direction")
        .from("building")
        .where("building.direction").build()
    ),

    RENT_PRICE("rentprice", Integer.class, true, SqlQuery.builder()
        .select("building.rentprice")
        .from("building")
        .where("building.rentprice").build()
    ),

    FLOOR_AREA ("floorarea", Integer.class, SqlQuery.builder()
        .select("building.floorarea")
        .from("building")
        .where("building.floorarea").build()
    ),

    MANAGER_NAME ("managername", String.class, SqlQuery.builder()
        .select("building.managername")
        .from("building")
        .where("building.managername").build()
    ),

    MANAGER_PHONE ("managerphone", String.class, SqlQuery.builder()
        .select("building.managerphone")
        .from("building")
        .where("building.managerphone").build()
    ),

    NUMBER_OF_BASEMENT ("numberofbasement", Integer.class, SqlQuery.builder()
        .select("building.numberofbasement")
        .from("building")
        .where("building.numberofbasement").build()
    ),

    RENT_AREA ("rentarea", Integer.class, true, SqlQuery.builder()
        .select("rentarea.id AS \"rentareaID\"",
                "rentarea.value AS \"rentareaValue\"")
        .from("building")
        .joinWith(SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join("rentarea")
            .on("building.id = rentarea.buildingid").done())
        .where("rentarea.value").build()
    ),

    RENT_TYPES ("types", Code[].class, SqlQuery.builder()
        .select("renttype.id AS \"renttypeID\"",
                "renttype.code AS \"renttypeCode\"",
                "renttype.name AS \"renttypeName\"")
        .from("building")
        .joinWith(
            SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join("buildingrenttype")
            .on("building.id = buildingrenttype.buildingid").done(),

            SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join("renttype")
            .on("renttype.id = buildingrenttype.renttypeid").done()
        )
        .where("renttype.code").build()
    ),

    STAFF ("staffid", Long.class, SqlQuery.builder()
        .select("ur.id AS \"userID\"",
                "ur.fullname AS \"userFullName\"")
        .from("building")
        .joinWith(
            SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join("assignmentbuilding")
            .on("building.id = assignmentbuilding.buildingid").done(),

            SqlJoin.builder()
            .type(SqlJoin.Type.INNER_JOIN)
            .join(
                SqlQuery.builder()
                .select("user.id", "user.fullname")
                .from("user")
                .joinWith(
                    SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("user_role")
                    .on("user.id = user_role.userid").done(),

                    SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("role")
                    .on("role.id = user_role.roleid").done()
                )
                .where("role.code = \"staff\"")
                .as("ur").build()
            )
        .on("ur.id = assignmentbuilding.staffid").done()
        )
        .where("ur.id").build()
    ),


    //MAKER
    MAP ("map", String.class),
    NOTE ("note", String.class),
    IMAGE ("image", String.class),
    CAR_FEE ("carfee", String.class),
    DEPOSIT ("deposit", String.class),
    PAYMENT ("payment", String.class),
    RENT_TIME ("renttime", String.class),
    WATER_FEE ("waterfee", String.class),
    STRUCTURE ("structure", String.class),
    CREATED_BY ("createdby", String.class),
    MODIFIED_BY ("modifiedby", String.class),
    SERVICE_FEE ("servicefee", String.class),
    DISTRICT_ID ("districtid", Long.class),
    OVERTIME_FEE ("overtimefee", String.class),
    CREATED_DATE ("createddate", Date.class),
    MODIFIED_DATE ("modifieddate", Date.class),
    MOTORBIKE_FEE ("motorbikefee", String.class),
    BROKERAGE_FEE ("brokeragefee", Double.class),
    DECORATION_TIME ("decorationtime", String.class),
    ELECTRICITY_FEE ("electricityfee", String.class),
    LINK_OF_BUILDING ("linkofbuilding", String.class),
    RENT_PRICE_DESCRIPTION ("rentpricedescription", String.class),


    //SCOPE
    ALL (SqlQuery.builder()
        .select("building.*")
        .from("building")
        .joinWith(SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("district")
            .on("building.districtid = district.id").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("rentarea")
            .on("building.id = rentarea.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("buildingrenttype")
            .on("building.id = buildingrenttype.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("renttype")
            .on("renttype.id = buildingrenttype.renttypeid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("assignmentbuilding")
            .on("building.id = assignmentbuilding.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join(SqlQuery.builder()
                .select("user.id", "user.fullname")
                .from("user")
                .joinWith(SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("user_role")
                    .on("user.id = user_role.userid").done(),

                         SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("role")
                    .on("role.id = user_role.roleid").done())

                .where("role.code = \"staff\"")
                .as("ur").build()
            )
            .on("ur.id = assignmentbuilding.staffid").done()
        ).build()
    ),

    //SCOPE
    SHORTEN (SqlQuery.builder()
        .select("building.*")
        .from("building")
        .joinWith(SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("district")
            .on("building.districtid = district.id").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("rentarea")
            .on("building.id = rentarea.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("buildingrenttype")
            .on("building.id = buildingrenttype.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("renttype")
            .on("renttype.id = buildingrenttype.renttypeid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join("assignmentbuilding")
            .on("building.id = assignmentbuilding.buildingid").done(),

                SqlJoin.builder()
            .type(SqlJoin.Type.LEFT_JOIN)
            .join(SqlQuery.builder()
                .select("user.id", "user.fullname")
                .from("user")
                .joinWith(SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("user_role")
                    .on("user.id = user_role.userid").done(),

                        SqlJoin.builder()
                    .type(SqlJoin.Type.INNER_JOIN)
                    .join("role")
                    .on("role.id = user_role.roleid").done())

                .where("role.code = \"staff\"")
                .as("ur").build()
            )
            .on("ur.id = assignmentbuilding.staffid").done()
        ).build()
    );

    private final String param;
    private final Class<?> type;
    private final boolean isRange;
    private final SqlStatement statement;

    BuildingKey(SqlStatement statement) {
        if (statement == null)
            throw new IllegalArgumentException();
        this.param = "";
        this.type = Object.class;
        this.isRange = false;
        this.statement = statement;
    }

    BuildingKey(String param, Class<?> type) {
        StringUtils.requireNonBlank(param);
        if (type == null)
            throw new IllegalArgumentException();
        this.param = param;
        this.type = type;
        this.isRange = false;
        this.statement = null;
    }

    BuildingKey(String param, Class<?> type, SqlStatement statement) {
        StringUtils.requireNonBlank(param);
        if (type == null)
            throw new IllegalArgumentException();
        this.param = param;
        this.type = type;
        this.isRange = false;
        this.statement = statement;
    }

    BuildingKey(String param, Class<?> type, boolean isRange, SqlStatement statement) {
        StringUtils.requireNonBlank(param);
        if (type == null)
            throw new IllegalArgumentException();
        this.param = param;
        this.type = type;
        this.isRange = isRange;
        this.statement = statement;
    }

    @Override
    public SqlStatement getStatement() {
        return statement;
    }

    @Override
    public String getParamName() {
        return param;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public boolean isScope() {
        return StringUtils.isBlank(getParamName())
                && getType() == Object.class
                && statement != null;
    }

    @Override
    public boolean isMarker() {
        return statement == null;
    }

    @Override
    public boolean isRange() {
        return isRange;
    }

}
