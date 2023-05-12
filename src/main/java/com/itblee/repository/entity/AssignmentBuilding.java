package com.itblee.repository.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assignmentbuilding")
public class AssignmentBuilding extends BaseEntityAudit {

    @ManyToOne
    @JoinColumn(name = "buildingid", nullable = false)
    private Building building;

    @ManyToOne
    @JoinColumn(name = "staffid", nullable = false)
    private User user;

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
