package com.abdullah.cardbook.models;

import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.models.promotion.Coupon;
import com.abdullah.cardbook.models.promotion.Credit;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abdullah on 10/26/13.
 */
public class Shopping {

    public static String ID="ShoppingId";
    public static String USER_ID="UserId";
    public static String COMPANY_ID="CompanyId";
    public static String COMPANY="ShoppingCompany";
    public static String DATE="ShoppingDate";
    public static String USER_CARD_ID="UserCardId";
    public static String PRODUCT_LIST="ShoppingProductList";
    public static String USED_PROMOTION_COUPON="UsedShoppingPromotionCoupon";
    public static String USED_PROMOTION_CREDIT="UsedShoppingPromotionCredit";
    public static String WON_PROMOTION_COUPON="WonShoppingPromotionCoupon";
    public static String WON_PROMOTION_CREDIT="WonShoppingPromotionCredit";
    public static String IS_THIS_SHOPPING_SHARED_ON_FACEBOOK="IsThisShoppingSharedOnFacebook";
    public static String IS_THIS_SHOPPING_SHARED_ON_TWITTER="IsThisShoppingSharedOnTwitter";

    private int id;
    private int userId;
    private int companyId;
    private Company company;
    private Date date;
    private ArrayList<Product> productsList;
    private Coupon usedCoupon;
    private Credit usedCredit;
    private Coupon wonCoupon;
    private Credit wonCredit;


    public Shopping(JSONObject object){

        this.id=object.optInt(ID);
        this.company=new Company(object.optJSONObject(COMPANY));
        this.companyId=object.optInt(COMPANY_ID);
        this.date= AppConstants.parseMsTimestampToDate(object.optString(DATE));
        setProductList(object.optJSONArray(PRODUCT_LIST));
        this.usedCoupon=new Coupon(object.optJSONObject(USED_PROMOTION_COUPON));
        this.usedCredit=new Credit(object.optJSONObject(USED_PROMOTION_CREDIT));
        this.wonCoupon=new Coupon(object.optJSONObject(WON_PROMOTION_COUPON));
        this.wonCredit=new Credit(object.optJSONObject(WON_PROMOTION_CREDIT));

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getDate() {
        SimpleDateFormat format=new SimpleDateFormat("dd MMMM yyyy");
        String date=format.format(this.date);
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(ArrayList<Product> productsList) {
        this.productsList = productsList;
    }

    public void setProductList(JSONArray array){

        if(this.productsList==null)
            this.productsList=new ArrayList<Product>();

        if(array==null)
            return;

        for(int i=0;i<array.length();i++){
            JSONObject jsonObject=array.optJSONObject(i);

            Product pro=new Product(jsonObject);

            if(pro!=null);
                this.productsList.add(pro);

        }
    }

    public Coupon getUsedCoupon() {
        return usedCoupon;
    }

    public void setUsedCoupon(Coupon usedCoupon) {
        this.usedCoupon = usedCoupon;
    }

    public Credit getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(Credit usedCredit) {
        this.usedCredit = usedCredit;
    }

    public Coupon getWonCoupon() {
        return wonCoupon;
    }

    public void setWonCoupon(Coupon wonCoupon) {
        this.wonCoupon = wonCoupon;
    }

    public Credit getWonCredit() {
        return wonCredit;
    }

    public void setWonCredit(Credit wonCredit) {
        this.wonCredit = wonCredit;
    }
}
