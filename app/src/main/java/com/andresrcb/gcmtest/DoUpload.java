package com.andresrcb.gcmtest;

import android.app.Activity;
import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.android.volley.NetworkResponse;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.IOUtils;
import com.google.appengine.repackaged.com.google.common.io.Files;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DoUpload extends Application{
    final String twoHyphens = "--";
    final String lineEnd = "\r\n";
    final String boundary = "apiclient-" + System.currentTimeMillis();
    final String mimeType = "multipart/form-data;boundary=" + boundary;
    private byte[] multipartBody;

    public void sendFile(String uploadUrl, File file){
        Log.d(GlobalClass.TAG, "File has arrived here");
        Log.d(GlobalClass.TAG, file.toString());
        byte[] fileData1 = new byte[(int) file.length()];
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileData1);
            fileInputStream.close();
        }catch (Exception e){
            Log.d("Error", e.getMessage());
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        try {
            // the first file
            buildPart(dos, fileData1, file.getName());
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // pass to multipart body
            multipartBody = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultipartRequest multipartRequest = new MultipartRequest(uploadUrl, null, mimeType, multipartBody, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try{
                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    JSONObject jsonObject = new JSONObject(json);
                    String finalUrl = jsonObject.getString("response");
                    ConversationActivity conversationActivity = new ConversationActivity();
                    conversationActivity.sendMessage(finalUrl, "", "", "");
                }catch(Exception e){
                    Log.d(GlobalClass.TAG, e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(GlobalClass.TAG, "File not uploaded");
            }
        });
        GlobalClass.getInstance().addToRequestQueue(multipartRequest);
    }
    private void buildPart(DataOutputStream dataOutputStream, byte[] fileData, String fileName) throws IOException {
        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                + fileName + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(fileData);
        int bytesAvailable = fileInputStream.available();

        int maxBufferSize = 1024 * 1024;
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        dataOutputStream.writeBytes(lineEnd);
    }

}
