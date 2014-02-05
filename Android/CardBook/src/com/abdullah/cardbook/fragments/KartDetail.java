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
import com.abdullah.cardbook.models.CompanyInfo;
import com.abdullah.cardbook.models.Location;
import com.abdullah.cardbook.models.promotion.Coupon;
import com.abdullah.cardbook.models.promotion.Credit;
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
    ImageButton btnNotification, btnHakkında, btnIletisim, btnSubeler;
    private ProgressDialog dialog;

    private static int BTN_SAVE=159;
    private static int BTN_NOTIFICATION=951;
    private static int BTN_KAMPANYALAR=111;
    private static int BTN_ALISVERISLER=222;
    private static int BTN_HAKKINDA=333;
    private static int BTN_ILETISIM=444;
    private static int BTN_SUBELER=555;

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
        dialog.setCancelable(false);
        view = inflater.inflate(R.layout.kart_detail, container, false);

        Typeface regular=Font.getFont(getActivity(),Font.ROBOTO_REGULAR);
        Typeface medium=Font.getFont(getActivity(),Font.ROBOTO_MEDIUM);
        Typeface light=Font.getFont(getActivity(),Font.ROBOTO_LIGHT);

        companyName=(TextView)view.findViewById(R.id.tvKartDetailCompanyName);
        puanPay=(TextView)view.findViewById(R.id.tvKartDetailPuan);
        puanPayda=(TextView)view.findViewById(R.id.tvKartDetailPuanLimit);

//        tvBildirim=(TextView)view.findViewById(R.id.tvKartDetailNotificationStatusHeader);
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

        btnSubeler=(ImageButton) view.findViewById(R.id.btnSubeler);
        btnSubeler.setOnClickListener(this);
        btnSubeler.setId(BTN_SUBELER);

        btnIletisim=(ImageButton) view.findViewById(R.id.btnIletisim);
        btnIletisim.setOnClickListener(this);
        btnIletisim.setId(BTN_ILETISIM);

        btnHakkında=(ImageButton) view.findViewById(R.id.btnHakinda);
        btnHakkında.setOnClickListener(this);
        btnHakkında.setId(BTN_HAKKINDA);

        btnNotification=(ImageButton)view.findViewById(R.id.btnNotificationState);
        btnNotification.setOnClickListener(this);
        btnNotification.setId(BTN_NOTIFICATION);

        btnSave=(Button)view.findViewById(R.id.btnkartDetailSave);
        btnSave.setTypeface(regular);
        btnSave.setId(BTN_SAVE);
        btnSave.setOnClickListener(this);

        setNavBarItemsStyle(view);

        Bundle bundle=this.getArguments();
        company=(Company)bundle.getSerializable(Company.COMPANY);

        app=CardbookApp.getInstance();
        user=app.getUser();

        Log.i("KertDetail positioın: "+company.getCompanyName());
        couponListView=(ListView)view.findViewById(R.id.kartDetailListView);
        couponListView.setDivider(null);

//        company=CardbookApp.getInstance().getCompanies().get(position);


//        getCompanyDetail();

        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(getActivity());

        this.cache=new BitmapLruCache(cacheSize);

        setContent();

        AppConstants.setupTouchForKeyBoard(getActivity(), view);
        return view;
	}

    private void getCompanyDetail(){

        Map<String, String> list=new HashMap<String, String>();
        list.put("userId", user.getId());
        list.put("companyId", String.valueOf(company.getCompanyId()));
        ConnectionManager.postData(getActivity(),this,AppConstants.SM_GET_COMPANY_DETAIL,list);

    }
    private void setContent(){

//        int pay=0;
//        ArrayList<Credit> credits=company.getCreditList();
//        for(int i=0; i<credits.size();i++){
//            pay+=credits.get(i).getPromotionAmount();
//        }


        companyName.setText(company.getCompanyName());
        puanPay.setText(String.valueOf(company.getUsableCredit()));
        puanPayda.setText(" / "+company.getTotalCredit());
        wantNotification=company.isUserWantNotification();
        addImage();
        setNotification();

        etKartNo.setText(company.getCard().getCardNumber());
//        etKartNo.setEnabled(false);
//        btnSave.setEnabled(false);

        setList(company.getCouponList());


    }

    private void getLocationSList(final Company company){

        ArrayList<Location> locations=CardbookApp.getInstance().getLocationsForCompany(company.getCompanyId());
        if(locations!=null){
            Bundle data=new Bundle();
            data.putSerializable(Company.COMPANY,company);
            Subeler kartDetail=new Subeler();
            kartDetail.setArguments(data);
            pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,kartDetail, KartDetail.this);

            return;
        }


        Map<String, String> list=new HashMap<String, String>();
        list.put("companyId", String.valueOf(company.getCompanyId()));
        ConnectionManager.postData(getActivity(), new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Bilgiler yükleniyor...");
                dialog.show();
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                JSONArray jLocations= null;
                try {
                    jLocations = result.getJSONArray("Data");


                for(int i=0; i<jLocations.length();i++){
                    try {
                        Location l=new Location(jLocations.getJSONObject(i));
                        CardbookApp.getInstance().addLocation(company.getCompanyId(),l);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<Location>locList=CardbookApp.getInstance().getLocationsForCompany(company.getCompanyId());
                if(locList!=null){
                    Bundle data=new Bundle();
                    data.putSerializable(Company.COMPANY,company);
                    Subeler kartDetail=new Subeler();
                    kartDetail.setArguments(data);
                    pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,kartDetail, KartDetail.this);


                }
                else
                    Toast.makeText(getActivity(), "Şube bilgisi bulunmamaktadır.",Toast.LENGTH_LONG).show();

                dialog.dismiss();
            }

            @Override
            public void onRequestError() {

            }
        }, AppConstants.SM_GET_COMPANY_LOCATION_LIST, list);
    }

    private void openIletisim(CompanyInfo info){


        Bundle data=new Bundle();
        data.putSerializable(Company.COMPANY,company);
        data.putSerializable(CompanyInfo.COMPANY_INFO,info);
        Iletisim fIletisim=new Iletisim();
        fIletisim.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,fIletisim, KartDetail.this);
    }

    private void openHakkinda(CompanyInfo info){

        if(info==null){
            Toast.makeText(getActivity(), "Şirket bilgisi bulanamadı.",Toast.LENGTH_LONG).show();
            return;
        }
        Bundle data=new Bundle();
        data.putSerializable(Company.COMPANY,company);
        data.putSerializable(CompanyInfo.COMPANY_INFO,info);
        Hakkinda hakkinda=new Hakkinda();
        hakkinda.setArguments(data);
        pageListener.onSwitchToNextFragment(AppConstants.KARTLARIM,hakkinda, KartDetail.this);
    }

    private void getCompanyInfo(final Company company, final int intentFrom){
        CompanyInfo info=CardbookApp.getInstance().getCompanyInfoForCompany(company.getCompanyId());
        if(info!=null){
           if(intentFrom==BTN_ILETISIM)
               openIletisim(info);
            else if(intentFrom==BTN_HAKKINDA)
               openHakkinda(info);
            return;
        }

        Map<String, String> list=new HashMap<String, String>();
        list.put("companyId", String.valueOf(company.getCompanyId()));
        ConnectionManager.postData(getActivity(), new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Bilgiler yükleniyor...");
                dialog.show();
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                JSONObject jLocations= null;
                CompanyInfo cI=null;
                try {
                    jLocations = result.getJSONObject("Data");
                    cI=new CompanyInfo(jLocations);
                    CardbookApp.getInstance().addCompanyInfo(company.getCompanyId(),cI);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(intentFrom==BTN_ILETISIM)
                    openIletisim(cI);
                else if(intentFrom==BTN_HAKKINDA)
                    openHakkinda(cI);

                dialog.dismiss();
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
            }
        }, AppConstants.SM_GET_COMPANY_INFO, list);
    }

    private void setNotification(){
        if(wantNotification)
            btnNotification.setBackgroundResource(R.drawable.notification_on);
        else
            btnNotification.setBackgroundResource(R.drawable.notification_off);
    }

    private void setList(ArrayList<Coupon> list){

        if(list==null)
            return;

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
        dialog.setMessage("Bilgileri yükleniyor...");
//        dialog.show();
    }

    @Override
    public void onRequestComplete(JSONObject result) {
        super.onRequestComplete(result);
        Log.i("onResponseComplete: "+result);
        setContent();
        JSONObject jsonObject=result.optJSONObject(AppConstants.POST_DATA);


        wantNotification=jsonObject.optBoolean(CardBookUser.WANT_NOTIFICATION);
        company.setUserWantNotification(wantNotification);

        JSONObject jCard=jsonObject.optJSONObject(CardBookUserCard.USER_CARD);
        if(jCard!=null){
            CardBookUserCard card=new CardBookUserCard(jsonObject.optJSONObject(CardBookUserCard.USER_CARD));
            etKartNo.setText(card.getCardNumber());
            etKartNo.setEnabled(false);
            btnSave.setEnabled(false);
        }


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

            mActivity.mViewPager.setCurrentItem(1);
            mActivity.mTabHost.setCurrentTab(1);
            mActivity.openCampaign(company);

        }
        else if(view.getId()==BTN_ALISVERISLER){

            mActivity.mViewPager.setCurrentItem(2);
            mActivity.mTabHost.setCurrentTab(2);
            mActivity.openShopping(company);

        }
        else if(view.getId()==BTN_SUBELER)
            getLocationSList(company);
        else if(view.getId()==BTN_ILETISIM)
            getCompanyInfo(company, BTN_ILETISIM);
        else if(view.getId()==BTN_HAKKINDA)
            getCompanyInfo(company, BTN_HAKKINDA);

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
                    dialog.dismiss();

                }
                else
                    Toast.makeText(getActivity(),"Hata oluştu: "+result.optString(ConnectionManager.RESULT_MESSAGE),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onRequestError() {
                AppConstants.ErrorToast(getActivity());
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
                    Toast.makeText(getActivity(),"Hata oluştu: "+result.optString(ConnectionManager.RESULT_MESSAGE),Toast.LENGTH_LONG).show();

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
        Log.i("addımage: " +company.getCompanyLogoURL());
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
