package com.android.flashbackmusic;

import android.app.Application;
import android.app.DownloadManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Reads song data and metadata from simple local files
 */

public class SimpleSongImporter implements SongImporter {

    private ArrayList<Song> songs;
    private ArrayList<Album> albums;
    private ArrayList<RemoteSong> remoteSongs;
    private Application app;
    private File downloadDir;
    private MediaMetadataRetriever mmr;

    public SimpleSongImporter(Application app){
        this.app = app;
        songs = new ArrayList<>();
        albums = new ArrayList<>();
        remoteSongs = new ArrayList<>();
        downloadDir = app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        mmr = new MediaMetadataRetriever();
    }

    /**
     * Populate list of songs and albums
     * Try using MediaMetadataRetriever class
     */
    public void  read(){
        if (downloadDir.exists()) Log.v("LOOK", "DIR EXISTS: " + downloadDir.toString() );
        File[] listAllFiles = downloadDir.listFiles();
        if(listAllFiles == null) Log.v("LOOK", "DIR IS EMPTY");

        handleAlbum(listAllFiles);
        listAllFiles = downloadDir.listFiles();

        if (listAllFiles != null && listAllFiles.length > 0) {
            for (File currentFile : listAllFiles) {
                importSong(currentFile);
            }
        }
    }

    private File[] handleAlbum(File[] listAllFiles) {

        for (File file : listAllFiles) {
            long n = 0;
            if (isAlbum(file)) {
                try {
                    ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file.toString()));
                    String filePath = "";
                    ZipEntry entry = zipIn.getNextEntry();
                    while (entry != null) {
                        filePath = downloadDir + File.separator + entry.getName();
                        if (!entry.isDirectory()) {
                            extractFile(zipIn, filePath);
                            Log.v("LOOK", "EXTRACTED: " + entry.getName() + " from " + file.toString());
                        }
                        zipIn.closeEntry();
                        entry = zipIn.getNextEntry();
                    }
                    zipIn.close();
                    deleteFile(file);
                } catch (Exception e) {
                    Log.v("LOOK", "ERROR IN CHECKING IF FILE IS A ZIP: " + e);
                }
            }
        }
        return listAllFiles;
    }

    public boolean isAlbum(File file) {
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            long n = raf.readInt();
            raf.close();

            if (n == 0x504B0304) return true;
            else return false;

        } catch (Exception e) {
            Log.v("LOOK", "ERROR IN CHECKING IF FILE IS A ZIP: " + e);
        }
        return false;
    }

    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[8];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }


    public boolean importSong(File currentFile) {
        String title;
        String artist;
        String albumName;
        Album album;
        try {
            Log.v("LOOK", "attempting to import: " + currentFile.getAbsolutePath());
            mmr.setDataSource(currentFile.toString());

            title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

            for (Song song : getSongList()) {
                if (song.getTitle().equals(title)) {
                    return false;
                }
            }
            artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            albumName = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);

            Log.v("LOOK", currentFile.getAbsolutePath());
            Log.v("LOOK", currentFile.getName());
            Log.v("LOOK", "title: " + title + " artist: " + artist + " albumName: " + albumName);

            Log.v("LOOK", "ADDED");
            album = addAlbum(albumName);
            addSong( title, artist, album, "", currentFile.toString());
        } catch (Exception e) {
            Log.v("LOOK", "COULDN'T IMPORT SONG: " + currentFile.toString()  + " " + e);
            return false;
        }
        return true;
    }

    private void addSong(String title, String artist, Album album, String url, String path) {
        Song newSong = new Song(title, artist, album.getTitle(), url, path);
        newSong.setLocal(true);

        album.addSong(newSong);
        remoteSongs.add(newSong.getRemoteSong());
        songs.add(newSong);
    }

    private Album addAlbum(String albumName) {
        for ( Album al : albums ) {
            if (al.getTitle().equals(albumName) ) {
                return al;
            }
        }

        Album album = new Album(albumName);
        albums.add(album);

        return album;
    }

    public ArrayList<Song> getSongList() {
        return songs;
    }

    public ArrayList<Album> getAlbumList() {
        return albums;
    }

    private void deleteFile(File currentFile) {
        currentFile.delete();
        try {
            if(currentFile.exists()){
                currentFile.getCanonicalFile().delete();
                if(currentFile.exists()){
                    app.getApplicationContext().deleteFile(currentFile.getName());
                }
            }
            Log.v("LOOK", "FILE ALREADY EXISTS - DELETED: " + currentFile.toString() + " " + currentFile.exists() );

        } catch (Exception e) {
            Log.v("LOOK", "FILE ALREADY EXISTS BUT NOT DELETED: " + currentFile );
        }
    }

    public ArrayList<RemoteSong> getRemoteSongList() {
        return remoteSongs;
    }
}