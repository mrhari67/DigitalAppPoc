package com.poc.speedreporter.mobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.poc.speedreporter.common.models.APIViewModule;
import com.poc.speedreporter.mobile.R;

public class Login extends AppCompatActivity {
    private APIViewModule mAPIViewModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAPIViewModule = new ViewModelProvider(this).get(APIViewModule.class);
        Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editTextInput);
                String vin = editText.getText().toString();

                if ( !vin.isEmpty()) {
                    mAPIViewModule.setVehicleNumberPref(vin);

                    Intent intent = new Intent(Login.this, SpeedThreshold.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this,getString(R.string.speed_unit),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
