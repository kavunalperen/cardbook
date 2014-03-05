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

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.BitmapLruCache;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.CardBookUserCard;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class KampanyalarListAdapter extends ArrayAdapter<Campaign>{

	private ArrayList<Campaign> items;
	private Context context;
	private int layout;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;
    NetworkImageView mImageView;
    View view;

	public KampanyalarListAdapter(Context context, int layout, ArrayList<Campaign> items) {
        super(context,layout, items);
        this.context = context;
        this.items = items;
        this.layout=layout;

        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(context);

        this.cache=new BitmapLruCache(cacheSize);
    }

	
	public View getView(int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(this.layout, null);
        }

        Campaign item = items.get(position);
        if (item!= null) {
        	// user image and mask
//        	ImageView mImageView= (ImageView)view.findViewById(R.id.kampanyalarListCardImage);
//
//        	Bitmap result=AppConstants.addMask(this.context, R.drawable.dummy_big_man, R.drawable.listview_photomask);

//        	mImageView.setImageBitmap(result);
//        	mImageView.setScaleType(ScaleType.CENTER);

            addImage(item.getIconUrl());
            imageLoader=new ImageLoader(requestQueue, this.cache);
            mImageView= (NetworkImageView)view.findViewById(R.id.kampanyalarListCardImage);
            mImageView.setImageUrl(item.getIconUrl(), imageLoader);
            mImageView.setScaleType(ScaleType.FIT_XY);

        	// user name and title
            Typeface font=Font.getFont(context, Font.ROBOTO_LIGHT);
            
            TextView name = (TextView) view.findViewById(R.id.kampanyalarListTvName);
            name.setTypeface(font);

            TextView detail = (TextView) view.findViewById(R.id.kampanyalarListTvDetail);
            TextView date = (TextView) view.findViewById(R.id.kampanyalarListTvDate);

            detail.setText(item.getDescription());
            date.setText(item.getStartDate()+" - "+item.getEndDate());

           	name.setText(item.getName());

         }
        else{
        	
        }

        return view;
    }

    public void addImage(String url){

        imageLoader=new ImageLoader(requestQueue, this.cache);
        mImageView= (NetworkImageView)view.findViewById(R.id.kampanyalarListCardImage);
        mImageView.setImageUrl(url, imageLoader);
    }


    @Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}
