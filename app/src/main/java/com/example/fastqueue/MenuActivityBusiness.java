package com.example.fastqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MenuActivityBusiness extends AppCompatActivity {

    private CardView clients_card;
    private CardView schedule_card;
    private CardView import_card;
    private CardView todays_queues_card;
    private CardView activity_card;
    private CardView settings_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_business);

        clients_card = findViewById(R.id.clients_card);
        schedule_card = findViewById(R.id.schedule_card);
        import_card =findViewById(R.id.import_card);
        todays_queues_card= findViewById(R.id.todays_queues_card);
        activity_card = findViewById(R.id.activity_card);
        settings_card= findViewById(R.id.settings_card);


        schedule_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityBusiness.this, BusinessSchedule.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });


        clients_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityBusiness.this, ClientsList.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });



        settings_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityBusiness.this, Settings.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });



        import_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityBusiness.this, ContactsList.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });

        activity_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivityBusiness.this, QueuesList.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });

        todays_queues_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivityBusiness.this, TodaysQueuesList.class);
                startActivity(intent);
                MenuActivityBusiness.this.finish();

            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(MenuActivityBusiness.this,Login.class);
        startActivity(backIntent);
        MenuActivityBusiness.this.finish();
    }



    }



