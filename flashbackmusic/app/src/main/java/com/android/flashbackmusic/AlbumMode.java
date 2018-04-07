package com.android.flashbackmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class AlbumMode extends LinearLayout {

    ImageButton playPause;
    ImageButton favoriteBtn;
    Player player;
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
        this.display(false);
    }

    public void display(boolean display) {
        if (display)  {
            Log.v("AM", "display is true");
            //this.setVisibility(View.VISIBLE);
            //this.bringToFront();
            display();
        } else {
            Log.v("AM", "display false");
            hide();
            //this.setVisibility(View.GONE);
        }

    }

    public void togglePlayPause() {
        if (player.isPlaying()) {
            playPause.setImageResource(android.R.drawable.ic_media_play);
        } else {
            playPause.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    public void reset() {
        this.removeAllViews();
        initializeViews(context);
    }

    public void hide() {
        this.setVisibility(LinearLayout.GONE);
    }

    public void display() {
        this.setVisibility(LinearLayout.VISIBLE);
    }

}
