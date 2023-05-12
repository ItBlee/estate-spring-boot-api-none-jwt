package com.itblee.repository.entity;

public class RentArea extends BaseEntityAudit {

    private static final long serialVersionUID = 4301012735028749930L;

    private Integer value;
    private Long buildingID;

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
