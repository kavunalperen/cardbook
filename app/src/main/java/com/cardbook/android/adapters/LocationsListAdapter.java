package com.cardbook.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.models.Location;

import java.util.ArrayList;

public class LocationsListAdapter extends ArrayAdapter<Location>{

	private ArrayList<Location> items;
	private Context context;
	private int layout;
    public static double lat=0;
    public static double lon=0;


	public LocationsListAdapter(Context context, int layout, ArrayList<Location> items) {
        super(context,layout, items);
        this.context = context;
        this.items = items;
        this.layout=layout;
    }



	
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.layout, null);

        }


        final Location item = items.get(position);

        if(position==0){
            Log.i("position 0");
            View header=view.findViewById(R.id.header);
            header.setBackgroundResource(R.drawable.listview_top_bg);
        }
        if(position==items.size()-1){

            View footer=view.findViewById(R.id.footer);
            footer.setBackgroundResource(R.drawable.listview_bottom_bg);

            Resources r = this.context.getResources();
            int px = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    18,
                    r.getDisplayMetrics()
            );

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, px);
            footer.setLayoutParams(params);

            View separator=view.findViewById(R.id.seperator);
            if(separator!=null)
                ((LinearLayout)separator.getParent()).removeView(separator);
        }


        if (item!= null) {

        	// user name and title
            Typeface fontRegular=Font.getFont(context, Font.ROBOTO_REGULAR);
            Typeface fontBold=Font.getFont(context, Font.ROBOTO_BOLD);
            
            TextView name = (TextView) view.findViewById(R.id.locationsListTvName);
            name.setTypeface(fontBold);
            name.setText(item.getLocationName());

            TextView address = (TextView) view.findViewById(R.id.locationsListTvAddress);
            address.setTypeface(fontRegular);
            address.setText(item.getAddressLine()+"\n"+item.getCountyName()+" "+item.getCityName());

            TextView tvKm=(TextView)view.findViewById(R.id.tvKm);
            if(lon!=0 && lat!=0){

                tvKm.setTypeface(fontRegular);

                android.location.Location locationA = new android.location.Location("point A");

                locationA.setLatitude(item.getLatitude());
                locationA.setLongitude(item.getLongitude());


                android.location.Location locationB = new android.location.Location("point B");
                locationB.setLatitude(lat);
                locationB.setLongitude(lon);


                float distance = locationA.distanceTo(locationB);


                tvKm.setText((int)(distance/1000)+" km");
            }
            else
            tvKm.setText("");

            ImageView btnCall=(ImageView)view.findViewById(R.id.btnBranchCall);
            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String number = "tel:" + item.getLocationPhone().trim();
                    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                    context.startActivity(callIntent);
                }
            });

        }
        else{
        	
        }

        return view;
    }

    @Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}
