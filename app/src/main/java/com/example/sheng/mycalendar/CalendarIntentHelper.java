package com.example.sheng.mycalendar;

import android.content.Intent;

public class CalendarIntentHelper {
    public static final String EVENT_BEGIN_TIME = "beginTime";
    public static final String EVENT_END_TIME = "endTime";
    public static final String EVENT_ALL_DAY = "allDay";
    public static final String EVENT_TITLE = "title";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_LOCATION = "eventLocation";
    private String title = "";
    private String description = "";
    private String location = "";
    private boolean allDay = false;
    private long beginTimeInMillis = 0;
    private long endTimeInMillis = 0;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public boolean isAllDay() {
        return allDay;
    }
    public void setAllDay(boolean isAllDay) {
        this.allDay = isAllDay;
    }
    public long getBeginTimeInMillis() {
        if (beginTimeInMillis == 0) {
            return System.currentTimeMillis();
        }
        return beginTimeInMillis;
    }
    public void setBeginTimeInMillis(long beginTimeInMillis) {
        this.beginTimeInMillis = beginTimeInMillis;
    }
    public long getEndTimeInMillis() {
        if (endTimeInMillis == 0) {
            return getBeginTimeInMillis() + (60 * 60 * 1000);//1 hour later
        }
        return endTimeInMillis;
    }
    public void setEndTimeInMillis(long endTimeInMillis) {
        this.endTimeInMillis = endTimeInMillis;
    }
    public Intent getIntentAfterSetting(){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarIntentHelper .EVENT_TITLE, getTitle());
        intent.putExtra(CalendarIntentHelper .EVENT_DESCRIPTION, getDescription());
        intent.putExtra(CalendarIntentHelper .EVENT_BEGIN_TIME, getBeginTimeInMillis());
        intent.putExtra(CalendarIntentHelper .EVENT_END_TIME, getEndTimeInMillis());
        intent.putExtra(CalendarIntentHelper .EVENT_ALL_DAY, isAllDay());
        intent.putExtra(CalendarIntentHelper .EVENT_LOCATION, getLocation());
        return intent;
    }
}
