package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

public class MenuActivityClient extends AppCompatActivity {

    private CardView contact_card;
    private CardView waze_card;
    private CardView create_queue;
    private MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_client);

        mySharedPreferences = new MySharedPreferences(this);

        String jsonClien = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES,"");
        User myAccountClient = new Gson().fromJson(jsonClien, User.class);

        contact_card = findViewById(R.id.contact_card);
        waze_card = findViewById(R.id.waze_card);
        create_queue = findViewById(R.id.create_queue);

        contact_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityClient.this, ActivityContact.class);
                startActivity(intent);
                MenuActivityClient.this.finish();

            }
        });


        waze_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String uri = "waze://?ll=40.761043, -73.980545&z=10";
                String uri = "geo: 31.886981, 35.022192";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
//                Intent intent = new Intent(MenuActivityClient.this, NegativeArraySizeException.class);
//                startActivity(intent);
//                MenuActivityClient.this.finish();

            }
        });


        create_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityClient.this, ClientSchedule.class);
                startActivity(intent);
                MenuActivityClient.this.finish();

            }
        });

    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(MenuActivityClient.this,Login.class);
        startActivity(backIntent);
        MenuActivityClient.this.finish();
    }
}
