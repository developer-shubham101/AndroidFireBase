package com.devpoint.firebasedb.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.devpoint.firebasedb.MyService;
import com.devpoint.firebasedb.R;


public class MainActivity extends AppCompatActivity {

    private Intent serviceIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void startService(View view) {
        // use this to start and trigger a service
        serviceIntent = new Intent(this, MyService.class);
        // potentially add data to the intent
        serviceIntent.putExtra("KEY1", "Value to be used by the service");
        startService(serviceIntent);
    }

    public void stopService(View view) {
        stopService(serviceIntent);
    }

    public void openMap(View view) {

    }
}