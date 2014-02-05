package com.abdullah.cardbook.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.CompanyInfo;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Hakkinda extends BaseFragment{

    private TextView tvCompanyAboutText, tvCompanyName;
    private Company company;
    private CompanyInfo info;

    public Hakkinda(){

    }

    public Hakkinda(FragmentPageListener listener) {
        pageListener = listener;
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        info=(CompanyInfo)getArguments().getSerializable(CompanyInfo.COMPANY_INFO);


        View view = inflater.inflate(R.layout.hakkinda, container, false);

//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.il);
//        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font=Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        company=((Company)getArguments().getSerializable(Company.COMPANY));

        tvCompanyAboutText=(TextView)view.findViewById(R.id.tvCompanyAboutText);
//        tvCompanyName=(TextView)view.findViewById(R.id.tvCompanyName);
//        tvCompanyName.setText(company.getCompanyName());
        tvCompanyAboutText.setText(info.getAboutText());

        setNavBarItemsStyle(view);

        TextView navBarText=(TextView)view.findViewById(R.id.navBarTxt);
        navBarText.setText(company.getCompanyName()+" - Hakkında");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }

}
