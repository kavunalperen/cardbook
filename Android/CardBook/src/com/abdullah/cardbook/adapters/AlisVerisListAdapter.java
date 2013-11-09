package com.abdullah.cardbook.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.Shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AlisVerisListAdapter extends ArrayAdapter<Shopping>{

	private ArrayList<Shopping> items;
	private Context context;
	private int layout;

	public AlisVerisListAdapter(Context context, int layout, ArrayList<Shopping> items) {
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

        Shopping item = items.get(position);
        if (item!= null) {

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
                ((LinearLayout)separator.getParent()).removeView(separator);
            }

            TextView name = (TextView) view.findViewById(R.id.tvCompany);
            name.setTypeface(Font.getFont(context, Font.ROBOTO_MEDIUM));

            TextView date=(TextView) view.findViewById(R.id.tvShoppingDate);
            date.setTypeface(Font.getFont(context, Font.ROBOTO_LIGHT));

            SimpleDateFormat ft=new SimpleDateFormat("dd MMM yyyy");
            
            if (name != null) {
            	name.setText(item.getCompany().getCompanyName());
                date.setText(ft.format(item.getDate()));
            }
         }
        else{
        	
        }

        return view;
    }
	
	@Override
	public boolean isEnabled(int position)
	{
        if(position==items.size()-1)
            return false;
		return true;
	}
}
