package com.abdullah.cardbook.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.abdullah.cardbook.adapters.FragmentCommunicator;
import com.abdullah.cardbook.adapters.PagerAdapter;
import com.abdullah.cardbook.common.*;

import com.abdullah.cardbook.fragments.*;

import java.util.HashMap;
import java.util.Stack;
import java.util.Timer;

public class AppMainTabActivity extends FragmentActivity implements OnTabChangeListener, OnPageChangeListener, OnClickListener{

    public FragmentCommunicator fragmentCommunicator;
	PagerAdapter pageAdapter;
	private ViewPager mViewPager;
	private TabHost mTabHost;

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

    Button navBarButton;
    TextView navBarText;

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

        /*
         *  Navigation stacks for each tab gets created..
         *  tab identifier is used as key to get respective stack for each tab
         */

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

        navBarText=(TextView)findViewById(R.id.navBarTxt);

        TabWidget tabwidget=(TabWidget)findViewById(android.R.id.tabs);
        tabwidget.bringToFront();

		setNavBarItemsStyle();

        initializeTabs();

     // Fragments and ViewPager Initialization
//        List<Fragment> fragments = mStacks;
        pageAdapter = new PagerAdapter(getSupportFragmentManager(), mStacks);
        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOnPageChangeListener(this);


        RelativeLayout tabLayout=(RelativeLayout)findViewById(R.id.tapLayout);
        tabLayout.bringToFront();


        this.lastinstance=this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        CardbookApp cardbookApp =(CardbookApp)getApplicationContext();

//        Log.i("Company length: "+ cardbookApp.getCompanies().size());
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

        Typeface font=Font.getFont(this, Font.ROBOTO_MEDIUM);

    	navBarText.setTypeface(font);

    }
    private void setNavbarItemsContent(String text, int imgId){
    	Log.i("Navbar text: "+text);

    	if(text!=null){
    		navBarText.setText(text);
    	}

    	if(imgId!=IMAGE_NULL){
    		navBarButton.setBackgroundResource(imgId);
    	}
    }
    public void initializeTabs(){
        /* Setup your tab icons and content views.. Nothing special in this..*/

        mTabHost.setCurrentTab(0);

        Typeface font=Font.getFont(this, Font.ROBOTO_REGULAR);

        for(int i=0; i<AppConstants.MENU.length;i++){
	        spec = mTabHost.newTabSpec(AppConstants.MENU[i]);
	        spec.setContent(new TabHost.TabContentFactory() {
	            public View createTabContent(String tag) {
	                return findViewById(android.R.id.tabcontent);
	            }
	        });
	        spec.setIndicator(createTabView(AppConstants.PASSIVE_BUTTONS[i],AppConstants.MENU[i]));
            View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
            spec.setIndicator(view);
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

      setNavbarItemsContent(tag, IMAGE_NULL);
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

        setNavbarItemsContent(AppConstants.MENU[pos], IMAGE_NULL);

	}


	@Override
	public void onClick(View v) {
		System.out.println("ONCLICK view: "+v.getClass());

        fragmentCommunicator.passDataToFragment("Bastım arkdaş");


	}

}
