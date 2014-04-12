package com.cardbook.android;

import android.app.Application;

import com.cardbook.android.activities.MainActivity;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Log;
import com.cardbook.android.models.Campaign;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.models.CardBookUserCard;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.CompanyInfo;
import com.cardbook.android.models.Location;
import com.cardbook.android.models.Shopping;
import com.cardbook.android.models.address.City;
import com.cardbook.android.models.address.Country;
import com.cardbook.android.models.address.County;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abdullah on 10/26/13.
 */
public class CardbookApp extends Application {

    private CardBookUser user;
    private ArrayList<CardBookUserCard> userCards;
    private ArrayList<Company> companies;
    private ArrayList<Country> countries;
    private ArrayList<Campaign> campaigns;
    private ArrayList<Shopping> shoppings;
    private HashMap<Integer, ArrayList<Location>> locationsList;
    private HashMap<Integer, CompanyInfo> companyInfoList;

    private static CardbookApp singleton;
    private RequestQueue requestQuee;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        requestQuee= Volley.newRequestQueue(getApplicationContext());

        try{
            if(AppConstants.getUserInformation(this)!=null)
                this.user= new CardBookUser(new JSONObject(AppConstants.getUserInformation(this)));
        }
        catch(JSONException e){
           e.printStackTrace();
        }
        Log.i("app is created");
    }

    public CardbookApp() {
        super();
        Log.i("CardbookApp constructor is called ");
    }

    public static CardbookApp getInstance(){
        return singleton;
    }

    public RequestQueue getRequestQuee() {
        return requestQuee;
    }

    public CardBookUser getUser() {
        return user;
    }

    public void setUser(CardBookUser user) {
        this.user = user;
    }

    public ArrayList<CardBookUserCard> getUserCards() {
        return this.userCards;
    }

    public CardBookUserCard getUserCardsAtIndex(int index) {
        return this.userCards.get(index);
    }
    public void setUserCards(ArrayList<CardBookUserCard> userCards) {
        this.userCards = userCards;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        this.companies = companies;
    }

    public void addCompany(Company company){
        if(this.companies==null)
            this.companies=new ArrayList<Company>();
            this.companies.add(company);
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(ArrayList<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public void addCampaign(Campaign campign){
        if(this.campaigns==null)
            this.campaigns=new ArrayList<Campaign>();
        this.campaigns.add(campign);
    }

    public ArrayList<Shopping> getShoppings() {
        return shoppings;
    }

    public void setShoppings(ArrayList<Shopping> shoppings) {
        this.shoppings = shoppings;
    }

    public void addShoppings(Shopping shopping){
        if(this.shoppings==null)
            this.shoppings=new ArrayList<Shopping>();
        this.shoppings.add(shopping);
    }

    public ArrayList<Country> getCountries() {
        if (this.countries==null || this.countries.size()==0){
            try { // SON HATA
                MainActivity.convertAddresList(new JSONObject(AppConstants.getAdressList(getApplicationContext())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public void addCountry(Country country){
        if(this.countries==null){
            this.countries=new ArrayList<Country>();
//            this.countries.add(setDummyCountry());
        }
            this.countries.add(country);
    }


    public Country setDummyCountry(){
        Country country=new Country(0,"Ülke");
        City city=new City(0,"Şehir",0);
        County county=new County(0,"İlçe",0);
        city.addCounty(county);
        country.addCity(city);

        return country;
    }

    public HashMap<Integer, ArrayList<Location>> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(HashMap<Integer, ArrayList<Location>> locationsList) {
        this.locationsList = locationsList;
    }

    public void addLocation(int companyId, Location location){

        if(this.locationsList==null)
            locationsList=new HashMap<Integer, ArrayList<Location>>();

        if(this.locationsList.containsKey(companyId)){
            ArrayList<Location> locations=this.locationsList.get(companyId);
            if(locations==null){
                locations=new ArrayList<Location>();
            }
            locations.add(location);
        }
        else{
            ArrayList<Location> locations=new ArrayList<Location>();
            locations.add(location);
            this.locationsList.put(companyId, locations);
        }
    }

    public ArrayList<Location> getLocationsForCompany(int companyId){
        if(locationsList!=null)
            return this.locationsList.get(companyId);
        else
            return null;
    }

    public CompanyInfo getCompanyInfoForCompany(int companyId) {
        if(companyInfoList!=null)
        return companyInfoList.get(companyId);
        else
            return null;
    }

    public void addCompanyInfo(int companyId, CompanyInfo info){
        if(companyInfoList==null)
            companyInfoList=new HashMap<Integer, CompanyInfo>();
        companyInfoList.put(companyId,info);
    }
}
