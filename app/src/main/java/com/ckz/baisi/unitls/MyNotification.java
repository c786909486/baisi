package com.ckz.baisi.unitls;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.ckz.baisi.activity.DownloadTaskManager;

/**
 * Created by CKZ on 2017/4/15.
 */

public class MyNotification {
    private NotificationManager notificationManager;
    private Notification.Builder mBuilder;
    private Context mContext;

    public MyNotification(Context context){
        this.mContext = context;
        if (notificationManager == null){
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        mBuilder = new Notification.Builder(context);
        Intent intent = new Intent(mContext,DownloadTaskManager.class);
        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        mBuilder.setContentIntent(contentIntent);
    }
    public void startNotification(String title, String content, int icon, int notifyId){
       mBuilder.setContentTitle(title)
               .setContentText(content)
               .setSmallIcon(icon)
               .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
               .setDefaults(Notification.DEFAULT_ALL)
               .setOnlyAlertOnce(true)
               .setAutoCancel(true)
               .setWhen(System.currentTimeMillis());
        notificationManager.notify(notifyId, mBuilder.build());
    }

    public void cancleNotification(int notifyId){
        notificationManager.cancel(notifyId);
    }

    public void updateNotification(int notifyId, long progress) {
        mBuilder.setProgress(100, (int) progress, false)
                .setContentText(progress + "%");
        notificationManager.notify(notifyId, mBuilder.build());
    }
    public void completeNotification(String title, String content, int icon, int notifyId){
        mBuilder.setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        notificationManager.notify(notifyId, mBuilder.build());

    }

}
