package com.abdullah.cardbook.fragments;


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
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KampanyalarListAdapter;
import com.abdullah.cardbook.adapters.KartlarimListAdapter;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.Company;

import java.util.ArrayList;


public class Kampanyalar extends BaseFragment implements OnItemClickListener {


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


        LinearLayout layout = (LinearLayout) view.findViewById(R.id.kampanyalarLayoutLinear);
        layout.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);

        Typeface font=Font.getFont(this.getActivity(), Font.ROBOTO_REGULAR);

        listView= (ListView) view.findViewById(R.id.lvKampanyalar);
        listView.setDivider(null);






        if(CardbookApp.getInstance().getCampaigns()!=null)
            setList(CardbookApp.getInstance().getCampaigns());
        else{
            Button button=new Button(getActivity());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText("Güncelle");

            layout.addView(button);
        }
//
        return view;
    }

    public void setList(ArrayList<Campaign> campaigns){
        KampanyalarListAdapter adapter=new KampanyalarListAdapter(this.getActivity(),R.layout.kampanyalar_list_template, campaigns);
        listView.setAdapter(adapter);
    }
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(getActivity(), "Clicked at positon = " + arg2, Toast.LENGTH_SHORT).show();
//		adapter.setSelectedPosition(arg2);
//		adapter.notifyDataSetChanged();
	}

    
}
