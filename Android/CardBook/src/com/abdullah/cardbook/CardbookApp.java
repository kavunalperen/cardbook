package com.abdullah.cardbook;

import android.app.Application;

import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.CardBookUserCard;
import com.abdullah.cardbook.models.Company;

import java.util.ArrayList;

/**
 * Created by abdullah on 10/26/13.
 */
public class CardbookApp extends Application {

    private CardBookUser user;
    private ArrayList<CardBookUserCard> userCards;
    private ArrayList<Company> companies;

    private static CardbookApp singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;
        Log.i("app is created");
    }

    public CardbookApp() {
        super();
        Log.i("CardbookApp cınstructor is called ");
    }

    public static CardbookApp getInstance(){
        return singleton;
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
        else
            this.companies.add(company);
    }
}
