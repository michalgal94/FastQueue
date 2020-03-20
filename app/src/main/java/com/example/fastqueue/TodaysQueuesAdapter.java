package com.example.fastqueue;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TodaysQueuesAdapter extends RecyclerView.Adapter<TodaysQueuesAdapter.ViewHolder> {
    private List<WeekViewEvent> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    // data is passed into the constructor
    TodaysQueuesAdapter(Context context, List<WeekViewEvent> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todays_queues_items, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeekViewEvent event = mData.get(position);
        String[] splited = event.getName().split(" ");
        String type = splited[splited.length-1];
        String name = event.getName().substring(0,event.getName().length() - type.length());
        holder.queueNameCreaterTextView.setText(name);
        holder.queueTypeTextView.setText(type);
        holder.queueStartHourTextView.setText(String.format(Locale.ENGLISH,"%d:00", event.getStartTime().get(Calendar.HOUR_OF_DAY)));
        holder.queueFinishHourTextView.setText(String.format(Locale.ENGLISH,"%d:00", event.getEndTime().get(Calendar.HOUR_OF_DAY)));
        holder.queueDateTextView.setText(String.format(Locale.ENGLISH,"%d/%d/%d",
                event.getStartTime().get(Calendar.DATE),event.getStartTime().get(Calendar.MONTH),event.getStartTime().get(Calendar.YEAR)%100));
        Log.e("RECYCLE","event " + position + " " + event.getName());

    }


    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView queueNameCreaterTextView;
        TextView queueDateTextView;
        TextView queueStartHourTextView;
        TextView queueFinishHourTextView;
        TextView queueTypeTextView;



        ViewHolder(View itemView) {
            super(itemView);
            queueNameCreaterTextView = itemView.findViewById(R.id.queue_creater_name);
            queueDateTextView = itemView.findViewById(R.id.queue_date);
            queueStartHourTextView = itemView.findViewById(R.id.queue_start_hour);
            queueFinishHourTextView = itemView.findViewById(R.id.queue_finish_hour);
            queueTypeTextView = itemView.findViewById(R.id.queue_type);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(), mData.get(getAdapterPosition()));
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position, WeekViewEvent event);
    }

}
