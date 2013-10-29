package com.abdullah.cardbook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;

public class KartDetail extends BaseFragment{

	ListView phoneListView;
	TextView tvName, tvTitle;
	ImageView userImage;
	
	public KartDetail(){

	}

	public KartDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kart_detail, container, false);
        
                return view;
	}


}
