package com.abdullah.cardbook.fragments;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.activities.AppMainTabActivity;
import com.abdullah.cardbook.adapters.AlisVerisListAdapter;
import com.abdullah.cardbook.adapters.AlisverisListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Shopping;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AlisVeris extends BaseFragment implements AdapterView.OnItemClickListener, AlisverisListener {


    private ListView listView;


    public AlisVeris(){

    }

    public AlisVeris(FragmentPageListener listener) {
        pageListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alis_veris, container, false);

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.alisVerisLayoutLinear);
        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font= Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        listView= (ListView) view.findViewById(R.id.lvAlisVeris);
        listView.setDivider(null);
        listView.setOnItemClickListener(this);


        Bundle bundle=getArguments();

        if(bundle!=null){
            Log.i("BUndle dolu");
            int companyId=bundle.getInt("companyId",0);
            JSONObject object=new JSONObject();
            try {
                object.put("companyId",companyId);
                object.put("userId",6);
                object= ConnectionManager.addDefaultParameters(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ArrayList<Shopping>allList=CardbookApp.getInstance().getShoppings();
            ArrayList<Shopping>list=new ArrayList<Shopping>();

            if(allList!=null && allList.size()>0){
                for(Shopping cp:allList){
                    if(cp.getCompanyId()==companyId)
                        list.add(cp);

                }
                setList(list);
            }
            else
                Toast.makeText(getActivity(),"Alışverişiniz bulunmamaktadır.", Toast.LENGTH_SHORT).show();

//            ConnectionManager.postData(getActivity(),new RequestCallBack() {
//                @Override
//                public void onRequestStart() {
//
//                }
//
//                @Override
//                public void onRequestComplete(JSONObject result) {
//                    Log.i("Company shopping Lİst is done");
//                    ArrayList<Shopping>list=new ArrayList<Shopping>();
//                    JSONArray resultArray=result.optJSONArray(AppConstants.POST_DATA);
//                    for(int i=0;i<resultArray.length();i++)
//                        list.add(new Shopping(resultArray.optJSONObject(i)));
//
//                    setList(list);
//                }
//
//                @Override
//                public void onRequestError() {
//                    Log.i("Company shopping Lİst is error");
//                }
//            },AppConstants.SM_GET_COMPANY_SHOPPING_LIST,object);
        }else{

            if(CardbookApp.getInstance().getShoppings()!=null)
                setList(CardbookApp.getInstance().getShoppings());
            else{
                Button button=new Button(getActivity());
                button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                button.setText("Güncelle");

                layout.addView(button);
            }
        }

            setNavBarItemsStyle(view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((AppMainTabActivity)this.getActivity()).alisverisListener=this;
    }

    public void setList(ArrayList<Shopping> item){
        AlisVerisListAdapter adapter=new AlisVerisListAdapter(this.getActivity(),R.layout.alis_veris_list_template, item);
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle data=new Bundle();
        data.putInt("position",i);
        AlisverisDetail detail=new AlisverisDetail();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.ALIS_VERIS,detail, this);
    }

    @Override
    public void openShopping(int companyId) {
        Bundle data=new Bundle();
        data.putInt("companyId", companyId);
        AlisVeris detail=new AlisVeris();
        detail.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.ALIS_VERIS,detail, this);
    }

    @Override
    public void backPressed() {
        getActivity().moveTaskToBack(true);
    }
}
