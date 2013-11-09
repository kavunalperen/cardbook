package com.abdullah.cardbook.fragments;


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


import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.AlisVerisListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.models.Shopping;

import java.util.ArrayList;


public class AlisVeris extends BaseFragment implements AdapterView.OnItemClickListener{


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



        if(CardbookApp.getInstance().getCompanies()!=null)
            setList(CardbookApp.getInstance().getShoppings());
        else{
            Button button=new Button(getActivity());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText("Güncelle");

            layout.addView(button);
        }


        return view;
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
}
