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
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.promotion.Coupon;

import java.util.ArrayList;

public class KartlarimDetailListAdapter extends ArrayAdapter<Coupon>{

	private ArrayList<Coupon> items;
	private Context context;
	private int layout;
    TextView couponName, couponDetail;
    Typeface medium;
    Typeface light;


	public KartlarimDetailListAdapter(Context context, int layout, ArrayList<Coupon> items) {
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

        Coupon item = items.get(position);
        if (item!= null) {

            couponName=(TextView) view.findViewById(R.id.kartlarimDetailListTvCouponName);
            couponDetail=(TextView) view.findViewById(R.id.kartlarimDetailListTvCouponDetail);

            couponName.setTypeface(medium);
            couponDetail.setTypeface(light);
            
            if (couponName != null) {

                couponName.setText(item.getCompanyPromotionId());
                couponDetail.setText(item.getCompanyPromotionText());

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
