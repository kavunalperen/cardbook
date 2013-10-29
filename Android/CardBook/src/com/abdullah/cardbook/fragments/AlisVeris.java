package com.abdullah.cardbook.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.adapters.FragmentPageListener;

import java.util.ArrayList;


public class AlisVeris extends BaseFragment{


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

//        Button btn=(Button)view.findViewById(R.id.btnBroadcast);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                BroadcastHierarchy broadcastHierarchy=new BroadcastHierarchy();
////                BroadcastMessage broadcastMessage=new BroadcastMessage();
//                pageListener.onSwitchToNextFragment(AppConstants.ALIS_VERIS,broadcastHierarchy,AlisVeris.this);
//            }
//        });

        return view;
    }




}
