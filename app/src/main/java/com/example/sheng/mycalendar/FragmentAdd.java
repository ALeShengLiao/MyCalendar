package com.example.sheng.mycalendar;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class FragmentAdd extends Fragment {

    public static final String CAT_COLOR = "cat_color";

    private String color = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        color = this.getArguments().getString(CAT_COLOR);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container);
    }

    private View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_cat, container, false);

        TextView txtCat = (TextView) view.findViewById(R.id.txtCat);
        String colorCat = color + " " + txtCat.getText().toString();
        txtCat.setText(colorCat);

        return view;
    }
}