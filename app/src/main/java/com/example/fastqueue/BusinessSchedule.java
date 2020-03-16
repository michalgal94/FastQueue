package com.example.fastqueue;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class BusinessSchedule extends AppCompatActivity {


    private ImageView settings_BTN;
    private ImageView open_hours;
    private ImageView type_of_queue;
    private Button set_date_BTN;
    private WeekView mWeekView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_schedule);
        settings_BTN = findViewById(R.id.settings_BTN);
        open_hours = findViewById(R.id.open_hours);
        type_of_queue =findViewById(R.id.type_of_queue);
        set_date_BTN =findViewById(R.id.set_date_BTN);

        final List<WeekViewEvent> events = new ArrayList<>();
        MyFirebase.getEvents(new Callback_EventsReady() {
            @Override
            public void eventsReady(List<WeekViewEvent> e) {
                events.clear();
                events.addAll(e);
//                mWeekView.goToDate(events.get(0).getStartTime());
                mWeekView.goToDate(Calendar.getInstance());
                for(WeekViewEvent ev : events){
                    Log.e("EVENT","E: " + ev.getStartTime().toString() + "\n" + ev.getEndTime().toString());
                }
            }

            @Override
            public void onError() {

            }
        });

        settings_BTN.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showSettingsDialog();
                                            }
                                        }
        );


        open_hours.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              showOpeningHoursDialog();
                                          }
                                      }
        );



        set_date_BTN.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showDateDialog();
                                            }
                                        }
        );


        type_of_queue.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showTypesDialog();
                                            }
                                        }
        );


        mWeekView = findViewById(R.id.weekView);

        mWeekView.setHorizontalFlingEnabled(false);
        mWeekView.setVerticalFlingEnabled(false);
        mWeekView.setXScrollingSpeed(0);
        mWeekView.setNumberOfVisibleDays(5);

        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_YEAR, -7);
        mWeekView.goToDate(calendar);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                Log.d("pttt", event.getName());
            }
        });

        mWeekView.setMonthChangeListener(new MonthLoader.MonthChangeListener() {
            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                Log.d("pttt","onMonthChange newYear="  + newYear + " newMonth=" + newMonth);
                List<WeekViewEvent> eventList = new ArrayList<>();
                for(WeekViewEvent event : events){
                    if(event.getStartTime().get(Calendar.MONTH)+1 == newMonth)
                        eventList.add(event);
                }
                return eventList;
            }
        });
    }

    private void showSettingsDialog() {

        final EditText minutesForCaell = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("הגדרות יומן")
                .setMessage("דקות לתא")
                .setView(minutesForCaell)
                .setPositiveButton("שמור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(minutesForCaell.getText());
                    }
                })
                .setNegativeButton("סגור", null)
                .create();
        dialog.show();
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


    private void showTypesDialog() {

        final Dialog dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.list_queue_types,null);
        dialog.setContentView(v);
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
        final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);
        Button btnSubmit = v.findViewById(R.id.btnSubmit);
        final EditText desc1Edit = v.findViewById(R.id.desc1_edit);
        final EditText desc2Edit = v.findViewById(R.id.desc2_edit);
        final EditText desc3Edit = v.findViewById(R.id.desc3_edit);
        final EditText min1Edit = v.findViewById(R.id.min1_edit);
        final EditText min2Edit = v.findViewById(R.id.min2_edit);
        final EditText min3Edit = v.findViewById(R.id.min3_edit);

        final Type[] typeSaved = new Type[3];

        if(myBussinessman.getTypes() != null) {
            myBussinessman.getTypes().toArray(typeSaved);
        }
        if(typeSaved[0] != null) {
            desc1Edit.setText(typeSaved[0].getDesc());
            desc2Edit.setText(typeSaved[1].getDesc());
            desc3Edit.setText(typeSaved[2].getDesc());
            min1Edit.setText(typeSaved[0].getMin());
            min2Edit.setText(typeSaved[1].getMin());
            min3Edit.setText(typeSaved[2].getMin());
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSaved[0] = new Type(desc1Edit.getText().toString(),min1Edit.getText().toString());
                typeSaved[1] = new Type(desc2Edit.getText().toString(),min2Edit.getText().toString());
                typeSaved[2] = new Type(desc3Edit.getText().toString(),min3Edit.getText().toString());
                myBussinessman.setTypesArr(typeSaved);


                String jsonUserBussinessUpdated = new Gson().toJson(myBussinessman);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
                MyFirebase.setBusiness(myBussinessman);
                dialog.dismiss();
            }

    });

        dialog.show();
}

    void showOpeningHoursDialog(){
        final Dialog dialog = new Dialog(this);
        View v = getLayoutInflater().inflate(R.layout.activity_time,null);
        dialog.setContentView(v);
        final ActivityTime activityTime = new ActivityTime();
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(this);
        String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
        Log.e("SCEASDSDFSF","GOT JSON: " + jsonUserBussiness);
        final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);
        Button btnSubmit = v.findViewById(R.id.btnSubmit);
        final EditText daysEdit = v.findViewById(R.id.edit_days);
        final EditText tillHourEdit = v.findViewById(R.id.edit_till_hour);
        final EditText fromHourEdit = v.findViewById(R.id.edit_from_hour);
        final EditText finishEdit = v.findViewById(R.id.edit_finish);
        final EditText startEdit = v.findViewById(R.id.edit_start);

        ActivityTime activityTimeSaved = myBussinessman.getActivityTime();
        daysEdit.setText(activityTimeSaved.getDays());
        tillHourEdit.setText(activityTimeSaved.getUntil_hour());
        fromHourEdit.setText(activityTimeSaved.getFrom_hour());
        finishEdit.setText(activityTimeSaved.getFinish_date());
        startEdit.setText(activityTimeSaved.getStart_date());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityTime activityTime = new ActivityTime(startEdit.getText().toString(),finishEdit.getText().toString(), fromHourEdit.getText().toString(), tillHourEdit.getText().toString(),daysEdit.getText().toString());
                myBussinessman.setActivityTime(activityTime);
                String jsonUserBussinessUpdated = new Gson().toJson(myBussinessman);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
                MyFirebase.setBusiness(myBussinessman);
                dialog.dismiss();


            }
        });


        dialog.show();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(BusinessSchedule.this,MenuActivityBusiness.class);
        startActivity(backIntent);
        BusinessSchedule.this.finish();
    }


}
