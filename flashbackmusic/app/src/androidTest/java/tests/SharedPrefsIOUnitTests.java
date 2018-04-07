package tests;

import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import com.android.flashbackmusic.Album;
import com.android.flashbackmusic.MainActivity;
import com.android.flashbackmusic.SharedPrefsIO;
import com.android.flashbackmusic.Song;
import com.android.flashbackmusic.Time;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by joswei on 2/18/18.
 */

public class SharedPrefsIOUnitTests {

    private SharedPreferences prefs;
    private SharedPrefsIO prefsIO;
    private Song song;
    private Set<String> set;
    private LatLng test_loc;
    private ArrayList<LatLng> locs;
    private Date testDate;
    private Time testTime;
    private Set<String> timesOfDay;
    private Set<String> daysOfWeek;
    private Album album;

    @Rule
    public ActivityTestRule<MainActivity> mainActivity = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void setup() {
        prefs = mainActivity.getActivity().getSharedPreferences("test", mainActivity.getActivity().MODE_PRIVATE);
        prefsIO = new SharedPrefsIO(prefs);

        album = new Album("Album_1");
        song = new Song("Title_1", "Artist_1", album.getTitle(), "url", "path");

        song.setDisliked(false);
        song.setFavorited(false);
        testDate = new Date(1000000);
        testTime = new Time(true, testDate);
        song.setLastPlayedTime(testTime);
        locs = song.getLocations();
        test_loc = new LatLng(10, 45);
        locs.add(test_loc);
        song.setLocations(locs);
        set = new HashSet<>();
        set.add(test_loc.toString());
        song.setLastLocation(test_loc);
        timesOfDay = song.getTimesOfDay();
        timesOfDay.add("MORNING");
        timesOfDay.add("AFTERNOON");
        daysOfWeek = song.getDaysOfWeek();
        daysOfWeek.add("MONDAY");

        SharedPreferences.Editor editor = prefs.edit();
        String keyPrefix = song.getTitle() + song.getArtist();
        editor.putBoolean(keyPrefix + "disliked", song.isDisliked());
        editor.putBoolean(keyPrefix + "favorited", song.isFavorited());
        editor.putStringSet(keyPrefix + "locs", set);
        editor.putStringSet(keyPrefix + "daysofweek", daysOfWeek);
        editor.putStringSet(keyPrefix + "timesofday", timesOfDay);
        editor.putString(keyPrefix + "lastloc", test_loc.toString());

    }
//public Song(int id, String title, String artist, Album album, String album_art, String track_number, String genre, String year) {

    @Test
    public void testpopulateSongInfo() {
        Song s = new Song("Title_1", "Artist_1", album.getTitle(), "url", "path");

        prefsIO.populateSongInfo(s);

        assertTrue(s.isDisliked());
        assertFalse(s.isFavorited());
        assertEquals(s.getLocations(), locs);
        assertEquals(s.getTimesOfDay(), timesOfDay);
        assertEquals(s.getDaysOfWeek(), daysOfWeek);
        assertEquals(s.getLastLocation(), test_loc);
    }

    @Test
    public void teststoreSongInfo() {
        prefsIO.storeSongInfo(song);

        String keyPrefix = song.getTitle() + song.getArtist();

        assertTrue(prefs.getBoolean(keyPrefix + "disliked", song.isDisliked()));
        assertFalse(prefs.getBoolean(keyPrefix + "favorited", song.isFavorited()));
        assertEquals(prefs.getStringSet(keyPrefix + "locs", null), set);
        assertEquals(prefs.getStringSet(keyPrefix + "timesofday", null), timesOfDay);
        assertEquals(prefs.getStringSet(keyPrefix + "daysofweek", null), daysOfWeek);
        assertEquals(prefs.getString(keyPrefix + "lastloc", ""), song.getLastLocation().toString());
    }
    @Before
    public void teardown() {

    }
}
