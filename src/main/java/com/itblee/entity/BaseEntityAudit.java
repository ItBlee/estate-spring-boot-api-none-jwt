package com.itblee.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Date;

@MappedSuperclass
public abstract class BaseEntityAudit extends BaseEntity {

    private static final long serialVersionUID = -4117660059663469812L;

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
}
