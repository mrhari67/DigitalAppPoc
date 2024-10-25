package com.poc.speedreporter;

import android.Manifest;
import android.car.Car;
import android.car.VehiclePropertyIds;
import android.car.hardware.CarPropertyValue;
import android.car.hardware.property.CarPropertyManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedreporter.common.data.SpeedViolation;
import com.poc.speedreporter.common.models.APIViewModule;
import com.poc.speedreporter.common.utils.SkodaPocConstants;
import com.poc.speedreporter.databinding.ActivitySkodaPocBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SkodaPocActivity extends AppCompatActivity {

    private CarPropertyManager mCarPropertyManager;
    private static final String TAG = "SkodaPocActivity";
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    private ActivitySkodaPocBinding mBinding;
    private APIViewModule mAPIViewModule;
    private Location mGPSCurrentLocation,mNetworkCurrentLocation;
    private SpeedViolation mSpeedViolation;
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

    private float mOldSpeed = 0f;
    private boolean isAPINeedToCall = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySkodaPocBinding.inflate(getLayoutInflater());
        mAPIViewModule = new ViewModelProvider(this).get(APIViewModule.class);
        // Request car speed and location permissions
        List<String> reqPerm = checkPermissions();

        if (reqPerm.isEmpty()) {
            init();
        } else {
            requestPermissions(reqPerm);
        }

        //mAPIViewModule.setSpeedThreshold(new Speed(SkodaPocConstants.VEHICLE_NUMBER,50));
        //mAPIViewModule.getSpeedViolationList(mAPIViewModule.getVehicleNumberPref());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            //all permissions have been granted
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                init();
            }
        }
    }

    private void init() {
        mCarPropertyManager = (CarPropertyManager) Car.createCar(this).getCarManager(Car.PROPERTY_SERVICE);
        registerCarPropertyManagerCallback();
        setContentView(mBinding.getRoot());
        //Call getSpeedThreshold() save it to preference
        mAPIViewModule.getSpeedThreshold();
    }

    @NonNull
    private List<String> checkPermissions() {
        List<String> permissions = new ArrayList<String>();
        if (checkSelfPermission(Car.PERMISSION_SPEED) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Car.PERMISSION_SPEED);
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        return permissions;
    }

    private void requestPermissions(@NonNull List<String> permissions) {
        requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_ASK_PERMISSIONS);
    }

    private void registerCarPropertyManagerCallback() {

        mCarPropertyManager.registerCallback(new CarPropertyManager.CarPropertyEventCallback() {
            @Override
            public void onChangeEvent(CarPropertyValue carPropertyValue) {
                float speed = (float) carPropertyValue.getValue() * (float) 2.237;
                if(speed < 0)
                    speed = 0;
//                Log.d(TAG, "PERF_VEHICLE_SPEED: MNG onChangeEvent(" + speed + ") kph");
                mBinding.txtSpeedVal.setText(String.valueOf(Math.round(speed))+" mph");

                if (speed > mAPIViewModule.getSpeedThresholdPref() && speed != mOldSpeed) {
                    //Call reportSpeedViolation() api
                    isAPINeedToCall = true;
                    mSpeedViolation = new SpeedViolation(SkodaPocConstants.VEHICLE_NUMBER,(int) speed,
                            mSimpleDateFormat.format(new Date()),"","",0f);
                    if (ActivityCompat.checkSelfPermission(SkodaPocActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(SkodaPocActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        getCurrentLocation();
                    } else {
                        // call api without lat log.
                        Log.d(TAG,"You don't have Location permission");
                    }
                }
                mOldSpeed = speed;
            }

            @Override
            public void onErrorEvent(int propId, int zone) {
                Log.d(TAG, "PERF_VEHICLE_SPEED: onErrorEvent(" + propId + ", " + zone + ")");
            }
        }, VehiclePropertyIds.PERF_VEHICLE_SPEED, CarPropertyManager.SENSOR_RATE_ONCHANGE);
    }

    @RequiresPermission (anyOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    private void getCurrentLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            boolean hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            Log.d(TAG,"hasGps:"+hasGps+ " hasNetwork:"+hasNetwork);
            if (hasGps) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,
                        0F, mGPSLocationListener);
            }
            if (hasNetwork) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
                        0F, mNetworkLocationListener);
            }
        }
    }

    private LocationListener mGPSLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            mGPSCurrentLocation = location;
//            Log.d(TAG,"onLocationChanged from GPS: LAT::"+location.getLatitude() +" LONG::"+ location.getLongitude());
            mSpeedViolation.setLatitude(String.valueOf(location.getLatitude()));
            mSpeedViolation.setLangitude(String.valueOf(location.getLongitude()));

            if (isAPINeedToCall) {
                isAPINeedToCall =  false;
                mAPIViewModule.reportSpeedViolation(mSpeedViolation);
            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    };

    private LocationListener mNetworkLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(@NonNull Location location) {
            mNetworkCurrentLocation = location;
//            Log.d(TAG,"onLocationChanged from Network: LAT::"+location.getLatitude() +" LONG::"+ location.getLongitude());
            mSpeedViolation.setLatitude(String.valueOf(location.getLatitude()));
            mSpeedViolation.setLangitude(String.valueOf(location.getLongitude()));

            if (isAPINeedToCall) {
                isAPINeedToCall =  false;
                mAPIViewModule.reportSpeedViolation(mSpeedViolation);
            }
        }

        @Override
        public void onLocationChanged(@NonNull List<Location> locations) {
            LocationListener.super.onLocationChanged(locations);
        }

        @Override
        public void onFlushComplete(int requestCode) {
            LocationListener.super.onFlushComplete(requestCode);
        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {
            LocationListener.super.onProviderEnabled(provider);
        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {
            LocationListener.super.onProviderDisabled(provider);
        }
    };
}