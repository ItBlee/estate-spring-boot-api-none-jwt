package com.itblee.entity;

import javax.persistence.*;

@Entity
@Table(name = "rentarea")
public class RentArea extends BaseEntityAudit {

    private static final long serialVersionUID = 4301012735028749930L;

    @Column(name = "value")
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "buildingid")
    private Building building;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

}
