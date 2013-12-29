package com.abdullah.cardbook.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by abdullah on 11/3/13.
 */
public class Product  implements Serializable{

    public static String NAME="ProductName";
    public static String VALUE="ProductValue";

    private String name;
    private float value;

    public Product(JSONObject object){
        this.name=object.optString(NAME);
        this.value=(float)object.optDouble(VALUE);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public String getValueWithFormat(){
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);

        return df.format(this.value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}
