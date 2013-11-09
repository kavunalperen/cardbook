package com.abdullah.cardbook.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Shopping;

import java.util.ArrayList;

public class AlisverisDetail extends BaseFragment{

    TextView tvHeader, tvDate, tvKazanilanPK,tvKazanilanPP, tvKullanilanPK,tvKullanilanPP;
    CardbookApp app;
    int position;


	public AlisverisDetail(){

	}

	public AlisverisDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alisveris_detay, container, false);

        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();
        Shopping shopping=app.getShoppings().get(position);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface bold=Font.getFont(getActivity(),Font.ROBOTO_BOLD);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);
        Typeface black=Font.getFont(getActivity(),Font.ROBOTO_BLACK);

        tvHeader=(TextView)view.findViewById(R.id.tvAlisverisHeader);
        tvHeader.setTypeface(black);
//        tvHeader.setText(shopping.getCompanyId());

        tvDate=(TextView)view.findViewById(R.id.tvAlisverisDate);
        tvDate.setTypeface(light);

        tvKazanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPK);
        tvKazanilanPK.setTypeface(bold);
        tvKazanilanPK.setText(shopping.getWonCoupon().getCompanyPromotionText());

        tvKazanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPP);
        tvKazanilanPP.setTypeface(bold);
//        tvKazanilanPP.setText(shopping.getWonCredit().getPromotionAmount());

        tvKullanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPP);
        tvKullanilanPP.setTypeface(bold);
//        tvKullanilanPP.setText(shopping.getUsedCredit().getPromotionAmount());

        tvKullanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPK);
        tvKullanilanPK.setTypeface(bold);
//        tvKullanilanPK.setText(shopping.getUsedCoupon().getCompanyPromotionText());


        return view;
	}


    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KAMPANYALAR);
    }

    public String getRequiements(ArrayList<String> requiments){

        requiments=new ArrayList<String>();
        requiments.add("Öyle aman aman bir koşulumuz yok. Ne zaman istiyorsan gel verelim ürünü.");
        requiments.add("Öyle aman aman bir koşulumuz yok. Ne zaman istiyorsan gel verelim ürünü.");

        StringBuilder txtRequiments=new StringBuilder();
        if(requiments==null)
            return "";

        for(String s:requiments){
            txtRequiments.append(s).append("\n");
        }

        return txtRequiments.toString();
    }
}
