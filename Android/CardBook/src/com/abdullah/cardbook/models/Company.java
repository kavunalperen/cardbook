package com.abdullah.cardbook.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdullah on 10/26/13.
 */
public class Company {

    /* Gelen Bilgi
        "UUser":0,
        "CardbookUserCard":null,
        "CompanyId":1,
        "ShoppingPromotionCreditList":null,
        "UDate":"\/Date(1372598605403)\/",
        "CompanyDescription":"Test company details 1",
        "CompanyCode":"company1",
        "CompanyName":"Test Company 1",
        "ShoppingPromotionCouponList":null,
        "UserWantNotification":false,
        "Status":1,
        "CompanyLogo":"http:\/\/test.mycardbook.gen.tr\/DynamicImages\/CompanyLogo\/1.jpg",
        "CompanyDetail":null,
        "CompanyPassword":"pass1",
        "XUser":0,
        "CompanyLocationList":null,
        "XDate":"\/Date(1372598605403)\/"
     */

    private static String U_USER="UUser";
    private static String CARDBOOK_USER_CARD="CardbookUserCard";
    private static String COMPANY_ID="CompanyId";
    private static String SHOPPING_PROMOTION_CREDIT_LIST="ShoppingPromotionCreditList";
    private static String U_DATE= "UDate";
    private static String COMPANY_NAME="CompanyName";
    private static String COMPANY_CODE="CompanyCode";
    private static String COMPANY_DESCRIPTION="CompanyDescription";
    private static String SHOPPING_PROMOTION_COUPON_LIST="CompanyCode";
    private static String USER_WANT_NOTIFICATION="UserWantNotification";
    private static String STATUS="Status";
    private static String COMPANY_LOGO="CompanyLogo";
    private static String COMPANY_DETAIL="CompanyDetail";
    private static String COMPANY_PASSWORD="CompanyPassword";
    private static String X_USER="XUser";
    private static String COMPANY_LOCATION_LIST="CompanyLocationList";
    private static String X_DATE="XDate";

    private int companyId;
    private String companyDecription;
    private String companyCode;
    private String companyName;
    private String companyLogoURL;
    private String companyDetail;
    private boolean isUserWantNotification;


    public Company(JSONObject object){

        this.companyId=object.optInt(COMPANY_ID);
        this.companyDecription=object.optString(COMPANY_DESCRIPTION);
        this.companyCode=object.optString(COMPANY_CODE);
        this.companyName=object.optString(COMPANY_NAME);
        this.companyLogoURL=object.optString(COMPANY_LOGO);
        this.companyDetail=object.optString(COMPANY_DETAIL);
        this.isUserWantNotification=object.optBoolean(USER_WANT_NOTIFICATION,false);

    }


    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyDecription() {
        return companyDecription;
    }

    public void setCompanyDecription(String companyDecription) {
        this.companyDecription = companyDecription;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogoURL() {
        return companyLogoURL;
    }

    public void setCompanyLogoURL(String companyLogoURL) {
        this.companyLogoURL = companyLogoURL;
    }

    public String getCompanyDetail() {
        return companyDetail;
    }

    public void setCompanyDetail(String companyDetail) {
        this.companyDetail = companyDetail;
    }

    public boolean isUserWantNotification() {
        return isUserWantNotification;
    }

    public void setUserWantNotification(boolean userWantNotification) {
        isUserWantNotification = userWantNotification;
    }
}
