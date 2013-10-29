package com.abdullah.cardbook.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KampanyalarListAdapter;
import com.abdullah.cardbook.models.Campaign;

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
        
//        listView=(ListView)view.findViewById(R.id.lvListeKurumsal);
//
//        ArrayList<Campaign> list=new ArrayList<Campaign>();
////        list.add("Genel Başkan");
////        list.add("MYK Üyesi");
////        list.add("PM Üyesi");
//
//        adapter=new KampanyalarListAdapter(this.getActivity(),R.layout.kampanyalar_list_template, list);
//		listView.setAdapter(adapter);
//		adapter.notifyDataSetChanged();
//
//		listView.setDivider(null);
//
//		listView.setClickable(true);
//		listView.setEnabled(true);
//		listView.setFocusable(true);
//		listView.setOnItemClickListener(this);
//
        return view;
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(getActivity(), "Clicked at positon = " + arg2, Toast.LENGTH_SHORT).show();
//		adapter.setSelectedPosition(arg2);
//		adapter.notifyDataSetChanged();
	}

    
}
