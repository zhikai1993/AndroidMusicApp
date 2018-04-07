package com.android.flashbackmusic;
import java.util.*;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;


/**
 * Stores information about a single album
 */

public class Album {

    String title;
    String artist;
    ArrayList<Song> songs;

    public Album(String title){
        this.title = title;
        songs = new ArrayList<>();
    }

    public Album(String title, ArrayList<Song> songs){
        this.title = title;
        this.songs = songs;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Song> getSongs() {
        return this.songs;
    }

    public void addSong(Song song) {
        this.artist = song.getArtist();
        songs.add(song);
    }

    public void play(final Player p) {

        final Queue<Song> songQueue = new ArrayDeque<Song>(songs);

        if (!p.isPlaying() && !(songQueue.isEmpty())) {
            p.play(songQueue.poll());
        }

        p.addSongCompletionListener(new SongCompletionListener() {
            @Override
            public void onSongCompletion() {
                if (!(songQueue.isEmpty())) {
                    Log.v("Currently playing " , songQueue.peek().getTitle());
                    p.play(songQueue.poll());
                }
            }
        });
    }
}
