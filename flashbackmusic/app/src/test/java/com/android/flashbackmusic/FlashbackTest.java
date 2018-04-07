package com.android.flashbackmusic;

import android.content.Context;
import com.android.flashbackmusic.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by vrkumar on 2/18/18.
 */
public class FlashbackTest {

    CurrentParameters curr = new CurrentParameters();

    FlashbackOrderGenerator f = new FlashbackOrderGenerator(curr, null);

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
        s.setFavorited(true);
        s.setDaysOfWeek(new HashSet<String>());
        s.setTimesOfDay(new HashSet<String>());
        assert(f.getScore(s) == 1);
    }

    @Test
    public void testSorter(){
        Song s1 = new Song();
        s1.setDisliked(true);

        Song s2 = new Song();
        s2.setFavorited(true);
        s2.setDaysOfWeek(new HashSet<String>());
        s2.setTimesOfDay(new HashSet<String>());

        List<Song> songList = new ArrayList<Song>();
        songList.add(s1);
        songList.add(s2);

        f.setSongList(songList);

        songList = f.getSongList();

        assert(songList.get(0) == s2);
    }

}
