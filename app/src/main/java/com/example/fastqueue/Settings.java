package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Settings extends AppCompatActivity {

    private ClientManagemenFragment clientManagemenFragment;
    private BusinessDetailsFragment businessDetailsFragment;
    private RemindersFragment remindersFragment;
    private ApplicationManagementFragment applicationManagementFragment;

    private Button settings_BTN_client_manage;
    private Button setting_BTN_business_details;
    private Button settings_BTN_reminders;
    private Button settings_BTN_app_manage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_BTN_client_manage = findViewById(R.id.settings_BTN_client_manage);
        setting_BTN_business_details = findViewById(R.id.setting_BTN_business_details);
        settings_BTN_reminders = findViewById(R.id.settings_BTN_reminders);
        settings_BTN_app_manage = findViewById(R.id.settings_BTN_app_manage);

        clientManagemenFragment = new ClientManagemenFragment();
        businessDetailsFragment = new BusinessDetailsFragment();
        remindersFragment = new RemindersFragment();
        applicationManagementFragment = new ApplicationManagementFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.settings_fragment_holder, businessDetailsFragment);
        transaction.add(R.id.settings_fragment_holder, clientManagemenFragment);
        transaction.add(R.id.settings_fragment_holder, remindersFragment);
        transaction.add(R.id.settings_fragment_holder, applicationManagementFragment);
        transaction.show(businessDetailsFragment);
        transaction.hide(clientManagemenFragment);
        transaction.hide(remindersFragment);
        transaction.hide(applicationManagementFragment);
        transaction.commit();


        setting_BTN_business_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(businessDetailsFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(clientManagemenFragment);
                    transaction.hide(remindersFragment);
                    transaction.hide(applicationManagementFragment);
                    transaction.show(businessDetailsFragment);
                    transaction.commit();
                }
            }
        });

        settings_BTN_client_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clientManagemenFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(businessDetailsFragment);
                    transaction.hide(remindersFragment);
                    transaction.hide(applicationManagementFragment);
                    transaction.show(clientManagemenFragment);
                    transaction.commit();
                }
            }
        });


        settings_BTN_reminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remindersFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(businessDetailsFragment);
                    transaction.hide(clientManagemenFragment);
                    transaction.hide(applicationManagementFragment);
                    transaction.show(remindersFragment);
                    transaction.commit();
                }
            }
        });


//        TableLayout tableLayout = findViewById(R.id.types_table);
//        TableRow row = new Type(this,"Tisporet","30");



        settings_BTN_app_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(applicationManagementFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(businessDetailsFragment);
                    transaction.hide(clientManagemenFragment);
                    transaction.hide(remindersFragment);
                    transaction.show(applicationManagementFragment);
                    transaction.commit();
                }
            }
        });



    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(Settings.this,MenuActivityBusiness.class);
        startActivity(backIntent);
        Settings.this.finish();
    }


//    // get the selected dropdown list value
//    public void addListenerOnButton() {
//
//        btnSubmit = (Button) findViewById(R.id.btnSubmit);
//
//        btnSubmit.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(Settings.this,
//                        "OnClickListener : " +
//                                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()) +
//                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
//                        Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }
}

