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
            this.countryId=object.optInt("CountryId");
            this.cityId=object.optInt("CityId");
            this.countId=object.optInt("CountyId");
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
