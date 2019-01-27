package com.example.ofunes.pennypanphone;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.util.Random;

public class BackgroundSoundService extends Service implements MediaPlayer.OnCompletionListener {
    private static final String TAG = null;
    MediaPlayer player;

    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setVolume(50, 50);
        player.setOnCompletionListener(this);
        playRandomSong();

    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        playRandomSong();
    }

    private void playRandomSong()
    {
        Random random = new Random();
        int song = random.nextInt(4);
        String filename = "android.resource://" + this.getPackageName() + "/raw/buymode" + song;
        try { player.setDataSource(this, Uri.parse(filename)); } catch (Exception e) {}
        try { player.prepare(); } catch (Exception e) {}
        player.start();
    }

    public void setVolume(int volume)
    {
        player.setVolume(volume, volume);
    }
}