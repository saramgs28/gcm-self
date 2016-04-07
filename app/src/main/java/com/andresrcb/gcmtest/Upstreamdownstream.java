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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Upstreamdownstream extends AppCompatActivity implements OnClickListener {

    private Button button_send;
    private TextView mTextView;
    private EditText mEditText;

    private LoggingService.Logger mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upstreamdownstream);

        mTextView = (TextView) findViewById(R.id.upstream_text);
        mEditText =(EditText) findViewById(R.id.edit_id_text);

        button_send = (Button) findViewById(R.id.upstream_send_button);
        button_send.setOnClickListener(this);
        findViewById(R.id.upstream_send_button).setOnClickListener(this);

        mLogger = new LoggingService.Logger(this);
    }
    @Override
    public void onClick(View v) {

        doGcmSendUpstreamMessage();
    }

    private void doGcmSendUpstreamMessage() {
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        final String senderId = "dSZ5uBCmrPs:APA91bHnHEaehgOkjGCG9rul_080G648CwMRLLslKKhj70CAoIlZawvR5VJDjPRfLmgScWtXhqXd9ZEyoBjyd41ThMZiUy859qS8jv1oqCX0fPUL7OEAnREVE5iJ6HHFerkm9p0aAyBB";
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
                    Log.d("MyGcmListenerService", senderId+"mensaje: "+message);
                    gcm.send(senderId + "@gcm.googleapis.com", id, data);
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
