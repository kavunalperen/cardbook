package com.abdullah.cardbook.models.address;

import org.json.JSONObject;

/**
 * Created by abdullah on 10/29/13.
 */

/*
"CountyList":[
	{"CountyId":2,"CountyName":"Beşiktaş","CityId":1},
	{"CountyId":5,"CountyName":"Çankaya","CityId":2},
	{"CountyId":1,"CountyName":"Gaziosmanpaşa","CityId":1},
	{"CountyId":4,"CountyName":"Keçiören","CityId":2},
	{"CountyId":7,"CountyName":"Mudanya","CityId":3},
	{"CountyId":6,"CountyName":"Osmangazi","CityId":3},
	{"CountyId":3,"CountyName":"Şişli","CityId":1}]
 */
public class County {

    public static String COUNTY_LIST="CountyList";
    public static String COUNTY_ID="CountyId";
    public static String COUNTY_NAME="CountyName";

    private int id;
    private String name;
    private int cityId;

    public County(int id, String name, int cityId) {
        this.id = id;
        this.name = name;
        this.cityId = cityId;
    }

    public County(JSONObject object){
        this.id=object.optInt(COUNTY_ID);
        this.name=object.optString(COUNTY_NAME);
        this.cityId=object.optInt(City.CITY_ID);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCityId() {
        return cityId;
    }
}
