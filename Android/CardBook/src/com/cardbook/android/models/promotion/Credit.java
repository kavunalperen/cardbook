package com.cardbook.android.models.promotion;

import org.json.JSONObject;

/**
 * Created by abdullah on 11/3/13.
 */
public class Credit extends Coupon {

    public static String COMPANY_PROMOTION_AMOUNT="CompanyPromotionAmount";

    private int promotionAmount;

    public Credit() {
    }

    public Credit(JSONObject object) {
        super(object);

        if(object!=null)
            this.promotionAmount = object.optInt(COMPANY_PROMOTION_AMOUNT);
    }

    public int getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(int promotionAmount) {
        this.promotionAmount = promotionAmount;
    }
}
