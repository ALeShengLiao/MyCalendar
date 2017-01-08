package com.example.sheng.mycalendar;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sheng.mycalendar.db.DBHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class FragmentDelete extends Fragment implements Serializable {

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
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        listView = (ListView)view.findViewById(R.id.list_delete);

        itemList = new ArrayList<>();
        adapter = new SimpleAdapter(context, itemList, R.layout.things_row,
                new String[] { "_id", "title", "description", "location", "date" },
                new int[] { R.id.show_id, R.id.show_title, R.id.show_description, R.id.show_location, R.id.show_date});
        // 3.注入適配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());

        fab = (ImageButton)view.findViewById(R.id.fab_del);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        return view;
    }

    // 重新整理ListView（將資料重新匯入）
    private void refreshListView() {
        adapter.notifyDataSetChanged();
        // 自動將listView捲軸都移到最下面
        listView.setSelection(adapter.getCount() - 1);
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

    private void delete(){
        while (!itemList.isEmpty()){
            Log.i("delete", itemList.get(0).get("_id")+"");
            db.delete("things_list", "_id=?", new String[]{itemList.get(0).get("_id").toString()});
            itemList.remove(0);
        }
        refreshAll();
        Toast.makeText(context, "delete success", Toast.LENGTH_SHORT).show();
    }

    public void setDelList(final Cursor cursor) {
        Log.i("setRandomList0", cursor.getString(0)+"");
        Log.i("setRandomList1", cursor.getString(1)+"");
        Log.i("setRandomList2", cursor.getString(2)+"");
        Log.i("setRandomList3", cursor.getString(3)+"");
        Log.i("setRandomList4", cursor.getString(4)+"");

        HashMap<String, Object> tmpInput = new HashMap<>();
        tmpInput.put("_id", cursor.getString(0));
        tmpInput.put("title", cursor.getString(1));
        tmpInput.put("description", cursor.getString(2));
        tmpInput.put("location", cursor.getString(3));
        tmpInput.put("date", cursor.getString(4));
        itemList.add(tmpInput);
        refreshListView();
    }

    public void refreshAll(){
        Fragment frag = new FragmentDelEdit();
        Bundle args = new Bundle();
        args.putSerializable("CONTAINER_FRAGMENT", new FragmentDelete());
        frag.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.content_frame, frag).commit();
    }
}
