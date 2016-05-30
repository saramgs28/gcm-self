package com.andresrcb.gcmtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityDisplay extends AppCompatActivity {

    public String filetype;
    public TextView text_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_display);

        text_display=(TextView)findViewById(R.id.text_display);

        filetype= getIntent().getExtras().getString("filetype");

        if(filetype=="audio")
        {
            text_display.setText("AUDIO");
        }else if(filetype=="picture")
        {
            text_display.setText("PICTURE");
        }else{
            text_display.setText("VIDEO");
        }
    }
}
