package com.abdullah.cardbook.fragments;


import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KartlarimListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.CardBookUserCard;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.Location;


import org.json.JSONArray;
import org.json.JSONException;
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
