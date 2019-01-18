package com.example.ofunes.pennypanphone;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
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
        playRandomSong();
        player.setOnCompletionListener(this);

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
        int song = random.nextInt(3);

        switch(song)
        {
            case 0:
                player = MediaPlayer.create(this, R.raw.buymode1);
                player.start();
                break;

            case 1:
                player = MediaPlayer.create(this, R.raw.buymode2);
                player.start();
                break;

            case 2:
                player = MediaPlayer.create(this, R.raw.buymode3);
                player.start();
                break;

            case 3:
                player = MediaPlayer.create(this, R.raw.buymode4);
                player.start();
                break;
        }
    }
}