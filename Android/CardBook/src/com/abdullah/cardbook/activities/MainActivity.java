package com.abdullah.cardbook.activities;


import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.models.Address;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.common.Log;

import com.abdullah.cardbook.models.Company;
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

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity implements OnClickListener {

	protected static final String TAG = MainActivity.class.getName();
	private static final String APP_ID = "235560183267507";
	private static final String APP_NAMESPACE = "cardbookapp";
	
	private SimpleFacebook mSimpleFacebook;
	private ImageButton btnLogin;
//	private TextView mTextStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        btnLogin=(ImageButton) findViewById(R.id.btnLogin);
//        mTextStatus=(TextView) findViewById(R.id.tvStatus);
        
        btnLogin.setOnClickListener(this);
        
        
     // initialize facebook configuration
     		Permissions[] permissions = new Permissions[]
     		{
     			Permissions.BASIC_INFO,
     			Permissions.EMAIL,
     			Permissions.USER_BIRTHDAY,
     			Permissions.USER_PHOTOS,
     			Permissions.PUBLISH_ACTION,
     			Permissions.PUBLISH_STREAM
     		};

     		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
     			.setAppId(APP_ID)
     			.setNamespace(APP_NAMESPACE)
     			.setPermissions(permissions)
     			.setDefaultAudience(SessionDefaultAudience.FRIENDS)
     			.build();

     		SimpleFacebook.setConfiguration(configuration);

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
		mSimpleFacebook = SimpleFacebook.getInstance(this);


		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
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
 			
 			toast("You are logged in");
 			
 			mSimpleFacebook.getProfile(new OnProfileRequestListener() {
				
				@Override
				public void onFail(String reason) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onException(Throwable throwable) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onThinking() {
					toast("Prfil on thinilkng");
					
				}
				
				@Override
				public void onComplete(Profile profile) {
                    CardBookUser user=new CardBookUser();
                    user.setId(profile.getId());
                    user.setDeviceId("123456789");
                    user.setName(profile.getFirstName());
                    user.setSurname(profile.getLastName());
                    user.setEmail(profile.getEmail());
                    user.setBirthDate(profile.getBirthday());
                    user.setGender(profile.getGender());
                    user.setPhone1("5059519915");
                    user.setPhone2("");
                    user.setProfilPhotoUrl("http://graph.facebook.com/"+user.getId()+"/picture?style=large" );

                    String[] location;
                    if(profile.getLocation()!=null)
                        location=profile.getLocation().getName().split(", ");
                    else
                        location=new String[]{"",""};

                    Address adres=new Address(location);
                    adres.setCountryId(1);
                    adres.setCityId(2);
                    adres.setCountId(4);
                    user.setAddres(adres);

                    Log.i("Gender From Face:"+profile.getGender());
//                    Log.i(profile.getLocale().toString()+", "+profile.getLocation().getName());

//                    new GetAdressList().execute(null);

				    new PostDataOperation().execute(user);


				}
			});
 		}

 		@Override
 		public void onNotAcceptingPermissions()
 		{
 			toast("You didn't accept read permissions");
 		}
 	};


 	private void toast(String message)
	{
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onClick(View v) {
		
		mSimpleFacebook.login(mOnLoginListener);
		
	}


    private class PostDataOperation  extends AsyncTask<CardBookUser, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);



        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Downloading source..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(CardBookUser... user) {

            ConnectionManager conManager=new ConnectionManager(MainActivity.this);
            JSONObject resultUser=conManager.postData(AppConstants.SM_CREATE_OR_UPDATE_USER,user[0].getUserInfoAsDict());

            JSONObject resultAddress=conManager.postData(AppConstants.SM_GET_ADDRESS_LIST,null);

            JSONObject resultCards=conManager.postData(AppConstants.SM_GET_COMPANY_LIST,null);

            JSONObject resultCampaign=conManager.postData(AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST, null);

            Log.i(resultCampaign.toString());


            JSONArray array;
            try {
                array=resultCards.getJSONArray("Data");
                Log.i(((JSONObject)array.get(0)).getString("UDate"));
                String dateString=((JSONObject)array.get(0)).getString("UDate");
                Date date=AppConstants.parseMsTimestampToDate(dateString);
                SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

                Log.i("Current Date: "+ft.format(date));


                for(int i=0; i<array.length();i++){
                    Company company=new Company((JSONObject)array.get(0));

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
            Dialog.dismiss();

            if (Error != null) {

//                uiUpdate.setText("Output : "+Error);

            } else {

//                uiUpdate.setText("Output : "+Content);

            }

            Intent intent=new Intent(MainActivity.this, AppMainTabActivity.class);
            startActivity(intent);
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
            ConnectionManager conManager=new ConnectionManager(MainActivity.this);
            JSONObject result=conManager.postData(AppConstants.SM_GET_ADDRESS_LIST,null);
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
}
