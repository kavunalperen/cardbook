package com.abdullah.cardbook.models.promotion;

import org.json.JSONObject;

/**
 * Created by abdullah on 11/3/13.
 */
public class Coupon {

    public static String ID="ShoppingPromotionId";
    public static String USER_ID="UserId";
    public static String COMPANY_ID="CompanyId";
    public static String USER_CARD_ID="UserCardId";
    public static String SHOPPING_ID="ShoppingId";
    public static String PROMOTION_TYPE="PromotionUsageType";
    public static String COMPANY_PROMOTION_ID="CompanyPromotionId";
    public static String COMPANY_PROMOTION_TEXT="CompanyPromotionText";


    protected int id;
    protected int shoppingId;
    protected int promotionType;
    protected String companyPromotionId;
    protected String companyPromotionText;

    public Coupon(){

    }

    public  Coupon(JSONObject object){

        if(object!=null){
            this.id=object.optInt(ID);
            this.shoppingId=object.optInt(SHOPPING_ID);
            this.promotionType=object.optInt(PROMOTION_TYPE);
            this.companyPromotionId=object.optString(COMPANY_PROMOTION_ID);
            this.companyPromotionText=object.optString(COMPANY_PROMOTION_TEXT);
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
}
