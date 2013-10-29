package com.abdullah.cardbook.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.AppConstants;


public class Profil extends BaseFragment {


    private Button mGotoButton;


    public Profil(){

    }

    public Profil(FragmentPageListener listener){
        pageListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view       =   inflater.inflate(R.layout.app_tab_d_first_screen, container, false);

        mGotoButton =   (Button) view.findViewById(R.id.id_next_tab_a_button);
        mGotoButton.setOnClickListener(listener);

        return view;
    }

    private OnClickListener listener        =   new OnClickListener(){
        @Override
        public void onClick(View v){
            /* Go to next fragment in navigation stack*/
//            mActivity.pushFragments(AppConstants.PROFIL, new AppTabASecondFragment(),true,true);
        }
    };
}
