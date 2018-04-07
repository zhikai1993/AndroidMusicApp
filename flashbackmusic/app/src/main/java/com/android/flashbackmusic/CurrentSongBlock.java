package com.android.flashbackmusic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CurrentSongBlock extends LinearLayout {

    ImageButton playPause;
    ImageButton favoriteBtn;
    Player player;
    Context context;
    Song song;

    public CurrentSongBlock(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.current_song_block, this);
        this.display(false);
    }

    public void setPlayPause(final Player player) {
        this.player = player;

        playPause = this.findViewById(R.id.song_playPause);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                togglePlayPause();
                player.togglePausePlay();
            }
        });
    }

    public void loadFavor (Song song, SharedPrefsIO sp, SongBlock songBlock){
        final Song song_f = song;
        final SharedPrefsIO sp_f = sp;
        final SongBlock songBlock_f = songBlock;
        favoriteBtn = this.findViewById(R.id.current_song_favorite);
        if (song_f.isFavorited()) {
            favoriteBtn.setImageResource(android.R.drawable.checkbox_on_background);
        } else if (song_f.isDisliked()) {
            favoriteBtn.setImageResource(android.R.drawable.ic_delete);
        } else{
            favoriteBtn.setImageResource(android.R.drawable.ic_input_add);
        }
        favoriteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!song_f.isFavorited() && !song_f.isDisliked()) {
                    favoriteBtn.setImageResource(android.R.drawable.checkbox_on_background);
                    //status = 1;
                    song_f.setFavorited(true);
                } else if (song_f.isFavorited()) {
                    favoriteBtn.setImageResource(android.R.drawable.ic_delete);
                    //status = -1;
                    song_f.setFavorited(false);
                    song_f.setDisliked(true);
                    if (player.getSong() == song_f) {
                        playPause.setImageResource(android.R.drawable.presence_offline);
                        player.reset();
                    }
                } else {
                    favoriteBtn.setImageResource(android.R.drawable.ic_input_add);
                    //status = 0;
                    song_f.setDisliked(false);
                    playPause.setImageResource(android.R.drawable.ic_media_pause);
                    player.play(song_f);
                }
                songBlock_f.loadFavor(song_f, sp_f);
                sp_f.storeSongInfo(song_f);
            }
        });
        //        String keyPrefix = s.getTitle() + s.getArtist() + s.getAlbum();

        // waiting for SharedPrefsIO to update, currently no much getters from SharedPrefsIO

//        boolean favorited = sp.getBoolean(keyPrefix + "favorited", false);
//        boolean disLike = sp.getBoolean(keyPrefix + "disliked", false);
//        if (favorited) {
//            favoriteBtn.setImageResource(android.R.drawable.checkbox_on_background);
//        } else if (disLike) {
//            favoriteBtn.setImageResource(android.R.drawable.ic_delete);
//        } else {
//            favoriteBtn.setImageResource(android.R.drawable.ic_input_add);
//        }
    }

    public void togglePlayPause() {
        if (player.isPlaying()) {
            playPause.setImageResource(android.R.drawable.ic_media_play);
        } else if (!player.isReset()){
            playPause.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    public void display(boolean display) {
        if (display)  {
            this.setVisibility(LinearLayout.VISIBLE);
        } else {
            this.setVisibility(LinearLayout.INVISIBLE);
        }
    }

    public void setText(Song song) {
        this.song = song;
        TextView title = this.findViewById(R.id.current_song_title);
        title.setText(song.getTitle());

        TextView artist = this.findViewById(R.id.current_song_artist);
        artist.setText(song.getArtist());

        TextView album = this.findViewById(R.id.current_song_album);
        album.setText(song.getAlbum());
    }

    public void setHistory(String s) {
        TextView history = this.findViewById(R.id.song_history_description);
        history.setText(s);
    }

    public ImageButton getFavorite() {
        return this.findViewById(R.id.current_song_favorite);

    }
}
