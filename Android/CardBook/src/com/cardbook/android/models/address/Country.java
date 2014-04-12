package com.cardbook.android.models.address;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abdullah on 10/29/13.


 /*
 "CountryList":[
 {"CountryId":2,"CountryName":"Almanya"},
 {"CountryId":1,"CountryName":"Türkiye"}],
 */


public class Country {

    public static String COUNTRY_LIST="CountryList";
    public static String COUNTRY_ID="CountryId";
    public static String COUNTRY_NAME="CountryName";

    private int id;
    private String name;
    private ArrayList<City> cities;

    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Country(JSONObject object){
        this.id=object.optInt(COUNTRY_ID);
        this.name=object.optString(COUNTRY_NAME);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void addCity(City city){
        if(this.cities==null)
            this.cities=new ArrayList<City>();
            this.cities.add(city);
    }
}
