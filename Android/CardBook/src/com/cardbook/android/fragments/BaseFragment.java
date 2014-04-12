package com.cardbook.android.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.activities.AppMainTabActivity;
import com.cardbook.android.common.Font;
import com.cardbook.android.connectivity.RequestCallBack;

import org.json.JSONObject;


public class BaseFragment extends Fragment implements RequestCallBack {
	public AppMainTabActivity mActivity;
    protected static FragmentPageListener pageListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mActivity		=	(AppMainTabActivity) this.getActivity();
	}
    public void setNavBarItemsStyle(View view){
        TextView navBarText;
        navBarText=(TextView)view.findViewById(R.id.navBarTxt);
        Typeface font= Font.getFont(getActivity(), Font.ROBOTO_MEDIUM);

        navBarText.setTypeface(font);

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
