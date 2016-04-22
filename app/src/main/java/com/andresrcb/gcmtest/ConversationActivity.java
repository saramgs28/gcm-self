package com.andresrcb.gcmtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private ChatAdapter chatAdapter;
    private ListView listView;
    private Button buttonSendAudio;
    private Button buttonSendVideo;
    private Button buttonSendPicture;
    private boolean side = false;

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
                chatAdapter.add(new ChatMessage(side, "PRUEBA SID"));
                side = !side;
            break;
            case R.id.button_picture:

                break;
            case R.id.button_video:

                break;
        }
        //sendChatMessage();
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
