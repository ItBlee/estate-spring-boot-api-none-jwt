package com.itblee.repository.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
public class Role extends BaseEntityCode {

    private static final long serialVersionUID = 8813280777538544796L;

    @CreatedDate
    @Column(name = "createddate")
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "modifieddate")
    private Date modifiedDate;

    @CreatedBy
    @Column(name = "createdby")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modifiedby")
    private String modifiedBy;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRole> users = new ArrayList<>();

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public List<UserRole> getUsers() {
        return users;
    }

    public void setUsers(List<UserRole> users) {
        this.users = users;
    }
}
