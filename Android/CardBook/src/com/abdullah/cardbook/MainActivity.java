package com.abdullah.cardbook;


import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.SessionDefaultAudience;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnProfileRequestListener;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;
import com.sromku.simple.fb.entities.Profile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	protected static final String TAG = MainActivity.class.getName();
	private static final String APP_ID = "235560183267507";
	private static final String APP_NAMESPACE = "cardbookapp";
	
	private SimpleFacebook mSimpleFacebook;
	private Button btnLogin;
	private TextView mTextStatus;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btnLogin=(Button) findViewById(R.id.btnLogin);
        mTextStatus=(TextView) findViewById(R.id.tvStatus);
        
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
 			mTextStatus.setText(reason);
 			Log.w(TAG, "Failed to login");
 		}

 		@Override
 		public void onException(Throwable throwable)
 		{
 			mTextStatus.setText("Exception: " + throwable.getMessage());
 			Log.e(TAG, "Bad thing happened", throwable);
 		}

 		@Override
 		public void onThinking()
 		{
 			// show progress bar or something to the user while login is happening
 			mTextStatus.setText("Thinking...");
 		}

 		@Override
 		public void onLogin()
 		{
 			// change the state of the button or do whatever you want
 			mTextStatus.setText("Logged in");
 			
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
					 String id = profile.getId();
				     String firstName = profile.getFirstName();
				     String birthday = profile.getBirthday();
				     String email = profile.getEmail();
				     String bio = profile.getBio();
				     String phone=profile.getLocale();
				     try {
						URL image_value = new URL("http://graph.facebook.com/"+id+"/picture?style=large" );
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 
				     Log.i(TAG,"email:"+email+", "+birthday+", "+id);
					
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
}
