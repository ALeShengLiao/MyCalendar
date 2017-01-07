package com.example.sheng.mycalendar;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class SampleAlarmReceiver extends WakefulBroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, SampleSchedulingService.class);

        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, service);
    }

}
