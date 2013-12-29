package com.abdullah.cardbook.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.CountryListAdapter;
import com.abdullah.cardbook.adapters.UserInformationListener;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.connectivity.BitmapLruCache;
import com.abdullah.cardbook.connectivity.ConnectionManager;
import com.abdullah.cardbook.connectivity.RequestCallBack;
import com.abdullah.cardbook.fragments.Profil;
import com.abdullah.cardbook.models.Campaign;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.Company;
import com.abdullah.cardbook.models.Shopping;
import com.abdullah.cardbook.models.address.Address;
import com.abdullah.cardbook.models.address.City;
import com.abdullah.cardbook.models.address.Country;
import com.abdullah.cardbook.models.address.County;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by abdullah on 10/24/13.
 */

/*
Name *
Surname *
E-mail
Birth date *
Profile photo
Phone Number
Address (Country ID, City ID, County ID, Address Line) *
Gender *
 */
public class UserInformation extends Activity implements View.OnClickListener{

    public static UserInformationListener informationListener;
    private ProgressDialog dialog;
    private ImageView userImage;
    private EditText name, surname, mail, phoneNumber,date, addressLine;
    private Spinner spCountry, spCity, spCounty, spGender;
    private Button btnuserInfoUpdate;
    private int positionCountry,positionCity, positionCounty;

    private ArrayList<Country> countryList;
    private ArrayList<Country> dummyCountryList;
    private CardBookUser user;

    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    BitmapLruCache cache;
    private boolean isFromProfil;
    private boolean isChangeProgramatically;

    private int year, month, day;

    private int countCountry, countCity, countCounty;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        isFromProfil=false;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isFromProfil=extras.getBoolean("isFromProfil");
            String resultProfil=isFromProfil==true?"True":"false";
            Log.i("isFromProfil"+resultProfil);
        }




        user=CardbookApp.getInstance().getUser();
        // SOn HATA
        countryList=CardbookApp.getInstance().getCountries();

        userImage=(ImageView)findViewById(R.id.userImage);
        name=(EditText)findViewById(R.id.editFirstName);
        surname=(EditText)findViewById(R.id.editLastName);
        mail=(EditText)findViewById(R.id.editMail);
        phoneNumber=(EditText)findViewById(R.id.editPhone);
        date=(EditText)findViewById(R.id.editDate);
        addressLine=(EditText)findViewById(R.id.editAddress);

        name.setText(user.getName());
        surname.setText(user.getSurname());
        mail.setText(user.getEmail());

        if(user.getPhone1()!=null)
            phoneNumber.setText(user.getPhone1());
        if(user.getBirthDate()!=null)
            date.setText(user.getBirthDate());
        if(user.getAddres()!=null && user.getAddres().getAddressLine()!=null)
            addressLine.setText(user.getAddres().getAddressLine());

        date.setId(99);
        date.setInputType(InputType.TYPE_NULL);
        date.setCursorVisible(false);
        date.setOnClickListener(this);

        spGender=(Spinner)findViewById(R.id.spGender);

        spCountry=(Spinner)findViewById(R.id.spCountry);
        spCity=(Spinner)findViewById(R.id.spCity);
        spCounty=(Spinner)findViewById(R.id.spCounty);

        if(!isFromProfil){
            spCity.setEnabled(false);
            spCounty.setEnabled(false);
        }

        btnuserInfoUpdate=(Button)findViewById(R.id.btnUserInfoUpdate);
        btnuserInfoUpdate.setOnClickListener(this);

        countCountry=0;
        countCity=0;
        countCounty=0;


        requestQueue= CardbookApp.getInstance().getRequestQuee();
        int cacheSize=AppConstants.getCacheSize(this);

        this.cache=new BitmapLruCache(cacheSize);

        addImage();
        addGender();




        if(!isFromProfil){
            if(CardbookApp.getInstance().getCountries().size()==1)
                addCountry(countryList, 1);
            else
                addCountry(countryList, 0);

        }
        else{
            isChangeProgramatically=true;
            int countryPosition=setCountryPosition(user.getAddres().getCountryId());
            addCountry(countryList, countryPosition);

            int cityPosition=setCityPosition(user.getAddres().getCityId(),user.getAddres().getCountryId());
            addCity(countryList.get(countryPosition-1).getCities(),cityPosition);

            int countyPosition=setCountyPosition(user.getAddres().getCountId(),user.getAddres().getCityId(), user.getAddres().getCountryId());
            addCounty(countryList.get(countryPosition-1).getCities().get(cityPosition-1).getCounties(),countyPosition);


        }

        dialog = new ProgressDialog(this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }



    public void checkForValidate(){

        Log.i("checkfor validate is runned");
        EditText[] editItems={ name, surname, mail, date, addressLine};
        Spinner[]  editSpinner={spCountry, spCity, spCounty, spGender};


        for(EditText et:editItems){
            if(et.getText().length()==0 || et.getText().equals(" ")){
                toastForRequirements();

                return;
            }
        }

        for(Spinner sp:editSpinner){
            if(sp.isEnabled()==false || sp.getSelectedItemPosition()==0 ){
                toastForRequirements();
                return;
            }
        }


        user.setName(name.getText().toString());
        user.setSurname(surname.getText().toString());
        user.setEmail(mail.getText().toString());
        user.setBirthDate(date.getText().toString());
        String gender=spGender.getSelectedItemPosition()==1?"M":"F";
        user.setGender(gender);

        if(phoneNumber.getText().length()>0)
            user.setPhone1(phoneNumber.getText().toString());

        Country ct=countryList.get(spCountry.getSelectedItemPosition()-1);

        Address adrs=new Address();
        adrs.setAddressLine(addressLine.getText().toString());
        adrs.setCountryId(ct.getId());
        adrs.setCountry(ct.getName());

        adrs.setCityId(ct.getCities().get(spCity.getSelectedItemPosition()-1).getId());
        adrs.setCity(ct.getCities().get(spCity.getSelectedItemPosition()-1).getName());

        adrs.setCountId(ct.getCities().get(spCity.getSelectedItemPosition()-1).getCounties().get(spCounty.getSelectedItemPosition()-1).getId());
        adrs.setCounty(ct.getCities().get(spCity.getSelectedItemPosition()-1).getCounties().get(spCounty.getSelectedItemPosition()-1).getName());
        user.setAddres(adrs);

        String method="";
        if(!isFromProfil)
            method=AppConstants.SM_CREATE_OR_UPDATE_USER;
        else
            method=AppConstants.SM_UPDATEE_USER_INFO;

        ConnectionManager.postData(this, new RequestCallBack() {
            @Override
            public void onRequestStart() {
                dialog.setMessage("Kart Detayı geliyor...");
                dialog.show();
            }

            @Override
            public void onRequestComplete(JSONObject result) {
                dialog.dismiss();
                if(result.optString(ConnectionManager.RESULT_CODE).equals(ConnectionManager.RESULT_CODE_OK)){

                    Toast.makeText(getApplicationContext(),"Bilgileriniz kaydedildi.",Toast.LENGTH_LONG).show();

                    if(!isFromProfil){
                       CardBookUser newUser=new CardBookUser(result.optJSONObject(AppConstants.POST_DATA));
//                        AppConstants.setUserInformation(getApplicationContext(),result.optJSONObject(AppConstants.POST_DATA).toString());
                        newUser.setAddres(user.getAddres());
                        user=newUser;
                        AppConstants.setUserInformation(getApplicationContext(),user.toJSON().toString());
//                    user.setBarcodeUrl(result.optJSONObject(AppConstants.POST_DATA).optString(CardBookUser.BARCODE_URL));
                        CardbookApp.getInstance().setUser(user);
                        Log.i("Validate is not from Profil");
                        getCompanyList();
                    }
                    else{
                        Log.i("Validate is from Profil");
                        if(informationListener!=null)
                            informationListener.updateInformations();

                        AppConstants.setUserInformation(getApplicationContext(),user.toJSON().toString());
//                    user.setBarcodeUrl(result.optJSONObject(AppConstants.POST_DATA).optString(CardBookUser.BARCODE_URL));
                        CardbookApp.getInstance().setUser(user);
                        onBackPressed();

                    }

                }
                else
                    Toast.makeText(getApplicationContext(),"Hata oluştu: "+result.optString(ConnectionManager.RESULT_MESSAGE),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestError() {
                AppConstants.ErrorToast(getApplicationContext());
            }
        },method, user.getUserInfoAsDict());

    }

    private void toastForRequirements(){
        Log.i("toast validate is runned");
        Toast.makeText(this,"Lütfen zorunlu alanları doldurunuz.",Toast.LENGTH_LONG).show();
    }

    public void showDialog(){
        Calendar calender=Calendar.getInstance();

        DatePickerDialog dialog=new DatePickerDialog(this, datePickerListener,
                calender.get(Calendar.YEAR), calender.get(Calendar.MONTH),calender.get(Calendar.DAY_OF_MONTH));

        dialog.setTitle("Doğum Tarihinizi Seçiniz");
        dialog.show();

    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year));

        }
    };

    public void addImage(){
            Log.i("addımage: "+user.getProfilPhotoUrl());
            ImageLoader imageLoader=new ImageLoader(requestQueue, this.cache);
            final ImageView mImageView= (ImageView)findViewById(R.id.userImage);
            imageLoader.get(user.getProfilPhotoUrl(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Log.i("Image Response is done ");


                    if(response.getBitmap()!=null){
//                        Bitmap result=AppConstants.addMask(UserInformation.this, response.getBitmap(), R.drawable.listview_photomask);
//
                        mImageView.setImageBitmap(response.getBitmap());
                        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Image Response Error");
                }
            });


//        Bitmap image= AppConstants.addMask(this, R.drawable.dummy_big_man, R.drawable.listview_photomask);
//        userImage.setImageBitmap(image);
    }

    public void addGender(){
        ArrayList<String> gender=new ArrayList<String>();
        gender.add("Cinsiyet Seçin");
        gender.add("Erkek");
        gender.add("Kadın");
        CountryListAdapter adapter=new CountryListAdapter(this,R.layout.address_adapter, gender);
        spGender.setAdapter(adapter);

        if(user.getGender()!=null){
            if(user.getGender().equals("M"))
                spGender.setSelection(1);
            else
                spGender.setSelection(0);
        }


    }

    public void addCountry(final ArrayList<Country> countries,final int selection) {
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<countries.size();i++){
            if(i==0)
                item.add("Ülke Seçin");
            item.add(countries.get(i).getName());
            Log.i("Contries: "+countries.get(i).getName()+", "+countries.size());
        }

        final CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        spCountry.setAdapter(dataAdapter);
        spCounty.setTag("Country");
        spCountry.setSelection(selection);

        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCountry=i;
                countCountry++;
                Log.i("İtem Selected Country: "+i);

                if(isChangeProgramatically)
                    return;

                if(i!=0){
                    spCity.setEnabled(true);
                    addCity(countries.get(i-1).getCities(),0);
                }
                else
                    addCity(countries.get(i).getCities(),0);

                dataAdapter.setNotifyOnChange(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



    }
    public void addCity(final ArrayList<City> cities, final int selection) {
        Log.i("Add City with selection: "+selection);
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<cities.size();i++){
            if(i==0)
                item.add("İl");
            item.add(cities.get(i).getName());
//            Log.i("Cities: "+cities.get(i).getName()+", "+cities.size());
        }

        final CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        spCity.setAdapter(dataAdapter);
        spCity.setTag("City");

        spCity.setSelection(selection);




        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCity=i;

                Log.i("İtem Selected City: "+i);

                if(isChangeProgramatically)
                    return;

                if(i!=0){
                    spCounty.setEnabled(true);
                    addCounty(cities.get(i-1).getCounties(),0);
                }
                else
                    addCounty(cities.get(i+1).getCounties(),0);

                dataAdapter.setNotifyOnChange(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
    public void addCounty(ArrayList<County> countries, int selection) {
        Log.i("Add County with selection: "+selection);
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<countries.size();i++){
            if(i==0)
                item.add("İlçe");
            item.add(countries.get(i).getName());
//            Log.i("Contries: "+countries.get(i).getName()+", "+countries.size());
        }

        final CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        // set the ArrayAdapter to the spinner
        spCounty.setAdapter(dataAdapter);
        spCounty.setTag("County");
        spCounty.setSelection(selection);



        spCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(isChangeProgramatically){
                    isChangeProgramatically=false;
                    return;
                }

                positionCounty=i;
                Log.i("İtem Selected County: "+i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setIdFromLocate(){
        int foundedCountryPosition, foundedCityPosition, foundedCountyPosition;
        ArrayList<City> cities;
        ArrayList<County> counties;
        for(int m=0; m<user.getAddres().getLocateFromFacebook().length;m++){
//            String item=
            for(int i=0; i<countryList.size();i++){
                Country country=countryList.get(i);
                cities=country.getCities();
                for(int j=0; j<cities.size();j++){
                    City city=cities.get(j);
                    counties=city.getCounties();
                    for(int k=0; k<country.getCities().size();k++){
                        County county=counties.get(k);
                    }
                }
            }
        }
    }

    private int setCountryPosition(int id){
        Country result=null;
        int position=0;
        ArrayList<Country> countries=CardbookApp.getInstance().getCountries();
        for(Country contry: countries){
            position++;
            if(contry.getId()==id){
                result=contry;
                break;
            }

        }
        Log.i("set Country result: "+result.getName());

//        addCountry(countries);
//        spCountry.setSelection(position);
//        setCity(user.getAddres().getCityId(),user.getAddres().getCountryId());

        return position;
    }

    private int setCityPosition(int cityId, int countryId){
        City result=null;
        int position=0;
        ArrayList<City> cities= Profil.getCountry(countryId).getCities();
        for(City city: cities){
            position++;
            if(city.getId()==cityId){
                result=city;
                break;
            }

        }
        return position;
    }

    private int setCountyPosition(int countyId, int cityId, int countryId){
        County result=null;
        int position=0;
        ArrayList<County> counties=Profil.getCity(cityId, countryId).getCounties();
        for(County county: counties){
            position++;
            if(county.getId()==countyId){
                result=county;
                break;
            }

        }


//        addCounty(counties,position);
//        Log.i("setCounty is runned: "+result.getName()+", p: "+position);
//        spCounty.setSelection(position);

        return position;

    }


    @Override
    public void onClick(View view) {
        if(view.getId()==99)
            showDialog();
        else
            checkForValidate();

    }


    public class PostDataOperation  extends AsyncTask<CardBookUser, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(UserInformation.this);
        private JSONArray array;


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.
            Log.i("Postdate fomr UserInformation is started");

            //UI Element

            Dialog.setMessage("Bilgiler yükleniyor...");
            Dialog.show();
        }

        // Call after onPreExecute method
        protected Void doInBackground(CardBookUser... user) {

            CardbookApp app=CardbookApp.getInstance();

//            JSONObject resultUser=ConnectionManager.postData2(AppConstants.SM_CREATE_OR_UPDATE_USER,user[0].getUserInfoAsDict());

            if(!ConnectionManager.isOnline(UserInformation.this)){
                Toast.makeText(UserInformation.this,"İnternet bağlantısı bulunmuyor; lütfen internete bağlanın.",Toast.LENGTH_LONG);
                cancel(true);

            }


            JSONObject resultCards=ConnectionManager.postData2(AppConstants.SM_GET_COMPANY_LIST,null);

            JSONObject resultCampaign=ConnectionManager.postData2(AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST, null);

            ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("userId", user[0].getId()));


            JSONObject resultShopping=ConnectionManager.postData2(AppConstants.SM_GET_ALL_SHOPPING_LIST,list);
            array=resultShopping.optJSONArray(AppConstants.POST_DATA);
            for(int i=0; i<array.length();i++){
                Shopping shopping=new Shopping(array.optJSONObject(i));
                app.addShoppings(shopping);
            }

            try{
                array=resultCampaign.getJSONArray(AppConstants.POST_DATA);
                for(int i=0; i<array.length();i++){
                    Campaign campaing=new Campaign((JSONObject)array.get(i));

                    CardbookApp.getInstance().addCampaign(campaing);
                }

            }
            catch (JSONException e){
                e.printStackTrace();
            }


            try {
                array=resultCards.getJSONArray(AppConstants.POST_DATA);
                Log.i(((JSONObject)array.get(0)).getString("UDate"));
                String dateString=((JSONObject)array.get(0)).getString("UDate");
                Date date=AppConstants.parseMsTimestampToDate(dateString);
                SimpleDateFormat ft = new SimpleDateFormat ("dd.mm.yyyy");

                Log.i("Current Date: "+ft.format(date));


                for(int i=0; i<array.length();i++){
                    Company company=new Company((JSONObject)array.get(i));

                    CardbookApp.getInstance().addCompany(company);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            Intent intent=new Intent(UserInformation.this, AppMainTabActivity.class);
            startActivity(intent);
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


    public void getCompanyList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Company");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try {
                    JSONArray array=result.getJSONArray(AppConstants.POST_DATA);
                    Log.i(((JSONObject) array.get(0)).getString("UDate"));
                    String dateString=((JSONObject)array.get(0)).getString("UDate");
                    Date date=AppConstants.parseMsTimestampToDate(dateString);
                    SimpleDateFormat ft = new SimpleDateFormat ("dd.mm.yyyy");

                    Log.i("Current Date: "+ft.format(date));


                    for(int i=0; i<array.length();i++){
                        Company company=new Company((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCompany(company);
                    }
                    getCampanignList();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };
        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_COMPANY_LIST,new JSONObject());
    }

    public void getCampanignList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Campaign");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                try{
                    JSONArray array=result.getJSONArray(AppConstants.POST_DATA);
                    for(int i=0; i<array.length();i++){
                        Campaign campaing=new Campaign((JSONObject)array.get(i));

                        CardbookApp.getInstance().addCampaign(campaing);
                    }
                    getShoppingList();

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };
        ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_ALL_ACTIVE_CAMPAIGN_LIST,new JSONObject());
    }

    public void getShoppingList(){
        RequestCallBack callback= new RequestCallBack() {
            @Override
            public void onRequestStart() {
//                dialog.setMessage("Bilgiler indiriliyor...");
//                dialog.show();
                Log.i("Bilgiler indiriliyor: Shopings");
            }

            @Override
            public void onRequestComplete(JSONObject result) {

                JSONArray array=result.optJSONArray(AppConstants.POST_DATA);
                for(int i=0; i<array.length();i++){
                    Shopping shopping=new Shopping(array.optJSONObject(i));
                    CardbookApp.getInstance().addShoppings(shopping);

                    Log.i("Shopping: "+i+" "+shopping.getCompany().getCompanyName());
                }

                dialog.dismiss();

                Intent intent=new Intent(UserInformation.this, AppMainTabActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRequestError() {
                dialog.dismiss();
                AppConstants.ErrorToast(getApplicationContext());

            }
        };

        JSONObject paramater=new JSONObject();
        try{
            paramater.putOpt("userId", CardbookApp.getInstance().getUser().getId());
            Log.i("Shoiing userID paramatere: "+paramater.optString("userId"));
            ConnectionManager.postData(getApplicationContext(), callback,AppConstants.SM_GET_ALL_SHOPPING_LIST,paramater);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        if(isFromProfil)
            super.onBackPressed();
        else
            moveTaskToBack(true);
    }
}

