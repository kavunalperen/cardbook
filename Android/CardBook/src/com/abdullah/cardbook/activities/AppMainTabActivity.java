package com.abdullah.cardbook.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.AlisverisListener;
import com.abdullah.cardbook.adapters.FragmentCommunicator;
import com.abdullah.cardbook.adapters.KampanyaListener;
import com.abdullah.cardbook.adapters.PagerAdapter;
import com.abdullah.cardbook.common.*;

import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.fragments.*;
import com.abdullah.cardbook.models.Company;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

public class AppMainTabActivity extends FragmentActivity implements OnTabChangeListener, OnPageChangeListener, OnClickListener{


    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static boolean isFromNotification=false;
    private static String campaignIdFromNotification="";

    GoogleCloudMessaging gcm;
    AtomicInteger msgId = new AtomicInteger();
    Context context;
    String SENDER_ID = "935708464592";
    String regid;

    public KampanyaListener kampanyaListener;
    public AlisverisListener alisverisListener;
	PagerAdapter pageAdapter;
	public ViewPager mViewPager;
	public TabHost mTabHost;

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    private HashMap<String, Stack<Fragment>> mStacks;

    /*Save current tabs identifier in this..*/
    private String mCurrentTab;

    // Tab Spec
    TabHost.TabSpec spec;

    ImageView mask;
    Timer timer;
    int maskLeftCurrentPosition;
    int maskLeftFuturePosition;
    int menuPlace;

    private static int IMAGE_NULL=-1;
    private static int offScrrenLimit=3;


    Button navBarButton;
//    TextView navBarText;

    private AppMainTabActivity lastinstance;
    public static AppMainTabActivity lastIntance(){
        return lastIntance();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.app_main_tab_fragment_layout);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setOffscreenPageLimit(offScrrenLimit);

        maskLeftCurrentPosition=0;
        mStacks = new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstants.KARTLARIM, new Stack<Fragment>());
        mStacks.put(AppConstants.KAMPANYALAR, new Stack<Fragment>());
        mStacks.put(AppConstants.ALIS_VERIS, new Stack<Fragment>());
        mStacks.put(AppConstants.PROFIL, new Stack<Fragment>());

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
//        mTabHost.setBackgroundColor(Color.rgb(142, 142, 147));
//        mTabHost.setBackgroundResource(R.drawable.tab_bg);
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setup();

        mTabHost.getTabWidget().setStripEnabled(false);
        mTabHost.getTabWidget().setDividerDrawable(null);

//        navBarText=(TextView)findViewById(R.id.navBarTxt);

        TabWidget tabwidget=(TabWidget)findViewById(android.R.id.tabs);
        tabwidget.bringToFront();

//		setNavBarItemsStyle(); // Navbar artıık her bir fragment içinde



     // Fragments and ViewPager Initialization
//        List<Fragment> fragments = mStacks;
        pageAdapter = new PagerAdapter(getSupportFragmentManager(), mStacks);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOnPageChangeListener(this);


        RelativeLayout tabLayout=(RelativeLayout)findViewById(R.id.tapLayout);
        tabLayout.bringToFront();

        initializeTabs();

        Bundle bundle=getIntent().getExtras();
        int tabNumbar;
        String campaignId;
        if(bundle!=null){
            tabNumbar=bundle.getInt("tab");
            campaignId=bundle.getString("campaignId");
            if(campaignId!=null)
                openCampaignDetail(campaignId);
            setCurrentTab(tabNumbar);
        }



        context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
//            regid = getRegistrationId(context);
//            Log.i("regID: "+regid);
//            if (regid.isEmpty()) {
                registerInBackground();
//            }
        } else {
            Log.i("No valid Google Play Services APK found.");
        }

        this.lastinstance=this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        CardbookApp cardbookApp =(CardbookApp)getApplicationContext();
//        Log.i("Company length: "+ cardbookApp.getCompanies().size());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle bundle=intent.getExtras();
        int tabNumbar;
        String campaignId;

        if(bundle!=null){
            tabNumbar=bundle.getInt("tab");
            campaignId=bundle.getString("campaignId");

            openCampaignDetail(campaignId);
            setCurrentTab(tabNumbar);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        EasyTracker.getInstance().activityStart(this);  // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();

        EasyTracker.getInstance().activityStop(this);  // Add this method.
    }

    private View createTabView(final int id, final String menuText) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        imageView.bringToFront();
        TextView text=(TextView)view.findViewById(R.id.tab_text);
        text.setText(menuText);
        text.bringToFront();
        return view;
    }

    private void setNavBarItemsStyle(){
        TextView navBarText;
        navBarText=(TextView)findViewById(R.id.navBarTxt);
        Typeface font=Font.getFont(this, Font.ROBOTO_MEDIUM);

    	navBarText.setTypeface(font);

    }

    public void setNavigationBarText(String text){
        TextView navBarText;
        navBarText=(TextView)findViewById(R.id.navBarTxt);
        navBarText.setText(text);
    }
    /*
    private void setNavbarItemsContent(String text, int imgId){
    	Log.i("Navbar text: "+text);

    	if(text!=null){
    		navBarText.setText(text);
    	}

    	if(imgId!=IMAGE_NULL){
    		navBarButton.setBackgroundResource(imgId);
    	}
    }
    */
    public void initializeTabs(){
        /* Setup your tab icons and content views.. Nothing special in this..*/

        mTabHost.setCurrentTab(0);

        Typeface font=Font.getFont(this, Font.ROBOTO_REGULAR);

        for(int i=0; i<AppConstants.MENU.length;i++){
            Log.i("initalize tab");
	        spec = mTabHost.newTabSpec(AppConstants.MENU[i]);
	        spec.setContent(new TabHost.TabContentFactory() {
	            public View createTabContent(String tag) {
	                return findViewById(android.R.id.tabcontent);
	            }
	        });
	        spec.setIndicator(createTabView(AppConstants.PASSIVE_BUTTONS[i],AppConstants.MENU[i]));
//            View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
//            spec.setIndicator(view);
            mTabHost.addTab(spec);

	        TextView tv;

	    	tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_text);
	        tv.setTypeface(font);
            tv.setText(AppConstants.MENU[i]);
        }



    }


    public void changeColor(int id){
    	TextView tv;
    	ImageView img;
    	for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++){
    		tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_text);
            tv.setTextColor(getResources().getColor(R.color.color8E8E93));

            img=(ImageView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tab_icon);
            img.setImageResource(AppConstants.PASSIVE_BUTTONS[i]);
    	}

    	tv=(TextView)mTabHost.getTabWidget().getChildAt(id).findViewById(R.id.tab_text);
        tv.setTextColor(getResources().getColor(R.color.color007AFF));

        img=(ImageView)mTabHost.getTabWidget().getChildAt(id).findViewById(R.id.tab_icon);
        img.setImageResource(AppConstants.ACTIVE_BUTTONS[id]);
    }

    /* Might be useful if we want to switch tab programmatically, from inside any of the fragment.*/
    public void setCurrentTab(int val){
          mTabHost.setCurrentTab(val);
    }


    /*
     *      To add fragment to a tab.
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack.
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(String tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
      if(shouldAdd)
          mStacks.get(tag).push(fragment);
      FragmentManager manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      if(shouldAnimate)
          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
      ft.replace(android.R.id.tabcontent, fragment);
      ft.commit();

//      setNavbarItemsContent(tag, IMAGE_NULL);
    }

    public void popFragments(){
      /*
       *    Select the second last fragment in current tab's stack..
       *    which will be shown after the fragment transaction given below
       */
      Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

      /*pop current fragment from stack.. */
      mStacks.get(mCurrentTab).pop();

      /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
      FragmentManager manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
      ft.replace(android.R.id.tabcontent, fragment);
      ft.commit();
    }


//    @Override
    public void onBackPressed() {

        BaseFragment baseFragment= (BaseFragment)pageAdapter.getItem(mViewPager.getCurrentItem());
//        if (baseFragment instanceof Kartlarim)
//            moveTaskToBack(true);
//        else if (baseFragment instanceof AlisVeris)
//            moveTaskToBack(true);
//        else if(baseFragment instanceof  KartDetail)
//            ((KartDetail)baseFragment).backPressed();
//        else if(baseFragment instanceof  BroadcastHierarchy)
//            ((BroadcastHierarchy)baseFragment).backPressed();
//        else if(baseFragment instanceof  BroadcastMessage)
//            ((BRo)baseFragment).backPressed();

          baseFragment.backPressed();

//       	if(((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed() == false){
//       		/*
//       		 * top fragment in current tab doesn't handles back press, we can do our thing, which is
//       		 *
//       		 * if current tab has only one fragment in stack, ie first fragment is showing for this tab.
//       		 *        finish the activity
//       		 * else
//       		 *        pop to previous fragment in stack for the same tab
//       		 *
//       		 */
//       		if(mStacks.get(mCurrentTab).size() == 1){
//       			super.onBackPressed();  // or call finish..
//       		}else{
//       			popFragments();
//       		}
//       	}else{
//       		//do nothing.. fragment already handled back button press.
//       	}
    }


    /*
     *   Imagine if you wanted to get an image selected using ImagePicker intent to the fragment. Ofcourse I could have created a public function
     *  in that fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("onActivityResult");

        if(mStacks.get(mCurrentTab).size() == 0){
            return;
        }

        /*Now current fragment on screen gets onActivityResult callback..*/
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }


	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
        Log.i("onPageScrolllStateChanged int: "+arg0);
	}


	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {


		int pos = this.mViewPager.getCurrentItem();
	    this.mTabHost.setCurrentTab(pos);

        Log.i("onPageScrolled. Pos: "+pos);
	}


	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onTabChanged(String tabId) {
//		mCurrentTab                     =   tabId;
//
//        if(mStacks.get(tabId).size() == 0){
//          /*
//           *    First time this tab is selected. So add first fragment of that tab.
//           *    Dont need animation, so that argument is false.
//           *    We are adding a new fragment which is not present in stack. So add to stack is true.
//           */
//          if(tabId.equals(AppConstants.KURUMSAL)){
//            pushFragments(tabId, new Kampanyalar(), true,true);
//          }
//          else if(tabId.equals(AppConstants.KISILER)){
//            pushFragments(tabId, new Kartlarim(), true,true);
//          }
//          else if(tabId.equals(AppConstants.MESAJLAR)){
//              pushFragments(tabId, new AlisVeris(), true,true);
//            }
//          else if(tabId.equals(AppConstants.PANO)){
//              pushFragments(tabId, new Profil(), true,true);
//            }
//          else if(tabId.equals(AppConstants.AYARLAR)){
//              pushFragments(tabId, new Ayarlar(), true,true);
//            }
//        }else {
//          /*
//           *    We are switching tabs, and target tab is already has atleast one fragment.
//           *    No need of animation, no need of stack pushing. Just show the target fragment
//           */
//          pushFragments(tabId, mStacks.get(tabId).lastElement(), true,false);
//        }
//
//        for(int i=0; i<AppConstants.MENU.length;i++){
//        	if(AppConstants.MENU[i].equals(tabId)){
//        		menuPlace=i;
//        		break;
//        	}
//        }
//

		int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);

        changeColor(pos);
        Log.i("Animation menu place: "+menuPlace);
//        animateMask(pos);

//        setNavbarItemsContent(AppConstants.MENU[pos], IMAGE_NULL);

	}


	@Override
	public void onClick(View v) {
		System.out.println("ONCLICK view: "+v.getClass());




	}


   public void openCampaign(Company company){
        kampanyaListener.openCampaign(company.getCompanyId());
   }

    public void openCampaignDetail(String campaignId){
        kampanyaListener.openCampaignDetail(campaignId);

    }
    public void openShopping(Company company){
        alisverisListener.openShopping(company.getCompanyId());
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);

                    Log.i("Register id" + regid);
                    // You should send the registration ID to your server over HTTP, so it
                    // can use GCM/HTTP or CCS to send messages to your app.
                    sendRegistrationIdToBackend();

                    // For this demo: we don't need to send it because the device will send
                    // upstream messages to a server that echo back the message using the
                    // 'from' address in the message.

                    // Persist the regID - no need to register again.
                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {

            }
        }.execute(null, null, null);
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGcmPreferences(context);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i( "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i("App version changed.");
            return "";
        }
        return registrationId;
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getGcmPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i("Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGcmPreferences(Context context) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the regID in your app is up to you.
        return getSharedPreferences(AppMainTabActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private void sendRegistrationIdToBackend() {
        JSONObject object=new JSONObject();
        try {
            object.put("userId",CardbookApp.getInstance().getUser().getId());
            object.put("mobileDeviceId", regid);
            object.put("mobileDeviceType","android");
            object=ConnectionManager.addDefaultParameters(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ConnectionManager.postData(getApplicationContext(), null,AppConstants.SM_UPDATE_DEVICE_ID,object);
    }
}
