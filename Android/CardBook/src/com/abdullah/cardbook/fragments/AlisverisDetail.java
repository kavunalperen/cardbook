package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.AlisverisDetailListAdapter;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Product;
import com.abdullah.cardbook.models.Shopping;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Feed;

import org.json.JSONObject;

import java.util.ArrayList;

public class AlisverisDetail extends BaseFragment implements View.OnClickListener{

    TextView tvHeader, tvDate, tvKazanilanPK,tvKazanilanPP, tvKullanilanPK,tvKullanilanPP;
    ImageButton btnShare;
    private ProgressDialog dialog;
    private ListView listView;
    CardbookApp app;
    Shopping shopping;
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
        shopping=(Shopping)bundle.getSerializable("shopping");

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
        tvDate.setText(shopping.getDate());

        tvKazanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPK);
        tvKazanilanPK.setTypeface(bold);
        try{
        tvKazanilanPK.setText(shopping.getWonCoupon().getCompanyPromotionId());
        }
        catch (Exception e){
            tvKazanilanPK.setText("0");
            e.printStackTrace();
        }
        tvKazanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKazanilaPP);
        tvKazanilanPP.setTypeface(bold);

        try{
            tvKazanilanPP.setText(shopping.getWonCredit().getPromotionAmount());

        }catch (Exception e){
            tvKazanilanPP.setText("0");
            e.printStackTrace();
        }


        tvKullanilanPP=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPP);
        tvKullanilanPP.setTypeface(bold);

        try{
            tvKullanilanPP.setText(shopping.getUsedCredit().getPromotionAmount());
        }catch (Exception e){
            tvKullanilanPP.setText("0");
            e.printStackTrace();
        }

        tvKullanilanPK=(TextView)view.findViewById(R.id.tvAlisverisDetayKullanilanPK);
        tvKullanilanPK.setTypeface(bold);

        try{
            tvKullanilanPK.setText(shopping.getUsedCoupon().getCompanyPromotionText());
        }catch (Exception e){
            tvKullanilanPK.setText("0");
            e.printStackTrace();
        }

        listView=(ListView)view.findViewById(R.id.listView);
        AlisverisDetailListAdapter adapter=new AlisverisDetailListAdapter(getActivity(),R.layout.alisveris_detail_list_template,shopping.getProductsList());
        listView.setAdapter(adapter);


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
                dialog.setMessage("Facebook paylaşımı tamamlandı.");
                dialog.dismiss();
                Toast.makeText(getActivity(),"İşlem tamamlandı",Toast.LENGTH_LONG);
                Log.i("Share Published successfully. The new post id = " + postId);

                try{
                JSONObject parameter=new JSONObject();
                parameter.putOpt("userId",CardbookApp.getInstance().getUser().getId());
                parameter.putOpt("shoppingId", shopping.getId());
                ConnectionManager.postData(getActivity(), null, AppConstants.SM_SET_SHARE_THIS_SHOPPING_ON_FACEBOOK, parameter);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };

// build feed

        StringBuilder str=new StringBuilder();
        str.append("Cardbook aracılığı ile ").append(shopping.getCompany().getCompanyName()).append(" mağazasından ");
        ArrayList<Product> list=shopping.getProductsList();
        for(int i=0; i<list.size();i++){
            Product p=list.get(i);
            if(i<list.size()-1)
                str.append(p.getName());
            else
                str.append(p.getName()).append(", ");
        }
        str.append(" aldım ve ").append(shopping.getWonCredit().getPromotionAmount()).append(" puan kazandım.");



        Feed feed = new Feed.Builder()
                .setMessage(str.toString())
                .setName(shopping.getCompany().getCompanyName())
                .setCaption("www.cardbook.com.tr")
                .setDescription("")
                .setPicture(shopping.getCompany().getCompanyLogoURL())
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
