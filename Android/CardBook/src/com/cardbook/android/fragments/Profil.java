package com.cardbook.android.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cardbook.android.CardbookApp;
import com.cardbook.android.R;
import com.cardbook.android.activities.Barcode;
import com.cardbook.android.activities.MainActivity;
import com.cardbook.android.activities.UserInformation;
import com.cardbook.android.adapters.FragmentPageListener;
import com.cardbook.android.adapters.UserInformationListener;
import com.cardbook.android.common.AppConstants;
import com.cardbook.android.common.Font;
import com.cardbook.android.common.Log;
import com.cardbook.android.connectivity.BitmapLruCache;
import com.cardbook.android.connectivity.ConnectionManager;
import com.cardbook.android.connectivity.RequestCallBack;
import com.cardbook.android.models.CardBookUser;
import com.cardbook.android.models.address.City;
import com.cardbook.android.models.address.Country;
import com.cardbook.android.models.address.County;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Profil extends BaseFragment implements OnClickListener, UserInformationListener {

    View view;
    TextView name,surname, mailH, mail, dateH, date, genderH, gender, phoneH, phone,
            countryH, country, cityH, city,countyH, county, addressH, address;

    ImageButton btnUpdate, btnLogout, btnBarcode;
    private ProgressDialog dialog;

    CardbookApp app;
    CardBookUser user;

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    BitmapLruCache cache;

    int btnUpdateId=99;
    int btnLogoutId=66;
    int btnBarcodeId=33;

    public Profil(){

    }

    public Profil(FragmentPageListener listener){
        pageListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profiilim, container, false);
        dialog = new ProgressDialog(getActivity());
        setContent();
        return view;
    }

    public void setContent(){
        app=CardbookApp.getInstance();
        user=app.getUser();

        if(user==null){
            Log.i("User is null");
            user=CardbookApp.getInstance().getUser();
        }

        Typeface regular, light;
        regular= Font.getFont(getActivity(), Font.ROBOTO_REGULAR);
        light=Font.getFont(getActivity(), Font.ROBOTO_LIGHT);

        name=(TextView) view.findViewById(R.id.tvProfilName);
        surname=(TextView) view.findViewById(R.id.tvProfilSurname);
        mailH=(TextView) view.findViewById(R.id.tvProfilMailHeader);
        mail=(TextView) view.findViewById(R.id.tvProfilMail);
        dateH=(TextView) view.findViewById(R.id.tvProfilDateHeader);
        date=(TextView) view.findViewById(R.id.tvProfilDate);
        genderH=(TextView) view.findViewById(R.id.tvProfilGenderHeader);
        gender=(TextView) view.findViewById(R.id.tvProfilGender);
        phoneH=(TextView) view.findViewById(R.id.tvProfilPhoneHeader);
        phone=(TextView) view.findViewById(R.id.tvProfilPhoone);
        countryH=(TextView) view.findViewById(R.id.tvProfilCountryHeader);
        country=(TextView) view.findViewById(R.id.tvProfilCountry);
        cityH=(TextView) view.findViewById(R.id.tvProfilCityHeader);
        city=(TextView) view.findViewById(R.id.tvProfilCity);
        countyH=(TextView) view.findViewById(R.id.tvProfilCountyHeader);
        county=(TextView) view.findViewById(R.id.tvProfilCounty);
        addressH=(TextView) view.findViewById(R.id.tvProfilAddressLineHeader);
        address=(TextView) view.findViewById(R.id.tvProfilAdressLine);

        btnUpdate=(ImageButton)view.findViewById(R.id.btnProfilUpdate);
        btnUpdate.setId(btnUpdateId);
        btnUpdate.setOnClickListener(this);

        btnLogout=(ImageButton)view.findViewById(R.id.btnLogout);
        btnLogout.setId(btnLogoutId);
//        btnLogout.setOnClickListener(this);

        btnLogout=(ImageButton)view.findViewById(R.id.btnBarcode);
        btnLogout.setId(btnBarcodeId);
        btnLogout.setOnClickListener(this);

        TextView[] regularArray={mailH,dateH,genderH,phoneH, countryH,countyH,cityH, addressH, name,surname};
        TextView[] ligthArray={mail,date,gender,phone, country,county,city, address};

        for(TextView tv:regularArray)
            tv.setTypeface(regular);

        for(TextView tv:ligthArray)
            tv.setTypeface(light);

        updateInformations();



        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(getActivity());

        this.cache=new BitmapLruCache(cacheSize);

        setNavBarItemsStyle(view);
        addImage();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UserInformation.informationListener=this;
    }

    @Override
    public void updateInformations() {

//        Country mcountry=getCountry(user.getAddres().getCountryId());
//        City mcity=getCity(user.getAddres().getCityId(),mcountry.getId());
//        County mcounty=getCounty(user.getAddres().getCountId(), mcity.getId(),mcountry.getId());

        String mcountry=user.getAddres().getCountry();
        String mcity=user.getAddres().getCity();
        String mcounty=user.getAddres().getCounty();

        String strGender=user.getGender().equals("M")?"Erkek":"Kadın";


        TextView[] textViews={name,surname,mail,date,gender,phone, country,city,county, address};
        String[] contentArray={user.getName(),user.getSurname(), user.getEmail(), user.getBirthDate(),
                strGender, user.getPhone1(), mcountry, mcity, mcounty, user.getAddres().getAddressLine()};

        for(int i=0; i<textViews.length;i++)
            textViews[i].setText(contentArray[i]);

    }

    public void showBarcode(){

        Intent i=new Intent(getActivity(), Barcode.class);
        i.putExtra("Navigation","AppMainTabActivity");
        startActivity(i);
    }


    public static Country getCountry(int id){
        Country result=null;
        ArrayList<Country> countries=CardbookApp.getInstance().getCountries();
        for(Country contry: countries){
            if(contry.getId()==id){
                result=contry;
                break;
            }

        }
        return result;
    }

    public static City getCity(int cityId, int countryId){
        City result=null;
        ArrayList<City> cities=getCountry(countryId).getCities();
        for(City city: cities){
            if(city.getId()==cityId){
                result=city;
                break;
            }

        }
        return result;
    }

    public static County getCounty(int countyId, int cityId, int countryId){
        County result=null;
        ArrayList<County> counties=getCity(cityId, countryId).getCounties();
        for(County county: counties){
            if(county.getId()==countyId){
                result=county;
                break;
            }

        }
        return result;
    }

    public void addImage(){
        Log.i("addImage: " + user.getProfilPhotoUrl());
        imageLoader=new ImageLoader(requestQueue, this.cache);
        final ImageView mImageView= (ImageView)view.findViewById(R.id.userImage);
        imageLoader.get(user.getProfilPhotoUrl(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Log.i("Image Response is done ");


                if(response.getBitmap()!=null){
                    Bitmap result=AppConstants.addMask(Profil.this.getActivity(), response.getBitmap(), R.drawable.listview_photomask);

                    mImageView.setImageBitmap(result);
                    mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    mImageView.setBackground(null);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Image Response Error");
            }
        });
    }


    public void logout(){

        try{
            AppConstants.setUserInformation(getActivity(),null);
            Toast.makeText(getActivity(),"Hesabınız başarıyla silindi.",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(getActivity(),"Hata oluştu; lütfen daha sonra tekrar deneyin.",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onClick(View view) {

        if(view.getId()==btnUpdateId){



            Intent intent=new Intent(getActivity(), UserInformation.class);
            intent.putExtra("isFromProfil",true);
            startActivity(intent);
        }
        else if(view.getId()==btnLogoutId)
            logout();
        else if(view.getId()==btnBarcodeId)
            showBarcode();

    }

    public void openUserInformationActivity(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Adres bilgileri indiriliyor...");
                dialog.show();
                Log.i("Adres bilgileri indiriliyor.");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try{
                    MainActivity.convertAddresList(result.getJSONObject(AppConstants.POST_DATA));
                    dialog.dismiss();

                    Intent intent=new Intent(getActivity(), UserInformation.class);
                    intent.putExtra("isFromProfil",true);
                    startActivity(intent);
                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getActivity());

            }
        };
        ConnectionManager.postData(getActivity(), callback,AppConstants.SM_GET_ADDRESS_LIST,new HashMap<String, String>());
    }


    @Override
    public void backPressed() {
        getActivity().moveTaskToBack(true);
    }


}
