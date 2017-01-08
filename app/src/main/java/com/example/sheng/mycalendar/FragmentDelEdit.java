package com.example.sheng.mycalendar;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

public class FragmentDelEdit extends Fragment implements Serializable {


    private Fragment fragment = null ;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment = (Fragment)this.getArguments().getSerializable("CONTAINER_FRAGMENT");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_del_edit, container, false);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        return view;
    }

    public void setSelectId(final Cursor cursor) {
        Fragment fragmentWHO = getFragmentManager().findFragmentById(R.id.container);
        switch (fragmentWHO.getClass().toString()) {
            case "class com.example.sheng.mycalendar.FragmentDelete":
                FragmentDelete fragmentDelete = (FragmentDelete) fragmentWHO;
                fragmentDelete.setDelList(cursor);
                break;
            case "class com.example.sheng.mycalendar.FragmentEdit":
                FragmentEdit fragmentEdit = (FragmentEdit) fragmentWHO;
                fragmentEdit.setEditList(cursor);
                break;
            default:
                break;
        }
        Log.d("D&E log", fragmentWHO.getClass() +"");
    }
}
