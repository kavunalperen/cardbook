package com.abdullah.cardbook.fragments;


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
import com.abdullah.cardbook.models.CardBookUserCard;
import com.abdullah.cardbook.models.Company;



import java.util.ArrayList;


public class Kartlarim extends BaseFragment implements OnItemClickListener{

    private ListView listView;

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

        if(CardbookApp.getInstance().getCompanies()!=null)
    		setList(CardbookApp.getInstance().getCompanies());
        else{
            Button button=new Button(getActivity());
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setText("Güncelle");

            layout.addView(button);
        }


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

        ArrayList<Company> cardList=cards;
        cardList.addAll(cards);

		KartlarimListAdapter adapter=new KartlarimListAdapter(this.getActivity(),R.layout.kartlarim_list_template, cardList);
		listView.setAdapter(adapter);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
//		Toast.makeText(getActivity(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();


        Bundle data=new Bundle();
        data.putInt("position",position);
        KartDetail kartDetail=new KartDetail();
        kartDetail.setArguments(data);
		pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,kartDetail, this);


		
	}

    @Override
    public boolean onBackPressed() {
        return super.onBackPressed();
    }
}
