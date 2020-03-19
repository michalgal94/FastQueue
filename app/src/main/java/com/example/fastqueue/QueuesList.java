package com.example.fastqueue;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class QueuesList extends AppCompatActivity {

    private RecyclerView queuesListRecycleView;
    private QueuesListAdapter adapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queues_list);

        queuesListRecycleView = findViewById(R.id.queues_list);
        queuesListRecycleView.setLayoutManager(new LinearLayoutManager(this));
        context = this;


        MyFirebase.getEvents(new MyFirebase.Callback_EventsReady() {
            @Override
            public void eventsReady(List<WeekViewEvent> newEvents) {
                Collections.sort(newEvents, new Comparator<WeekViewEvent>() {
                    @Override
                    public int compare(WeekViewEvent o1, WeekViewEvent o2) {
                        return o1.getStartTime().compareTo(o2.getStartTime());
                    }
                });
                adapter = new QueuesListAdapter(context, newEvents);
                adapter.setClickListener(new QueuesListAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, WeekViewEvent event) {

                    }
                });
                queuesListRecycleView.setAdapter(adapter);
            }

            @Override
            public void onError() {

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(QueuesList.this,MenuActivityBusiness.class);
        startActivity(backIntent);
        QueuesList.this.finish();
    }
}


