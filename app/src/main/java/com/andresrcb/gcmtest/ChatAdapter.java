package com.andresrcb.gcmtest;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    private String filetype;
    private String fileUrl;
    ImageView imageView;
    Bitmap image;

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
        imageView =(ImageView) convertView.findViewById(R.id.image_view);
        singleMessageContainer = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        chatText = (TextView) convertView.findViewById(R.id.singleMessage_left);
        chatText.setText(chatMessageObj.message);
        filetype=chatMessageObj.getFileType();
        fileUrl = chatMessageObj.getFileUrl();


        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImageTask(imageView).execute(fileUrl);
//                Bundle savedInstanceState = new Bundle();
//                savedInstanceState.putString("fileUrl", fileUrl);
//                FragmentMedia fragmentMedia = new FragmentMedia(savedInstanceState);
//                fragmentMedia.setArguments();
                //fragmentMedia.newInstance();
            }
        });
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
    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(View.VISIBLE);
        }
    }
}



