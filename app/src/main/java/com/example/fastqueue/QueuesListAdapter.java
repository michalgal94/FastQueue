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

public class QueuesListAdapter extends RecyclerView.Adapter<QueuesListAdapter.ViewHolder> {
    private static List<WeekViewEvent> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    // data is passed into the constructor
    QueuesListAdapter(Context context, List<WeekViewEvent> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.queues_items, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeekViewEvent event = mData.get(position);
        holder.queueNameCreaterTextView.setText(event.getName());
        holder.queueStartHourTextView.setText(event.getStartTime().get(Calendar.HOUR_OF_DAY));
        holder.queueFinishHourTextView.setText(event.getEndTime().get(Calendar.HOUR_OF_DAY));
        holder.queueDateTextView.setText(event.getStartTime().get(Calendar.DATE));

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


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    static WeekViewEvent getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
