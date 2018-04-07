package com.android.flashbackmusic;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class FirebaseIO extends Observable{

    FirebaseDatabase database;
    DatabaseReference myRef;
    List<RemoteSong> remoteSongList;
    List<Song> songList;

    public FirebaseIO(final List<RemoteSong> remoteSongList, final List<Song> songList){
        this.remoteSongList = remoteSongList;
        this.songList = songList;
        for (RemoteSong r : remoteSongList){
            r.setSong(null);
            r.album = null;
        }
        setup();
    }

    public void setup() {
        database = database.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                remoteSongList.clear();
                GenericTypeIndicator<List<RemoteSong>> t = new GenericTypeIndicator<List<RemoteSong>>() {};
                //List<RemoteSong> newSongs = dataSnapshot.getValue(t);
                remoteSongList = dataSnapshot.getValue(t);
                //remoteSongList.addAll(newSongs);
                for (RemoteSong r : remoteSongList){
                    if (r.getPlays() == null){
                        Log.v(r.getTitle(), "null");
                    }
                    r.setSong(null);
                    for (Song s : songList){
                        if (r.getId().equals(s.getId())){
                            r.setSong(s);
                            s.setRemoteSong(r);
                        }
                    }
                }
                notifyObservers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Database:", "Database unexpectedly closed");
            }
        });
    }

    public void update(){
        Log.v("update remote", "beepbeepbeep");
        for (RemoteSong r : remoteSongList){
            r.setSong(null);
            r.album = null;
        }
        myRef.setValue(remoteSongList);
    }
}
