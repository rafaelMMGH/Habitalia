package com.ambarrojostudios.rafael.habitalias;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by rafael on 21/10/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "Noticias";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
            //Log.d(TAG,"Mensaje Recibido de " + from);

        if (remoteMessage.getNotification() != null){
//            Log.d(TAG,"Notificación " + remoteMessage.getNotification().getBody());

            showNotifications(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        }

        if (remoteMessage.getData().size() > 0){
           // Log.d(TAG,"Data " + remoteMessage.getData());

        }
    }

    private void showNotifications(String title, String body) {

        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri = Settings.System.DEFAULT_NOTIFICATION_URI;

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
