package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KartlarimDetailListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.promotion.Coupon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KartDetail extends BaseFragment{

	ListView couponListView;
    int position;
    CardbookApp app;
	Company company;

    Button btnKampanyalar, btnAlisverisler, btnSave;
    private ProgressDialog Dialog;



	public KartDetail(){

	}

	public KartDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Dialog = new ProgressDialog(getActivity());
        View view = inflater.inflate(R.layout.kart_detail, container, false);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);

        btnAlisverisler=(Button) view.findViewById(R.id.btnkartDetailAlisveris);
        btnAlisverisler.setTypeface(regular);
        btnKampanyalar=(Button) view.findViewById(R.id.btnKartDetailKampanyalar);
        btnKampanyalar.setTypeface(regular);
        btnSave=(Button)view.findViewById(R.id.btnkartDetailSave);
        btnSave.setTypeface(regular);



        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();

        Log.i("KertDetail positioın: "+position);
        couponListView=(ListView)view.findViewById(R.id.kartDetailListView);
        couponListView.setDivider(null);

        company=CardbookApp.getInstance().getCompanies().get(position);
//        new PostDataOperation().execute(0);

        Map<String, String> list=new HashMap<String, String>();
        list.put("userId", "6");
        list.put("companyId", String.valueOf(company.getCompanyId()));

        ConnectionManager.postData(getActivity(),this,AppConstants.SM_GET_COMPANY_DETAIL,list);

        return view;
	}

    private void setList(ArrayList<Coupon> list){

        KartlarimDetailListAdapter adapter=new KartlarimDetailListAdapter(getActivity(),R.layout.kartlarim_detail_list_template,list);

        couponListView.setAdapter(adapter);
    }
    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }

    @Override
    public void onRequestStart() {

        super.onRequestStart();
        Dialog.setMessage("Kart Detayı geliyor...");
        Dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);
        Log.i("onResponseComplete");
        JSONObject jsonObject=result.optJSONObject(AppConstants.POST_DATA);
        JSONArray jsonArray=jsonObject.optJSONArray(Company.SHOPPING_PROMOTION_COUPON_LIST);

        company.setCouponList(jsonArray);

        setList(company.getCouponList());
        Dialog.dismiss();
    }

    @Override
    public void onRequestError() {
        super.onRequestError();
    }
}
