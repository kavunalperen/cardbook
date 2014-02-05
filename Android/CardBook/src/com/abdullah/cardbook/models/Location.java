package com.abdullah.cardbook.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abdullah on 29.01.2014.
 *
 *
 */
public class Location  implements Serializable{

    /*
    "LocationId":1,
    "CompanyId":11,
    "LocationName":"Teşvikiye Şubesi",
    "LocationPhone":"2123274234",
    "LocationAddress":{
    "AddressLine":"Teşvikiye Mh., 34365",
    "CountyName":"Şişli",
    "CityName":"İstanbul",
    "CountryName":"Türkiye"
    },
    "Latitude":41.060455322265625,
    "Longitude":28.994583129882812,
    "Status":1,
    "XUser":0,
    "UUser":0,
    "XDate":"\/Date(1390739848957)\/",
    "UDate":"\/Date(1390739848957)\/"

    */

    public static final String LOCATION="Location";
    public static final String LOCATION_ID="LocationId";
    public static final String COMPANY_ID="CompanyId";
    public static final String NAME="LocationName";
    public static final String PHONE="LocationPhone";
    public static final String ADDRESS="LocationAddress";
    public static final String ADDRESS_LINE="AddressLine";
    public static final String COUNTY_NAME="CountyName";
    public static final String CITY_NAME="CityName";
    public static final String COUNTRY_NAME="CountryName";
    public static final String LATITUDE="Latitude";
    public static final String LONGITUDE="Longitude";

    private int locationId;
    private int companyId;
    private String locationName;
    private String locationPhone;
    private String addressLine;
    private String countyName;
    private String cityName;
    private String countryName;
    private double latitude;
    private double longitude;

    public Location(JSONObject object){
        this.locationId=object.optInt(LOCATION_ID);
        this.companyId=object.optInt(COMPANY_ID);
        this.locationName=object.optString(NAME);
        this.locationPhone=object.optString(PHONE);
        this.latitude=object.optDouble(LATITUDE);
        this.longitude=object.optDouble(LONGITUDE);
        JSONObject jAddress=object.optJSONObject(ADDRESS);
        this.addressLine=jAddress.optString(ADDRESS_LINE);
        this.countyName=jAddress.optString(COUNTY_NAME);
        this.countryName=jAddress.optString(COUNTRY_NAME);
        this.cityName=jAddress.optString(CITY_NAME);
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationPhone() {
        return locationPhone;
    }

    public void setLocationPhone(String locationPhone) {
        this.locationPhone = locationPhone;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
