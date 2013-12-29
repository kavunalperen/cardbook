package com.abdullah.cardbook.models.address;

/*
"CountryList":[
	{"CountryId":2,"CountryName":"Almanya"},
	{"CountryId":1,"CountryName":"Türkiye"}],

"CityList":[
	{"CityId":2,"CityName":"Ankara","CountryId":1},
	{"CityId":4,"CityName":"Berlin","CountryId":2},
	{"CityId":3,"CityName":"Bursa","CountryId":1},
	{"CityId":1,"CityName":"İstanbul","CountryId":1},
	{"CityId":5,"CityName":"München","CountryId":2}],

"CountyList":[
	{"CountyId":2,"CountyName":"Beşiktaş","CityId":1},
	{"CountyId":5,"CountyName":"Çankaya","CityId":2},
	{"CountyId":1,"CountyName":"Gaziosmanpaşa","CityId":1},
	{"CountyId":4,"CountyName":"Keçiören","CityId":2},
	{"CountyId":7,"CountyName":"Mudanya","CityId":3},
	{"CountyId":6,"CountyName":"Osmangazi","CityId":3},
	{"CountyId":3,"CountyName":"Şişli","CityId":1
 */

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Address {

    public static String COUNTRY_ID="CountryId";
    public static String CITY_ID="CityId";
    public static String COUNTY_ID="CountyId";
    public static String COUNTRY="Country";
    public static String CITY="City";
    public static String COUNTY="County";
    public static String ADDRESS_LINE="AddressLine";
    public static String ADDRESS="Address";

    public static ArrayList<Country> countArray;

	private int countryId;  // Ülke
	private int cityId;     // Şehir
	private int countId;    // İlçe

    private String country;  // Ülke
    private String city;     // Şehir
    private String county;
	private String addressLine;

    private String[] locateFromFacebook;

    public Address(){}

    public Address(JSONObject object){

        if(object!=null){
            this.countryId=object.optInt(COUNTRY_ID);
            this.cityId=object.optInt(CITY_ID);
            this.countId=object.optInt(COUNTY_ID);

            this.country=object.optString(COUNTRY);
            this.city=object.optString(CITY);
            this.county=object.optString(COUNTY);

            this.addressLine=object.optString(ADDRESS_LINE,"");


        }
    }

    public Address(String locate){
        String[] array=locate.trim().split(",");


        this.locateFromFacebook=array;

    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountId() {
        return countId;
    }

    public void setCountId(int countId) {
        this.countId = countId;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }


    public String[] getLocateFromFacebook() {
        return locateFromFacebook;
    }
}
