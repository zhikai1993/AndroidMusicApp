package com.android.flashbackmusic;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SongBlock extends LinearLayout implements DatePickerDialog.OnDateSetListener {

    private String titleText;
    private String artist;
    private String album;
    private String path;

    private TextView title;
    private TextView artistAlbum;
    private ImageButton favorite;
    private Button moreInfo;
    private EditText setTime;
    private String strTime;
    private Song song;
    private Context context;
    private Time time;
    private InputMethodManager mgr;

    public SongBlock(Context context) {
        super(context);
        this.context = context;
        initializeViews(context);
    }

    public SongBlock(Context context, Song song) {
        super(context);

        this.titleText = song.getTitle();
        this.artist = song.getArtist();
        this.album = song.getAlbum();
        this.path = song.getPath();
        this.song = song;

        initializeViews(context);
    }

    public String getTitle(){
        return this.titleText;
    }

    public String getartistAlbum(){
        return artist+"|"+album;
    }

    public Song getSong() {
        return song;
    }

    public void loadFavor(Song song, SharedPrefsIO sp) {
        final Song song_f = song;
        final SharedPrefsIO sp_f = sp;
        favorite = this.findViewById(R.id.song_favorite);
        if (song_f.isFavorited()) {
            favorite.setImageResource(android.R.drawable.checkbox_on_background);
        } else if (song_f.isDisliked()) {
            favorite.setImageResource(android.R.drawable.ic_delete);
        } else{
            favorite.setImageResource(android.R.drawable.ic_input_add);
        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!song_f.isFavorited() && !song_f.isDisliked()) {
                    favorite.setImageResource(android.R.drawable.checkbox_on_background);
                    //status = 1;
                    song_f.setFavorited(true);

                } else if (song_f.isFavorited()) {
                    favorite.setImageResource(android.R.drawable.ic_delete);
                    //status = -1;
                    song_f.setFavorited(false);
                    song_f.setDisliked(true);
                } else {
                    favorite.setImageResource(android.R.drawable.ic_input_add);
                    //status = 0;
                    song_f.setDisliked(false);
                }

                sp_f.storeSongInfo(song_f);
            }
        });
    }

    public void setTime() {
        setTime = (EditText) this.findViewById(R.id.setTime);
        setTime.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    strTime = setTime.getText().toString();
                    Log.v("time", strTime);
                    Date d = new Date();
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-MM-dd-yyyy");
                        d = sdf.parse(strTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    time = new Time(true, d);
                    Log.v("new time", String.valueOf(time.getDate()));
                    song.setLastPlayedTime(time);
                }
                return false;
            }
        });
    }

    public void toggleInfo(Song song, SharedPrefsIO sp) {
        final Song song_f = song;
        final SharedPrefsIO sp_f = sp;
        moreInfo = this.findViewById(R.id.more_info);

        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (true /* user clicked to see more info */) {

                } else {

                }

                sp_f.storeSongInfo(song_f);
            }
        });
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.song_block, this);
    }

    //set the title and artistAlbum
    public void setText() {
        title = this.findViewById(R.id.song_title);
        title.setText(titleText);

        artistAlbum = this.findViewById(R.id.song_artist_album);
        artistAlbum.setText(artist + " | " + album);
    }
    public ImageButton getFavorite() {
        return this.favorite;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // store time somewhere
        return;
    }
}