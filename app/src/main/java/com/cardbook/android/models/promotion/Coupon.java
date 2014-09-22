package com.cardbook.android.models.promotion;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abdullah on 11/3/13.
 */
public class Coupon implements Serializable {

    public static String ID="ShoppingPromotionId";
    public static String USER_ID="UserId";
    public static String COMPANY_ID="CompanyId";
    public static String USER_CARD_ID="UserCardId";
    public static String SHOPPING_ID="ShoppingId";
    public static String PROMOTION_TYPE="PromotionUsageType";
    public static String COMPANY_PROMOTION_ID="CompanyPromotionId";
    public static String COMPANY_PROMOTION_TEXT="CompanyPromotionText";
    public static String COMPANY_PROMOTION_DESC="CompanyPromotionDescription";


    protected int id;
    protected int shoppingId;
    protected int promotionType;
    protected String companyPromotionId;
    protected String companyPromotionText, companyPromotionDescrpition;

    public Coupon(){

    }

    public  Coupon(JSONObject object){

        if(object!=null){
            this.id=object.optInt(ID);
            this.shoppingId=object.optInt(SHOPPING_ID);
            this.promotionType=object.optInt(PROMOTION_TYPE);
            this.companyPromotionId=object.optString(COMPANY_PROMOTION_ID);
            this.companyPromotionText=object.optString(COMPANY_PROMOTION_TEXT);
            this.companyPromotionDescrpition=object.optString(COMPANY_PROMOTION_DESC);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(int shoppingId) {
        this.shoppingId = shoppingId;
    }

    public int getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(int promotionType) {
        this.promotionType = promotionType;
    }

    public String getCompanyPromotionId() {
        return companyPromotionId;
    }

    public void setCompanyPromotionId(String companyPromotionId) {
        this.companyPromotionId = companyPromotionId;
    }

    public String getCompanyPromotionText() {
        return companyPromotionText;
    }

    public void setCompanyPromotionText(String companyPromotionText) {
        this.companyPromotionText = companyPromotionText;
    }

    public String getCompanyPromotionDescrpition() {
        return companyPromotionDescrpition;
    }

    public void setCompanyPromotionDescrpition(String companyPromotionDescrpition) {
        this.companyPromotionDescrpition = companyPromotionDescrpition;
    }
}
