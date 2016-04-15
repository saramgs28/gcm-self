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
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.FrameLayout;


public class ActivityLogin extends AppCompatActivity implements View.OnClickListener{

    Button login;
    EditText name;
    EditText phone;
    FrameLayout loadingScreen;
    ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=(Button)findViewById(R.id.button_login);
        login.setOnClickListener(this);
        loadingScreen = (FrameLayout) findViewById(R.id.loading_screen);
        loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loadingScreen.setVisibility(View.INVISIBLE);
        loadingSpinner.setVisibility(View.INVISIBLE);
        name=(EditText)findViewById(R.id.edit_name);
        phone=(EditText)findViewById(R.id.edit_phone);
    }

    @Override
    public void onClick(View v) {
//        Intent i = new Intent(this, Main2Activity.class );
//        startActivity(i);
//        registerUser();
//        String token = generateToken();
//        loadingSpinner.setVisibility(View.INVISIBLE);
//        if(token == null){
//            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
//        }else{
//            Toast.makeText(getApplicationContext(), "Token Received, Logging in", Toast.LENGTH_LONG).show();
//            registerUser(token);
//        }
        try{
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("590942745468",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("Reg intent service", "GCM Token: "+token);
//            return token;
        }catch(java.io.IOException e){
//            return null;
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
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
            } catch(JSONException je){

            }
        }
    }
    private String generateToken(){
        loadingSpinner.setVisibility(View.VISIBLE);
        try{
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken("590942745468",
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i("Reg intent service", "GCM Token: "+token);
            return token;
        }catch(java.io.IOException e){
            return null;
        }
    }
}

