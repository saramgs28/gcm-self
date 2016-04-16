package com.andresrcb.gcmtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private ChatAdapter chatAdapter;
    private ListView listView;
    private EditText chatText;

    private Button buttonSendAudio;
    private Button buttonSendVideo;
    private Button buttonSendPicture;

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
        chatAdapter = new ChatAdapter(this, R.layout.fragment_chat_singlemessage);
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
    private boolean sendChatMessage(){
        return true;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_audio:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
            case R.id.button_picture:

                break;
            case R.id.button_video:

                break;
        }
        //sendChatMessage();
    }
}
