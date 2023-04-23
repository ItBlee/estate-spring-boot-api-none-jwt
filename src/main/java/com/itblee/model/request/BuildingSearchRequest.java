package com.itblee.model.request;

public class BuildingSearchRequest {
    private String name;
    private Integer floorarea;
    private String districtcode;
    private String ward;
    private String street;
    private Integer numberofbasement;
    private String direction;
    private String level;
    private Integer areafrom;
    private Integer areato;
    private Integer rentpricefrom;
    private Integer rentpriceto;
    private String managerName;
    private String managerPhone;
    private Long staffid;
    private String[] renttypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloorarea() {
        return floorarea;
    }

    public void setFloorarea(Integer floorarea) {
        this.floorarea = floorarea;
    }

    public String getDistrictcode() {
        return districtcode;
    }

    public void setDistrictcode(String districtcode) {
        this.districtcode = districtcode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumberofbasement() {
        return numberofbasement;
    }

    public void setNumberofbasement(Integer numberofbasement) {
        this.numberofbasement = numberofbasement;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getAreafrom() {
        return areafrom;
    }

    public void setAreafrom(Integer areafrom) {
        this.areafrom = areafrom;
    }

    public Integer getAreato() {
        return areato;
    }

    public void setAreato(Integer areato) {
        this.areato = areato;
    }

    public Integer getRentpricefrom() {
        return rentpricefrom;
    }

    public void setRentpricefrom(Integer rentpricefrom) {
        this.rentpricefrom = rentpricefrom;
    }

    public Integer getRentpriceto() {
        return rentpriceto;
    }

    public void setRentpriceto(Integer rentpriceto) {
        this.rentpriceto = rentpriceto;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public Long getStaffid() {
        return staffid;
    }

    public void setStaffid(Long staffid) {
        this.staffid = staffid;
    }

    public String[] getRenttypes() {
        return renttypes;
    }

    public void setRenttypes(String[] renttypes) {
        this.renttypes = renttypes;
    }
}
