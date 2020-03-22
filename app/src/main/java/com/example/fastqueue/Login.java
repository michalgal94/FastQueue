package com.example.fastqueue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

   private TextView main_LBL_login;
   private EditText edit_TXT_username;
   private EditText edit_TXT_phone;
   private EditText edit_TXT_pass;
   private Button main_BTN_login;
   private ImageView main_BTN_create;


   private MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mySharedPreferences = new MySharedPreferences(this);

        main_LBL_login = findViewById(R.id.main_LBL_login);
        edit_TXT_username = findViewById(R.id.edit_TXT_username);
        edit_TXT_phone = findViewById(R.id.edit_TXT_phone);
        edit_TXT_pass = findViewById(R.id.edit_TXT_pass);
        main_BTN_login = findViewById(R.id.main_BTN_login);
        main_BTN_create =findViewById(R.id.main_BTN_create);

        main_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(Login.this, ChooseOption.class);
                startActivity(signUpIntent);

            }
        });

        main_BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFirebase.getUsers(new CallBack_UsersReady() {
                    @Override
                    public void usersReady(ArrayList<User> users) {
                        //Toast.makeText(getApplicationContext(),"Users Ready " + users.size(),Toast.LENGTH_SHORT).show();
                        boolean userDetected = false;
                        for(final User user : users) {
                            String phoneEntered = edit_TXT_phone.getText().toString();
                            String passwordEntered = edit_TXT_pass.getText().toString();
                            if(user.getPhone().equals(phoneEntered) && user.getPassword().equals(passwordEntered)) {
                                userDetected = true;
                                User.MyUser.setUser(user);
                                if(user.isClient()) {
                                    String jsonClient = new Gson().toJson(user);
                                    mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonClient);
                                    Intent loginIntent = new Intent(Login.this, MenuActivityClient.class);
                                    Toast.makeText(Login.this, "מתחבר לקוח", Toast.LENGTH_SHORT).show();
                                    startActivity(loginIntent);
                                    finish();
                                } else if(!user.isClient()) {
                                    MyFirebase.getBusiness(new CallBack_BussinessReady() {
                                        @Override
                                        public void businessReady(ArrayList<BusinessMan> business) {
                                            BusinessMan businessMan = null;
                                            for(BusinessMan b : business){
                                                if(b.getEmail().equals(user.getEmail())){
                                                    businessMan = b;
                                                    break;
                                                }
                                            }
                                            if(businessMan == null)
                                                return;
                                            String jsonBusiness = new Gson().toJson(businessMan);
                                            mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonBusiness);
                                            Intent loginIntent = new Intent(Login.this, MenuActivityBusiness.class);
                                            Toast.makeText(Login.this, "מתחבר עסק", Toast.LENGTH_SHORT).show();
                                            startActivity(loginIntent);
                                            finish();
                                        }

                                        @Override
                                        public void error() {

                                        }
                                    });
                                }
                                break;
                            }
                        }
                        if(!userDetected)
                            Toast.makeText(Login.this, "טעות בפרטי ההתחברות, נסה שנית", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error() {
                        Toast.makeText(getApplicationContext(),"Unexpected Error!",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

        public void err(EditText editText, String message) {
            editText.setError(message);
        }


}

