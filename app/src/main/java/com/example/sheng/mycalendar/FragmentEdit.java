package com.example.sheng.mycalendar;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sheng.mycalendar.db.DBHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentEdit extends Fragment implements Serializable {

    private Context context;
    private ImageButton fab;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ListView listView;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> itemList;

    // 回呼狀態 : Fragment 剛被建立
    // 使用時機 : 用來設定物件變數初始值
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    // 回呼狀態 : Fragment 即將可以顯示在螢幕時
    // 使用時機 : 用來設定 Fragment Layout 界面佈局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        listView = (ListView)view.findViewById(R.id.list_edit);

        itemList = new ArrayList<>();
        adapter = new SimpleAdapter(context, itemList, R.layout.things_row,
                new String[] { "_id", "title", "description", "location", "date" },
                new int[] { R.id.show_id, R.id.show_title, R.id.show_description, R.id.show_location, R.id.show_date});
        // 3.注入適配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());

        fab = (ImageButton)view.findViewById(R.id.fab_edit);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!itemList.isEmpty()){
                    showDialog();
                    Toast.makeText(context, "edit success", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, "select empty", Toast.LENGTH_SHORT).show();
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
                ContentValues values = new ContentValues();

                values.put("title", input1.getText().toString());
                values.put("description", input2.getText().toString());
                values.put("location", input3.getText().toString());

                // Log.d("dialog",title+"\ndescription"+description+"\nlocation"+location);
                if(!values.getAsString("title").isEmpty()){
                    Toast.makeText(context, "Edit an event", Toast.LENGTH_SHORT).show();
                    db.update("things_list", values, "_id=?", new String[]{itemList.get(0).get("_id").toString()});
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

    //按下Item監聽器
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 取得 Cursor
            HashMap<String, Object> item = (HashMap<String, Object>)parent.getItemAtPosition(position);
            // 取得Item內容
            String content = item.get("date") + "";
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

    public void setEditList(final Cursor cursor) {
        if(!itemList.isEmpty()) {
            itemList.clear();
        }
        Log.i("setRandomList0", cursor.getString(0) + "");
        Log.i("setRandomList1", cursor.getString(1) + "");
        Log.i("setRandomList2", cursor.getString(2) + "");
        Log.i("setRandomList3", cursor.getString(3) + "");
        Log.i("setRandomList4", cursor.getString(4) + "");

        HashMap<String, Object> tmpInput = new HashMap<>();
        tmpInput.put("_id", cursor.getString(0));
        tmpInput.put("title", cursor.getString(1));
        tmpInput.put("description", cursor.getString(2));
        tmpInput.put("location", cursor.getString(3));
        tmpInput.put("date", cursor.getString(4));
        itemList.add(tmpInput);
        adapter.notifyDataSetChanged();
    }

    // 重新整理ListView（將資料重新匯入）
    private void refreshAll() {
        Fragment frag = new FragmentDelEdit();
        Bundle args = new Bundle();
        args.putSerializable("CONTAINER_FRAGMENT", new FragmentEdit());
        frag.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
    }


}
