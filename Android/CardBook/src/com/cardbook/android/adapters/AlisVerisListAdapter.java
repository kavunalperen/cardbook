package com.cardbook.android.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.models.Shopping;

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

            View header=view.findViewById(R.id.header);
            header.setVisibility(View.GONE);

            View footer=view.findViewById(R.id.footer);
            footer.setVisibility(View.GONE);

            View separator=view.findViewById(R.id.seperator);
            separator.setVisibility(View.GONE);

            if(position==0){
                Log.i("position 0");

                header.setBackgroundResource(R.drawable.listview_top_bg);
                header.setVisibility(View.VISIBLE);
            }

            if(position==items.size()-1){


                footer.setBackgroundResource(R.drawable.listview_bottom_bg);
                footer.setVisibility(View.VISIBLE);

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

            }
            else{
                separator.setVisibility(View.VISIBLE);
            }

            TextView name = (TextView) view.findViewById(R.id.tvCompany);
            name.setTypeface(Font.getFont(context, Font.ROBOTO_MEDIUM));

            TextView date=(TextView) view.findViewById(R.id.tvShoppingDate);
            date.setTypeface(Font.getFont(context, Font.ROBOTO_LIGHT));


            
            if (name != null) {
            	name.setText(item.getCompany().getCompanyName());
                date.setText(item.getDate());
            }
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
