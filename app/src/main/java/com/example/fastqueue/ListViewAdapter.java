package com.example.fastqueue;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    public ArrayList<ActivityTime> activityTimeArrayList;
    Activity activity;


    public ListViewAdapter(Activity activity, ArrayList<ActivityTime> activityTimeArrayList) {
        super();
        this.activity = activity;
        this.activityTimeArrayList = activityTimeArrayList;
    }



    @Override
    public int getCount() {
        return activityTimeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityTimeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

private class ViewHolder {
    TextView days;
    TextView until_hour;
    TextView from_hour;
    TextView finish;
    TextView start;

}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_time, null);
            holder = new ViewHolder();
            holder.days = (TextView) convertView.findViewById(R.id.days);
            holder.until_hour = (TextView) convertView.findViewById(R.id.finish_hour);
            holder.from_hour = (TextView) convertView.findViewById(R.id.start_hour);
            holder.finish = (TextView) convertView.findViewById(R.id.finish_date);
            holder.start = (TextView) convertView.findViewById(R.id.start_date);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ActivityTime activityTime = activityTimeArrayList.get(position);
        holder.days.setText(activityTime.getDays().toString());
        holder.until_hour.setText(activityTime.getUntil_hour().toString());
        holder.from_hour.setText(activityTime.getFrom_hour().toString());
        holder.finish.setText(activityTime.getFinish_date().toString());
        holder.start.setText(activityTime.getStart_date().toString());

        return convertView;
    }
}
