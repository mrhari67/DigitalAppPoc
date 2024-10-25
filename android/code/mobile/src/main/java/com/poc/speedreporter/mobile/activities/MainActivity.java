package com.poc.speedreporter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedreporter.common.models.APIViewModule;

public class MainActivity extends AppCompatActivity {
    private APIViewModule mAPIViewModule;
    private String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIViewModule = new ViewModelProvider(this).get(APIViewModule.class);
        String vin = mAPIViewModule.getVehicleNumberNotEmptyPref();
        Log.d(TAG,"MainActivity vin:"+vin);
        // check if VIN available
        if (vin.isEmpty()) {
            // Login
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, SpeedThreshold.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
