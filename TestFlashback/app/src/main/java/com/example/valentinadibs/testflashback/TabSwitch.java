package com.example.valentinadibs.testflashback;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabSwitch extends LinearLayout{
    Button songs;
    Button album;
    Button flashback;
    Context context;

    public TabSwitch(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.tab_switch, this);
    }

    public Button getAlbum() {
        return this.findViewById(R.id.Album);
    }
    public Button getSongs() {
        return this.findViewById(R.id.Songs);
    }
    public Button getFlashback() {
        return this.findViewById(R.id.Flashback);
    }

}
