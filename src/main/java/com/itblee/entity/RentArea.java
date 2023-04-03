package com.itblee.entity;

public class RentArea extends BaseEntityAudit {
    private Integer value;
    private Long buildingID;

    public RentArea() {
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(Long buildingID) {
        this.buildingID = buildingID;
    }
}
