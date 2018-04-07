package com.android.flashbackmusic;

import android.app.Application;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Plays a specific song, and is responsible for displaying metadata as well
 */
@SuppressWarnings("serial") //With this annotation we are going to hide compiler warnings
public class Player implements Serializable {

    private MediaPlayer mediaPlayer;
    private Application app;
    private List<SongCompletionListener> songCompletionListenerList = new ArrayList<SongCompletionListener>();
    private Song song;
    private Song lastSong;
    private boolean isReset;

    public Player(Application app){
        this.app = app;
        loadMedia();
    }

    public void play(Song s){
        this.song = s;
        mediaPlayer.reset();
        loadMedia();
        isReset = false;
        Log.v("LOOK", s.getTitle() + " should be played right now " + s.getPath());

        try {
            //AssetFileDescriptor afd = app.getResources().openRawResourceFd(s.getPath());
            mediaPlayer.setDataSource(s.getPath());
            mediaPlayer.prepareAsync();
        } catch(Exception e) {
            Log.v("LOOK", e.toString());
        }
    }

    public void togglePausePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }
    }

    public void reset() {
        mediaPlayer.reset();
        isReset = true;
    }

    private void loadMedia() {

        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    public void addSongCompletionListener(SongCompletionListener s){
        this.songCompletionListenerList.add(s);
    }

    public void songCompletionEvent(){
        lastSong = song;
        for (SongCompletionListener s : songCompletionListenerList){
            s.onSongCompletion();
        }
    }

    public boolean isPlaying(){
        return this.mediaPlayer.isPlaying();
    }

    public Song getSong() {
        return this.song;
    }

    public Song getLastSong() { return this.lastSong; }

    public Boolean isReset() {
        return isReset;
    }
}
