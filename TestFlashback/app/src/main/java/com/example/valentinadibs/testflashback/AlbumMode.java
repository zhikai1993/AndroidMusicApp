package com.example.valentinadibs.testflashback;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class AlbumMode extends LinearLayout {

    ImageButton playPause;
    ImageButton favoriteBtn;
    //Player player;
    Context context;
    Button disableFlashback;

    public AlbumMode(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.album_mode, this);
        //this.display(false);
    }

    public void display(boolean display) {
        if (display)  {
            this.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.setVisibility(LinearLayout.INVISIBLE);
        }
    }
}
