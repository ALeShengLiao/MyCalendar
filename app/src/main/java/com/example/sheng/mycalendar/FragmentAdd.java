package com.example.sheng.mycalendar;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.sheng.mycalendar.db.DBHelper;

import java.io.Serializable;
import java.sql.Date;


public class FragmentAdd extends Fragment implements Serializable {

    private Context context;
    private DatePicker datePicker;
    private ImageButton fab;
    private String title;
    private String description;
    private String location;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        datePicker = (DatePicker)view.findViewById(R.id.datePicker);
        fab = (ImageButton)view.findViewById(R.id.fab_add);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    //多欄位輸入型對話框
    private void showDialog()
    {
        // 自定Layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // 將 xml layout 轉換成視圖 View 物件
        final View inputView = inflater.inflate(R.layout.dialog_input,
                (ViewGroup) getActivity().findViewById(R.id.input_layout));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                    // 將資料匯入ListView
                    refreshAll();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("dialog","cancel");
                dialog.dismiss();
            }
        });
        builder.show();
    }


    public void add(){
        Date date = new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth());
        Toast.makeText(context, "Add "+ date, Toast.LENGTH_SHORT).show();
        //Log.i("date", date+"");

        // 將記錄新增到things資料表的參數
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("description", description);
        cv.put("location", location);
        cv.put("date", date.toString());
        // 執行SQL語句
        long id = db.insert("things_list", null, cv);
        Toast.makeText(context, "_id：" + id, Toast.LENGTH_SHORT).show();
    }

    public void refreshAll(){
        Fragment frag = new FragmentDelEdit();
        Bundle args = new Bundle();
        args.putSerializable("CONTAINER_FRAGMENT", new FragmentAdd());
        frag.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
    }
}