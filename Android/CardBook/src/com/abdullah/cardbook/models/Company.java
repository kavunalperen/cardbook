package com.abdullah.cardbook.models;

import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.promotion.Coupon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    public static String COMPANY_ID="CompanyId";
    public static String SHOPPING_PROMOTION_CREDIT_LIST="ShoppingPromotionCreditList";
    private static String U_DATE= "UDate";
    private static String COMPANY_NAME="CompanyName";
    private static String COMPANY_CODE="CompanyCode";
    private static String COMPANY_DESCRIPTION="CompanyDescription";
    public static String SHOPPING_PROMOTION_COUPON_LIST="ShoppingPromotionCouponList";
    public static String USER_WANT_NOTIFICATION="UserWantNotification";
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
    private ArrayList<Coupon> couponList;
    private boolean isUserWantNotification;


    public Company(JSONObject object){

        this.companyId=object.optInt(COMPANY_ID);
        this.companyDecription=object.optString(COMPANY_DESCRIPTION);
        this.companyCode=object.optString(COMPANY_CODE);
        this.companyName=object.optString(COMPANY_NAME);
        this.companyLogoURL=object.optString(COMPANY_LOGO);
        this.companyDetail=object.optString(COMPANY_DETAIL);

        JSONArray couponArray=object.optJSONArray(SHOPPING_PROMOTION_COUPON_LIST);

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

    public ArrayList<Coupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(ArrayList<Coupon> couponList) {
        this.couponList = couponList;
    }

    public void addCouponList(Coupon coupon){
        if(this.couponList==null)
            this.couponList=new ArrayList<Coupon>();
        this.couponList.add(coupon);
    }

    public void setCouponList(JSONArray list){
        Log.i("setCouponList");
        if(list!=null){
            Log.i("setCouponList list not null");
            int length=list.length();
            ArrayList<Coupon> couponList=new ArrayList<Coupon>();
            for(int i=0; i<length;i++){
                Coupon c=new Coupon(list.optJSONObject(i));
                couponList.add(c);

            }
            this.couponList= couponList;
            Log.i("setCouponList done");
        }

        Log.i("setCouponList is null");
    }

    public boolean isUserWantNotification() {
        return isUserWantNotification;
    }

    public void setUserWantNotification(boolean userWantNotification) {
        isUserWantNotification = userWantNotification;
    }


}
