package com.andresrcb.gcmtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.FrameLayout;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";
    Button login;
    EditText name;
    EditText phone;
    FrameLayout loadingScreen;
    ProgressBar loadingSpinner;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.button_login);
        loadingScreen = (FrameLayout) findViewById(R.id.loading_screen);
        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadingScreen.setVisibility(View.INVISIBLE);
        loadingSpinner.setVisibility(View.INVISIBLE);
        name=(EditText)findViewById(R.id.edit_name);
        phone=(EditText)findViewById(R.id.edit_phone);
        login.setOnClickListener(this);
        checkPlayServices();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onClick(View v) {
        String token = generateToken();
        loadingSpinner.setVisibility(View.VISIBLE);
        if(token == null){
            Toast.makeText(getApplicationContext(), "Token is not found"+token, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), token, Toast.LENGTH_LONG).show();
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
//            registerUser(token);
        }
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
    private String generateToken(){
        try{
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("590942745468",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("tokenssssss: ", token);
            return token;
        }catch(java.io.IOException e){
            Log.i("Error generated", e.getMessage());
            return null;
        }
    }
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

