package com.abdullah.cardbook.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.activities.AppMainTabActivity;
import com.abdullah.cardbook.connectivity.RequestCallBack;

import org.json.JSONObject;


public class BaseFragment extends Fragment implements RequestCallBack {
	public AppMainTabActivity mActivity;
    protected static FragmentPageListener pageListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity		=	(AppMainTabActivity) this.getActivity();
	}
	
	public boolean onBackPressed(){
		return false;
	}

    public void backPressed(){

    }

	public void onActivityResult(int requestCode, int resultCode, Intent data){
		
	}

    public static FragmentPageListener getFirstPageListener() {
        return pageListener;
    }

    public static void setFirstPageListener(FragmentPageListener firstPageListener) {
        BaseFragment.pageListener = firstPageListener;
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestComplete(JSONObject result) {

    }

    @Override
    public void onRequestError() {

    }
}
