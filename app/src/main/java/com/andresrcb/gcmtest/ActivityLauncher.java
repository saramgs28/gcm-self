package com.andresrcb.gcmtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class ActivityLauncher extends AppCompatActivity {

    SharedPreferences mSettings;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_activity_launcher);

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);
        String token= mSettings.getString("Token",null);

        if (token==null)
        {
            intent = new Intent(this, ActivityLogin.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }
        ActivityLauncher.this.finish();
        this.startActivity (intent);
        //this.finishActivity (0);
    }
}
