package com.example.fastqueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class BusinessDetailsFragment extends Fragment {

    private View view = null;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.activity_business_details_fragment, container, false);
        }

        findViews();



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Todo with aviv

//                final MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
//                String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
//                Log.e("SCEASDSDFSF","GOT JSON: " + jsonUserBussiness);
//                final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);
//
//
//                ActivityTime activityTimeSaved = myBussinessman.getActivityTime();
//                daysEdit.setText(activityTimeSaved.getDays());
//                tillHourEdit.setText(activityTimeSaved.getUntil_hour());
//                fromHourEdit.setText(activityTimeSaved.getFrom_hour());
//                finishEdit.setText(activityTimeSaved.getFinish_date());
//                startEdit.setText(activityTimeSaved.getStart_date());
//
//                btnSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ActivityTime activityTime = new ActivityTime(startEdit.getText().toString(),finishEdit.getText().toString(), fromHourEdit.getText().toString(), tillHourEdit.getText().toString(),daysEdit.getText().toString());
//                        myBussinessman.setActivityTime(activityTime);
//                        String jsonUserBussinessUpdated = new Gson().toJson(myBussinessman);
//                        mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
//                        MyFirebase.setBusiness(myBussinessman);




            }

        });

        return view;
    }


    private void findViews() {
        btnSubmit = view.findViewById(R.id.btnSubmit);

    }
}
