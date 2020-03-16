package com.example.fastqueue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChooseOption extends AppCompatActivity {

    private Button clientOption;
    private Button businessOption;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);


        clientOption = findViewById(R.id.clientOption);
        businessOption = findViewById(R.id.businessOption);

        clientOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseIntent = new Intent(ChooseOption.this, SignUpClient.class);
                startActivity(chooseIntent);
                ChooseOption.this.finish();

            }
        });


        businessOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseIntent = new Intent(ChooseOption.this, SignUpBusiness.class);
                startActivity(chooseIntent);
                ChooseOption.this.finish();

            }
        });


            }



}
