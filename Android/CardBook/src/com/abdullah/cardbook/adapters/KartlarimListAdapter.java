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
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.BitmapLruCache;
import com.abdullah.cardbook.models.CardBookUserCard;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.models.Company;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;


import java.util.ArrayList;

public class KartlarimListAdapter extends ArrayAdapter<Company>{

	private ArrayList<Company> items;
	private Context context;
	private int layout;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;
    ImageView mImageView;
    View view;
	
	public KartlarimListAdapter(Context context, int layout, ArrayList<Company> items) {
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

        Company item = items.get(position);
        if (item!= null) {
        	// user image and mask
        	mImageView= (ImageView)view.findViewById(R.id.kartlarimListCardImage);

            addImage(item.getCompanyLogoURL());
//        	Bitmap result=AppConstants.addMask(this.context, R.drawable.dummy_big_man, R.drawable.listview_photomask);
        	
        	//mImageView.setImageBitmap(result);
//        	mImageView.setScaleType(ScaleType.CENTER);

        	// user name and title
            Typeface font=Font.getFont(context, Font.ROBOTO_MEDIUM);
            
            TextView name = (TextView) view.findViewById(R.id.kartlarimListTvName);
            name.setTypeface(font);
            
            if (name != null) {
                
            	name.setText(item.getCompanyName());

            }
         }
        else{
        	
        }

        return view;
    }

    public void addImage(String url){

        imageLoader=new ImageLoader(requestQueue, this.cache);
        mImageView= (ImageView)view.findViewById(R.id.kartlarimListCardImage);
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.i("Image Response is done ");


                if(response.getBitmap()!=null){
//                    Bitmap result=AppConstants.addMask(KartDetail.this.getActivity(), response.getBitmap(), R.drawable.listview_photomask);

                    mImageView.setImageBitmap(response.getBitmap());
                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Image Response Error");
            }
        });
    }


    @Override
	public boolean isEnabled(int position)
	{
		return true;
	}
}
