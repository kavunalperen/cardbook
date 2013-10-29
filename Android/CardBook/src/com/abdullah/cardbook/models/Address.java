package com.abdullah.cardbook.models;

public class Address {

    public static String[] countryArray;
    public static String[] cityArray;
    public static String[] countArray;

	private int countryId;  // Ülke
	private int cityId;     // Şehir
	private int countId;    // İlçe
	private String addressLine;

    private String[] locateFromFacebook;

    public Address(String[] locate){
        this.locateFromFacebook=locate;

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
}
