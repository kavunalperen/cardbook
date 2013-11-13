package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Shopping;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;

import java.util.ArrayList;

public class AlisverisDetail extends BaseFragment implements View.OnClickListener{

    TextView tvHeader, tvDate, tvKazanilanPK,tvKazanilanPP, tvKullanilanPK,tvKullanilanPP;
    ImageButton btnShare;
    private ProgressDialog dialog;
    CardbookApp app;
    int position;
    private SimpleFacebook mSimpleFacebook;

	public AlisverisDetail(){

	}

	public AlisverisDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alisveris_detay, container, false);
        dialog = new ProgressDialog(getActivity());
        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();
        Shopping shopping=app.getShoppings().get(position);

        Log.i("Shoping: "+shopping.getId());

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface bold=Font.getFont(getActivity(),Font.ROBOTO_BOLD);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);
        Typeface black=Font.getFont(getActivity(),Font.ROBOTO_BLACK);

        tvHeader=(TextView)view.findViewById(R.id.tvAlisverisHeader);
        tvHeader.setTypeface(black);
        tvHeader.setText(shopping.getCompany().getCompanyName());

        tvDate=(TextView)view.findViewById(R.id.tvAlisverisDate);
        tvDate.setTypeface(light);

        tvKazanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPK);
        tvKazanilanPK.setTypeface(bold);
        tvKazanilanPK.setText(shopping.getWonCoupon().getCompanyPromotionText());

        tvKazanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPP);
        tvKazanilanPP.setTypeface(bold);

        try{
            tvKazanilanPP.setText(shopping.getWonCredit().getPromotionAmount());
        }catch (Exception e){}


        tvKullanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPP);
        tvKullanilanPP.setTypeface(bold);

        try{
            tvKullanilanPP.setText(shopping.getUsedCredit().getPromotionAmount());
        }catch (Exception e){}

        tvKullanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPK);
        tvKullanilanPK.setTypeface(bold);

        try{
            tvKullanilanPK.setText(shopping.getUsedCoupon().getCompanyPromotionText());
        }catch (Exception e){}

        btnShare=(ImageButton)view.findViewById(R.id.btnAlisverisDetailShare);
        btnShare.setOnClickListener(this);
        setNavBarItemsStyle(view);
        return view;
	}

    @Override
    public void onResume() {
        super.onResume();
        mSimpleFacebook = SimpleFacebook.getInstance(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onClick(View view) {
        SimpleFacebook.OnPublishListener onPublishListener = new SimpleFacebook.OnPublishListener()
        {

            @Override
            public void onFail(String reason)
            {
                // insure that you are logged in before publishing
                Log.i("Share OnFail: " + reason);
            }

            @Override
            public void onException(Throwable throwable)
            {
                Log.i("Share Bad thing happened: " + throwable);
            }

            @Override
            public void onThinking()
            {
                dialog.setMessage("Facebook paylaşımı gerçekleştiriliyor...");
                dialog.show();
                Log.i("Share: In progress");
            }

            @Override
            public void onComplete(String postId)
            {
                dialog.dismiss();
                Toast.makeText(getActivity(),"İşlem tamamlandı",Toast.LENGTH_LONG);
                Log.i("Share Published successfully. The new post id = " + postId);
            }
        };

// build feed
        Feed feed = new Feed.Builder()
                .setMessage("Alışveriş...")
                .setName("Cardbook ile alişverişs ")
                .setCaption("caption")
                .setDescription("Buu uygulama ile neler aldım neler.")
                .setPicture("https://raw.github.com/sromku/android-simple-facebook/master/Refs/android_facebook_sdk_logo.png")
                .setLink("cardbook.com")
                .build();

// publish the feed
        mSimpleFacebook.publish(feed, onPublishListener);
    }

    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.ALIS_VERIS);
    }
}
