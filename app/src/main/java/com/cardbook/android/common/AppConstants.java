package com.cardbook.android.common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.activities.AppMainTabActivity;
import com.cardbook.android.adapters.NotificationListener;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.Campaign;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.Shopping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppConstants {

    private static ProgressDialog dialog;
    public static NotificationListener notificationListener;
	
	// API
	public static final String API_ADDRESS = "http://test.mycardbook.gen.tr/ApplicationService/";
	public static final String SM_CREATE_OR_UPDATE_USER = "CreateOrUpdateUser";
    public static final String SM_UPDATEE_USER_INFO = "UpdateUserInfo";
	public static final String SM_GET_USER_DETAIL = "GetUserDetail";
	public static final String SM_GET_COMPANY_LIST = "GetCompanyList";
	public static final String SM_GET_COMPANY_DETAIL = "GetCompanyDetailContent";
	public static final String SM_SET_USER_COMPANY_NOTIFICATION_STATUS = "SetUserCompanyNotificationStatus";
	public static final String SM_SET_USER_COMPANY_CARD = "SetUserCompanyCard";
	public static final String SM_GET_ALL_ACTIVE_CAMPAIGN_LIST = "GetAllActiveCampaignList";
	public static final String SM_GET_COMPANY_ACTIVE_CAMPAIGN_LIST = "GetCompanyActiveCampaignList";
	public static final String SM_GET_CAMPAIGN_DETAIL_CONTENT = "GetCampaignDetailContent";
	public static final String SM_GET_ALL_SHOPPING_LIST = "GetAllShoppingList";
	public static final String SM_GET_COMPANY_SHOPPING_LIST = "GetCompanyShoppingList";
	public static final String SM_GET_SHOPPING_DETAIL_CONTENT = "GetShoppingDetailContent";
	public static final String SM_SET_SHARE_THIS_SHOPPING_ON_FACEBOOK = "ShareThisShoppingOnFacebook";
	public static final String SM_SET_SHARE_THIS_SHOPPING_ON_TWTITER = "ShareThisShoppingOnTwitter";
	public static final String SM_GET_ADDRESS_LIST = "GetAddressLists";
    public static final String SM_UPDATE_DEVICE_ID="UpdateMobileDeviceId";
    public static final String SM_GET_COMPANY_LOCATION_LIST="GetCompanyLocationList";
    public static final String SM_GET_COMPANY_INFO="GetCompanyInfo";
    public static final String SM_SEND_MESSAGE_TO_COMPANY="SendEmailToCompany";

    public static final String POST_DATA_ERROR="postDataError";
    public static final String POST_DATA="Data";


    public static final String KARTLARIM  = "Kartlarım";
    public static final String KAMPANYALAR  = "Kampanyalar";
    public static final String ALIS_VERIS  = "Alışveriş";
    public static final String PROFIL  = "Profil";

    public static final int KARTLARIM_POS=0;
    public static final int KAMPANYALAR_POS=1;
    public static final int ALIS_VERIS_POS=2;
    public static final int PROFIL_POS=3;

    public static final String[] MENU={KARTLARIM,KAMPANYALAR,ALIS_VERIS,PROFIL};
    public static final int[] PASSIVE_BUTTONS={R.drawable.tabicon_mycards_normal,R.drawable.tabicon_campaign_normal,
            R.drawable.tabicon_shopping_normal, R.drawable.tabicon_profile_normal};
    public static final int[] ACTIVE_BUTTONS={R.drawable.tabicon_mycards_active,R.drawable.tabicon_campaign_active,
            R.drawable.tabicon_shopping_active, R.drawable.tabicon_profile_active};

    public static final String CARDBOOK_SHARED_PREFERENCES ="com.cardbook.android";
    public static final String USER_INFORMATION ="com.cardbook.android.authenticationToken";
    public static final String TUTORIAL_INFORMATION ="com.cardbook.android.tutorialInfo";
    public static final String ADDRESS_LIST ="com.cardbook.android.addressList";

    public static Bitmap addMask(Context context, int image, int usedMask){
        Bitmap mask = BitmapFactory.decodeResource(context.getResources(), usedMask);
        Bitmap original = BitmapFactory.decodeResource(context.getResources(),image);

        Bitmap resizedbitmap = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
        original=resizedbitmap;


        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(result);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);

        paint.setXfermode(null);

        return result;
    }

    public static Bitmap addMask(Context context, Bitmap image, int usedMask){

        Bitmap mask = BitmapFactory.decodeResource(context.getResources(), usedMask);
        Bitmap original = image;

        Bitmap resizedbitmap = Bitmap.createScaledBitmap(original, mask.getWidth(), mask.getHeight(), true);
        original=resizedbitmap;


        Bitmap result = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(result);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        mCanvas.drawBitmap(original, 0, 0, null);
        mCanvas.drawBitmap(mask, 0, 0, paint);

        paint.setXfermode(null);

        return result;
    }

    public static Date parseMsTimestampToDate(final String msJsonDateTime) {
        if(msJsonDateTime == null) return null;
//        String JSONDateToMilliseconds = "\\\\/(Date\\\\((-*.*?)([\\\\+\\\\-].*)?\\\\))\\\\/";
        String JSONDateToMilliseconds = "/(Date\\((-*.*?)\\))/";
        Pattern pattern = Pattern.compile(JSONDateToMilliseconds);
        Matcher matcher = pattern.matcher(msJsonDateTime);
        String ts = matcher.replaceAll("$2");
        Log.i("TS: "+ts);
        Date retValue = new Date(new Long(ts));

        return retValue;
    }

    public static int getCacheSize(Context context) {
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        final int screenBytes = screenWidth * screenHeight * 4; // 4 bytes per pixel
        Log.i("CacheSize: "+screenBytes*30);
        return screenBytes * 3;
    }

    public static void ErrorToast(Context context){
        Toast.makeText(context, context.getResources().getString(R.string.error_form_server), Toast.LENGTH_LONG).show();
    }

    public static void NotOnlineToast(Context context){
        Toast.makeText(context,context.getResources().getString(R.string.no_internet_connection),Toast.LENGTH_LONG);
    }

    public static void setUserInformation(Context context,String info){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);
//        if(sp.getString(USER_INFORMATION,null)==null){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(USER_INFORMATION,info).commit();
//        }
//
    }

    public static String getUserInformation(Context context){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);

        return sp.getString(USER_INFORMATION,null);
    }

    public static void setTutorialShow(Context context){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);
//        if(sp.getString(USER_INFORMATION,null)==null){
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(TUTORIAL_INFORMATION,true).commit();
//        }
//
    }

    public static boolean isTutorialShowed(Context context){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);

        return sp.getBoolean(TUTORIAL_INFORMATION,false);
    }


    public static void setAddressList(Context context,String info){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);
//        if(sp.getString(USER_INFORMATION,null)==null){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(ADDRESS_LIST,info).commit();
//        }
//
    }

    public static String getAdressList(Context context){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);

        return sp.getString(ADDRESS_LIST,null);
    }

    public static void setNotificationsData(Context context,String info){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);
//        if(sp.getString(USER_INFORMATION,null)==null){
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("NotificationsData",info).commit();
//        }
//
    }
    
    public static String getNotificationsData(Context context){
        SharedPreferences sp=context.getSharedPreferences(CARDBOOK_SHARED_PREFERENCES,Context.MODE_PRIVATE);

        return sp.getString("NotificationsData",null);
    }
    

    /**
     *
     * Bilgileri yüklemek için gerekli metodlar.
     *
     */

    public static void getCompanyList(final Activity activity, final boolean isWorkInBackground){

        if(!isWorkInBackground)
            dialog=new ProgressDialog(activity);

        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
                if(!isWorkInBackground){
                    dialog.setMessage(activity.getResources().getString(R.string.loading));
                    dialog.show();
                }
                Log.i("Bilgiler indiriliyor: Company");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try {
                    JSONArray array=result.getJSONArray(AppConstants.POST_DATA);
                    Log.i(((JSONObject) array.get(0)).getString("UDate"));
                    String dateString=((JSONObject)array.get(0)).getString("UDate");
                    Date date=AppConstants.parseMsTimestampToDate(dateString);
                    SimpleDateFormat ft = new SimpleDateFormat ("dd.mm.yyyy");

                    Log.i("Current Date: "+ft.format(date));

                    CardbookApp.getInstance().setCompanies(null);
                    for(int i=0; i<array.length();i++){
                        Company company=new Company((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCompany(company);
                    }
                    getCampanignList(isWorkInBackground);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {

                if(!isWorkInBackground)
                    dialog.dismiss();
                AppConstants.ErrorToast(CardbookApp.getInstance().getApplicationContext());

            }
        };
        ConnectionManager.postData(CardbookApp.getInstance().getApplicationContext(), callback, AppConstants.SM_GET_COMPANY_LIST, new JSONObject());
    }

    public static void getCampanignList(final boolean isWorkInBackground){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Campaign");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try{
                    JSONArray array=result.getJSONArray(AppConstants.POST_DATA);
                    CardbookApp.getInstance().setCampaigns(null);
                    for(int i=0; i<array.length();i++){
                        Campaign campaing=new Campaign((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCampaign(campaing);
                    }
                    getShoppingList(isWorkInBackground);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {

                if(!isWorkInBackground)
                    dialog.dismiss();
                AppConstants.ErrorToast(CardbookApp.getInstance().getApplicationContext());

            }
        };
        ConnectionManager.postData(CardbookApp.getInstance().getApplicationContext(), callback,AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST,new JSONObject());
    }

    public static void getShoppingList(final boolean isWorkInBackground){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Shopings");
            }

            @Override
            public void onRequestComplete(JSONObject result) {
                CardbookApp.getInstance().setShoppings(null);
                JSONArray array=result.optJSONArray(AppConstants.POST_DATA);
                for(int i=0; i<array.length();i++){
                    Shopping shopping=new Shopping(array.optJSONObject(i));
                    CardbookApp.getInstance().addShoppings(shopping);

                    Log.i("Shopping: "+i+" "+shopping.getCompany().getCompanyName());
                }

                if(!isWorkInBackground)
                    dialog.dismiss();

                if(isWorkInBackground && notificationListener!=null){
                    notificationListener.showNotification();
                }
                else{
                    Intent intent=new Intent(CardbookApp.getInstance().getApplicationContext(), AppMainTabActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CardbookApp.getInstance().getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onRequestError() {
                if(dialog!=null)
                    dialog.dismiss();
                AppConstants.ErrorToast((CardbookApp.getInstance().getApplicationContext()));

            }
        };

        JSONObject paramater=new JSONObject();
        try{
            paramater.putOpt("userId", CardbookApp.getInstance().getUser().getId());
            ConnectionManager.postData((CardbookApp.getInstance().getApplicationContext()), callback, AppConstants.SM_GET_ALL_SHOPPING_LIST, paramater);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Ekran geçişlerinde keybordu kapatmak için kullanılır. Her bir ekranda setupTouchForKeyBoard çalıştırılmalı.
     * */
    public static void hideKeyboard(Context context,View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Ekran geçişlerinde keybordu kapatmak için kullanılır. Her bir ekranda setupTouchForKeyBoard çalıştırılmalı.
     * */
    public static void setupTouchForKeyBoard(final Context context, View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(context, v);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupTouchForKeyBoard(context,innerView);
            }
        }
    }
}
