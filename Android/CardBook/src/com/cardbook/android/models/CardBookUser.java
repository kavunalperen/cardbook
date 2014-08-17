package com.cardbook.android.models;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Log;
import com.cardbook.android.models.address.Address;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CardBookUser implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String ID="UserId";
    public static String FACEBOOK_ID="FacebookId";
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
    public static String SCREEN_WIDTH="BarcodeWidth";
    public static String SCREEN_HEIGHT="BarcodeHeight";

    public static String WANT_NOTIFICATION="UserWantNotification";

    private String id;
    private String facebookId;
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
        this.facebookId=object.optString(FACEBOOK_ID);
        this.deviceId=object.optString(DEVICE_ID);
        this.name=object.optString(NAME);
        this.surname=object.optString(SURNAME);
        this.email=object.optString(EMAIL);
        this.birthDate= AppConstants.parseMsTimestampToDate(object.optString(BIRTH_DATE));
        this.profilPhotoUrl="http://graph.facebook.com/"+this.facebookId+"/picture?style=large";
        this.phone1=object.optString(PHONE_1);
        this.phone2=object.optString(PHONE_2);
        this.gender=object.optString(GENDER);
        this.barcodeUrl=object.optString(BARCODE_URL);

        this.addres=new Address(object.optJSONObject(Address.ADDRESS));
    }

    public Map<String,String> getUserInfoAsDict(Context context){

        int width;
        int height;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        if(android.os.Build.VERSION.SDK_INT >= 13){


        Point size = new Point();
        display.getSize(size);

        width = size.x;
        height = size.y;

        }
        else{

            width = display.getWidth();  // deprecated
            height = display.getHeight();
        }


        Map<String,String> list=new HashMap<String, String>();

        list.put(ID,this.id);
        list.put(FACEBOOK_ID,this.facebookId);
        list.put(DEVICE_ID,this.deviceId);
        list.put(NAME,this.name);
        list.put(SURNAME,this.surname);
        list.put(EMAIL,this.email);

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        list.put(BIRTH_DATE,format.format(birthDate));
        list.put(PROFILE_PHOTO_URL,this.profilPhotoUrl);
        list.put(PHONE_1,this.phone1);
        list.put(PHONE_2,this.phone2);
        list.put(GENDER,this.gender);
        list.put(BARCODE_URL,this.barcodeUrl);
        list.put(SCREEN_WIDTH,String.valueOf(width));
        list.put(SCREEN_HEIGHT, String.valueOf(height));

//        ArrayList<NameValuePair> addressList=new ArrayList<NameValuePair>();
        list.put(Address.COUNTRY_ID,Integer.toString(addres.getCountryId()));
        list.put(Address.CITY_ID,Integer.toString(addres.getCityId()));
        list.put(Address.COUNTY_ID,Integer.toString(addres.getCountId()));
        list.put(Address.ADDRESS_LINE,addres.getAddressLine());

//      list.add(new BasicNameValuePair(ADDRESS,addressList.toString()));

        return list;
    }

    public JSONObject toJSON(){
        JSONObject jsonObject=new JSONObject();
        try{
            jsonObject.put(ID,this.id);
            jsonObject.put(FACEBOOK_ID,this.facebookId);
            jsonObject.put(DEVICE_ID,this.deviceId);
            jsonObject.put(NAME,this.name);
            jsonObject.put(SURNAME,this.surname);
            jsonObject.put(EMAIL,this.email);
            jsonObject.put(BIRTH_DATE,this.birthDate.getTime());
            jsonObject.put(PROFILE_PHOTO_URL,this.profilPhotoUrl);
            jsonObject.put(PHONE_1,this.phone1);
            jsonObject.put(PHONE_2,this.phone2);
            jsonObject.put(GENDER,this.gender);
            jsonObject.put(BARCODE_URL,this.barcodeUrl);

            JSONObject address=new JSONObject();

            address.put(Address.COUNTRY_ID,Integer.toString(addres.getCountryId()));
            address.put(Address.CITY_ID,Integer.toString(addres.getCityId()));
            address.put(Address.COUNTY_ID,Integer.toString(addres.getCountId()));

            address.put(Address.COUNTRY,addres.getCountry());
            address.put(Address.CITY,addres.getCity());
            address.put(Address.COUNTY,addres.getCounty());

            address.put(Address.ADDRESS_LINE,addres.getAddressLine());
            jsonObject.put(Address.ADDRESS,address);

        }
        catch (JSONException e){
            e.printStackTrace();
        }

        Log.i("toJson: "+jsonObject);
        return jsonObject;
    }

    public String getId() {
        return id;
//        return "4";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
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
