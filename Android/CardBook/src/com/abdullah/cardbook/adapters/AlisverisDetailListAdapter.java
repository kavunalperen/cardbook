package com.abdullah.cardbook.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.Product;

import java.util.ArrayList;

public class AlisverisDetailListAdapter extends ArrayAdapter<Product>{

	private ArrayList<Product> items;
	private Context context;
	private int layout;
    TextView productName, productValue;
    Typeface medium;
    Typeface light;


	public AlisverisDetailListAdapter(Context context, int layout, ArrayList<Product> items) {
        super(context,layout, items);
        this.context = context;
        this.items = items;
        this.layout=layout;
        medium=Font.getFont(context,Font.ROBOTO_MEDIUM);
        light=Font.getFont(context,Font.ROBOTO_LIGHT);
    }

	
	public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.layout, null);
        }

        Product item = items.get(position);
        if (item!= null) {

            Log.i("Product name:" + item.getName());
            Log.i("Product value:" + item.getValue());
            productName =(TextView) view.findViewById(R.id.alisverisDetailListTvProductName);
            productValue =(TextView) view.findViewById(R.id.alisverisDetailListTvProductValue);

            productName.setTypeface(medium);
            productValue.setTypeface(light);
            
            if (productName != null) {

                productName.setText(item.getName());
                productValue.setText(item.getValueWithFormat()+" TL");

            }
         }
        else{
        	
        }

        return view;
    }
	
	@Override
	public boolean isEnabled(int position)
	{
		return false;
	}
}
