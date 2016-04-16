package com.andresrcb.gcmtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONException;
import org.json.JSONObject;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "ActivityLogin";

    Button login;

    static EditText name;
    static EditText phone;

    String token;
    ProgressBar loadingSpinner;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.button_login);
        login.setOnClickListener(this);

        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);

        name=(EditText)findViewById(R.id.edit_name);
        phone=(EditText)findViewById(R.id.edit_phone);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View v) {
        if (checkPlayServices()) {
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
    private void registerUser(String token){
        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "https://momentchatv2.appspot.com/_ah/api/register/v1/registerDevice";
        if (name.getText().toString().isEmpty() || phone.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter the username and password", Toast.LENGTH_LONG).show();
        else{
            try{
                JSONObject reqObject = new JSONObject();
                String username = name.getText().toString();
                String phone_number = phone.getText().toString();
                reqObject.put("username", username);
                reqObject.put("phone", phone_number);
                reqObject.put("regId", token);
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, reqObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String x = response.getString("name");
                                } catch (JSONException e) {
                                    Log.d("Fail", "Fail");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error connecting to the server",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(req);
            } catch(JSONException e){

            }
        }
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

