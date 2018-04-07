package com.android.flashbackmusic;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class FlashbackActivity extends AppCompatActivity {
    private Player player;
    private SimpleSongImporter songImporter;
    private Application app;

    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashback_mode);

        app = this.getApplication();
//        songImporter = new SimpleSongImporter(app);
//        songImporter.read();

        Bundle bundle = new Bundle();
        songs = (List<Song>) bundle.getSerializable("songs");

        player = (Player) bundle.getSerializable("player");

        //player = new Player(app);

        CurrentSongBlock csb = findViewById(R.id.current_song_block_main);
        csb.setPlayPause(player);

        SwitchActivity swc = findViewById(R.id.switch_between_main);

        loadFlashback();

    }

    private void loadFlashback() {
        final ArrayList<Song> songList = songImporter.getSongList();
        setContentView(R.layout.activity_main);
        final LinearLayout layout = findViewById(R.id.main_layout);

        LocationInterface locationAdapter = new LocationMock();
        locationAdapter.establishLocationPermission(this, this);

        FlashbackOrderGenerator fog = new FlashbackOrderGenerator(new CurrentParameters(locationAdapter), songList);
        this.songs = fog.getSongList();

        fog.play(player);
        FlashbackMode flashback = new FlashbackMode(app,  null);
        flashback.setText(songs.get(0));
        flashback.setHistory("You're listening from " + "San Diego" + " on a "
                + "Tuesday" + " " + "Morning");
        layout.addView(flashback);
        flashback.display(true);
    }

}
