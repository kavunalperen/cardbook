package com.cardbook.android.models;

import org.json.JSONObject;

/**
 * Created by Abdullah on 23.03.2014.
 *
 * { "alert" : "1 yeni alışverişiniz var!" , "notificationType" : "shopping", "detailId" : "30" }

 { "alert" : "Sıcak çikolata - Gel kupanı al!" , "notificationType" : "campaign", "detailId" : "11" }
 */
public class CBNotification {

    public static final String ALERT="alert";
    public static final String NOTIFICATION_TYPE="notificationType";
    public static final String DETAIL_ID="detailId";

    public static final String NOTIFICATION_TYPE_SHOPPING="shopping";
    public static final String NOTIFICATION_TYPE_CAMPAIGN="campaign";

    private String alert;
    private String notificationType;
    private String detailId;

    public CBNotification(JSONObject n){
        alert=n.optString(ALERT);
        notificationType=n.optString(NOTIFICATION_TYPE);
        detailId=n.optString(DETAIL_ID);
    }

    public String getAlert() {
        return alert;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public String getDetailId() {
        return detailId;
    }
}
