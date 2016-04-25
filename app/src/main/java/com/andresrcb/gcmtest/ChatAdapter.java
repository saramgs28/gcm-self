package com.andresrcb.gcmtest;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    private String filetype;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_chat_singlemessage_left, parent, false);
        }
        singleMessageContainer = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        chatText = (TextView) convertView.findViewById(R.id.singleMessage_left);
        chatText.setText(chatMessageObj.message);
        filetype=chatMessageObj.getFileType();
        if(filetype=="audio")
        {
            chatText.setBackgroundResource(R.drawable.audio);

        }else if(filetype=="picture")
        {
            chatText.setBackgroundResource(R.drawable.picture);

        }else{
            chatText.setBackgroundResource(R.drawable.video);
        }
        singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return convertView;
    }

    /*public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }*/
}



