package com.andresrcb.gcmtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Upstreamdownstream extends AppCompatActivity implements OnClickListener {

    private Button button_send;
    private TextView mTextView;
    private EditText mEditText;
    private EditText uEditText;
    private EditText pEditText;
    private Button button_register;
    private LoggingService.Logger mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upstreamdownstream);

        mTextView = (TextView) findViewById(R.id.upstream_text);
        mEditText =(EditText) findViewById(R.id.edit_id_text);
        uEditText =(EditText) findViewById(R.id.edit_name);
        pEditText =(EditText) findViewById(R.id.edit_phone);
        button_send = (Button) findViewById(R.id.upstream_send_button);
        button_register = (Button) findViewById(R.id.button_register);
        button_send.setOnClickListener(this);
        findViewById(R.id.upstream_send_button).setOnClickListener(this);
        button_register.setOnClickListener(this);
        findViewById(R.id.button_register).setOnClickListener(this);
        mLogger = new LoggingService.Logger(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_register:
                registerUser();
                break;
            case R.id.upstream_send_button:
                doGcmSendUpstreamMessage();
                break;
        }
    }
    private void registerUser(){
        if (uEditText.getText().toString().isEmpty() || pEditText.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter the username and password", Toast.LENGTH_LONG).show();
        else{
            try{
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken("590942745468",
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                try{
                    JSONObject reqObject = new JSONObject();
                    String username = uEditText.getText().toString();
                    String phone = pEditText.getText().toString();
                    reqObject.put("username", username);
                    reqObject.put("phone", phone);
                    reqObject.put("regId", token);

                } catch(JSONException je){

                }
            }catch(java.io.IOException e){

            }

        }



    }
    private void doGcmSendUpstreamMessage() {
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        final String senderId = "590942745468";
        final String message = mEditText.getText().toString();
        final Bundle data = new Bundle();
        final AtomicInteger msgId = new AtomicInteger();
        if (msgId.equals("")) {
            Toast.makeText(this,"message Id cannot be empty", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    data.putString("message", message);
                    String id = Integer.toString(msgId.incrementAndGet());
//                    Log.d("MyGcmListenerService", senderId+"mensaje: "+message);
                    gcm.send(senderId+"@gcm.googleapis.com", id, data);
                    mLogger.log(Log.INFO, "Successfully sent upstream message");
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    mLogger.log(Log.ERROR, "Error sending upstream message", e);
                    return "Error sending upstream message:" + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    mLogger.log(Log.ERROR, "Error sending upstream message");
                }
            }
        }.execute(null, null, null);
    }
}
