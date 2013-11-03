package com.abdullah.cardbook.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.CardBookUserCard;

import java.util.ArrayList;

public class KampanyalarListAdapter extends ArrayAdapter<Campaign>{

	private ArrayList<Campaign> items;
	private Context context;
	private int layout;

	public KampanyalarListAdapter(Context context, int layout, ArrayList<Campaign> items) {
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

        Campaign item = items.get(position);
        if (item!= null) {
        	// user image and mask
        	ImageView mImageView= (ImageView)view.findViewById(R.id.kampanyalarListCardImage);

        	Bitmap result=AppConstants.addMask(this.context, R.drawable.dummy_big_man, R.drawable.listview_photomask);
        	
        	mImageView.setImageBitmap(result);
        	mImageView.setScaleType(ScaleType.CENTER);

        	// user name and title
            Typeface font=Font.getFont(context, Font.ROBOTO_LIGHT);
            
            TextView name = (TextView) view.findViewById(R.id.kampanyalarListTvName);
            name.setTypeface(font);

           	name.setText(item.getName());

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
