package com.itblee.repository.query.key;

import com.itblee.repository.query.ConditionKey;

import java.sql.Date;

public enum BuildingKey implements ConditionKey {

    ID ("id", Long.class, new SqlQuery() {{
        select("building.id");
        from("building");
        where("building.id");
    }}),

    NAME ("name", String.class, new SqlQuery() {{
        select("building.name");
        from("building");
        where("building.name");
    }}),

    DISTRICT_CODE("districtcode", String.class, new SqlQuery() {{
        select("district.code AS \"districtCode\"",
                "district.name AS \"districtName\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("district");
            on("building.districtid = district.id");
        }});
        where("district.code");
    }}),

    WARD ("ward", String.class, new SqlQuery() {{
        select("building.ward");
        from("building");
        where("building.ward");
    }}),

    LEVEL ("level", String.class, new SqlQuery() {{
        select("building.level");
        from("building");
        where("building.level");
    }}),

    STREET ("street", String.class, new SqlQuery() {{
        select("building.street");
        from("building");
        where("building.street");
    }}),


    DIRECTION ("direction", String.class, new SqlQuery() {{
        select("building.direction");
        from("building");
        where("building.direction");
    }}),

    RENT_PRICE("rentprice", Integer.class, true, new SqlQuery() {{
        select("building.rentprice");
        from("building");
        where("building.rentprice");
    }}),

    FLOOR_AREA ("floorarea", Integer.class, new SqlQuery() {{
        select("building.floorarea");
        from("building");
        where("building.floorarea");
    }}),

    MANAGER_NAME ("managername", String.class, new SqlQuery() {{
        select("building.managername");
        from("building");
        where("building.managername");
    }}),

    MANAGER_PHONE ("managerphone", String.class, new SqlQuery() {{
        select("building.managerphone");
        from("building");
        where("building.managerphone");
    }}),

    NUMBER_OF_BASEMENT ("numberofbasement", Integer.class, new SqlQuery() {{
        select("building.numberofbasement");
        from("building");
        where("building.numberofbasement");
    }}),

    RENT_AREA ("rentarea", Integer.class, true, new SqlQuery() {{
        select("rentarea.id AS \"rentareaID\"",
                "rentarea.value AS \"rentareaValue\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("rentarea");
            on("building.id = rentarea.buildingid");
        }});
        where("rentarea.value");
    }}),

    RENT_TYPES ("types", String[].class, new SqlQuery() {{
        select("renttype.id AS \"renttypeID\"",
                "renttype.code AS \"renttypeCode\"",
                "renttype.name AS \"renttypeName\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("buildingrenttype");
            on("building.id = buildingrenttype.buildingid");
        }},
                new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("renttype");
            on("renttype.id = buildingrenttype.renttypeid");
        }});
        where("renttype.code");
    }}),

    STAFF ("staffid", Long.class, new SqlQuery() {{
        select("ur.id AS \"userID\"",
                "ur.fullname AS \"userFullName\"");
        joinWith(
                new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("assignmentbuilding");
            on("building.id = assignmentbuilding.buildingid");
        }},
                new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join(new SqlQuery() {{
                select("user.id", "user.fullname");
                from("user");
                joinWith(
                        new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("user_role");
                    on("user.id = user_role.userid");
                }},
                        new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("role");
                    on("role.id = user_role.roleid");
                }});
                where("role.code = \"staff\"");
                as("ur");
            }});
            on("ur.id = assignmentbuilding.staffid");
        }});
        where("ur.id");
    }}),

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

    ALL ("*", Object.class, new SqlQuery() {{
        select("building.id",
                "building.name",
                "building.street",
                "building.ward",
                "building.districtid",
                "building.structure",
                "building.numberofbasement",
                "building.floorarea",
                "building.direction",
                "building.level",
                "building.rentprice",
                "building.rentpricedescription",
                "building.servicefee",
                "building.carfee",
                "building.motorbikefee",
                "building.overtimefee",
                "building.waterfee",
                "building.electricityfee",
                "building.deposit",
                "building.payment",
                "building.renttime",
                "building.decorationtime",
                "building.brokeragefee",
                "building.managername",
                "building.managerphone",
                "building.note",
                "building.linkofbuilding",
                "building.map",
                "building.image",
                "building.createddate",
                "building.createdby",
                "building.modifieddate",
                "building.modifiedby",
                "district.code AS \"districtCode\"",
                "district.name AS \"districtName\"",
                "rentarea.id AS \"rentareaID\"",
                "rentarea.value AS \"rentareaValue\"",
                "renttype.id AS \"renttypeID\"",
                "renttype.code AS \"renttypeCode\"",
                "renttype.name AS \"renttypeName\"",
                "ur.id AS \"userID\"",
                "ur.fullname AS \"userFullName\""
        );
        from("building");
        joinWith(new SqlJoin() {{
                     type(SqlJoin.Type.LEFT_JOIN);
                     join("district");
                     on("building.districtid = district.id");
                 }},
                new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("rentarea");
                    on("building.id = rentarea.buildingid");
                }},
                new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("buildingrenttype");
                    on("building.id = buildingrenttype.buildingid");
                }},
                new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("renttype");
                    on("renttype.id = buildingrenttype.renttypeid");
                }},
                new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join("assignmentbuilding");
                    on("building.id = assignmentbuilding.buildingid");
                }},
                new SqlJoin() {{
                    type(SqlJoin.Type.LEFT_JOIN);
                    join(new SqlQuery() {{
                        select("user.id", "user.fullname");
                        from("user");
                        joinWith(
                            new SqlJoin() {{
                                type(SqlJoin.Type.LEFT_JOIN);
                                join("user_role");
                                on("user.id = user_role.userid");
                            }},
                            new SqlJoin() {{
                                type(SqlJoin.Type.LEFT_JOIN);
                                join("role");
                                on("role.id = user_role.roleid");
                            }});
                        where("role.code = \"staff\"");
                        as("ur");
                    }});
                    on("ur.id = assignmentbuilding.staffid");
                }}
        );
    }});

    private final String param;
    private final Class<?> type;
    private final boolean isRange;
    private final SqlQuery query;

    BuildingKey(String param, Class<?> type) {
        this.param = param;
        this.type = type;
        this.isRange = false;
        this.query = null;
    }

    BuildingKey(String param, Class<?> type, SqlQuery query) {
        this.param = param;
        this.type = type;
        this.isRange = false;
        this.query = query;
    }

    BuildingKey(String param, Class<?> type, boolean isRange, SqlQuery query) {
        this.param = param;
        this.type = type;
        this.isRange = isRange;
        this.query = query;
    }

    @Override
    public SqlQuery props() {
        return query;
    }

    @Override
    public String getParam() {
        return param;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isRange() {
        return isRange;
    }

    @Override
    public String getName() {
        return "Building - " + this.name().toLowerCase();
    }

}
