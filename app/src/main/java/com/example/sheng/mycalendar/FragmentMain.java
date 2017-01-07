package com.example.sheng.mycalendar;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class FragmentMain extends Fragment {

    private Context context;
    private CalendarView cv;
    private TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        HashSet<Date> events = new HashSet<>();
        events.add(new Date());
        cv = (CalendarView) getView().findViewById(R.id.calendar_view);
        textView = (TextView)getActivity().findViewById(R.id.textView2);
        Calendar current = Calendar.getInstance();
        textView.setText(current.get(Calendar.YEAR) + " - "
                +current.get(Calendar.MONTH)+1 + " - "
                +current.get(Calendar.DAY_OF_MONTH));
        cv.updateCalendar(events);
        // assign event handler
        cv.setEventHandler(new CalendarView.EventHandler() {
            @Override
            public void onDayLongPress(Date date) {
                // show returned day
                DateFormat df = SimpleDateFormat.getDateInstance();
                Toast.makeText(context, df.format(date), Toast.LENGTH_SHORT).show();
            }
        });
    }



}