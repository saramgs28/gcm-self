package com.andresrcb.gcmtest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    String mCurrentPhotoPath;
    Uri file;

    //AUDIO
    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder;
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
            Toast.makeText(this, "NO EXISTE", Toast.LENGTH_LONG).show();
            file.mkdirs();
        }
        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }
    private void startRecording() {
        if(checkStoragePermission()){
            if(checkAudioPermission()){
                if( recorder == null ) {
                    recorder=new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(output_formats[currentFormat]);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile(getFilename());
                }
                try {
                    recorder.prepare();
                    recorder.start();
                    Toast.makeText(this, "GRABANDO", Toast.LENGTH_LONG).show();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                requestAudioPermission();
            }
        }else{
            requestExternalPermission();
        }
    }

    private void stopRecording(){
        Toast.makeText(this, "STOP RECORDING", Toast.LENGTH_LONG).show();
        if(null != recorder){
            recorder.stop();
            recorder.reset();
            recorder.release();
            Toast.makeText(this, "SE HA GRABADO", Toast.LENGTH_LONG).show();
            chatAdapter.add(new ChatMessage(side, "AUDIO", "audio"));
            side = !side;
        }
    }
    /*private boolean sendChatMessage(){
        return true;
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_picture:
                fileType="picture";
                if(checkCamPermission()){
                    takePicture();
                    chatAdapter.add(new ChatMessage(side, "PICTURE", fileType));
                }else {
                    requestCamPermission();
                }
                side = !side;
                break;
            case R.id.button_video:
                fileType="video";
                if(checkCamPermission()){
                    recordVideo();
                    chatAdapter.add(new ChatMessage(side, "VIDEO", fileType));
                }else{
                    requestCamPermission();
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
        file = Uri.fromFile(getOutputMediaFile());
        i.putExtra(MediaStore.EXTRA_OUTPUT, file);
        startActivityForResult(i, 100);
    }
    //VIDEO
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
    }
//    private void sendForUpload(String path){
    private void sendForUpload(){
//        Log.d("path", path);
        String uploadUrl = getUploadUrl();

        Log.d(GlobalClass.TAG, ""+uploadUrl);
        Toast.makeText(this, uploadUrl, Toast.LENGTH_LONG).show();
//        if(uploadUrl!=null){
//            File dir = Environment.getExternalStorageDirectory();
//            File imgFile = new File(dir, path);
//            doUpload.sendFile(uploadUrl, imgFile);
//        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(file!=null){
                    Log.d("File", file.toString());
//                    sendForUpload(file.getPath());
                    sendForUpload();
                }else{
                    Toast.makeText(this, "Error in file", Toast.LENGTH_LONG).show();
                }

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
    private boolean checkCamPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }
    private void requestCamPermission(){
        final String[] permissions = new String[]{Manifest.permission.CAMERA};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("MomentChat requires camera");
                builder.setTitle("Camera");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, permissions, 0);
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
        }
    }
    private boolean checkAudioPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }
    private void requestAudioPermission(){
        final String[] permissions = new String[]{Manifest.permission.RECORD_AUDIO};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("MomentChat requires audio");
                builder.setTitle("Audio");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, permissions, 0);
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
        }
    }
    private boolean checkStoragePermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }else{
            return true;
        }
    }
    private void requestExternalPermission(){
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("MomentChat requires external storage");
                builder.setTitle("Storage");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(activity, permissions, 0);
                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 0);
            }
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
    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MomentChat");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    public String getUploadUrl(){

        final String URL = "http://momentchatupload.appspot.com/get_upload_url";
        final JSONObject uploadUrl = new JSONObject();
        StringRequest getRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(GlobalClass.TAG, ""+response);
                try{
                    uploadUrl.put("response", response);
                }catch(JSONException e){
                    Log.d("Error", e.getMessage());
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });
        GlobalClass.getInstance().addToRequestQueue(getRequest);
        try{
            return uploadUrl.get("response").toString();
        }catch (JSONException e){
            Log.d("Erorr", e.getMessage());
            return null;
        }
    }
}
