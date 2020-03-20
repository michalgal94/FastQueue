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
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class ClientSchedule extends AppCompatActivity {


    private Button set_date_BTN;
    private WeekView mWeekView;
    private Context context;
    private final List<WeekViewEvent> weekViewEvents = new ArrayList<>();

/*
0503896023
12345678
 */
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

        MyFirebase.getEvents(new MyFirebase.Callback_EventsReady() {
            @Override
            public void eventsReady(List<WeekViewEvent> events) {
                weekViewEvents.clear();
                weekViewEvents.addAll(events);
                mWeekView.goToDate(Calendar.getInstance());
            }

            @Override
            public void onError() {

            }
        });

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
                                    weekViewEvents.add(event);
                                    mWeekView.goToDate(mWeekView.getFirstVisibleDay());
                                }
                            })
                            .setNegativeButton("ביטול", null)
                            .create();
                    dialog.show();
                }else if(event.getId() == 1){
                    AlertDialog dialog = new AlertDialog.Builder(context)
                            .setTitle("ביטול תור")
                            .setMessage("האם אתה בטוח שתרצה לבטל את התור?")
                            .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MyFirebase.removeEvent(event, new MyFirebase.Callback_EventRemoved() {
                                        @Override
                                        public void eventRemoved() {
                                            event.setId(-1);
                                            event.setColor(Color.BLUE);
                                            event.setName("פנוי");
                                            mWeekView.goToDate(mWeekView.getFirstVisibleDay());
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });

                                }
                            })
                            .setNegativeButton("ביטול",null)
                            .create();
                    dialog.show();
                }

            }
        });




        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                Log.d("pttt","onMonthChange newYear="  + newYear + " newMonth=" + newMonth);

                List<WeekViewEvent> events = new ArrayList<>();
                for(WeekViewEvent event : weekViewEvents){
                    if(event == null)continue;
                    if(event.getStartTime().get(Calendar.MONTH)+1 == newMonth && event.getStartTime().get(Calendar.YEAR) == newYear)
                        events.add(event);
                }

                final MySharedPreferences mySharedPreferences = new MySharedPreferences(getApplicationContext());
                String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
                final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);

                for(int hour = 7 ; hour < 24 ; hour++) {
                    for(int day = 0 ; day < 28 ; day++) {
                        Calendar calendar = new GregorianCalendar();
                        calendar.set(Calendar.YEAR,newYear);
                        calendar.set(Calendar.MONTH,newMonth-1);
                        calendar.set(Calendar.DAY_OF_MONTH,day);
                        calendar.set(Calendar.HOUR_OF_DAY,hour);
                        calendar.set(Calendar.MINUTE,0);
                        calendar.set(Calendar.SECOND,0);
                        calendar.set(Calendar.MILLISECOND,0);
                        Calendar endCal = (Calendar) calendar.clone();
                        endCal.add(Calendar.HOUR_OF_DAY,1);
                        WeekViewEvent emptyEvent = new WeekViewEvent(-1,"פנוי",calendar,endCal);
                        emptyEvent.setColor(Color.BLUE);
                        boolean add = true;
                        for(WeekViewEvent event : weekViewEvents){
                            if(dateIsWithin(event.getStartTime(),emptyEvent.getStartTime())){
                                Log.e("EVENT",event.getStartTime().toString());
                                add = false;
                                break;
                            }
                        }
                        if(add)
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

    private static boolean dateIsWithin(Calendar a, Calendar b){
        if(a.equals(b))
            return true;
        int aYear = a.get(Calendar.YEAR);
        int bYear = b.get(Calendar.YEAR);
        int aMonth = a.get(Calendar.MONTH);
        int bMonth = b.get(Calendar.MONTH);
        int aDay = a.get(Calendar.DAY_OF_WEEK);
        int bDay = b.get(Calendar.DAY_OF_WEEK);
        int aHour = a.get(Calendar.HOUR_OF_DAY);
        int bHour = b.get(Calendar.HOUR_OF_DAY);
        if(aYear != bYear || aDay != bDay || aMonth != bMonth)
            return false;
        return aHour == bHour;
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

