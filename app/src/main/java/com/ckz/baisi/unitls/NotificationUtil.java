package com.ckz.baisi.unitls;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.SparseArray;

import com.ckz.baisi.activity.DownloadTaskManager;

/**
 * Created by CKZ on 2017/4/7.
 */

public class NotificationUtil {

    private static SparseArray<NotificationCompat.Builder> notificationMap = new SparseArray<>();

    private static NotificationManager notificationManager;


    private static NotificationManager initNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return notificationManager;
    }

    /**
     * 创建进度通知栏
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     */
    public static void createProgressNotification(Context context, String title, String content, int icon, int notifyId) {
        initNotificationManager(context);

        NotificationCompat.Builder builder = initBaseBuilder(context, title, content, icon);
//        builder.setOngoing(true);
            Intent intent = new Intent(context,DownloadTaskManager.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
            builder.setContentIntent(contentIntent);
        notificationManager.notify(notifyId, builder.build());

        notificationMap.put(notifyId, builder);
    }


    /**
     * 初始化Builder
     *
     * @param context
     * @param title
     * @param content
     * @param icon
     * @return
     */
    private static NotificationCompat.Builder initBaseBuilder(Context context, String title, String content, int icon) {

        return new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setDefaults(Notification.DEFAULT_ALL)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                ;
    }

    /**
     * 取消进度通知栏
     *
     * @param notifyId
     */
    public static void cancelNotification(int notifyId) {
        notificationManager.cancel(notifyId);
        notificationMap.remove(notifyId);
    }

    /**
     * 更新通知栏进度
     *
     * @param notifyId
     * @param progress
     */
    public static void updateNotification(int notifyId, long progress) {
        NotificationCompat.Builder builder = notificationMap.get(notifyId);
        builder.setProgress(100, (int) progress, false);
        builder.setContentText(progress + "%");
        notificationManager.notify(notifyId, builder.build());
    }
    /**
     * 下载完成进度条
     *
     * @param notifyId
     * @param icon
     *
     */
    public static void completeNotification(Context context, int notifyId, int icon){
        NotificationCompat.Builder builder = notificationMap.get(notifyId);
        builder.setContentText("下载完成")
                .setSmallIcon(icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon));
        notificationManager.notify(notifyId, builder.build());
    }
}