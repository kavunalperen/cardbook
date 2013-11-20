package com.abdullah.cardbook.models;

import org.json.JSONObject;

/**
 * Created by abdullah on 11/3/13.
 */
public class Product {

    public static String NAME="ProductName";
    public static String VALUE="ProductValue";

    private String name;
    private int value;

    public Product(JSONObject object){
        this.name=object.optString(NAME);
        this.value=object.optInt(VALUE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
