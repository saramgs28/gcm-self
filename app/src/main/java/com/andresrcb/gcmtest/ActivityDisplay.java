package com.andresrcb.gcmtest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityDisplay extends AppCompatActivity {

    public String filetype;
    public TextView text_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_display);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        filetype= getIntent().getExtras().getString("filetype");

        if(filetype=="audio")
        {
            //text_display.setText("AUDIO");
        }else if(filetype=="picture")
        {
            Bitmap bitmap = getIntent().getParcelableExtra("BitmapImage");
            imageView.setImageBitmap(bitmap);

        }else{
            //text_display.setText("VIDEO");
            Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("BitmapImage");
            imageView.setImageBitmap(bitmap);
        }
    }
}
