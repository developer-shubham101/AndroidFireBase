package com.devpoint.firebasedb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.devpoint.firebasedb.PreferenceUtils;
import com.devpoint.firebasedb.R;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DURATION = 3000;

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Intent intent = getIntent();


//        if(bundle.containsKey("notification_type")){
//            raw_content
//        }


       /* Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());*/
        Handler handler = new Handler();
        handler.postDelayed(() -> {

            Intent homeActivity = new Intent(SplashActivity.this, StartupActivity.class);
//                Bundle notifyData = intent.getExtras();
//                if (notifyData != null && notifyData.containsKey("notification_type")) {
//                    homeActivity.putExtra(StartupActivity.INTENT_EXTRA_NOTI_TYPE, notifyData.getString("notification_type"));
//                    homeActivity.putExtra(StartupActivity.INTENT_EXTRA_RAW_DATA, notifyData.getString("raw_content"));
//                }

            startActivity(homeActivity);


            finish();
        }, SPLASH_DURATION);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, instanceIdResult -> {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken", newToken);
            PreferenceUtils preferenceUtils = new PreferenceUtils();
            preferenceUtils.saveData("newToken", newToken, this);

        });
    }
}
