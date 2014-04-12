package com.cardbook.android.models.address;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abdullah on 10/29/13.
 */
/*
"CityList":[
        {"CityId":2,"CityName":"Ankara","CountryId":1},
        {"CityId":4,"CityName":"Berlin","CountryId":2},
        {"CityId":3,"CityName":"Bursa","CountryId":1},
        {"CityId":1,"CityName":"İstanbul","CountryId":1},
        {"CityId":5,"CityName":"München","CountryId":2}],
        */
public class City {

    public static String CIYTY_LIST="CityList";
    public static String CITY_ID="CityId";
    public static String CITY_NAME="CityName";

    private int id;
    private String name;
    private int countryId;
    private ArrayList<County> counties;

    public City(int id, String name, int countryId){

        this.id=id;
        this.name=name;
        this.countryId=countryId;
    }

    public City(JSONObject object){
        this.countryId=object.optInt(Country.COUNTRY_ID);
        this.id=object.optInt(CITY_ID);
        this.name=object.optString(CITY_NAME);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCountryId() {
        return countryId;
    }

    public ArrayList<County> getCounties() {
        return counties;
    }

    public void setCounties(ArrayList<County> counties) {
        this.counties = counties;
    }

    public void addCounty(County county) {
        if(this.counties==null)
            this.counties=new ArrayList<County>();
            this.counties.add(county);
    }
}
