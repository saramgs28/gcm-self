package com.andresrcb.gcmtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "ActivityLogin";

    Button login;

    static EditText name;
    static EditText phone;

    String token;
    //ProgressBar loadingSpinner;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.button_login);
        login.setOnClickListener(this);

        //loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);

        name=(EditText)findViewById(R.id.edit_name);
        phone=(EditText)findViewById(R.id.edit_phone);

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // creating an shared Preference file for the information to be stored
// first argument is the name of file and second is the mode, 0 is private mode
        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
// get editor to edit in file
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
//        Intent i=new Intent(this, MainActivity.class);
//        startActivity(i);
        if (checkPlayServices()) {
            if(name.getText().length()<=0){
                Toast.makeText(ActivityLogin.this, "Enter n ame", Toast.LENGTH_SHORT).show();
            }
            else if(phone.getText().length()<=0){
                Toast.makeText(ActivityLogin.this, "Enter phone", Toast.LENGTH_SHORT).show();
            }
            else {
                // as now we have information in string. Lets stored them with the help of editor
                editor.putString("Name", name.getText().toString());
                editor.putString("Phone",phone.getText().toString());
                editor.commit();}   // commit the values

                //after saving the value, open a new Activity
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
                token = RegistrationIntentService.getToken();

                login.setVisibility(View.VISIBLE);
                phone.setVisibility(View.VISIBLE);
                name.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "TOKEN:" + token, Toast.LENGTH_LONG).show();
        }
    }
    public static String getName()
    {
        return name.getText().toString();
    }
    public static String getPhone()
    {
        return phone.getText().toString();
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}

