package com.android.flashbackmusic;

import android.app.Application;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

public class SimpleDownloader {

    private Application app;
    private DownloadManager downloadManager;
    private File downloadDir;
    private SimpleSongImporter songImporter;

    public SimpleDownloader(Application app, SimpleSongImporter songImporter) {
        this.app = app;
        this.songImporter = songImporter;
        downloadManager = (DownloadManager) app.getSystemService(DOWNLOAD_SERVICE);
        downloadDir = app.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        app.getApplicationContext().registerReceiver(downloadReceiver, filter);
    }


    public long downloadSong(String url) {

        Uri uri = Uri.parse(url);

        long downloadReference;
        String downloadId;
        try {
            // Create request for android download manager
            DownloadManager.Request request = new DownloadManager.Request(uri);

            String[] arr = url.split("/");
            downloadId = arr[arr.length - 1];

            if (downloadExists(downloadId)) return -1;

            request.setTitle(downloadId);
            request.setDescription("Android Data download using DownloadManager.");
            request.setDestinationInExternalFilesDir(app, Environment.DIRECTORY_DOWNLOADS, downloadId + ".mp3");

            //Enqueue download and save into referenceId

            downloadReference = downloadManager.enqueue(request);

            Toast toast = Toast.makeText(app,
                    "Starting download: " + downloadReference, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        } catch (Exception e) {
          Log.v("LOOK", "COULDN'T DOWNLOAD SONG" + e);
          return -1;
        }

        Log.v("LOOK", "Download song : " + downloadId + " referenceID: " + downloadReference);

        return downloadReference;
    }

    private boolean downloadExists(String downloadId) {
        for (File f : downloadDir.listFiles()) {
            if (f.getAbsolutePath().equals((downloadDir.toString() + "/" + downloadId + ".mp3"))) {
                Log.v("LOOK", "SONG ALREADY EXISTS: " + downloadId);
                Toast toast = Toast.makeText(app,
                        "SONG ALREADY EXISTS: " + downloadId, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 25, 400);
                toast.show();
                return true;
            }
        }
        return false;
    }

    public BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            Toast toast = Toast.makeText(app, "Music Download Complete " + referenceId, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
            songImporter.read();
        }
    };

    public void Check_Music_Status(long downloadId) {

        DownloadManager.Query ImageDownloadQuery = new DownloadManager.Query();
        //set the query filter to our previously Enqueued download
        ImageDownloadQuery.setFilterById(downloadId);

        //Query the download manager about downloads that have been requested.
        Cursor cursor = downloadManager.query(ImageDownloadQuery);
        if(cursor.moveToFirst()){
            DownloadStatus(cursor, downloadId);
        }

    }

    private void DownloadStatus(Cursor cursor, long downloadId) {

        //column for download  status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename

        String statusText = "";
        String reasonText = "";

        switch (status) {
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "STATUS_SUCCESSFUL";
                break;
        }


        Log.v("LOOK", statusText + ": " + reason + " " + downloadId);

        Toast toast = Toast.makeText(app,
                "Music Download Status:" + "\n" + statusText + "\n" +
                        reasonText,
                Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 25, 400);
        toast.show();
    }
}