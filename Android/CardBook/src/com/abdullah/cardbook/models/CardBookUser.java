package com.abdullah.cardbook.models;

import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.address.Address;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public static String BARCODE_URL="UserBarcodeUrl";

    public static String COUNTRY_ID="CountryId";
    public static String CITY_ID="CityId";
    public static String COUNTY_ID="CountyId";
    public static String ADDRESS_LINE="AddressLine";
    public static String ADDRESS="Address";


    public static String WANT_NOTIFICATION="UserWantNotification";

    private String id;
	private String deviceId;
	private String name;
    private String surname;
	private String email;
	private Date birthDate;
	private String profilPhotoUrl;
	private String phone1;
	private String phone2;
	private String gender; // M or F,
    private String barcodeUrl;
	private Address addres;


    public CardBookUser(){


    }
    public CardBookUser(JSONObject object){
        this.id=object.optString(ID);
        this.deviceId=object.optString(DEVICE_ID);
        this.name=object.optString(NAME);
        this.surname=object.optString(SURNAME);
        this.email=object.optString(EMAIL);
        this.birthDate= AppConstants.parseMsTimestampToDate(object.optString(BIRTH_DATE));
        this.profilPhotoUrl="http://graph.facebook.com/"+id+"/picture?style=large";
        this.phone1=object.optString(PHONE_1);
        this.phone2=object.optString(PHONE_2);
        this.gender=object.optString(GENDER);
        this.barcodeUrl=object.optString(BARCODE_URL);
        this.addres=new Address(object.optJSONObject(ADDRESS));
    }

    public Map<String,String> getUserInfoAsDict(){

        Map<String,String> list=new HashMap<String, String>();

        list.put(ID,this.id);
        list.put(DEVICE_ID,this.deviceId);
        list.put(NAME,this.name);
        list.put(SURNAME,this.surname);
        list.put(EMAIL,this.email);
        list.put(BIRTH_DATE,getBirthDate());
        list.put(PROFILE_PHOTO_URL,this.profilPhotoUrl);
        list.put(PHONE_1,this.phone1);
        list.put(PHONE_2,this.phone2);
        list.put(GENDER,this.gender);
        list.put(BARCODE_URL,this.barcodeUrl);

//        ArrayList<NameValuePair> addressList=new ArrayList<NameValuePair>();
        list.put(COUNTRY_ID,Integer.toString(addres.getCountryId()));
        list.put(CITY_ID,Integer.toString(addres.getCityId()));
        list.put(COUNTY_ID,Integer.toString(addres.getCountId()));
        list.put(ADDRESS_LINE,addres.getAddressLine());

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
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        return format.format(this.birthDate);
    }

    public void setBirthDate(String birthDate) {

        birthDate=birthDate.replace("/","-");
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        try {
            this.birthDate=format.parse(birthDate);
        } catch (ParseException e) {

            e.printStackTrace();
        }

    }

    public void setBirthDateFromJson(String birthDate) {



        Date date=AppConstants.parseMsTimestampToDate(birthDate);
        this.birthDate=date;


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

    public String getBarcodeUrl() {
        return barcodeUrl;
    }

    public void setBarcodeUrl(String barcodeUrl) {
        this.barcodeUrl = barcodeUrl;
    }

    public Address getAddres() {
        return addres;
    }

    public void setAddres(Address addres) {
        this.addres = addres;
    }
}
