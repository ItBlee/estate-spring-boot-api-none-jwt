package com.itblee.entity;

public abstract class BaseEntityCode extends BaseEntity {

    private String name;
    private String code;

    public BaseEntityCode() {
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
