package com.android.flashbackmusic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mac on 18/02/2018.
 */

public class AlbumBlock extends LinearLayout {
    private String albumName;
    private String artistName;

    ImageButton playPause;
    Player player;

    private TextView albumView;
    private TextView artistView;
    private Context context;

    private Album album;

    public AlbumBlock(Context context) {
        super(context);
    }

    public AlbumBlock(Context context, Album album) {
        super(context);
        this.albumName = album.getTitle();
        this.artistName = album.getArtist();
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.album_block, this);
        this.setVisibility(View.VISIBLE);
        //this.bringToFront();
    }

    /*public void setText() {
        albumView = this.findViewById(R.id.album_title);
        albumView.setText(albumName);

        artistView = this.findViewById(R.id.artist_title);
        artistView.setText(artistName);
        Log.v("set text to","albumName");
    }

    public void setPlayPause(final Player player) {
        this.player = player;

        playPause = this.findViewById(R.id.album_play);
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause();
                player.togglePausePlay();
            }
        });
    }

    public void togglePlayPause() {
        if (player.isPlaying()) {
            playPause.setImageResource(android.R.drawable.ic_media_play);
        } else {
            playPause.setImageResource(android.R.drawable.ic_media_pause);
        }
    }*/

    public String getAlbumTitle(){
        return this.albumName;
    }
}
