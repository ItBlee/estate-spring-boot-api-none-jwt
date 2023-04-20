package com.itblee.repository.query.key;

import com.itblee.repository.query.ConditionKey;

import java.sql.Date;

public enum BuildingKey implements ConditionKey {

    ID ("id", new SqlQuery() {{
        typeOf(Long.class);
        select("building.id");
        from("building");
        where("building.id");
    }}),

    NAME ("name", new SqlQuery() {{
        typeOf(String.class);
        select("building.name");
        from("building");
        where("building.name");
    }}),

    DISTRICT_CODE("districtcode", new SqlQuery() {{
        typeOf(String.class);
        select("district.code AS \"districtCode\"",
                "district.name AS \"districtName\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("district");
            on("building.districtid = district.id");
        }});
        where("district.code");
    }}),

    WARD ("ward", new SqlQuery() {{
        typeOf(String.class);
        select("building.ward");
        from("building");
        where("building.ward");
    }}),

    LEVEL ("level", new SqlQuery() {{
        typeOf(String.class);
        select("building.level");
        from("building");
        where("building.level");
    }}),

    STREET ("street", new SqlQuery() {{
        typeOf(String.class);
        select("building.street");
        from("building");
        where("building.street");
    }}),


    DIRECTION ("direction", new SqlQuery() {{
        typeOf(String.class);
        select("building.direction");
        from("building");
        where("building.direction");
    }}),

    RENT_PRICE("rentprice", true, new SqlQuery() {{
        typeOf(Integer.class);
        select("building.rentprice");
        from("building");
        where("building.rentprice");
    }}),

    FLOOR_AREA ("floorarea", new SqlQuery() {{
        typeOf(Integer.class);
        select("building.floorarea");
        from("building");
        where("building.floorarea");
    }}),

    MANAGER_NAME ("managername", new SqlQuery() {{
        typeOf(String.class);
        select("building.managername");
        from("building");
        where("building.managername");
    }}),

    MANAGER_PHONE ("managerphone", new SqlQuery() {{
        typeOf(String.class);
        select("building.managerphone");
        from("building");
        where("building.managerphone");
    }}),

    NUMBER_OF_BASEMENT ("numberofbasement", new SqlQuery() {{
        typeOf(Integer.class);
        select("building.numberofbasement");
        from("building");
        where("building.numberofbasement");
    }}),

    RENT_AREA ("rentarea", true, new SqlQuery() {{
        typeOf(Integer.class);
        select("rentarea.id AS \"rentareaID\"",
                "rentarea.value AS \"rentareaValue\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("rentarea");
            on("building.id = rentarea.buildingid");
        }});
        where("rentarea.value");
    }}),

    RENT_TYPES ("types", new SqlQuery() {{
        typeOf(String[].class);
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

    STAFF ("staffid", new SqlQuery() {{
        typeOf(Long.class);
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

    MAP ("map", new SqlQuery() {{
        typeOf(String.class);
    }}),
    NOTE ("note", new SqlQuery() {{
        typeOf(String.class);
    }}),
    IMAGE ("image", new SqlQuery() {{
        typeOf(String.class);
    }}),
    CAR_FEE ("carfee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    DEPOSIT ("deposit", new SqlQuery() {{
        typeOf(String.class);
    }}),
    PAYMENT ("payment", new SqlQuery() {{
        typeOf(String.class);
    }}),
    RENT_TIME ("renttime", new SqlQuery() {{
        typeOf(String.class);
    }}),
    WATER_FEE ("waterfee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    STRUCTURE ("structure", new SqlQuery() {{
        typeOf(String.class);
    }}),
    CREATED_BY ("createdby", new SqlQuery() {{
        typeOf(String.class);
    }}),
    MODIFIED_BY ("modifiedby", new SqlQuery() {{
        typeOf(String.class);
    }}),
    SERVICE_FEE ("servicefee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    DISTRICT_ID ("districtid", new SqlQuery() {{
        typeOf(Long.class);
    }}),
    OVERTIME_FEE ("overtimefee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    CREATED_DATE ("createddate", new SqlQuery() {{
        typeOf(Date.class);
    }}),
    MODIFIED_DATE ("modifieddate", new SqlQuery() {{
        typeOf(Date.class);
    }}),
    MOTORBIKE_FEE ("motorbikefee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    BROKERAGE_FEE ("brokeragefee", new SqlQuery() {{
        typeOf(Double.class);
    }}),
    DECORATION_TIME ("decorationtime", new SqlQuery() {{
        typeOf(String.class);
    }}),
    ELECTRICITY_FEE ("electricityfee", new SqlQuery() {{
        typeOf(String.class);
    }}),
    LINK_OF_BUILDING ("linkofbuilding", new SqlQuery() {{
        typeOf(String.class);
    }}),
    RENT_PRICE_DESCRIPTION ("rentpricedescription", new SqlQuery() {{
        typeOf(String.class);
    }}),

    ALL (new SqlQuery() {{
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
    private final boolean isRange;
    private final SqlQuery query;

    BuildingKey(SqlQuery query) {
        if (query == null)
            throw new IllegalArgumentException();
        this.param = null;
        this.isRange = false;
        this.query = query;
    }

    BuildingKey(String param, SqlQuery query) {
        if (query == null)
            throw new IllegalArgumentException();
        this.param = param;
        this.isRange = false;
        this.query = query;
    }

    BuildingKey(String param, boolean isRange, SqlQuery query) {
        if (query == null)
            throw new IllegalArgumentException();
        this.param = param;
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

    public boolean isRange() {
        return isRange;
    }

    @Override
    public String getName() {
        return "Building - " + this.name().toLowerCase();
    }

}
