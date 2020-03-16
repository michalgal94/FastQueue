package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ActivityTimeBusiness extends AppCompatActivity {

    private Button btnSubmit;
    private EditText edit_TXT_start_date;
    private EditText edit_TXT_finish_date;
    private EditText edit_TXT_from_hour;
    private EditText edit_TXT_till_hour;
    private EditText edit_TXT_days;


    private MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        edit_TXT_start_date = findViewById(R.id.edit_start);
        edit_TXT_finish_date = findViewById(R.id.edit_finish);
        edit_TXT_from_hour = findViewById(R.id.edit_from_hour);
        edit_TXT_till_hour = findViewById(R.id.edit_till_hour);
        edit_TXT_days = findViewById(R.id.edit_days);
        btnSubmit = findViewById(R.id.btnSubmit);

//        mySharedPreferences = new MySharedPreferences(this);
//        String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
//        final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ActivityTime activityTime = new ActivityTime(edit_TXT_start_date.getText().toString(),edit_TXT_finish_date.getText().toString(), edit_TXT_from_hour.getText().toString(), edit_TXT_till_hour.getText().toString(),edit_TXT_days.getText().toString());
//                myBussinessman.setActivityTime(activityTime);
//
//                String jsonUserBussinessUpdated = new Gson().toJson(myBussinessman);
//                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
//                MyFirebase.setBusiness(myBussinessman);
//            }
//        });



    }

}



