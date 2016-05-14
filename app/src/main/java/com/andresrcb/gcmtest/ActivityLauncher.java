package com.andresrcb.gcmtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ActivityLauncher extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_activity_launcher);


        if (logged_in_check_is_true)
        {
            Intent intent = new Intent(this, HomeScreenActivity.class);
            this.startActivity (intent);
            this.finishActivity (0);
        }

    }
}
