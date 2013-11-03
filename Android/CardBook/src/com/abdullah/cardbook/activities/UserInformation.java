package com.abdullah.cardbook.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.abdullah.cardbook.CardbookApp;
import com.abdullah.cardbook.R;
import com.abdullah.cardbook.adapters.CountryListAdapter;
import com.abdullah.cardbook.common.AppConstants;
import com.abdullah.cardbook.common.Log;
import com.abdullah.cardbook.models.CardBookUser;
import com.abdullah.cardbook.models.address.City;
import com.abdullah.cardbook.models.address.Country;
import com.abdullah.cardbook.models.address.County;

import java.util.ArrayList;

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

    private ImageView userImage;
    private EditText name, surname, mail, phoneNumber, addressLine;
    private Spinner spCountry, spCity, spCounty;
    private Button btnuserInfoUpdate;
    private int positionCountry,positionCity, positionCounty;

    private ArrayList<Country> countryList;
    private ArrayList<Country> dummyCountryList;
    private CardBookUser user;

    private int countCountry, countCity, countCounty;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);

        user=CardbookApp.getInstance().getUser();
        countryList=CardbookApp.getInstance().getCountries();

        userImage=(ImageView)findViewById(R.id.userImage);
        name=(EditText)findViewById(R.id.editFirstName);
        surname=(EditText)findViewById(R.id.editLastName);
        mail=(EditText)findViewById(R.id.editMail);
        phoneNumber=(EditText)findViewById(R.id.editPhone);
        addressLine=(EditText)findViewById(R.id.editAddress);

        spCountry=(Spinner)findViewById(R.id.spCountry);
        spCity=(Spinner)findViewById(R.id.spCity);
        spCounty=(Spinner)findViewById(R.id.spCounty);

        spCity.setEnabled(false);
        spCounty.setEnabled(false);

        btnuserInfoUpdate=(Button)findViewById(R.id.btnUserInfoUpdate);
        btnuserInfoUpdate.setOnClickListener(this);

        countCountry=0;
        countCity=0;
        countCounty=0;

        addCountry(countryList);
//        addCity(dummyCountryList.get(0).getCities());
//        addCounty(dummyCountryList.get(0).getCities().get(0).getCounties());


    }

    public void addImage(){
        Bitmap image= AppConstants.addMask(this, R.drawable.dummy_big_man, R.drawable.listview_photomask);
        userImage.setImageBitmap(image);
    }
    public void addCountry(final ArrayList<Country> countries) {
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<countries.size();i++){
            if(i==0)
                item.add("Ülke Seçin");
            item.add(countries.get(i).getName());
            Log.i("Contries: "+countries.get(i).getName()+", "+countries.size());
        }

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, item);
//        // set the view for the Drop down list
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // set the ArrayAdapter to the spinner

        CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        spCountry.setAdapter(dataAdapter);
        spCounty.setTag("Country");
        spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCountry=i;
                countCountry++;
                Log.i("İtem Selected Country");
                if(i!=0){
                    spCity.setEnabled(true);
                    addCity(countries.get(i-1).getCities());
                }
                else
                    addCity(countries.get(i+1).getCities());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    public void addCity(final ArrayList<City> cities) {
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<cities.size();i++){
            if(i==0)
                item.add("İl");
            item.add(cities.get(i).getName());
            Log.i("Contries: "+cities.get(i).getName()+", "+cities.size());
        }

        CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        spCity.setAdapter(dataAdapter);
        spCity.setTag("City");
//        spCountry.setSelection(1);


        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCity=i;

                Log.i("İtem Selected City");

                if(i!=0){
                    spCounty.setEnabled(true);
                    addCounty(cities.get(i-1).getCounties());
                }
                else
                    addCounty(cities.get(i+1).getCounties());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void addCounty(ArrayList<County> countries) {
        ArrayList<String> item=new ArrayList<String>();
        for(int i=0; i<countries.size();i++){
            if(i==0)
                item.add("İlçe");
            item.add(countries.get(i).getName());
            Log.i("Contries: "+countries.get(i).getName()+", "+countries.size());
        }

        CountryListAdapter dataAdapter=new CountryListAdapter(this,R.layout.address_adapter,item);
        // set the ArrayAdapter to the spinner
        spCounty.setAdapter(dataAdapter);
        spCounty.setTag("County");
//        spCountry.setSelection(1);
        spCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                positionCounty=i;
                Log.i("İtem Selected County");
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

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(this, AppMainTabActivity.class);
        startActivity(intent);
    }


}

