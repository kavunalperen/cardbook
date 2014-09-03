package com.cardbook.android.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.BitmapLruCache;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.Campaign;
import com.cardbook.android.models.Company;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KampanyaDetail extends BaseFragment{

    TextView tvHeader, tvDescriptionHeader, tvDate, tvDescription, tvRequimentHeader,tvRequiments;
    CardbookApp app;
    Campaign campain;
    int position;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;
    ImageView mImageView;

	public KampanyaDetail(){

	}

	public KampanyaDetail(FragmentPageListener listener){
		super.pageListener = listener;


	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kampanya_detail, container, false);

        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(getActivity());

        this.cache=new BitmapLruCache(cacheSize);


        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();
        campain=(Campaign)bundle.getSerializable(Campaign.CAMPAIGN);

        Typeface regular=Font.getFont(getActivity(), Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);
        Typeface black=Font.getFont(getActivity(),Font.ROBOTO_BLACK);

        String companyName="";
        ArrayList<Company> companies=app.getCompanies();
        for(Company cp:companies)
            if(String.valueOf(cp.getCompanyId())==campain.getCompanyId())
                companyName=cp.getCompanyName();

        tvHeader=(TextView)view.findViewById(R.id.tvKampanyaDetayHeader);
        tvHeader.setTypeface(black);
        tvHeader.setText(companyName);

        mImageView=(ImageView)view.findViewById(R.id.campainBanner);

        tvDescriptionHeader=(TextView)view.findViewById(R.id.tvKampanyaDetailDescriptionHeader);
        tvDescriptionHeader.setTypeface(medium);
        tvDescriptionHeader.setText(campain.getName());

        tvDescription=(TextView)view.findViewById(R.id.tvKampanyaDetailDesription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(campain.getDescription());
        tvDescription.setText(campain.getDescription());

        tvDate=(TextView)view.findViewById(R.id.tvKapmanyaDetailDate);
        tvDate.setTypeface(light);
        tvDate.setText(campain.getStartDate()+" - "+campain.getEndDate());

        tvRequimentHeader=(TextView)view.findViewById(R.id.tvKampanyaDetailRequimentHeader);
        tvRequimentHeader.setTypeface(light);
        tvRequimentHeader.setText("Kampanya Şartları");


        tvRequiments=(TextView)view.findViewById(R.id.tvKampanyaDetailRequiment);
        tvRequiments.setTypeface(light);

//        String requiments=getRequiements(campain.getDetailList());
//        tvRequiments.setText(requiments);

        addImage(campain.getBannerUrl());
        setNavBarItemsStyle(view);
        getCampaignDetail();
        return view;
	}

    public void addImage(String url){

        imageLoader=new ImageLoader(requestQueue, this.cache);

        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.i("Image Response is done ");


                if(response.getBitmap()!=null){
//                    Bitmap result=AppConstants.addMask(KartDetail.this.getActivity(), response.getBitmap(), R.drawable.listview_photomask);

                    mImageView.setImageBitmap(response.getBitmap());
                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Image Response Error");
            }
        });
    }



    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KAMPANYALAR);
    }

    private void getCampaignDetail(){

        JSONObject param=new JSONObject();
        try{
        param.putOpt("campaignId",campain.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ConnectionManager.postData(getActivity(),new RequestCallBack() {
            @Override
            public void onRequestStart() {

            }

            @Override
            public void onRequestComplete(JSONObject result) {
                Log.i("getCampaignDetail Request: "+result);

                JSONArray jRequiements=result.optJSONObject(AppConstants.POST_DATA).optJSONArray("CampaignDetailList");
                tvRequiments.setText(getRequiements(jRequiements));

            }

            @Override
            public void onRequestError() {
                Log.i("getCampaignDetail Error: ");
            }
        },AppConstants.SM_GET_CAMPAIGN_DETAIL_CONTENT,param);
    }

    public String getRequiements(JSONArray requiments){
        Log.i("getRequiments: "+requiments.length());
//        requiments=new ArrayList<String>();
//        requiments.add("Ã–yle aman aman bir koÅŸulumuz yok. Ne zaman istiyorsan gel verelim Ã¼rÃ¼nÃ¼.");
//        requiments.add("Ã–yle aman aman bir koÅŸulumuz yok. Ne zaman istiyorsan gel verelim Ã¼rÃ¼nÃ¼.");

        StringBuilder txtRequiments=new StringBuilder();
        if(requiments==null || requiments.length()==0)
            return "Kampanya ile ilgili herhangi bir şart bulunmamaktadır.";


        for(int i=0;i<requiments.length();i++){
            txtRequiments.append(requiments.optJSONObject(i).optString("CampaignDetailText")).append("\n");
            Log.i("getRequiments: "+txtRequiments.toString());
        }

        return txtRequiments.toString();
    }
}
