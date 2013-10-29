package com.abdullah.cardbook.connectivity;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.JsonReader;

import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by abdullah on 10/22/13.
 */
public class ConnectionManager {


    public static String AUTHORIZATION_TOKEN_KEY="authorizationToken";
    public static String AUTHORIZATION_TOKEN_VALUE="B05D84EC9F5F2AD042273956090435C3";
    public static String AUTHORIZATION_TIME="authorizationTime";

    public static String RESULT_CODE="ResultCode";
    public static String RESULT_MESSAGE="ResultMessage";
    public static String RESULT_EXCEPTION="Exception";
    public static String RESULT_CODE_OK="00";

    private Context context;

    public ConnectionManager(){
        super();
    }

    public ConnectionManager(Context context){
        this.context=context;
    }

    // Formatted date for api
    public static String getFormattedDate(Date date){
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
    }

    // adding default parameters
    private ArrayList addDefaultParameters(ArrayList arraylist){

        BasicNameValuePair basicnamevaluepair = new BasicNameValuePair(AUTHORIZATION_TOKEN_KEY, AUTHORIZATION_TOKEN_VALUE);
        arraylist.add(basicnamevaluepair);

        String s = getFormattedDate(new Date());
        BasicNameValuePair basicnamevaluepair1 = new BasicNameValuePair(AUTHORIZATION_TIME, s);
        arraylist.add(basicnamevaluepair1);

        return arraylist;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public JSONObject postData(String method, ArrayList<NameValuePair> parameterList) {

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(new StringBuilder().append(AppConstants.API_ADDRESS).append(method).toString());
        JSONObject result=null;
        try {

            // Add your data
            ArrayList<NameValuePair> nameValuePairs;
            if(parameterList!=null)
                nameValuePairs = parameterList;
            else
                nameValuePairs=new ArrayList<NameValuePair>();

            addDefaultParameters(nameValuePairs);

            Log.i("Array Listt: "+nameValuePairs.toString());

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
//            JSONTokener tokener = new JSONTokener(builder.toString());
//            JSONArray finalResult = new JSONArray(tokener);

            try{
                Log.i("JSON result: "+builder.toString());
                result=new JSONObject(builder.toString());
//                result.put(RESULT_CODE, AppConstants.POST_DATA_ERROR);
            }
            catch (JSONException j){
                Log.i("EXpection: parsin Json " + j.toString());
            }
            Log.i("Parsing JSON");

            return result;
        } catch (Exception e) {
            try{
                JSONObject object=new JSONObject();
                object.put(RESULT_CODE, AppConstants.POST_DATA_ERROR+": "+e.toString());
                return object;
            }
            catch (JSONException j){
                Log.i("parsin Json" + j.toString());
            }

        }
        return result;
    }

}
