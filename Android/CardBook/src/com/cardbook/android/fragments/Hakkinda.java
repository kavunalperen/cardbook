package com.cardbook.android.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.CompanyInfo;


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
        navBarText.setText(company.getCompanyName()+" - Hakkýnda");
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
