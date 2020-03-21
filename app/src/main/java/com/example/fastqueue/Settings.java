package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class Settings extends AppCompatActivity {

    private ClientManagemenFragment clientManagemenFragment;
    private BusinessDetailsFragment businessDetailsFragment;
    private NotificationsFragment notificationsFragment;
    private ApplicationManagementFragment applicationManagementFragment;

    private Button settings_BTN_client_manage;
    private Button setting_BTN_business_details;
    private Button settings_BTN_notifications;
    private Button settings_BTN_app_manage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings_BTN_client_manage = findViewById(R.id.settings_BTN_client_manage);
        setting_BTN_business_details = findViewById(R.id.setting_BTN_business_details);
        settings_BTN_notifications = findViewById(R.id.settings_BTN_notifications);
        settings_BTN_app_manage = findViewById(R.id.settings_BTN_app_manage);

        clientManagemenFragment = new ClientManagemenFragment();
        businessDetailsFragment = new BusinessDetailsFragment();
        notificationsFragment = new NotificationsFragment();
        applicationManagementFragment = new ApplicationManagementFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.settings_fragment_holder, businessDetailsFragment);
        transaction.add(R.id.settings_fragment_holder, clientManagemenFragment);
        transaction.add(R.id.settings_fragment_holder, notificationsFragment);
        transaction.add(R.id.settings_fragment_holder, applicationManagementFragment);
        transaction.show(businessDetailsFragment);
        transaction.hide(clientManagemenFragment);
        transaction.hide(notificationsFragment);
        transaction.hide(applicationManagementFragment);
        transaction.commit();


        setting_BTN_business_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(businessDetailsFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(clientManagemenFragment);
                    transaction.hide(notificationsFragment);
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
                    transaction.hide(notificationsFragment);
                    transaction.hide(applicationManagementFragment);
                    transaction.show(clientManagemenFragment);
                    transaction.commit();
                }
            }
        });


        settings_BTN_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notificationsFragment.isHidden()) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.hide(businessDetailsFragment);
                    transaction.hide(clientManagemenFragment);
                    transaction.hide(applicationManagementFragment);
                    transaction.show(notificationsFragment);
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
                    transaction.hide(notificationsFragment);
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


}

