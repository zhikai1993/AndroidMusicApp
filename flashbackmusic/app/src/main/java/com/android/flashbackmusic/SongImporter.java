package com.android.flashbackmusic;

import java.util.ArrayList;

/**
 * Interface to read the song data and metadata
 */

public interface SongImporter {

    void read();
    ArrayList<Song> getSongList();
    ArrayList<Album> getAlbumList();

}
