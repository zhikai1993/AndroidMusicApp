package com.android.flashbackmusic;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by vrkumar on 2/18/18.
 */
public class VibeTest {

    CurrentParameters curr = new CurrentParameters();

    VibeOrderGenerator f = new VibeOrderGenerator(curr, null);

    @Test
    public void testDisliked()
    {
        Song s = new Song();
        s.setDisliked(true);
        assert(f.getScore(s) == -1);
    }

    @Test
    public void testNormal()
    {
        Song s = new Song();
        Time t = new Time();
        t.setMockableDate(new Date(System.currentTimeMillis()));
        curr.setLastPlayedTime(t);

        //Note currentParameters is initialized to now, hence causing the score of 1
        assert(f.getScore(s) == 1);
    }

    @Test
    public void testOldSong()
    {
        Song s = new Song();
        Time t = new Time();
        t.setMockableDate(new Date(0));
        curr.setLastPlayedTime(t);

        //Note currentParameters is initialized to epoch, hence causing the score of 0
        assert(f.getScore(s) == 0);
    }

    @Test
    public void testCloseby()
    {
        Song s = new Song();
        Time t = new Time();
        t.setMockableDate(new Date(0));
        curr.setLastPlayedTime(t);
        s.addPlay(new SongPlay("", new LatLng(1,1), "", t.getDate()));
        curr.setLatLng(new LatLng(1.001, 1));

        //Note currentParameters is initialized to epoch, hence causing the score of 0
        assert(f.getScore(s) == 1.1);
    }

    @Test
    public void testSorter(){
        Song s1 = new Song();
        Song s2 = new Song();

        Time t = new Time();
        t.setMockableDate(new Date(System.currentTimeMillis()));
        s1.setLastPlayedTime(t);

        Time t2 = new Time();
        t2.setMockableDate(new Date(System.currentTimeMillis() - 100000));
        s2.setLastPlayedTime(t2);

        curr.setLastPlayedTime(t);

        List<Song> songList = new ArrayList<Song>();
        songList.add(s1);
        songList.add(s2);

        f.setSongList(songList);

        songList = f.getSongList();

        assert(songList.get(0) == s2);
    }

}
