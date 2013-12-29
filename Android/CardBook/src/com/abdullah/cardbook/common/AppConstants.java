package com.abdullah.cardbook.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.abdullah.cardbook.R;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppConstants {

	
	// API
	public static String API_ADDRESS = "http://test.mycardbook.gen.tr/ApplicationService/";
	public static String SM_CREATE_OR_UPDATE_USER = "CreateOrUpdateUser";
    public static String SM_UPDATEE_USER_INFO = "UpdateUserInfo";
	public static String SM_GET_USER_DETAIL = "GetUserDetail";
	public static String SM_GET_COMPANY_LIST = "GetCompanyList";
	public static String SM_GET_COMPANY_DETAIL = "GetCompanyDetailContent";
	public static String SM_SET_USER_COMPANY_NOTIFICATION_STATUS = "SetUserCompanyNotificationStatus";
	public static String SM_SET_USER_COMPANY_CARD = "SetUserCompanyCard";
	public static String SM_GET_ALL_ACTIVE_CAMPAIGN_LIST = "GetAllActiveCampaignList";
	public static String SM_GET_COMPANY_ACTIVE_CAMPAIGN_LIST = "GetCompanyActiveCampaignList";
	public static String SM_GET_CAMPAIGN_DETAIL_CONTENT = "GetCampaignDetailContent";
	public static String SM_GET_ALL_SHOPPING_LIST = "GetAllShoppingList";
	public static String SM_GET_COMPANY_SHOPPING_LIST = "GetCompanyShoppingList";
	public static String SM_GET_SHOPPING_DETAIL_CONTENT = "GetShoppingDetailContent";
	public static String SM_SET_SHARE_THIS_SHOPPING_ON_FACEBOOK = "ShareThisShoppingOnFacebook";
	public static String SM_SET_SHARE_THIS_SHOPPING_ON_TWTITER = "ShareThisShoppingOnTwitter";
	public static String SM_GET_ADDRESS_LIST = "GetAddressLists";
    public static String SM_UPDATE_DEVICE_ID="UpdateMobileDeviceId";

    public static String POST_DATA_ERROR="postDataError";
    public static String POST_DATA="Data";


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
        Toast.makeText(context, "Sunucu kaynaklı bir hata ile karşılaşıldı; lütfen işleminizi daha sonra tekrar deneyiniz.", Toast.LENGTH_LONG).show();
    }

    public static void NotOnlineToast(Context context){
        Toast.makeText(context,"İnternet bağlantısı bulunmuyor; lütfen internete bağlanın.",Toast.LENGTH_LONG);
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
}
