package com.itblee.model.dto;

public class DistrictDTO {
    private Long id;
    private String code;
    private String name;

    public DistrictDTO() {
    }

    public String getCode() {
        return code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
