package com.cardbook.android.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.activities.MapActivity;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.adapters.LocationsListAdapter;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.Location;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Subeler extends BaseFragment implements OnItemClickListener, RequestCallBack, LocationListener{

    private ListView listView;
    LocationsListAdapter adapter;
    private ProgressDialog dialog;
    private Company company;

    private LocationManager locationManager;
    private String provider;

    public Subeler(){

    }

    public Subeler(FragmentPageListener listener) {
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



        company=((Company)getArguments().getSerializable(Company.COMPANY));

        TextView navBarText=(TextView)view.findViewById(R.id.navBarTxt);
        navBarText.setText(company.getCompanyName()+" Þubeleri");
        setList(CardbookApp.getInstance().getLocationsForCompany(company.getCompanyId()));



        dialog = new ProgressDialog(getActivity());
        dialog.setCancelable(false);

        // Get the location manager
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);

        provider = LocationManager.NETWORK_PROVIDER;
        android.location.Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
//            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {


        }

        return view;
    }


	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();


	}

    @Override
    public void onResume() {
        super.onResume();

        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public void setList(ArrayList<Location> loc){

//        if(adapter!=null)
//            adapter.clear();
	    adapter=new LocationsListAdapter(this.getActivity(), R.layout.locations_list_template, loc);
//        listView.setClickable(true);
//        listView.setEnabled(true);
        listView.setFocusable(true);
        listView.setOnItemClickListener(this);
//        listView.setItemsCanFocus(true);
		listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
	}

    @Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
//		Toast.makeText(getActivity(), "Clicked at positon = " + position, Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(getActivity(), MapActivity.class);
        intent.putExtra(Location.LOCATION, adapter.getItem(position));
        intent.putExtra(Company.COMPANY, company);
        startActivity(intent);

//       getCompanyDetail(adapter.getItem(position));

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
        dialog.setMessage("Kart DetayÄ± geliyor...");
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
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        Log.i("onLocationChanged");

        double lat =  (location.getLatitude());
        double lng =  (location.getLongitude());

        adapter.lat=lat;
        adapter.lon=lng;
        adapter.notifyDataSetChanged();


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.i("onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.i("onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.i("onProviderDisabled");
    }
}
