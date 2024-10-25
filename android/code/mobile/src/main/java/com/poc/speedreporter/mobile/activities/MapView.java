package com.poc.speedreporter.mobile.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.poc.speedreporter.common.data.SpeedViolation;
import com.poc.speedreporter.mobile.R;

import java.util.ArrayList;

public class MapView extends AppCompatActivity implements OnMapReadyCallback{
    private String TAG = "MapView";
    private GoogleMap mMap;
    private ProgressBar progressBar;
    private ArrayList<SpeedViolation> mViolationArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Log.d("MNG", "onCreate: ");

        mViolationArrayList = this.getIntent().getParcelableArrayListExtra("violationList");
        Log.d(TAG,"mViolationArrayList : "+mViolationArrayList.size());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("MNG", "onMapReady: ");
        for (int i=0; i < mViolationArrayList.size(); i++) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(mViolationArrayList.get(i).getLatitude()),
                    Double.valueOf(mViolationArrayList.get(i).getLangitude())))
                    .title(""+mViolationArrayList.get(i).getSpeed_limit()
                                    +" mph, "+mViolationArrayList.get(i).getViolatedDate()));

        }
        progressBar.setVisibility(View.GONE);
        // Move the camera to the first location
        if (mViolationArrayList.size() > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(mViolationArrayList.get(0).getLatitude()),
                    Double.valueOf(mViolationArrayList.get(0).getLangitude())), 10));
        }
    }
}
