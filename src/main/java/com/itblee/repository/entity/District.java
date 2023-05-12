package com.itblee.repository.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "district")
public class District extends BaseEntityCode {

    private static final long serialVersionUID = -5101158090567579679L;

    @OneToMany(mappedBy = "district", fetch = FetchType.LAZY)
    private List<Building> buildings = new ArrayList<>();

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

}
