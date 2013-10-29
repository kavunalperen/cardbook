package com.abdullah.cardbook.models;

public class CardBookUserCard {

    private int userId;
    private int companyId;
    private String companyName;
    private String cardNumber;

    public CardBookUserCard(int userId, int companyId, String cardNumber){
        this.userId=userId;
        this.companyId=companyId;
        this.cardNumber=cardNumber;
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
