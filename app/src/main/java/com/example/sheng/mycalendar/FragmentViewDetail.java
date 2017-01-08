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
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheng.mycalendar.db.DBHelper;

public class FragmentViewDetail extends Fragment {

    private TextView textView;
    private Context context;
    private ListView listView;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private Cursor maincursor; // 記錄目前資料庫查詢指標
    private int selectPos;

    // 回呼狀態 : Fragment 剛被建立
    // 使用時機 : 用來設定物件變數初始值
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_detail, container, false);
        textView = (TextView)view.findViewById(R.id.textView);
        textView.setText("you have");

        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        listView = (ListView)view.findViewById(R.id.list_detail);
        listView.setEmptyView(view.findViewById(R.id.emptyView));
        refreshListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 取得 Cursor
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                // 取得Item內容
                selectPos = cursor.getPosition();
                Log.d("CursorPosition", selectPos+"");
                Toast.makeText(context, selectPos+"", Toast.LENGTH_SHORT).show();
                FragmentDelEdit fragmentDelEdit = (FragmentDelEdit)getParentFragment();
                fragmentDelEdit.setSelectId(cursor);
            }
        });

        return view;
    }

    // 重新整理ListView（將資料重新匯入）
    private void refreshListView() {
        if (maincursor == null) {
            // 1.取得查詢所有資料的cursor
            maincursor = db.rawQuery(
                    "SELECT _id, title, description, location, date FROM things_list", null);
            // 2.設定ListAdapter適配器(使用SimpleCursorAdapter)
            adapter = new SimpleCursorAdapter(context, R.layout.things_row,
                    maincursor,
                    new String[] { "_id", "title", "description", "location", "date" },
                    new int[] { R.id.show_id, R.id.show_title, R.id.show_description, R.id.show_location, R.id.show_date},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            // 3.注入適配器
            listView.setAdapter(adapter);
        } else {
            if (maincursor.isClosed()) { // 彌補requery()不會檢查cursor closed的問題
                maincursor = null;
                refreshListView();
            } else {
                maincursor.requery(); // 若資料龐大不建議使用此法（應改用 CursorLoader）
                adapter.changeCursor(maincursor);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
