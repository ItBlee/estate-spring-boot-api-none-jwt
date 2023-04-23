package com.itblee.entity;

public class Role extends BaseEntityAudit {

    private static final long serialVersionUID = 8813280777538544796L;

    private String name;
    private String code;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
