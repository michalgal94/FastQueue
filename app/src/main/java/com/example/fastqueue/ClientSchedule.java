package com.example.fastqueue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClientSchedule extends AppCompatActivity {


    private Button set_date_BTN;
    private WeekView mWeekView;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_schedule);
        context = this;
        set_date_BTN = findViewById(R.id.set_date_BTN);

        set_date_BTN.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showDateDialog();
                                            }
                                        }
        );


        mWeekView = findViewById(R.id.weekView);

        mWeekView.setHorizontalFlingEnabled(false);
        mWeekView.setVerticalFlingEnabled(false);
        mWeekView.setXScrollingSpeed(0);
        mWeekView.setNumberOfVisibleDays(5);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        mWeekView.goToDate(calendar);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener(){
            @Override
            public void onEventClick(final WeekViewEvent event, RectF eventRect) {
                if(event.getId() == -1){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,Type.TYPES);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    final Spinner creatQueue = new Spinner(context);
                    creatQueue.setAdapter(adapter);
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("קביעת תור")
                            .setMessage("סוג התור")
                            .setView(creatQueue)
                            .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    event.setId(1);
                                    event.setName(User.MyUser.getUser().getUsername() + " " + creatQueue.getSelectedItem());
                                    event.setColor(Type.TYPE_COLOR[creatQueue.getSelectedItemPosition()]);
                                    Log.e("Saving",event.getStartTime().toString());
                                    Log.e("Saved",new Gson().toJson(event));
                                    MyFirebase.addEvent(event);
//                                    mWeekView.goToDate(Calendar.getInstance());
                                }
                            })
                            .setNegativeButton("ביטול", null)
                            .create();
                    dialog.show();
                }
            }
        });


//        final List<WeekViewEvent> listevents = new ArrayList<>();
//
//        MyFirebase.getEvents(new Callback_EventsReady() {
//            @Override
//            public void eventsReady(List<WeekViewEvent> events) {
//                listevents.addAll(events);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });


        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                Log.d("pttt","onMonthChange newYear="  + newYear + " newMonth=" + newMonth);

                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

                for(int hour = 0 ; hour < 24 ; hour++){
                    for(int day = 0 ; day < 3 ; day++){

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR,newYear);
                        calendar.set(Calendar.MONTH,newMonth-1);
                        calendar.add(Calendar.HOUR,hour);
                        calendar.add(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.MINUTE,0);
                        Calendar endCal = (Calendar) calendar.clone();
                        endCal.add(Calendar.HOUR,1);
                        WeekViewEvent emptyEvent = new WeekViewEvent(-1,"פנוי",calendar,endCal);
                        emptyEvent.setColor(Color.BLUE);
                        events.add(emptyEvent);


//                for(WeekViewEvent e : listevents) {
//                    if(!e.getStartTime().equals(emptyEvent.getStartTime()) && (!e.getEndTime().equals(emptyEvent.getEndTime())))
//                        events.add(emptyEvent);
//                    }



                    }
                }

                return events;
            }
        });

        mWeekView.goToDate(Calendar.getInstance());
    }


    private void showDateDialog(){
        final Dialog dialog = new Dialog(this);
        View v = dialog.getLayoutInflater().inflate(R.layout.date_picker_dialog,null);
        dialog.setContentView(v);
        final DatePicker datePicker = v.findViewById(R.id.date_picker_dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int month, int day) {
                    Toast.makeText(getApplicationContext(),String.format(Locale.ENGLISH,"You chose: %d/%d/%d",day,month,year),Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,day);
                    mWeekView.goToDate(calendar);
                    dialog.dismiss();
                }
            });

        }else{
            Button ok = v.findViewById(R.id.date_picker_ok);
            ok.setVisibility(View.VISIBLE);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth();
                    int day = datePicker.getDayOfMonth();
                    Toast.makeText(getApplicationContext(),String.format(Locale.ENGLISH,"You chose: %d/%d/%d",day,month,year),Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year,month,day);
                    mWeekView.goToDate(calendar);
                    dialog.dismiss();
                }
            });
        }

        dialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(ClientSchedule.this,MenuActivityClient.class);
        startActivity(backIntent);
        ClientSchedule.this.finish();
    }


}

