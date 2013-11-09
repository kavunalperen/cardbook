package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KartlarimDetailListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.promotion.Coupon;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KampanyaDetail extends BaseFragment{

	ListView couponListView;
    int position;
    CardbookApp app;
	Company company;

    Button btnKampanyalar, btnAlisverisler, btnSave;

	public KampanyaDetail(){

	}

	public KampanyaDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kart_detail, container, false);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);

        btnAlisverisler=(Button) view.findViewById(R.id.btnkartDetailAlisveris);
        btnAlisverisler.setTypeface(regular);
        btnKampanyalar=(Button) view.findViewById(R.id.btnKartDetailKampanyalar);
        btnKampanyalar.setTypeface(regular);
        btnSave=(Button)view.findViewById(R.id.btnkartDetailSave);
        btnSave.setTypeface(regular);



        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();

        Log.i("KertDetail positioın: "+position);
        couponListView=(ListView)view.findViewById(R.id.kartDetailListView);
        couponListView.setDivider(null);

        company=CardbookApp.getInstance().getCompanies().get(position);
        new PostDataOperation().execute(0);
        return view;
	}

    private void setList(ArrayList<Coupon> list){

        KartlarimDetailListAdapter adapter=new KartlarimDetailListAdapter(getActivity(),R.layout.kartlarim_detail_list_template,list);

        couponListView.setAdapter(adapter);
    }
    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }


    private class PostDataOperation  extends AsyncTask<Integer, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(KampanyaDetail.this.getActivity());
        private JSONObject jsonObject;
        private JSONArray jsonArray;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element

            Dialog.setMessage("Downloading source..");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(Integer... item) {



            ConnectionManager conManager=new ConnectionManager(KampanyaDetail.this.getActivity());

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("userId","6"));
            list.add(new BasicNameValuePair("companyId",String.valueOf(company.getCompanyId())));


            JSONObject companyDetail=conManager.postData(AppConstants.SM_GET_COMPANY_DETAIL,list);

            jsonObject=companyDetail.optJSONObject(AppConstants.POST_DATA);
            jsonArray=jsonObject.optJSONArray(Company.SHOPPING_PROMOTION_COUPON_LIST);
            company=app.getCompanies().get(position);
            company.setCouponList(jsonArray);

//
//            for(int i=0; i<array.length();i++){
//                Shopping shopping=new Shopping(array.optJSONObject(i));
//                app.addShoppings(shopping);
//            }


            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            setList(company.getCouponList());
            Dialog.dismiss();

//            if (Error != null) {
//
//                Intent intent=new Intent(MainActivity.this, AppMainTabActivity.class);
//                startActivity(intent);
//
//            } else {
//
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Hata")
//                        .setMessage("Uygulamayı daha sonra tekrar başlatın.")
//                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                System.exit(0);
//                            }
//                        }).show();
//
//            }


        }

    }



}
