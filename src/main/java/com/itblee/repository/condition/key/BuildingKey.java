package com.itblee.repository.condition.key;

import com.itblee.repository.condition.ConditionKey;

public enum BuildingKey implements ConditionKey {

    DEFAULT (new SqlQuery() {{
        select("building.id");
    }}),

    ID (new SqlQuery() {{
        type(Integer.class);
        select("building.id");
        where("building.id");
    }}),

    NAME (new SqlQuery() {{
        type(String.class);
        select("building.name");
        where("building.name");
    }}),

    WARD (new SqlQuery() {{
        type(String.class);
        select("building.ward");
        where("building.ward");
    }}),

    LEVEL (new SqlQuery() {{
        type(String.class);
        select("building.level");
        where("building.level");
    }}),

    STREET (new SqlQuery() {{
        type(String.class);
        select("building.street");
        where("building.street");
    }}),

    DIRECTION (new SqlQuery() {{
        type(String.class);
        select("building.direction");
        where("building.direction");
    }}),

    COST_RENT (new SqlQuery() {{
        type(Integer.class);
        select("building.rentprice");
        where("building.rentprice");
    }}),

    FLOOR_AREA (new SqlQuery() {{
        type(Integer.class);
        select("building.floorarea");
        where("building.floorarea");
    }}),

    MANAGER_NAME (new SqlQuery() {{
        type(String.class);
        select("building.managername");
        where("building.managername");
    }}),

    MANAGER_PHONE (new SqlQuery() {{
        type(String.class);
        select("building.managerphone");
        where("building.managerphone");
    }}),

    NUMBER_OF_BASEMENT (new SqlQuery() {{
        type(Integer.class);
        select("building.numberofbasement");
        where("building.numberofbasement");
    }}),

    DISTRICT (new SqlQuery() {{
        type(String.class);
        select("district.code AS \"districtCode\"",
                "district.name AS \"districtName\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("district");
            on("building.districtid = district.id");
        }});
        where("district.code");
    }}),

    RENT_AREA (new SqlQuery() {{
        type(Integer.class);
        select("rentarea.id AS \"rentareaID\"",
                "rentarea.value AS \"rentareaValue\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("rentarea");
            on("building.id = rentarea.buildingid");
        }});
        where("rentarea.value");
    }}),

    RENT_TYPES (new SqlQuery() {{
        type(String[].class);
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

    STAFF (new SqlQuery() {{
        type(Long.class);
        select("ur.id AS \"userID\"",
                "ur.fullname AS \"userFullName\"");
        joinWith(new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("assignmentbuilding");
            on("building.id = assignmentbuilding.buildingid");
        }},
                new SqlJoin() {{
            type(SqlJoin.Type.LEFT_JOIN);
            join("(SELECT user.id, user.fullname " +
                    "FROM user LEFT JOIN user_role ON user.id = user_role.userid " +
                    "LEFT JOIN role ON role.id = user_role.roleid " +
                    "WHERE role.code = \"staff\") AS ur ");
            on("ur.id = assignmentbuilding.staffid");
        }});
        where("ur.id");
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
                    join("(SELECT user.id, user.fullname " +
                            "FROM user LEFT JOIN user_role ON user.id = user_role.userid " +
                            "LEFT JOIN role ON role.id = user_role.roleid " +
                            "WHERE role.code = \"staff\") AS ur");
                    on("ur.id = assignmentbuilding.staffid");
                }}
        );
    }});

    private final SqlQuery query;

    BuildingKey(SqlQuery query) {
        this.query = query;
    }

    @Override
    public ConditionKey getDefault() {
        return DEFAULT;
    }

    @Override
    public SqlQuery props() {
        return query;
    }

    @Override
    public String getName() {
        return getTableName() + " " + this.name().toLowerCase();
    }

    @Override
    public String getTableName() {
        return "building";
    }
}
