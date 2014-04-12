package com.cardbook.android.fragments;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.adapters.KartlarimListAdapter;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.models.Company;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Kartlarim extends BaseFragment implements OnItemClickListener, RequestCallBack{

    private ListView listView;
    KartlarimListAdapter adapter;
    private ProgressDialog dialog;

    public Kartlarim(){
    	
    }
    
    public Kartlarim(FragmentPageListener listener) {
        pageListener = listener;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kartlarim, container, false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.kartlarimLayoutLinear);
        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font=Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        listView= (ListView) view.findViewById(R.id.lvKartlarim);
        listView.setDivider(null);

        setNavBarItemsStyle(view);

        if(CardbookApp.getInstance().getCompanies()!=null){
    		setList(CardbookApp.getInstance().getCompanies());
            Log.i("companies are not null");
        }
        else{
            Button button=new Button(getActivity());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText("Güncelle");

            layout.addView(button);
        }

        dialog = new ProgressDialog(getActivity());
        return view;
    }


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		listView.setClickable(true);
		listView.setEnabled(true);
		listView.setFocusable(true);
		listView.setOnItemClickListener(this);
	}

	public void setList(ArrayList<Company> cards){

	    adapter=new KartlarimListAdapter(this.getActivity(),R.layout.kartlarim_list_template, cards);
		listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
	}
    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
//		Toast.makeText(getActivity(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();

       getCompanyDetail(adapter.getItem(position));
//        getLocationSList(adapter.getItem(position));
	}

    private void getCompanyDetail(Company company){

        CardbookApp app=CardbookApp.getInstance();
        CardBookUser user=app.getUser();

        Map<String, String> list=new HashMap<String, String>();
        list.put("userId", user.getId());
        list.put("companyId", String.valueOf(company.getCompanyId()));
        ConnectionManager.postData(getActivity(), this, AppConstants.SM_GET_COMPANY_DETAIL, list);

    }


    @Override
    public void onRequestStart() {

        super.onRequestStart();
        dialog.setMessage("Bilgiler yükleniyor...");
        dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);
        Log.i("onResponseComplete: "+result);
        dialog.dismiss();

        Company company=new Company(result.optJSONObject(AppConstants.POST_DATA));
        Log.i("Gelen company detail: " + company.getCompanyName());
        Bundle data=new Bundle();
        data.putSerializable(Company.COMPANY,company);
        KartDetail kartDetail=new KartDetail();
        kartDetail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,kartDetail, this);



    }

    @Override
    public void backPressed() {
        getActivity().moveTaskToBack(true);
    }
}
