package com.example.sheng.mycalendar;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SampleBootReceiver extends BroadcastReceiver {
    FragmentAlarm alarm = new FragmentAlarm();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            // Set the alarm here.
            alarm.setAlarm(context);
        }
    }
}
