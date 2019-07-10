package com.testflow.apitest.testentity;

public class Basic {

    private String cid;
    private String location;
    private String parent_city;

    public String getParent_city(){
        return this.parent_city;
    }
    public void setParent_city(String parent_city){
        this.parent_city=parent_city;
    }
    private String admin_area;

    public String getAdmin_area(){
        return this.admin_area;
    }
    public void setAdmin_area(String admin_area){
        this.admin_area=admin_area;
    }
    private String cnty;
    private String lat;
    private String lon;
    private String tz;
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }
    public String getCnty() {
        return cnty;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLat() {
        return lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getLon() {
        return lon;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }
    public String getTz() {
        return tz;
    }

}