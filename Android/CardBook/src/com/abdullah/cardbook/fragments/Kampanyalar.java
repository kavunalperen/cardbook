package com.abdullah.cardbook.fragments;


import android.app.Activity;
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
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.activities.AppMainTabActivity;
import com.abdullah.cardbook.adapters.FragmentCommunicator;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KampanyaListener;
import com.abdullah.cardbook.adapters.KampanyalarListAdapter;
import com.abdullah.cardbook.adapters.KartlarimListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Company;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Kampanyalar extends BaseFragment implements OnItemClickListener, KampanyaListener {


	private ListView listView;
	KampanyalarListAdapter adapter;

    public Kampanyalar(){

    }

    public Kampanyalar(FragmentPageListener listener){
        pageListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view       =   inflater.inflate(R.layout.kampanyalar, container, false);

        Bundle bundle=getArguments();



        LinearLayout layout = (LinearLayout) view.findViewById(R.id.kampanyalarLayoutLinear);
        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font=Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        listView= (ListView) view.findViewById(R.id.lvKampanyalar);
        listView.setDivider(null);
        listView.setOnItemClickListener(this);

        setNavBarItemsStyle(view);




        if(bundle!=null){
            Log.i("BUndle dolu");
            int companyId=bundle.getInt("companyId",0);
            JSONObject object=new JSONObject();
            try {
                object.put("companyId",companyId);
                object=ConnectionManager.addDefaultParameters(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Campaign>allList=CardbookApp.getInstance().getCampaigns();
            ArrayList<Campaign>list=new ArrayList<Campaign>();

            for(Campaign cp:allList){
                if(cp.getCompanyId()==String.valueOf(companyId))
                    list.add(cp);

            }
            setList(list);

//            ConnectionManager.postData(getActivity(),new RequestCallBack() {
//                @Override
//                public void onRequestStart() {
//
//                }
//
//                @Override
//                public void onRequestComplete(JSONObject result) {
//                    ArrayList<Campaign>list=new ArrayList<Campaign>();
//                    JSONArray resultArray=result.optJSONArray(AppConstants.POST_DATA);
//                    for(int i=0;i<resultArray.length();i++)
//                        list.add(new Campaign(resultArray.optJSONObject(i)));
//
//                    setList(list);
//                }
//
//                @Override
//                public void onRequestError() {
//
//                }
//            },AppConstants.SM_GET_COMPANY_ACTIVE_CAMPAIGN_LIST,object);
        }
        else{


            if(CardbookApp.getInstance().getCampaigns()!=null)
                setList(CardbookApp.getInstance().getCampaigns());
            else{
                Button button=new Button(getActivity());
                button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                button.setText("Güncelle");

                layout.addView(button);
            }
        }



        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((AppMainTabActivity)this.getActivity()).kampanyaListener=this;


    }

    public void setList(ArrayList<Campaign> campaigns){
        KampanyalarListAdapter adapter=new KampanyalarListAdapter(this.getActivity(),R.layout.kampanyalar_list_template, campaigns);
        listView.setAdapter(adapter);
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		Toast.makeText(getActivity(), "Clicked at positon = " + arg2, Toast.LENGTH_SHORT).show();

        Bundle data=new Bundle();
        data.putInt("position",arg2);
        KampanyaDetail detail=new KampanyaDetail();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.KAMPANYALAR,detail, this);
//		adapter.setSelectedPosition(arg2);
//		adapter.notifyDataSetChanged();
	}


    @Override
    public void openCampaign(int companyId) {
        Bundle data=new Bundle();
        data.putInt("companyId", companyId);
        Kampanyalar detail=new Kampanyalar();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.KAMPANYALAR,detail, this);
    }

    @Override
    public void backPressed() {
        getActivity().moveTaskToBack(true);
    }
}
