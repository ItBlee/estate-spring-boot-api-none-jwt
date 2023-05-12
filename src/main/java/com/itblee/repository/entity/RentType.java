package com.itblee.repository.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "renttype")
public class RentType extends BaseEntityCode {

    private static final long serialVersionUID = -2805628056451633990L;

    @OneToMany(mappedBy = "rentType", fetch = FetchType.LAZY)
    private List<BuildingRentType> buildings = new ArrayList<>();

    public List<BuildingRentType> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<BuildingRentType> buildings) {
        this.buildings = buildings;
    }

}
