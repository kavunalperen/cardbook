package com.cardbook.android.models.address;

/*
"CountryList":[
	{"CountryId":2,"CountryName":"Almanya"},
	{"CountryId":1,"CountryName":"TÃ¼rkiye"}],

"CityList":[
	{"CityId":2,"CityName":"Ankara","CountryId":1},
	{"CityId":4,"CityName":"Berlin","CountryId":2},
	{"CityId":3,"CityName":"Bursa","CountryId":1},
	{"CityId":1,"CityName":"Ä°stanbul","CountryId":1},
	{"CityId":5,"CityName":"MÃ¼nchen","CountryId":2}],

"CountyList":[
	{"CountyId":2,"CountyName":"BeÅŸiktaÅŸ","CityId":1},
	{"CountyId":5,"CountyName":"Ã‡ankaya","CityId":2},
	{"CountyId":1,"CountyName":"GaziosmanpaÅŸa","CityId":1},
	{"CountyId":4,"CountyName":"KeÃ§iÃ¶ren","CityId":2},
	{"CountyId":7,"CountyName":"Mudanya","CityId":3},
	{"CountyId":6,"CountyName":"Osmangazi","CityId":3},
	{"CountyId":3,"CountyName":"Å�iÅŸli","CityId":1
 */

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Address  implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String COUNTRY_ID="CountryId";
    public static String CITY_ID="CityId";
    public static String COUNTY_ID="CountyId";
    public static String COUNTRY="Country";
    public static String CITY="City";
    public static String COUNTY="County";
    public static String ADDRESS_LINE="AddressLine";
    public static String ADDRESS="Address";

    public static ArrayList<Country> countArray;

	private int countryId;  // Ãœlke
	private int cityId;     // Å�ehir
	private int countId;    // Ä°lÃ§e

    private String country;  // Ãœlke
    private String city;     // Å�ehir
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
