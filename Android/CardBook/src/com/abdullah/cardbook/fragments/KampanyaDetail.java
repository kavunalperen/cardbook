package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KartlarimDetailListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.promotion.Coupon;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KampanyaDetail extends BaseFragment{

    TextView tvHeader, tvDescriptionHeader, tvDate, tvDescription, tvRequimentHeader,tvRequiments;
    CardbookApp app;
    int position;


	public KampanyaDetail(){

	}

	public KampanyaDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kampanya_detail, container, false);

        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();
        Campaign campain=app.getCampaigns().get(position);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);
        Typeface black=Font.getFont(getActivity(),Font.ROBOTO_BLACK);

        tvHeader=(TextView)view.findViewById(R.id.tvKampanyaDetayHeader);
        tvHeader.setTypeface(black);
        tvHeader.setText(campain.getCompanyId());

        tvDescriptionHeader=(TextView)view.findViewById(R.id.tvKampanyaDetailRequimentHeader);
        tvDescriptionHeader.setTypeface(medium);
        tvDescriptionHeader.setText("Kampanya başlığı");

        tvDescription=(TextView)view.findViewById(R.id.tvKampanyaDetailDesription);
        tvDescription.setTypeface(regular);
        tvDescription.setText(campain.getDescription());
        tvDescription.setText("Buaraya gelecek alan sunucdan tarafımıza gelmiyor. Bu sebeple bu yazıyı görüyorsunuz.");

        tvDate=(TextView)view.findViewById(R.id.tvKapmanyaDetailDate);
        tvDate.setTypeface(light);

        tvRequimentHeader=(TextView)view.findViewById(R.id.tvKampanyaDetailRequimentHeader);
        tvRequimentHeader.setTypeface(light);


        tvRequiments=(TextView)view.findViewById(R.id.tvKampanyaDetailRequiment);
        tvRequiments.setTypeface(light);
        String requiments=getRequiements(campain.getDetailList());
        tvRequiments.setText(requiments);

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
