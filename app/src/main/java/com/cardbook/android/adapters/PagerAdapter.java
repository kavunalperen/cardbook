package com.cardbook.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cardbook.android.activities.AppMainTabActivity;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Log;
import com.cardbook.android.fragments.AlisVeris;
import com.cardbook.android.fragments.BaseFragment;
import com.cardbook.android.fragments.Kampanyalar;
import com.cardbook.android.fragments.Kartlarim;
import com.cardbook.android.fragments.Profil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class PagerAdapter extends FragmentPagerAdapter {



    public static HashMap<String, Stack<Fragment>> fragments;
    private ArrayList<Fragment> fragmentList;
    
    private final FragmentManager mFragmentManager;
    public Fragment mFragmentAtPos0;
    PageListener listener = new PageListener();
    private boolean isDataReloaded;
    public PagerAdapter(FragmentManager fm, HashMap<String, Stack<Fragment>> fragments) {
        super(fm);
        PagerAdapter.fragments = fragments;
        mFragmentManager=fm;
        getFragments();
        
    }

    private final class PageListener implements
            FragmentPageListener {

        @Override
        public synchronized void onSwitchToNextFragment(String fragmentGroup, BaseFragment nextFragment, BaseFragment oldFragment) {
            
        	try{
        	Log.i("onSwitchToNextFragment: "+fragmentGroup+", "+nextFragment.toString());
            Stack<Fragment> stack=fragments.get(fragmentGroup);
            Log.i("Fragment Size in Stack: "+stack.size());

//            fragments.get(fragmentGroup).pop();
//            fragments.get(fragmentGroup).push(oldFragment);
            mFragmentManager.beginTransaction().remove(stack.get(stack.size()-1)).commit();



            nextFragment.setFirstPageListener(listener);

            fragments.get(fragmentGroup).push(nextFragment);

            notifyDataSetChanged();
            
        	}
        	catch(Exception e){
        		Log.i("Hata onSwitchToNextFragment");
        		onSwitchToNextFragment(fragmentGroup, nextFragment, oldFragment);
        		if(fragmentGroup.equals(AppConstants.KAMPANYALAR))
        			AppMainTabActivity.lastIntance().setCurrentTab(AppConstants.KAMPANYALAR_POS);
        		else if(fragmentGroup.equals(AppConstants.ALIS_VERIS))
        			AppMainTabActivity.lastIntance().setCurrentTab(AppConstants.ALIS_VERIS_POS);
        	}

        }

        @Override
        public synchronized void onSwitchBeforeFragment(String fragmentGroup) {

            Stack<Fragment> stack=fragments.get(fragmentGroup);
            Log.i("Fragment Size in Stack: "+stack.size());
            mFragmentManager.beginTransaction().remove(stack.get(stack.size()-1)).commit();


            fragments.get(fragmentGroup).pop();
            isDataReloaded=false;
            notifyDataSetChanged();
        }
    }
    
    @Override
    public synchronized Fragment getItem(int position) {
    	Log.i("Fragmetn getItem: "+position);

        Stack<Fragment> stack=null;

        switch (position){
            case AppConstants.KARTLARIM_POS:
               stack=PagerAdapter.fragments.get(AppConstants.KARTLARIM);
                break;

            case AppConstants.KAMPANYALAR_POS:
                stack=PagerAdapter.fragments.get(AppConstants.KAMPANYALAR);
                break;

            case AppConstants.ALIS_VERIS_POS:
                stack=PagerAdapter.fragments.get(AppConstants.ALIS_VERIS);
                break;

            case AppConstants.PROFIL_POS:
                stack=PagerAdapter.fragments.get(AppConstants.PROFIL);
                break;

        }
        Log.i("getItem return: "+stack.get(stack.size()-1));
        return stack.get(stack.size()-1);

    }

    @Override
    public int getCount() {
        return PagerAdapter.fragments.size();
    }
    
    private synchronized void getFragments(){
    	Log.i("getFragments: "+PagerAdapter.fragments.toString());
    	if(PagerAdapter.fragments==null){
    		PagerAdapter.fragments= new HashMap<String, Stack<Fragment>>();

            PagerAdapter.fragments.put(AppConstants.KARTLARIM, new Stack<Fragment>());
            PagerAdapter.fragments.put(AppConstants.KAMPANYALAR, new Stack<Fragment>());
            PagerAdapter.fragments.put(AppConstants.ALIS_VERIS, new Stack<Fragment>());
            PagerAdapter.fragments.put(AppConstants.PROFIL, new Stack<Fragment>());

            Log.i("FragmentsList is null and list size is: "+this.fragmentList.size());
    		
    	}

        if(PagerAdapter.fragments.get(AppConstants.KARTLARIM).size()<=0){
            Kartlarim kartlarim =new Kartlarim(listener);
            PagerAdapter.fragments.get(AppConstants.KARTLARIM).add(kartlarim);
        }

        if(PagerAdapter.fragments.get(AppConstants.KAMPANYALAR).size()<=0){
            Kampanyalar kampanyalar =new Kampanyalar(listener);
            PagerAdapter.fragments.get(AppConstants.KAMPANYALAR).add(kampanyalar);
        }

        if(PagerAdapter.fragments.get(AppConstants.ALIS_VERIS).size()<=0){
           AlisVeris alisVeris =new AlisVeris(listener);
            PagerAdapter.fragments.get(AppConstants.ALIS_VERIS).add(alisVeris);
        }

        if(PagerAdapter.fragments.get(AppConstants.PROFIL).size()<=0){
            Profil profil =new Profil(listener);
            PagerAdapter.fragments.get(AppConstants.PROFIL).add(profil);
        }

    }

	@Override
	public int getItemPosition(Object object) {

        Log.i("getItemPos: "+((Fragment)object).getClass());

        return POSITION_NONE;
	}

    @Override
    public synchronized void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!isDataReloaded){
            isDataReloaded=true;
            notifyDataSetChanged();
           
        }
        Log.i("notifyDataSetChanged is called");
    }
}

