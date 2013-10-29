package com.abdullah.cardbook.models;

import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Date;

public class CardBookUser {

    public static String ID="FacebookId";
    public static String DEVICE_ID="MobileDeviceId";
    public static String NAME="Name";
    public static String SURNAME="Surname";
    public static String EMAIL="Email";
    public static String BIRTH_DATE="BirthDate";
    public static String PROFILE_PHOTO_URL="ProfilePhotoUrl";
    public static String PHONE_1="Phone1";
    public static String PHONE_2="Phone2";
    public static String GENDER="Gender";

    public static String COUNTRY_ID="CountryId";
    public static String CITY_ID="CityId";
    public static String COUNTY_ID="CountyId";
    public static String ADDRESS_LINE="AddressLine";
    public static String ADDRESS="Address";

    private String id;
	private String deviceId;
	private String name;
    private String surname;
	private String email;
	private String birthDate;
	private String profilPhotoUrl;
	private String phone1;
	private String phone2;
	private String gender; // M or F
	private Address addres;

    public ArrayList<NameValuePair> getUserInfoAsDict(){

        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(ID,this.id));
        list.add(new BasicNameValuePair(DEVICE_ID,this.deviceId));
        list.add(new BasicNameValuePair(NAME,this.name));
        list.add(new BasicNameValuePair(SURNAME,this.surname));
        list.add(new BasicNameValuePair(EMAIL,this.email));
        list.add(new BasicNameValuePair(BIRTH_DATE,this.birthDate));
        list.add(new BasicNameValuePair(PROFILE_PHOTO_URL,this.profilPhotoUrl));
        list.add(new BasicNameValuePair(PHONE_1,this.phone1));
        list.add(new BasicNameValuePair(PHONE_2,this.phone2));
        list.add(new BasicNameValuePair(GENDER,this.gender));

//        ArrayList<NameValuePair> addressList=new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair(COUNTRY_ID,Integer.toString(addres.getCountryId())));
        list.add(new BasicNameValuePair(CITY_ID,Integer.toString(addres.getCityId())));
        list.add(new BasicNameValuePair(COUNTY_ID,Integer.toString(addres.getCountId())));
        list.add(new BasicNameValuePair(ADDRESS_LINE,"karakaya mah"));

//      list.add(new BasicNameValuePair(ADDRESS,addressList.toString()));



        return list;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        birthDate=birthDate.replace("/","-");
        this.birthDate = birthDate;
    }

    public String getProfilPhotoUrl() {
        return profilPhotoUrl;
    }

    public void setProfilPhotoUrl(String profilPhotoUrl) {

        this.profilPhotoUrl = profilPhotoUrl;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        Log.i("Gender: "+gender);
        if(gender.equalsIgnoreCase("Female"))
            gender="F";
        else
            gender="M";
        this.gender = gender;
    }

    public Address getAddres() {
        return addres;
    }

    public void setAddres(Address addres) {
        this.addres = addres;
    }
}
