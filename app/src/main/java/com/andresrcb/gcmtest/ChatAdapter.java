package com.andresrcb.gcmtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private TextView chatText;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    Intent intent;
    private String filetype;
    private String fileUrl;
    ImageView image_received;
    ImageView image_download;
    Bitmap image;

    VideoView videoView;
    private ListView listView;

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
        image_received =(ImageView) convertView.findViewById(R.id.image_received);
        singleMessageContainer = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        chatText = (TextView) convertView.findViewById(R.id.singleMessage_left);
        chatText.setText(chatMessageObj.message);
        filetype=chatMessageObj.getFileType();
        fileUrl = chatMessageObj.getFileUrl();

        listView = (ListView) convertView.findViewById(R.id.list_messages);
        videoView = (VideoView) convertView.findViewById(R.id.video_received);

        chatText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //IMAGE
                new DownloadImageTask(image_download).execute(fileUrl);
                //listView.setVisibility(v.INVISIBLE);
                //image_received.setVisibility(v.VISIBLE);
                image_download.buildDrawingCache();
                Bitmap bmap = image_download.getDrawingCache();
                image_received.setImageBitmap(bmap);
                image_received.setVisibility(v.VISIBLE);
                //VIDEO
                //videoView.setVisibility(v.VISIBLE);
                //videoView.start();
                //AUDIO
                //Descargar audio y ponerlo en create en el segundo apartado
                //MediaPlayer mp = MediaPlayer.create(this, );
                //mp.start();

                //intent = new Intent(getContext(), ActivityDisplay.class);
                //imageView.buildDrawingCache();
                //Bitmap bitmap = imageView.getDrawingCache();

                //intent.putExtra("BitmapImage", bitmap);
                //intent.putExtra("filetype", filetype);
                //v.getContext().startActivity(intent);


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



