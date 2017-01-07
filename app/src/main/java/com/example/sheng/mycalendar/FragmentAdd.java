package com.example.sheng.mycalendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Calendar;

public class FragmentAdd extends Fragment {

    private Context context;
    private DatePicker datePicker;
    private ImageButton fab;
    private String title;
    private String description;
    private String location;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        datePicker = (DatePicker)getActivity().findViewById(R.id.datePicker);
        fab = (ImageButton)getActivity().findViewById(R.id.fab_add);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    //多欄位輸入型對話框
    private void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // 使用你設計的layout
        final View inputView = inflater.inflate(R.layout.dialog_input,
                (ViewGroup) getActivity().findViewById(R.id.input_layout));
        builder.setView(inputView);
        final EditText input1 = (EditText)inputView.findViewById(R.id.input_title);
        final EditText input2 = (EditText)inputView.findViewById(R.id.input_description);
        final EditText input3 = (EditText)inputView.findViewById(R.id.input_location);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                title = input1.getText().toString();
                description = input2.getText().toString();
                location = input3.getText().toString();
                // Log.d("dialog",title+"\ndescription"+description+"\nlocation"+location);
                if(!title.isEmpty()){
                    Toast.makeText(context, "Add an event", Toast.LENGTH_SHORT).show();
                    add();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dialog","cancel");
            }
        });
        builder.show();
    }


    public void add(){
        int year = datePicker.getYear();
        int month = datePicker.getMonth()+1;
        int day = datePicker.getDayOfMonth();
        String tmpStr = year + "-" + month + "-" + day;
        Toast.makeText(context, "Add "+ tmpStr, Toast.LENGTH_SHORT).show();
        Calendar whatTime = Calendar.getInstance();
        whatTime.set(year, month, day);
        /*
        //建立事件開始時間
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, 0, 0);
        //建立事件結束時間
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month, day, 0, 0);
        */

        //建立 CalendarIntentHelper 實體
        CalendarIntentHelper calIntent = new CalendarIntentHelper();
        //設定值
        calIntent.setTitle(title);
        calIntent.setDescription(description);
        calIntent.setAllDay(true);
        //calIntent.setBeginTimeInMillis(beginTime.getTimeInMillis());
        //calIntent.setEndTimeInMillis(endTime.getTimeInMillis());
        calIntent.setLocation(location);
        //全部設定好後就能夠取得 Intent
        Intent intent = calIntent.getIntentAfterSetting();
        //送出意圖
        startActivity(intent);
    }

}