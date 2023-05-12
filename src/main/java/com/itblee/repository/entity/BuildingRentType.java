package com.itblee.repository.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "buildingrenttype")
public class BuildingRentType extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "buildingid", nullable = false)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "renttypeid", nullable = false)
    private RentType rentType;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public RentType getRentType() {
        return rentType;
    }

    public void setRentType(RentType rentType) {
        this.rentType = rentType;
    }

}
