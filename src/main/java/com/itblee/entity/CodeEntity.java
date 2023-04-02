package com.itblee.entity;

public abstract class CodeEntity extends BaseEntity {
    protected String name;
    protected String code;

    public CodeEntity() {
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
