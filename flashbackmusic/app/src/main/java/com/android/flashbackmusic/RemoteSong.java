package com.android.flashbackmusic;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RemoteSong {

    private String title = "";
    private String artist = "";
    public String album = "";
    private String URL;

    private ArrayList<SongPlay> plays;
    private Song song;
    private String id;

    public RemoteSong(){}

    //Every song and remotesong has an ID - it is used to tie them to each other
    public RemoteSong(/*id,*/ String title, String artist, String album, String url, String id) {
        //this.id = id;
            if (title != null) this.title = title;
            if (artist != null) this.artist = artist;
            if (album != null)  this.album = album;
        this.URL = url;
        this.id = id;
        plays = new ArrayList<>();

        if (this.getPlays() == null){
            //Log.v("remote song plays", "is null");
        } else {
            //Log.v("remote song plays", "is not null");
        }
    }

    public String getId() { return id; }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getAlbum() {
        return album;
    }

    public String getURL() { return URL; }
    public void setURL(String url) { URL = url; }

    public Song getSong(){ return song; }
    public void setSong(Song s){ this.song = s;}

    public ArrayList<SongPlay> getPlays() { return plays; }
    public SongPlay getMostRecentPlay() { return plays == null || plays.size() == 0 ? null : plays.get(plays.size() - 1); }
    public void addPlay(SongPlay newPlay) {
        if (newPlay == null){
            Log.v("newplay", "is null");
        } else {
            Log.v("newplay", "is not null");
        }
        if (plays == null){
            Log.v("plays", "is null");
            plays = new ArrayList<>();
        } else {
            Log.v("plays", "is not null");
        }
        plays.add(newPlay);
    }

}


