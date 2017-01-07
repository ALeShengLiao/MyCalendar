package com.example.sheng.mycalendar.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sheng.mycalendar.R;
import com.example.sheng.mycalendar.vo.ThingsToDo;
import java.util.List;

public class ThingsToDoAdapter extends BaseAdapter {

	private LayoutInflater myInflater;
	private List<ThingsToDo> list;
	
	public ThingsToDoAdapter(Context context, List<ThingsToDo> list) {
		myInflater = LayoutInflater.from(context);
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public int getPosition(String title) {
		for(int i=0;i<list.size();i++) {
			ThingsToDo things = list.get(i);
			if(things.getTitle().equals(title)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = myInflater.inflate(R.layout.things_row, null);
        TextView things_title = (TextView)convertView.findViewById(R.id.show_title);
        TextView things_description = (TextView)convertView.findViewById(R.id.show_description);
        TextView things_location = (TextView)convertView.findViewById(R.id.show_location);

        ThingsToDo things = list.get(position);

        things_title.setText(things.getTitle());
        things_description.setText(things.getDescription());
        things_location.setText(things.getLocation());
        
		return convertView;
	}

}
