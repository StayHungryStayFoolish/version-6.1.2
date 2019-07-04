package com.monolithic.web.rest.vm;

import java.io.Serializable;

public class IPApiResultVM implements Serializable {

    // zip code
    private String zip;
    // country name
    private String country;
    // city name
    private String city;
    // organization
    private String org;
    // time zone
    private String timezone;
    // internet service provider
    private String isp;
    // internet protocol address
    private String query;
    // region name
    private String regionName;
    // longitude
    private String lon;
    // latitude
    private String lat;
    // autonomous system Number
    private String as;
    // country code
    private String countryCode;
    // region
    private String region;
    // status success/fail
    private String status;

    // record access user device
    private String device;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "IPResultVM{" +
            "zip='" + zip + '\'' +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", org='" + org + '\'' +
            ", timezone='" + timezone + '\'' +
            ", isp='" + isp + '\'' +
            ", query='" + query + '\'' +
            ", regionName='" + regionName + '\'' +
            ", lon='" + lon + '\'' +
            ", lat='" + lat + '\'' +
            ", as='" + as + '\'' +
            ", countryCode='" + countryCode + '\'' +
            ", region='" + region + '\'' +
            ", status='" + status + '\'' +
            ", device='" + device + '\'' +
            '}';
    }
}


