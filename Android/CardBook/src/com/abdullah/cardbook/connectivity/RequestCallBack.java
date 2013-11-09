package com.abdullah.cardbook.connectivity;

import org.json.JSONObject;

/**
 * Created by abdullah on 11/9/13.
 */
public interface RequestCallBack {
    public void onRequestStart();
    public void onRequestComplete(JSONObject result);
    public void onRequestError();
}
