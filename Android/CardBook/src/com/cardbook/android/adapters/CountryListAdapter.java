package com.cardbook.android.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.common.Font;

import java.util.ArrayList;

public class CountryListAdapter extends ArrayAdapter<String>{

	private ArrayList<String> items;
	private Context context;
	private int layout;

	public CountryListAdapter(Context context, int layout, ArrayList<String> items) {
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


        String item = items.get(position);


        if (item!= null) {
        	// user name and title
            Typeface font=Font.getFont(context, Font.ROBOTO_REGULAR);
            
            TextView name = (TextView) view.findViewById(R.id.addressAdapterName);
            name.setTypeface(font);

            name.setText(item);

         }
        else{
        	
        }

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}
