package com.andresrcb.gcmtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private ChatAdapter chatAdapter;
    private ListView listView;
    private Button buttonSendAudio;
    private Button buttonSendVideo;
    private Button buttonSendPicture;
    private boolean side = false;
    private String fileType;

    //IMAGE
    private static Intent i;
    final static int cons = 0;
    private static Bitmap bmp;
    private static ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        buttonSendAudio = (Button) findViewById(R.id.button_audio);
        buttonSendVideo = (Button) findViewById(R.id.button_video);
        buttonSendPicture = (Button) findViewById(R.id.button_picture);

        buttonSendAudio.setOnClickListener(this);
        buttonSendVideo.setOnClickListener(this);
        buttonSendPicture.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.list_messages);
        chatAdapter = new ChatAdapter(this, R.layout.fragment_chat_singlemessage_left);
        listView.setAdapter(chatAdapter);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatAdapter);

        //to scroll the list view to bottom on data change. NO HAGO SCROLL
        /*chatAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatAdapter.getCount() - 1);
            }
        });*/
    }
    /*private boolean sendChatMessage(){
        return true;
    }*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_audio:
                fileType="audio";
                sendAudio();
                chatAdapter.add(new ChatMessage(side, "AUDIO", fileType));
                side = !side;
            break;
            case R.id.button_picture:
                fileType="picture";
                sendPicture();
                chatAdapter.add(new ChatMessage(side, "PICTURE",fileType));
                side = !side;
                break;
            case R.id.button_video:
                fileType="video";
                chatAdapter.add(new ChatMessage(side, "VIDEO",fileType));
                side = !side;
                break;
        }
        //sendChatMessage();
    }
    public void sendPicture()
    {
        //DEBO RETURN PICTURE
        i= new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Open camera
        //i= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(i, cons);//para recibir datos de esta actividad
    }
    //UNA SOLA IMAGEN
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data); //No alterar lo que esta escrito. Si no lo pongo, no hace el contenido
        //ArrayList<Object> listImage = null;
        if (resultCode == Activity.RESULT_OK) {
            Bundle ext = data.getExtras();
            bmp =(Bitmap)ext.get("data"); //Keep the image information in a bitmap
            //img.setImageBitmap(bmp);
        }
    }

    public void sendAudio()
    {

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
