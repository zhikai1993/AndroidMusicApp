package com.android.flashbackmusic;

/**
 * This reads from database all the user-specific information about a specific
 */

public interface SongInfoIO {

    /**
     * Create database connection, or equivalent
     */
    void setup();

    /*
     * TODO: Possibly add seperate methods to get each song field
     */

    void populateSongInfo(Song s);

    void storeSongInfo(Song s);

    /**
     * Close database connection, or equivalent
     */
    void teardown();
}
