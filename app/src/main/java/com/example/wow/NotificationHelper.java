package com.example.wow;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;


public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID ="channelID";
    public static final String channel1Name ="Channel 1";
    public static final String channel2ID ="channe2ID";
    public static final String channel2Name ="Channel 2";

    private NotificationManager manager;
    public NotificationHelper(Context base) {

        super(base);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            createChannels();
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){

        //channel ID,channel Name,int Importance .Importance Defines the styles of it i.e. left top and no mixed
        NotificationChannel channel1= new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);

        //Setting for Look
        channel1.enableLights(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.enableVibration(true);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        //NotificationChannel ->NotificationManager
        getManager().createNotificationChannel(channel1);

        //channel ID,channel Name,int Importance .Importance Defines the styles of it i.e. left top and no mixed
        NotificationChannel channel2= new NotificationChannel(channel2ID,channel2Name, NotificationManager.IMPORTANCE_DEFAULT);

        //Setting for Look in channel
        channel2.enableLights(true);
        channel2.setLightColor(R.color.colorPrimary);
        channel2.enableVibration(true);
        channel2.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        //NotificationChannel ->NotificationManager
        getManager().createNotificationChannel(channel2);

    }

    public NotificationManager getManager(){
        if(manager == null){

            //Set Up NotificationManager
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    //Builder->Define the OutLook in Message
    //if lower than 26 ->no channel require ->ignore channelID
    public NotificationCompat.Builder getChannel1Notification(String title, String message){
        Intent resultIntent = new Intent(this,MainActivity.class);
        //Update if existed
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,1,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getBaseContext(),channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_one)//logo of the box
                .setAutoCancel(true) //Notification disappear after clicked
                .setContentIntent(resultPendingIntent); //Add the Intent to the Notification
    }

    public NotificationCompat.Builder getChannel2Notification(String title, String message){
        Intent resultIntent = new Intent(this,MainActivity.class);
        //Update if existed
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,2,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);





        return new NotificationCompat.Builder(getBaseContext(),channel2ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_two) //logo of the box
                .setAutoCancel(true) //Notification disappear after clicked
                .setContentIntent(resultPendingIntent); //Add the Intent to the Notification
    }

}

