package com.example.sheng.mycalendar;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentViewDetail extends Fragment{

    private TextView textView;
    private Context context;
    private String[] things;
    private ListAdapter adapter;
    private ListView listView;

    // 回呼狀態 : Fragment 剛被建立
    // 使用時機 : 用來設定物件變數初始值
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_detail, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView = (TextView)getView().findViewById(R.id.textView);
        textView.setText("you have");

        listView = (ListView)getView().findViewById(R.id.list_detail);
        listView.setEmptyView(getView().findViewById(R.id.emptyView));
        things = getResources().getStringArray(R.array.things);
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1, things);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MyOnItemClickListener());

    }

    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 取得Item內容
            String content = parent.getItemAtPosition(position).toString();
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

}
