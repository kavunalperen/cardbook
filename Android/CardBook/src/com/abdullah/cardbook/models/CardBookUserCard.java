package com.abdullah.cardbook.models;

import java.util.HashMap;
import java.util.Map;

public class CardBookUserCard {

    private static String USER_ID="UserId";
    private static String COMPANY_ID="CompanyId";
    private static String CARD_NUMBER="CardNumber";

    private int userId;
    private int companyId;
    private String companyName;
    private String cardNumber;

    public CardBookUserCard(int userId, int companyId, String cardNumber){
        this.userId=userId;
        this.companyId=companyId;
        this.cardNumber=cardNumber;
    }

    public Map<String, String> getCardAsMap(){
        Map<String, String> items=new HashMap<String, String>();
        items.put(USER_ID,String.valueOf(this.userId));
        items.put(COMPANY_ID, String.valueOf(this.companyId));
        items.put(CARD_NUMBER,this.cardNumber);

        return items;

    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
