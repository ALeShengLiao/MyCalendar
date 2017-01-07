package com.example.sheng.mycalendar;


import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class FragmentAlarm extends Fragment{

    private Context context;
    private Button btn_set;
    private Button btn_cancel;
    private TimePicker timePicker;
    private TextView textView;
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        Log.i("FragAlarmCreate", context+"");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        return view;
    }

    //相當於MainActivity的onCreate()中做的事情一樣。
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_set = (Button) getActivity().findViewById(R.id.setupAlarmBtn);
        btn_cancel = (Button) getActivity().findViewById(R.id.removeAlarmBtn);
        timePicker = (TimePicker)getActivity().findViewById(R.id.timePicker);
        textView = (TextView)getActivity().findViewById(R.id.pickTime);

        btn_set.setOnClickListener(btnONClick);
        btn_cancel.setOnClickListener(btnONClick);
    }


    private View.OnClickListener btnONClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.setupAlarmBtn:
                    setAlarm(context);
                    break;
                case R.id.removeAlarmBtn:
                    cancelAlarm(context);
                    break;
                default:
                    break;
            }
        }
    };

    //picker 顯示時間
    public void showTime(int hour, int min) {
        textView.setText(new StringBuilder().append(hour).append(" : ").append(min));
    }

    public void setAlarm(Context context) {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SampleAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        int hour = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        showTime(hour, min);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.i("time", calendar.getTime()+"");
        Log.i("set time", hour+"");
        Log.i("set time", min+"");

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);

        // clock, and to repeat once a day.
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmManager!= null) {
            alarmManager.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}
