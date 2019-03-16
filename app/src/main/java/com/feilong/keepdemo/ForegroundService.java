package com.feilong.keepdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class ForegroundService extends Service {

    private static final int SERVICE_ID = 1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        return null;
        return new LocalBinder();
    }

    private class LocalBinder extends Binder{

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT < 18){
            //4.3
            startForeground(SERVICE_ID,new Notification());
        }else if (Build.VERSION.SDK_INT < 26){
            //7.0
            startForeground(SERVICE_ID,new Notification());
            //删除通知栏消息
            startService(new Intent(ForegroundService.this,InnerService.class));
        }else {
            //7.0要设置channel
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //定义importance为最小min，优先级最低
            NotificationChannel channel = new NotificationChannel("channel","feilong",NotificationManager.IMPORTANCE_MIN);
            if (null != notificationManager){
                notificationManager.createNotificationChannel(channel);
                //channelId需与上面一致
                Notification notification = new NotificationCompat.Builder(this, "channel").build();
                startForeground(SERVICE_ID,notification);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static class InnerService extends Service{

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            //骗系统已经停掉了SERVICE_ID这个服务
            startForeground(SERVICE_ID,new Notification());
            stopForeground(true);
            stopSelf();

            return super.onStartCommand(intent, flags, startId);
        }
    }
}
