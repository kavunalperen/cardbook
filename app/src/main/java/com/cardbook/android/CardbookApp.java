package com.cardbook.android;

import android.app.Application;

import com.android.volley.toolbox.ImageLoader;
import com.cardbook.android.activities.MainActivity;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.BitmapLruCache;
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
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by abdullah on 10/26/13.
 */
public class CardbookApp extends Application {

	public static String USER="user";
	public static String USER_CARD="userCard";
	public static String COMPANIES="companies";
	public static String COUNTRIES="countries";
	public static String CAMPAIGNS="campaings";
	public static String SHOPPINGS="shoppings";
	public static String LOCATION_LIST="locationList";
	public static String COMPANY_INFO_LIST="companyInfoList";
	
    private static CardBookUser user;
    private static ArrayList<CardBookUserCard> userCards;
    private static ArrayList<Company> companies;
    private static ArrayList<Country> countries;
    private static ArrayList<Campaign> campaigns;
    private static ArrayList<Shopping> shoppings;
    private static HashMap<Integer, ArrayList<Location>> locationsList;
    private static HashMap<Integer, CompanyInfo> companyInfoList;

    private static CardbookApp singleton;
    private RequestQueue requestQuee;
    private ImageLoader imageLoader;
    BitmapLruCache cache;

    private static Tracker tracker;

    public CardbookApp() {
        super();
        Log.i("CardbookApp constructor is called ");
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        requestQuee= Volley.newRequestQueue(getApplicationContext());
        cache=new BitmapLruCache(AppConstants.getCacheSize(this));
        imageLoader=new ImageLoader(requestQuee, this.cache);






        try{
            if(AppConstants.getUserInformation(this)!=null)
                CardbookApp.user= new CardBookUser(new JSONObject(AppConstants.getUserInformation(this)));
        }
        catch(JSONException e){
           e.printStackTrace();
        }
        Log.i("app is created");
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
        CardbookApp.user = user;
    }

    public ArrayList<CardBookUserCard> getUserCards() {
        return CardbookApp.userCards;
    }

    public CardBookUserCard getUserCardsAtIndex(int index) {
        return CardbookApp.userCards.get(index);
    }
    public void setUserCards(ArrayList<CardBookUserCard> userCards) {
        CardbookApp.userCards = userCards;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(ArrayList<Company> companies) {
        CardbookApp.companies = companies;
    }

    public void addCompany(Company company){
        if(CardbookApp.companies==null)
            CardbookApp.companies=new ArrayList<Company>();
            CardbookApp.companies.add(company);
    }

    public ArrayList<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(ArrayList<Campaign> campaigns) {
        CardbookApp.campaigns = campaigns;
    }

    public void addCampaign(Campaign campign){
        if(CardbookApp.campaigns==null)
            CardbookApp.campaigns=new ArrayList<Campaign>();
        CardbookApp.campaigns.add(campign);
    }

    public ArrayList<Shopping> getShoppings() {
        return shoppings;
    }

    public void setShoppings(ArrayList<Shopping> shoppings) {
        CardbookApp.shoppings = shoppings;
    }

    public void addShoppings(Shopping shopping){
        if(CardbookApp.shoppings==null)
            CardbookApp.shoppings=new ArrayList<Shopping>();
        CardbookApp.shoppings.add(shopping);
    }

    public ArrayList<Country> getCountries() {
        if (CardbookApp.countries==null || CardbookApp.countries.size()==0){
            try { // SON HATA
                MainActivity.convertAddresList(new JSONObject(AppConstants.getAdressList(getApplicationContext())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return countries;
    }

    public void setCountries(ArrayList<Country> countries) {
        CardbookApp.countries = countries;
    }

    public void addCountry(Country country){
        if(CardbookApp.countries==null){
            CardbookApp.countries=new ArrayList<Country>();
//            this.countries.add(setDummyCountry());
        }
            CardbookApp.countries.add(country);
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
        CardbookApp.locationsList = locationsList;
    }

    public void addLocation(int companyId, Location location){

        if(CardbookApp.locationsList==null)
            locationsList=new HashMap<Integer, ArrayList<Location>>();

        if(CardbookApp.locationsList.containsKey(companyId)){
            ArrayList<Location> locations=CardbookApp.locationsList.get(companyId);
            if(locations==null){
                locations=new ArrayList<Location>();
            }
            locations.add(location);
        }
        else{
            ArrayList<Location> locations=new ArrayList<Location>();
            locations.add(location);
            CardbookApp.locationsList.put(companyId, locations);
        }
    }

    public static HashMap<Integer, CompanyInfo> getCompanyInfoList() {
		return companyInfoList;
	}

	public static void setCompanyInfoList(
			HashMap<Integer, CompanyInfo> companyInfoList) {
		CardbookApp.companyInfoList = companyInfoList;
	}

	public ArrayList<Location> getLocationsForCompany(int companyId){
        if(locationsList!=null)
            return CardbookApp.locationsList.get(companyId);
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

    public synchronized Tracker getTracker() {

        if(tracker==null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            tracker = analytics.newTracker(R.xml.global_tracker);

        }


        return tracker;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }
}
