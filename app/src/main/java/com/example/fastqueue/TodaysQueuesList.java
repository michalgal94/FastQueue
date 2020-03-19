package com.example.fastqueue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alamkanak.weekview.WeekViewEvent;

import java.nio.file.WatchEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class TodaysQueuesList extends AppCompatActivity {


        private RecyclerView todaysQueuesListRecycleView;
        private QueuesListAdapter adapter;
        private Context context;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.todays_queues_list);

            todaysQueuesListRecycleView = findViewById(R.id.queues_list);
            todaysQueuesListRecycleView.setLayoutManager(new LinearLayoutManager(this));
            context = this;


            MyFirebase.getEvents(new MyFirebase.Callback_EventsReady() {
                @Override
                public void eventsReady(List<WeekViewEvent> newEvents) {

                    List<WeekViewEvent> todaysEvents = new ArrayList<>();
                    List<WeekViewEvent> others = new ArrayList<>();
                    if(!others.get(Calendar.DAY_OF_YEAR).equals(todaysEvents.get(Calendar.DAY_OF_YEAR)))
                        newEvents.remove(others);
                        newEvents.addAll(todaysEvents);

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
                    todaysQueuesListRecycleView.setAdapter(adapter);
                }

                @Override
                public void onError() {

                }
            });
        }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(TodaysQueuesList.this,MenuActivityBusiness.class);
        startActivity(backIntent);
        TodaysQueuesList.this.finish();
    }



}