package com.cardbook.android.models;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abdullah on 04.02.2014.
 */
public class CompanyInfo implements Serializable {

/*
    "Data":{
        "CompanyInfoId":1,
                "CompanyId":11,
                "AboutText":"2004 yılında ilk mağazasını Eminönü’nde açarak toptan ve perakende kahve satışlarına başlayan Kahve Dünyası, hızla büyüyerek sektörün önemli markalarından biri haline geldi.",
                "CallCenterPhoneNumber":"212440700",
                "InfoEmail":"info@kahvedunyasi.com",
                "HeadOfficeAddress":{
            "AddressLine":"Ömer Avni Mahallesi İnebolu Sokak",
                    "CountyName":"Beyoğlu",
                    "CityName":"İstanbul",
                    "CountryName":"Türkiye"
        },
        "HeadOfficeLatitude":41.045181274414062,
                "HeadOfficeLongitude":28.992523193359375,
                "Status":1,
                "XUser":0,
                "UUser":0,
                "XDate":"\/Date(1391440733303)\/",
                "UDate":"\/Date(1391440733303)\/"
    },
 */

    public static final String COMPANY_INFO="CompnayInfo";
    public static final String ABOUT_TEXT="AboutText";
    public static final String PHONE="CallCenterPhoneNumber";
    public static final String MAIL="InfoEmail";
    public static final String HEAD_OFFICE_ADDRESS="HeadOfficeAddress";
    public static final String LATITUDE="HeadOfficeLatitude";
    public static final String LONGITUDE="HeadOfficeLongitude";

    private int companyId;
    private String aboutText;
    private String phone, mail;
    private String addressLine;
    private String countyName;
    private String cityName;
    private String countryName;
    private double latitude;
    private double longitude;

    public CompanyInfo(JSONObject object){
        this.companyId=object.optInt(Location.COMPANY_ID);
        this.aboutText=object.optString(ABOUT_TEXT);
        this.phone=object.optString(PHONE);
        this.mail=object.optString(MAIL);
        this.latitude=object.optDouble(LATITUDE);
        this.longitude=object.optDouble(LONGITUDE);
        JSONObject jAddress=object.optJSONObject(HEAD_OFFICE_ADDRESS);
        this.addressLine=jAddress.optString(Location.ADDRESS_LINE);
        this.countyName=jAddress.optString(Location.COUNTY_NAME);
        this.countryName=jAddress.optString(Location.COUNTRY_NAME);
        this.cityName=jAddress.optString(Location.CITY_NAME);
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getAboutText() {
        return aboutText;
    }

    public void setAboutText(String aboutText) {
        this.aboutText = aboutText;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
