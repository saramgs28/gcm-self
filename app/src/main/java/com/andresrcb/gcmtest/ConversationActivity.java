package com.andresrcb.gcmtest;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.apphosting.client.serviceapp.RpcHandler;

import java.io.File;
import java.io.IOException;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener{

    private ChatAdapter chatAdapter;
    private ListView listView;
    private Button buttonSendAudio;
    private Button buttonSendVideo;
    private Button buttonSendPicture;
    private boolean side = false;
    private String fileType;
    private Context context;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static View view;
    private Activity activity;
    private RelativeLayout mRoot;
    //IMAGE
    private static Intent i;
    final static int cons = 0;
    private static Bitmap bmp;
    private static ImageView img;

    //AUDIO
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int currentFormat = 0;
    private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,             MediaRecorder.OutputFormat.THREE_GPP };
    private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP };

    //VIDEO
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private String selectedImagePath = "";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        activity = this;
        view = (View) findViewById(R.id.form);
        context = getApplicationContext();
        buttonSendAudio = (Button) findViewById(R.id.button_audio);
        buttonSendVideo = (Button) findViewById(R.id.button_video);
        buttonSendPicture = (Button) findViewById(R.id.button_picture);

        //buttonSendAudio.setOnClickListener(this);
        buttonSendVideo.setOnClickListener(this);
        buttonSendPicture.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list_messages);
        chatAdapter = new ChatAdapter(this, R.layout.fragment_chat_singlemessage_left);
        listView.setAdapter(chatAdapter);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatAdapter);

        //AUDIO
        buttonSendAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startRecording();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopRecording();
                        break;
                }
                return false;
            }
        });

        //to scroll the list view to bottom on data change. NO HAGO SCROLL
        /*chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatAdapter.getCount() - 1);
            }
        });*/
    }
    private String getFilename(){
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath,AUDIO_RECORDER_FOLDER);

        if(!file.exists()){
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }
    private void startRecording() {
        if(checkPermission("audio")){
            if( recorder == null ) {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(output_formats[currentFormat]);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(getFilename());
            }
            try {
                recorder.prepare();
                recorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            requestPermission("audio");
        }
    }
    private void stopRecording(){
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();
            Toast.makeText(this, "SE HA GRABADO", Toast.LENGTH_LONG).show();
            chatAdapter.add(new ChatMessage(side, "AUDIO", fileType));
            side = !side;
            recorder = null;
        }
    }
    /*private boolean sendChatMessage(){
        return true;
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.button_audio:
//                fileType="audio";
//                if(checkPermission()){
//
//                }else{
//                    requestPermission("audio");
//                }
//                chatAdapter.add(new ChatMessage(side, "AUDIO", fileType));
//                side = !side;
//            break;
            case R.id.button_picture:
                fileType="picture";
                if(checkPermission("picture")){
                    takePicture();
                    chatAdapter.add(new ChatMessage(side, "PICTURE", fileType));
                }else{
                    requestPermission("picture");
                }

                side = !side;
                break;
            case R.id.button_video:
                fileType="video";
//                int hasVideoPermission = checkSelfPermission(android.Manifest.permission.)
                if(checkPermission("video")){
                    recordVideo();
                    chatAdapter.add(new ChatMessage(side, "VIDEO", fileType));
                }else{
                    requestPermission("video");
                }
                side = !side;
                break;
        }
        //sendChatMessage(); //SEND THE PICTURE OR VIDEO
    }

    //IMAGE
    public void takePicture()
    {
        //DEBO RETURN PICTURE
        i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Open camera
        //i= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i, cons);//para recibir datos de esta actividad
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //IMAGE
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                Bundle ext = data.getExtras();
                bmp =(Bitmap)ext.get("data"); //Keep the image information in a bitmap
                img.setImageBitmap(bmp);
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
        //VIDEO
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Video saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }
    //VIDEO
    private boolean checkPermission(String type){
        String permission = "";
        if(type == "video"){
            permission = Manifest.permission.CAMERA;
        }else if(type == "picture"){
            permission = Manifest.permission.CAMERA;
        }else if(type == "audio"){
            permission = Manifest.permission.RECORD_AUDIO;
        }
        int result = ContextCompat.checkSelfPermission(this, permission);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }
    private void requestPermission(String type){
        String permission = "";
        String requestText = "";
        mRoot = (RelativeLayout) findViewById(R.id.conversation_layout);
        if(type.equals("video")){
            permission = Manifest.permission.CAMERA;
            requestText = "Need Camera Permission";
        }else if(type.equals("picture")){
            permission = Manifest.permission.CAMERA;
            requestText = "Need Camera Permission";
        }else if(type.equals("audio")){
            permission = Manifest.permission.RECORD_AUDIO;
            requestText = "Need Audio Permission";
        }

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
            Snackbar.make(mRoot, requestText, Snackbar.LENGTH_INDEFINITE).setAction("Ok",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
                        }
                    });
        }else {
            Snackbar.make(mRoot, "Permission is not available. Requesting camera permission.", Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(view,"Permission Granted, Now you can access location data.",Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view,"Permission Denied, You cannot access location data.",Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }
    public void recordVideo()
    {

        //specifies that the video should be stored on the SD card in a file named myvideo.mp4
        File mediaFile =
                new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/myvideo.mp4");

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
        Uri videoUri = Uri.fromFile(mediaFile);

//        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
//        startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
    }

   /* public void sendTextMessage(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String toPhone = "";
        String textMessage = "";
        String fromPhone = "";
        final String URL = "https://momentchatv2.appspot.com/_ah/api/usermessaging/v1/messageUser";
        if (toPhone.isEmpty()|| fromPhone.isEmpty())
            Toast.makeText(getApplicationContext(), "Please enter the username and password", Toast.LENGTH_LONG).show();
        else{
            try{
                JSONObject reqObject = new JSONObject();
                reqObject.put("toPhone", toPhone);
                reqObject.put("textMessage", textMessage);
                reqObject.put("fromPhone", fromPhone);
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, reqObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String x = response.getString("username");
                                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    getApplication().startActivity(intent);
//                                    startService(intent);
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
    }*/
}
