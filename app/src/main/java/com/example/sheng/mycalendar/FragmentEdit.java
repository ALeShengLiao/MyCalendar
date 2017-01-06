package com.example.sheng.mycalendar;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class FragmentEdit extends Fragment {

    private Context context;
    private ImageButton fab;


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

        return view;
    }

    // 回呼狀態 : 可以開始取得 Fragment Layout 界面物件
    // 使用時機 : 用來設定 Layout 上的 UI View 物件
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fab = (ImageButton)getView().findViewById(R.id.fab_edit);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit success", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
