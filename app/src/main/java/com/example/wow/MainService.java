package com.example.wow;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.security.Provider;

/**
 * Created by D on 1/25/2018.
 */

public class MainService extends Service {
    //Notification
    private NotificationHelper notificationHelper;

    private MediaPlayer musicPlayer;

    //Control Service
    static int mCount;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Notification
        notificationHelper = new NotificationHelper(this);
        sendToChannel1("WOW","Welcome,Let's Rock!!!");
        sendToChannel2("WOW","Welcome,Let's Rock~~~");


        //Background music
        musicPlayer = MediaPlayer.create(this,R.raw.seeyouagain);
        musicPlayer.setLooping(true);
        musicPlayer.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        musicPlayer.stop();
    }

    //Notification

    public void sendToChannel1(String title ,String message){
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title,message);

        //NotificationManager.notify() sends Notification to system
        notificationHelper.getManager().notify(1,nb.build());

    }

    public void sendToChannel2(String title ,String message){
        NotificationCompat.Builder nb = notificationHelper.getChannel2Notification(title,message);

        //NotificationManager.notify() sends Notification to system
        notificationHelper.getManager().notify(2,nb.build());
    }
}
