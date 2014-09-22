package com.cardbook.android.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.cardbook.android.R;
import com.cardbook.android.common.Font;
import com.cardbook.android.models.Company;
import com.cardbook.android.models.Location;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by abdullah on 01.02.2014.
 */
public class MapActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Bundle bundle=getIntent().getExtras();

        Location loc=(Location)bundle.getSerializable(Location.LOCATION);
        Company company=(Company) bundle.getSerializable(Company.COMPANY);

        GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        LatLng sube = new LatLng(loc.getLatitude(),loc.getLongitude());

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sube, 13));
        Marker marker;
        marker=map.addMarker(new MarkerOptions()
                .title(loc.getLocationName())
                .snippet(loc.getAddressLine())
                .position(sube));

        marker.showInfoWindow();

        TextView name=(TextView) findViewById(R.id.mapNameHolder);

        Typeface font= Font.getFont(this, Font.ROBOTO_MEDIUM);

        name.setTypeface(font);
        name.setText(company.getCompanyName());
        name.bringToFront();

    }

}
