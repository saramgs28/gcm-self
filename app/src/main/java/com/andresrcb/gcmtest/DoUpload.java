package com.andresrcb.gcmtest;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class DoUpload extends Activity{
    public String getUploadUrl(){
        RequestQueue queue = Volley.newRequestQueue(this);
        final String URL = "http://momentchatupload.appspot.com/get_upload_url";
        final JSONObject uploadUrl = new JSONObject();
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            uploadUrl.put("response", response.get("response").toString());
                        }catch (JSONException e){
                            Log.d("Erorr", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        );
        queue.add(getRequest);
        try{
            return uploadUrl.get("response").toString();
        }catch (JSONException e){
            Log.d("Erorr", e.getMessage());
            return null;
        }
    }
    public void sendFile(String uploadUrl, File file){
        
    }
}
