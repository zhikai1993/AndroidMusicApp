package com.android.flashbackmusic;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class VibeOrderGenerator {
    final static double MAX_DIST_METERS = 304.8;
    final static double NEARBY_SCORE = 1.1;
    final static double RECENTLY_SCORE = 1.0;
    final static double FRIEND_SCORE = 0.9;
    final static long WEEK_IN_MILLIS = 604800000;

    private CurrentParameters c;
    private List<Song> songList;

    public VibeOrderGenerator(CurrentParameters c, List<Song> songList){
        this.c = c;
        this.songList = songList;
    }


    public CurrentParameters getC() {
        return c;
    }

    public void setC(CurrentParameters c) {
        this.c = c;
    }

    public List<Song> getSongList() {
        Collections.sort(songList, songComparator);
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    //Note this is in order generator and not in song since different order generators
    //could return different scores for the same song

    public double getScore(Song s){
        if (s.isDisliked()) {
            return -1;
        } else {
            double score = 0;
            if (c.getLastPlayedTime() != null && s.getLastPlayedTime() != null){
                if ((c.getLastPlayedTime().getDate().getTime() - s.getLastPlayedTime().getDate().getTime()) > WEEK_IN_MILLIS) {
                    score += RECENTLY_SCORE;
                }
            }
            LatLng currLoc = c.getLocation();
            float[] results = new float[1];
            for (int i = 0; i < s.getPlays().size(); i++){
                SongPlay p = s.getPlays().get(i);
                LatLng l = new LatLng(p.latitude, p.longitude);
                Location.distanceBetween(currLoc.latitude, currLoc.longitude, l.latitude, l.longitude, results);
                if (results[0] <= MAX_DIST_METERS){
                    score += NEARBY_SCORE;
                    break;
                }
            }
            //Add check for friends here
            if (false){
                score += FRIEND_SCORE;
            }
            return score;
        }
    }

    Comparator<Song> songComparator = new Comparator<Song>(){

        @Override
        public int compare(final Song s1, final Song s2){
            double score1 = getScore(s1);
            double score2 = getScore(s2);

            if (score1 == score2 && s1.getLastPlayedTime() != null && s2.getLastPlayedTime() != null){
                return s1.getLastPlayedTime().getDate().after(s2.getLastPlayedTime().getDate()) ? 1 : -1;
            } else {
                return score2 > score1 ? 1 : -1;
            }
        }
    };

    public void play(final Player p) {
        final Queue<Song> songQueue = new PriorityQueue<Song>(songComparator);
        songQueue.addAll(songList);

        final LatLng oldLoc = c.getLocation();
        final String oldDay = c.getDayOfWeek();
        final String oldTime = c.getTimeOfDay();

        if (!p.isPlaying() && !(songQueue.isEmpty())) {
            p.play(songQueue.poll());
        }

        p.addSongCompletionListener(new SongCompletionListener() {
            @Override
            public void onSongCompletion() {
                if (!(oldLoc.equals(c.getLocation()) &&
                        oldDay.equals(c.getDayOfWeek()) && oldTime.equals(c.getTimeOfDay()))){
                    songQueue.clear();
                    play(p);
                }

                if (!(songQueue.isEmpty())) {
                    p.play(songQueue.poll());
                }
            }
        });
    }
}