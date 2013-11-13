package com.abdullah.cardbook.fragments;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.activities.AppMainTabActivity;
import com.abdullah.cardbook.adapters.FragmentCommunicator;
import com.abdullah.cardbook.adapters.FragmentPageListener;
import com.abdullah.cardbook.adapters.KampanyaListener;
import com.abdullah.cardbook.adapters.KartlarimDetailListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Font;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.BitmapLruCache;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.CardBookUserCard;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.promotion.Coupon;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KartDetail extends BaseFragment implements View.OnClickListener {

    public KampanyaListener kampanyaListener;
    public FragmentCommunicator fragmentCommunicator;

	ListView couponListView;
    int position;
    CardbookApp app;
    CardBookUser user;
	Company company;

    TextView companyName, puanPay, puanPayda, tvBildirim, tvKartNo;
    ImageView companyImage;
    EditText etKartNo;
    Button btnKampanyalar, btnAlisverisler, btnSave;
    ImageButton btnNotification;
    private ProgressDialog dialog;

    private static int BTN_SAVE=159;
    private static int BTN_NOTIFICATION=951;
    private static int BTN_KAMPANYALAR=111;
    private static int BTN_ALISVERISLER=222;

    private boolean wantNotification;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;

    private View view;
	public KartDetail(){

	}

	public KartDetail(FragmentPageListener listener){
		super.pageListener = listener;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        view = inflater.inflate(R.layout.kart_detail, container, false);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);

        companyName=(TextView)view.findViewById(R.id.tvKartDetailCompanyName);
        puanPay=(TextView)view.findViewById(R.id.tvKartDetailPuan);
        puanPayda=(TextView)view.findViewById(R.id.tvKartDetailPuanLimit);

        tvBildirim=(TextView)view.findViewById(R.id.tvKartDetailNotificationStatusHeader);
        tvKartNo=(TextView)view.findViewById(R.id.tvKartDetailKartNoHeader);

        etKartNo=(EditText)view.findViewById(R.id.editKartDetailKartNo);
        etKartNo.setCursorVisible(false);

        btnAlisverisler=(Button) view.findViewById(R.id.btnkartDetailAlisveris);
        btnAlisverisler.setTypeface(regular);
        btnAlisverisler.setId(BTN_ALISVERISLER);
        btnAlisverisler.setOnClickListener(this);

        btnKampanyalar=(Button) view.findViewById(R.id.btnKartDetailKampanyalar);
        btnKampanyalar.setTypeface(regular);
        btnKampanyalar.setId(BTN_KAMPANYALAR);
        btnKampanyalar.setOnClickListener(this);

        btnNotification=(ImageButton)view.findViewById(R.id.btnNotificationState);
        btnNotification.setOnClickListener(this);
        btnNotification.setId(BTN_NOTIFICATION);

        btnSave=(Button)view.findViewById(R.id.btnkartDetailSave);
        btnSave.setTypeface(regular);
        btnSave.setId(BTN_SAVE);
        btnSave.setOnClickListener(this);

        setNavBarItemsStyle(view);

        Bundle bundle=this.getArguments();
        position=bundle.getInt("position",0);

        app=CardbookApp.getInstance();
        user=app.getUser();

        Log.i("KertDetail positioın: "+position);
        couponListView=(ListView)view.findViewById(R.id.kartDetailListView);
        couponListView.setDivider(null);

        company=CardbookApp.getInstance().getCompanies().get(position);



        getCompanyDetail();


        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(getActivity());

        this.cache=new BitmapLruCache(cacheSize);

        return view;
	}

    private void getCompanyDetail(){

        Map<String, String> list=new HashMap<String, String>();
        list.put("userId", user.getId());
        list.put("companyId", String.valueOf(company.getCompanyId()));
        ConnectionManager.postData(getActivity(),this,AppConstants.SM_GET_COMPANY_DETAIL,list);

    }
    private void setContent(){
        companyName.setText(company.getCompanyName());
        puanPay.setText("1000");
        puanPayda.setText(" / 2000");
        wantNotification=company.isUserWantNotification();
        addImage();
        setNotification();


    }

    private void setNotification(){
        if(wantNotification)
            btnNotification.setBackgroundResource(R.drawable.notification_on);
        else
            btnNotification.setBackgroundResource(R.drawable.notification_off);
    }

    private void setList(ArrayList<Coupon> list){

        KartlarimDetailListAdapter adapter=new KartlarimDetailListAdapter(getActivity(),R.layout.kartlarim_detail_list_template,list);

        couponListView.setAdapter(adapter);
    }
    @Override
    public void backPressed() {
        pageListener.onSwitchBeforeFragment(AppConstants.KARTLARIM);
    }

    @Override
    public void onRequestStart() {

        super.onRequestStart();
        dialog.setMessage("Kart Detayı geliyor...");
        dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);
        Log.i("onResponseComplete: "+result);
        setContent();
        JSONObject jsonObject=result.optJSONObject(AppConstants.POST_DATA);


        wantNotification=jsonObject.optBoolean(CardBookUser.WANT_NOTIFICATION);
        company.setUserWantNotification(wantNotification);

        if(wantNotification==true){
            Log.i("Notification is true");
        }
        else
            Log.i("Notification is false");

        JSONArray jsonArray=jsonObject.optJSONArray(Company.SHOPPING_PROMOTION_COUPON_LIST);
        Log.i("Coupon List: "+jsonArray.toString());
        company.setCouponList(jsonArray);
        setContent();
        setList(company.getCouponList());
        dialog.dismiss();
    }

    @Override
    public void onRequestError() {
        super.onRequestError();
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==BTN_SAVE)
            saveCard();
        else if(view.getId()==BTN_NOTIFICATION){
            saveNotificationSetting();
        }
        else if(view.getId()==BTN_KAMPANYALAR){

//            Bundle data=new Bundle();
//            data.putInt("company",company.getCompanyId());
//            Kampanyalar detail=new Kampanyalar();
//            detail.setArguments(data);
//            pageListener.onSwitchToNextFragment(AppConstants.KAMPANYALAR,detail, this);

            mActivity.mViewPager.setCurrentItem(1);
            mActivity.mTabHost.setCurrentTab(1);
            mActivity.openCampaign(company);

        }

    }

    public void saveCard(){
        RequestCallBack callback=new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Kart kaydediliyor...");
                dialog.show();
            }

            @Override
            public void onRequestComplete(JSONObject result) {
                Log.i("kart kaydetme: "+result.toString());
                if(result.optString(ConnectionManager.RESULT_CODE).equals(ConnectionManager.RESULT_CODE_OK)){
                    Toast.makeText(getActivity(),"Kartınız kaydedildi.",Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getActivity(),"Kayıt işleminde hata oluştu; lütfen tekrar deneyiniz.",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
            }
        };

        CardBookUser user=CardbookApp.getInstance().getUser();

        CardBookUserCard card=new CardBookUserCard(Integer.parseInt(user.getId()),company.getCompanyId(),etKartNo.getText().toString());

        Map<String,String> list=card.getCardAsMap();
        ConnectionManager.postData(getActivity(), callback, AppConstants.SM_SET_USER_COMPANY_CARD, list);


    }

    public void saveNotificationSetting() {

        Map<String,String> list=new HashMap<String, String>();
        list.put("companyId",String.valueOf(company.getCompanyId()));
        list.put("userId",user.getId());
        JSONObject jsonObject=new JSONObject(list);


        boolean is=this.wantNotification==true?false:true;

        try {
            jsonObject.put("wantNotification", is);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject withParameters=ConnectionManager.addDefaultParameters(jsonObject);

        RequestCallBack callback=new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Ayarlarınız kaydediliyor...");
                dialog.show();
            }

            @Override
            public void onRequestComplete(JSONObject result) {
                Log.i("notification ayar: "+result.toString());
                if(result.optString(ConnectionManager.RESULT_CODE).equals(ConnectionManager.RESULT_CODE_OK)){
//                    boolean is=result.optBoolean();

                    getCompanyDetail();
                    Toast.makeText(getActivity(),"Ayarlarınız kaydedildi.",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getActivity(),"Kayıt işleminde hata oluştu; lütfen tekrar deneyiniz.",Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getActivity());
            }
        };




        ConnectionManager.postData(getActivity(), callback, AppConstants.SM_SET_USER_COMPANY_NOTIFICATION_STATUS, withParameters);


    }


    public void addImage(){
        Log.i("addımage: " + user.getProfilPhotoUrl());
        imageLoader=new ImageLoader(requestQueue, this.cache);
        final ImageView mImageView= (ImageView)view.findViewById(R.id.kartDetailCompanyImage);
        imageLoader.get(company.getCompanyLogoURL(), new ImageLoader.ImageListener() {
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


}
