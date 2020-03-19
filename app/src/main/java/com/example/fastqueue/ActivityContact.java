package com.example.fastqueue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ActivityContact extends AppCompatActivity {

    private EditText edit_TXT_name;
    private EditText edit_TXT_number;
    private Button main_BTN_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        edit_TXT_name = findViewById(R.id.edit_TXT_name);
        edit_TXT_number = findViewById(R.id.edit_TXT_number);
        main_BTN_send = findViewById(R.id.main_BTN_send);


        main_BTN_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "הפרטים נשלחו בהצלחה!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityContact.this, MenuActivityClient.class);
                startActivity(intent);
                ActivityContact.this.finish();

            }
        });

    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(ActivityContact.this,MenuActivityClient.class);
        startActivity(backIntent);
        ActivityContact.this.finish();
    }

}
