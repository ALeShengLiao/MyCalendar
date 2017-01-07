package com.example.sheng.mycalendar;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationListener;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class SampleSchedulingService extends IntentService {
    public SampleSchedulingService(){
        super("SchedulingService");
    }
    // An ID used to post the notification.
    public static final int NOTIFICATION_ID = 1;

    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    //Notification.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        // BEGIN_INCLUDE(service_onhandle)
        sendNotification("Time over !!!");
        Log.i("Notifi", "time over");
        // Release the wake lock provided by the BroadcastReceiver.
        SampleAlarmReceiver.completeWakefulIntent(intent);
        // END_INCLUDE(service_onhandle)
    }

    // Post a notification indicating whether a doodle was found.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        // 建立-通知服務建構器
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        // 建立震動效果，陣列中元素依序為停止、震動的時間，單位是毫秒
        long[] vibratepattern = { 100, 400, 500, 400 };
        // 定義 Notification.Builder 建構器
        mBuilder.setSmallIcon(R.drawable.alarm) // 通知服務 icon
                //.setLargeIcon(bmp)
                .setContentTitle("時間到了") // 標題
                .setContentText(msg) // 內文
                //.setContentInfo("") // 信息
                //.setTicker("") // Ticker 標題
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setLights(0xFFFFFFFF, 1000, 1000) // LED
                .setVibrate(vibratepattern) // 震動
                .setContentIntent(contentIntent) // 設定Intent服務
                .setAutoCancel(true); // true：按下訊息嵌板後會自動消失
        // 設定閃燈效果，參數依序為顏色、打開與關閉時間，單位是毫秒
        mBuilder.setLights(Color.GREEN, 1000, 1000);
        //mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build()); // 發佈Notification
    }


}
