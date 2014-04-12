package com.cardbook.android.activities;


import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.Campaign;
import com.cardbook.android.models.Shopping;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.common.Log;

import com.cardbook.android.models.Company;
import com.cardbook.android.models.address.*;
import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.entities.Profile;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends Activity implements OnClickListener {

	protected static final String TAG = MainActivity.class.getName();
	private static final String APP_ID = "235560183267507";
	private static final String APP_NAMESPACE = "cardbookapp";
	
	private SimpleFacebook mSimpleFacebook;
	private ImageButton btnLogin;
//	private TextView mTextStatus;
    private ProgressDialog dialog;

    String userInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userInformation= AppConstants.getUserInformation(this);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        btnLogin=(ImageButton) findViewById(R.id.btnLogin);
//        mTextStatus=(TextView) findViewById(R.id.tvStatus);

        // initialize facebook configuration
        Permissions[] permissions = new Permissions[]
                {
                        Permissions.BASIC_INFO,
                        Permissions.EMAIL,
                        Permissions.USER_BIRTHDAY,
                        Permissions.USER_PHOTOS,
                        Permissions.PUBLISH_ACTION,
                        Permissions.PUBLISH_STREAM,
                };

        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace(APP_NAMESPACE)
                .setPermissions(permissions)
                .setDefaultAudience(SessionDefaultAudience.FRIENDS)
                .build();

        SimpleFacebook.setConfiguration(configuration);

        if(userInformation==null){
            btnLogin.setOnClickListener(this);




        }
        else{
            btnLogin.setVisibility(View.INVISIBLE);

            JSONObject obje;
            try {
                obje=new JSONObject(userInformation);

                CardBookUser user=new CardBookUser(obje);
                CardbookApp.getInstance().setUser(user);
                if(ConnectionManager.isOnline(this)){
//                    new PostDataOperation().execute(user);
                    AppConstants.getCompanyList(MainActivity.this, false); // Devamında diğer bilgiler alıncak ve diğar ekrana yönelenecek.
                }
                else
                    AppConstants.NotOnlineToast(this);
            }
            catch(JSONException e){

            }


        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
	protected void onResume()
	{
		super.onResume();
        if(userInformation==null)
		mSimpleFacebook = SimpleFacebook.getInstance(this);


		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
        if(userInformation==null)
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);
	}
    
 // Login listener
 	private OnLoginListener mOnLoginListener = new OnLoginListener()
 	{

 		@Override
 		public void onFail(String reason)
 		{
// 			mTextStatus.setText(reason);
 			Log.i("Failed to login");
 		}

 		@Override
 		public void onException(Throwable throwable)
 		{
// 			mTextStatus.setText("Exception: " + throwable.getMessage());
 			Log.i( "Bad thing happened: "+throwable);
 		}

 		@Override
 		public void onThinking()
 		{
 			// show progress bar or something to the user while login is happening
// 			mTextStatus.setText("Thinking...");
 		}

 		@Override
 		public void onLogin()
 		{
 			// change the state of the button or do whatever you want
// 			mTextStatus.setText("Logged in");
 			

 			
 			mSimpleFacebook.getProfile(new OnProfileRequestListener() {
				
				@Override
				public void onFail(String reason) {
					// TODO Auto-generated method stub
                    toast("Facebook girişi başarısız oldu: "+reason);
					
				}
				
				@Override
				public void onException(Throwable throwable) {
					// TODO Auto-generated method stub
                    toast("Facebook girişi sırasında hala oluştu: "+throwable.toString());

					
				}
				
				@Override
				public void onThinking() {
					toast("Yanıt bekleniyor.");
					
				}
				
				@Override
				public void onComplete(Profile profile) {

                    toast("Facebook girişi tamamlandı.");

                    CardBookUser user=new CardBookUser();
                    user.setFacebookId(profile.getId());
                    user.setDeviceId("0");
                    user.setName(profile.getFirstName());
                    user.setSurname(profile.getLastName());
                    user.setEmail(profile.getEmail());
                    user.setBirthDate(profile.getBirthday());
                    user.setGender(profile.getGender());
                    user.setProfilPhotoUrl("http://graph.facebook.com/"+user.getFacebookId()+"/picture?style=large" );

                    CardbookApp app=CardbookApp.getInstance();
                    app.setUser(user);

                    openUserInformationActivity();
				}
			});
 		}

    		@Override
 		public void onNotAcceptingPermissions()
 		{
 			toast("İzinleri kabul etmediniz.");
 		}
 	};

    public void openUserInformationActivity(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Adres bilgileri indiriliyor...");
                dialog.show();
                Log.i("Adres bilgileri indiriliyor.");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try{
                    JSONObject resultAddress=result.getJSONObject(AppConstants.POST_DATA);
                    convertAddresList(resultAddress);
                    AppConstants.setAddressList(getApplicationContext(), resultAddress.toString());
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
                dialog.dismiss();
                Intent intent=new Intent(MainActivity.this, UserInformation.class);
                startActivity(intent);
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };
        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_ADDRESS_LIST,new HashMap<String, String>());
    }

    public void getAddressList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Bilgiler indiriliyor...");
                dialog.show();
                Log.i("Bilgiler indiriliyor: Adres");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try{
                    JSONObject resultAddress=result.getJSONObject(AppConstants.POST_DATA);
                    convertAddresList(resultAddress);
                    AppConstants.setAddressList(getApplicationContext(), resultAddress.toString());
//                    getCompanyList();
                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };

        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_ADDRESS_LIST,new JSONObject());
    }
/*
    public void getCompanyList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Bilgiler indiriliyor...");
                dialog.show();
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


                    for(int i=0; i<array.length();i++){
                        Company company=new Company((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCompany(company);
                    } 
                    getCampanignList();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };
        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_COMPANY_LIST,new JSONObject());
    }

    public void getCampanignList(){
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
                    for(int i=0; i<array.length();i++){
                        Campaign campaing=new Campaign((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCampaign(campaing);
                    }
                    getShoppingList();

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };
        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST,new JSONObject());
    }

    public void getShoppingList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Shopings");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                JSONArray array=result.optJSONArray(AppConstants.POST_DATA);
                for(int i=0; i<array.length();i++){
                    Shopping shopping=new Shopping(array.optJSONObject(i));
                    CardbookApp.getInstance().addShoppings(shopping);

                    Log.i("Shopping: "+i+" "+shopping.getCompany().getCompanyName());
                }

                dialog.dismiss();

                Intent intent=new Intent(MainActivity.this, AppMainTabActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };

        JSONObject paramater=new JSONObject();
        try{
            paramater.putOpt("userId", CardbookApp.getInstance().getUser().getId());
            ConnectionManager.postData(getApplicationContext(), callback, AppConstants.SM_GET_ALL_SHOPPING_LIST, paramater);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

*/


    private void toast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
	}


	@Override
	public void onClick(View v) {
		
		mSimpleFacebook.login(mOnLoginListener);
		
	}


    public class PostDataOperation  extends AsyncTask<CardBookUser, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);
        private  JSONArray array;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            dialog.setMessage("Bilgiler alınıyor...");
            dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(CardBookUser... user) {

            CardbookApp app=CardbookApp.getInstance();

//            JSONObject resultUser=ConnectionManager.postData2(AppConstants.SM_CREATE_OR_UPDATE_USER,user[0].getUserInfoAsDict());

            if(!ConnectionManager.isOnline(MainActivity.this)){
                AppConstants.NotOnlineToast(MainActivity.this);
                cancel(true);

            }
            JSONObject resultAddress=ConnectionManager.postData2(AppConstants.SM_GET_ADDRESS_LIST,null);
            AppConstants.setAddressList(getApplicationContext(),resultAddress.optJSONObject(AppConstants.POST_DATA).toString());

            JSONObject resultCards=ConnectionManager.postData2(AppConstants.SM_GET_COMPANY_LIST,null);

            JSONObject resultCampaign=ConnectionManager.postData2(AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST, null);

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("userId", app.getUser().getId()));


            JSONObject resultShopping=ConnectionManager.postData2(AppConstants.SM_GET_ALL_SHOPPING_LIST,list);
            array=resultShopping.optJSONArray(AppConstants.POST_DATA);
            for(int i=0; i<array.length();i++){
                Shopping shopping=new Shopping(array.optJSONObject(i));
                app.addShoppings(shopping);

                Log.i("Shopping: "+i+" "+shopping.getCompany().getCompanyName());
            }


            try{
                convertAddresList(resultAddress.getJSONObject(AppConstants.POST_DATA));
            }
            catch (JSONException e){
                e.printStackTrace();
            }

            try{
               array=resultCampaign.getJSONArray(AppConstants.POST_DATA);
                for(int i=0; i<array.length();i++){
                    Campaign campaing=new Campaign((JSONObject)array.get(i));

                    CardbookApp.getInstance().addCampaign(campaing);
                }

            }
            catch (JSONException e){
                e.printStackTrace();
            }


            try {
                array=resultCards.getJSONArray(AppConstants.POST_DATA);
                Log.i(((JSONObject)array.get(0)).getString("UDate"));
                String dateString=((JSONObject)array.get(0)).getString("UDate");
                Date date=AppConstants.parseMsTimestampToDate(dateString);
                SimpleDateFormat ft = new SimpleDateFormat ("dd.mm.yyyy");

                Log.i("Current Date: "+ft.format(date));


                for(int i=0; i<array.length();i++){
                    Company company=new Company((JSONObject)array.get(i));

                    CardbookApp.getInstance().addCompany(company);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            dialog.dismiss();

            Intent intent=new Intent(MainActivity.this, AppMainTabActivity.class);
            startActivity(intent);
//            if (Error != null) {
//
//                Intent intent=new Intent(MainActivity.this, AppMainTabActivity.class);
//                startActivity(intent);
//
//            } else {
//
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Hata")
//                        .setMessage("Uygulamayı daha sonra tekrar başlatın.")
//                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                System.exit(0);
//                            }
//                        }).show();
//
//            }


        }

    }

    public static void convertAddresList(JSONObject list){
        try{
            Log.i("converting is started");
            JSONArray jCountryArray=list.getJSONArray(Country.COUNTRY_LIST);
            JSONArray jCityArray=list.getJSONArray(City.CIYTY_LIST);
            JSONArray jCountyArray=list.getJSONArray(County.COUNTY_LIST);
            for(int i=0; i<jCountryArray.length();i++){
                JSONObject jCountry=jCountryArray.getJSONObject(i);
                Country country=new Country(jCountry);
                Log.i("Country: "+country.getName());
                for(int j=0; j<jCityArray.length();j++){
                    int countryId=jCityArray.getJSONObject(j).optInt(Country.COUNTRY_ID);
                    if(countryId==country.getId()){
                        City city=new City(jCityArray.getJSONObject(j));
                        Log.i("Country: "+country.getName()+", city: "+city.getName());
                        for(int k=0; k<jCountyArray.length();k++){
                            int cityId=jCountyArray.getJSONObject(k).optInt(City.CITY_ID);
                            if(city.getId()==cityId){
                                County county=new County(jCountyArray.getJSONObject(k));
                                Log.i("Country: "+country.getName()+", city: "+city.getName()+", county: "+county.getName());
                                city.addCounty(county);
                            }
                        }
                        country.addCity(city);
                    }
                }
                CardbookApp.getInstance().addCountry(country);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class GetAdressList  extends AsyncTask<String, Void, Void> {


        private String Content;
        private String Error = null;
//        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);



        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

//            Dialog.setMessage("Downloading source..");
//            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... string) {

            JSONObject result=ConnectionManager.postData2(AppConstants.SM_GET_ADDRESS_LIST,null);
//            Log.i("Adres list: "+result.toString());

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
//            Dialog.dismiss();

            if (Error != null) {

//                uiUpdate.setText("Output : "+Error);

            } else {

//                uiUpdate.setText("Output : "+Content);

            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
