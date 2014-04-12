package com.cardbook.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
    private Context context;
    PageListener listener = new PageListener();
    private boolean isDataReloaded;
    public PagerAdapter(FragmentManager fm, HashMap<String, Stack<Fragment>> fragments) {
        super(fm);
        this.fragments = fragments;
        mFragmentManager=fm;
        getFragments();
        
    }

    private final class PageListener implements
            FragmentPageListener {

        @Override
        public void onSwitchToNextFragment(String fragmentGroup, BaseFragment nextFragment, BaseFragment oldFragment) {
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

        @Override
        public void onSwitchBeforeFragment(String fragmentGroup) {

            Stack<Fragment> stack=fragments.get(fragmentGroup);
            Log.i("Fragment Size in Stack: "+stack.size());
            mFragmentManager.beginTransaction().remove(stack.get(stack.size()-1)).commit();


            fragments.get(fragmentGroup).pop();
            isDataReloaded=false;
            notifyDataSetChanged();
        }
    }
    
    @Override
    public Fragment getItem(int position) {
    	Log.i("Fragmetn getItem: "+position);

        Stack<Fragment> stack=null;

        switch (position){
            case AppConstants.KARTLARIM_POS:
               stack=this.fragments.get(AppConstants.KARTLARIM);
                break;

            case AppConstants.KAMPANYALAR_POS:
                stack=this.fragments.get(AppConstants.KAMPANYALAR);
                break;

            case AppConstants.ALIS_VERIS_POS:
                stack=this.fragments.get(AppConstants.ALIS_VERIS);
                break;

            case AppConstants.PROFIL_POS:
                stack=this.fragments.get(AppConstants.PROFIL);
                break;

        }
        Log.i("getItem return: "+stack.get(stack.size()-1));
        return stack.get(stack.size()-1);

    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
    
    private void getFragments(){
    	Log.i("getFragments: "+this.fragments.toString());
    	if(this.fragments==null){
    		this.fragments= new HashMap<String, Stack<Fragment>>();

            this.fragments.put(AppConstants.KARTLARIM, new Stack<Fragment>());
            this.fragments.put(AppConstants.KAMPANYALAR, new Stack<Fragment>());
            this.fragments.put(AppConstants.ALIS_VERIS, new Stack<Fragment>());
            this.fragments.put(AppConstants.PROFIL, new Stack<Fragment>());

            Log.i("FragmentsList is null and list size is: "+this.fragmentList.size());
    		
    	}

        if(this.fragments.get(AppConstants.KARTLARIM).size()<=0){
            Kartlarim kartlarim =new Kartlarim(listener);
            this.fragments.get(AppConstants.KARTLARIM).add(kartlarim);
        }

        if(this.fragments.get(AppConstants.KAMPANYALAR).size()<=0){
            Kampanyalar kampanyalar =new Kampanyalar(listener);
            this.fragments.get(AppConstants.KAMPANYALAR).add(kampanyalar);
        }

        if(this.fragments.get(AppConstants.ALIS_VERIS).size()<=0){
           AlisVeris alisVeris =new AlisVeris(listener);
            this.fragments.get(AppConstants.ALIS_VERIS).add(alisVeris);
        }

        if(this.fragments.get(AppConstants.PROFIL).size()<=0){
            Profil profil =new Profil(listener);
            this.fragments.get(AppConstants.PROFIL).add(profil);
        }

    }

	@Override
	public int getItemPosition(Object object) {

        Log.i("getItemPos: "+((Fragment)object).getClass());

        return POSITION_NONE;
	}

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!isDataReloaded){
            isDataReloaded=true;
            notifyDataSetChanged();

        }
        Log.i("notifyDataSetChanged is called");
    }
}

