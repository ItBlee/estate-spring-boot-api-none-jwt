package com.itblee.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "renttype")
public class RentType extends BaseEntityCode {

    private static final long serialVersionUID = -2805628056451633990L;

    @ManyToMany(mappedBy = "rentTypes", fetch = FetchType.LAZY)
    private List<Building> buildings = new ArrayList<>();

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

}
