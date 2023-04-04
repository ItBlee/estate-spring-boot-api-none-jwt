package com.itblee.repository.conditions;

public enum ConditionKey {

    //Key for Building - map with column name.
    BUILDING_NAME("building.name"),
    BUILDING_FLOOR_AREA("building.floorarea"),
    BUILDING_DISTRICT("building.ward"),
    BUILDING_WARD("building.ward"),
    BUILDING_STREET("building.street"),
    BUILDING_NUMBER_OF_BASEMENT("building.numberofbasement"),
    BUILDING_DIRECTION("building.direction"),
    BUILDING_LEVEL("building.level"),
    BUILDING_AREA("building.floorarea"),
    BUILDING_RENT_PRICE("building.rentprice"),
    BUILDING_MANAGER_NAME("building.managername"),
    BUILDING_MANAGER_PHONE("building.managerphone"),
    BUILDING_STAFF("ur.id"),
    BUILDING_RENT_TYPES("renttype.code");

    private final String columnName;

    ConditionKey(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return columnName;
    }
}
