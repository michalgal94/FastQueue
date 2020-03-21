package com.example.fastqueue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SignUpBusiness extends AppCompatActivity {

    private EditText edit_TXT_username;
    private EditText edit_TXT_email;
    private EditText edit_TXT_pass;
    private EditText edit_TXT_verify;
    private EditText edit_phoneNumber;
    private EditText edit_TXT_businessName;
    private EditText edit_TXT_businessAddress;
    private Button main_BTN_sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_bussinessman);
        edit_TXT_username = findViewById(R.id.edit_TXT_username);
        edit_TXT_email = findViewById(R.id.edit_TXT_email);
        edit_TXT_pass = findViewById(R.id.edit_TXT_pass);
        edit_TXT_verify = findViewById(R.id.edit_TXT_verify);
        edit_phoneNumber = findViewById(R.id.edit_phoneNumber);
        edit_TXT_businessName = findViewById(R.id.edit_TXT_businessName);
        edit_TXT_businessAddress = findViewById(R.id.edit_TXT_businessAddress);

        main_BTN_sign = findViewById(R.id.main_BTN_sign);



        main_BTN_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userNameOk = false;
                boolean passwordOk = false;
                boolean emailOk = false;
                boolean verifyOk = false;
                boolean phoneOk = false;
                boolean businessNameOk = false;
                boolean businessAddressOk = false;

                if (!isUserNameValid(edit_TXT_username.getText().toString())) {
                    err(edit_TXT_username, "אנא הכנס שם חוקי");
                } else {
                    userNameOk = true;
                }
                if (!isEmailValid(edit_TXT_email.getText().toString())) {
                    err(edit_TXT_email, "אנא הכנס אימייל חוקי");
                } else {
                    emailOk = true;
                }

                if (isEmpty(edit_TXT_pass)) {
                    err(edit_TXT_pass, "אנא הכנס סיסמה חוקית");
                } else {
                    if (edit_TXT_pass.getText().toString().length() < 8) {
                        err(edit_TXT_pass, "אנא הכנס סיסמה בעלת 8 תווים לפחות");
                    } else {

                        passwordOk = true;
                    }
                }


                if (isEmpty(edit_TXT_verify)) {
                    err(edit_TXT_verify, "אנא הכנס אימות חוקי");
                } else {
                    if (!edit_TXT_verify.getText().toString().equals(edit_TXT_pass.getText().toString())) {
                        err(edit_TXT_verify, "הסיסמאות אינן תואמות ! אנא נסה שנית");
                    } else {

                        verifyOk = true;
                    }
                }


                if (!isPhoneValid(edit_phoneNumber.getText().toString())) {
                    err(edit_phoneNumber, "אנא הכנס מספר טלפןן חוקי");
                } else {
                    phoneOk = true;
                }



                if (isEmpty(edit_TXT_businessName)) {
                    err(edit_TXT_businessName, "אנא הכנס שם עסק חוקי");
                } else {
                    businessNameOk = true;
                }


                if(isEmpty(edit_TXT_businessAddress)) {
                    err(edit_TXT_businessAddress, "אנא הכנס כתובת חוקית");
                }  else {
                    businessAddressOk = true;
                }


                if(userNameOk && passwordOk && emailOk && verifyOk && businessNameOk && businessAddressOk) {
                    MyFirebase.getUsers(new CallBack_UsersReady() {
                        BusinessMan businessMan = new BusinessMan(edit_TXT_username.getText().toString(),edit_TXT_email.getText().toString(), edit_TXT_pass.getText().toString(), edit_TXT_verify.getText().toString(),edit_phoneNumber.getText().toString(), false, edit_TXT_businessName.getText().toString(), edit_TXT_businessAddress.getText().toString());
                        boolean okCreate = true;
                        @Override
                        public void usersReady(ArrayList<User> users) {
                            for(User userFire : users) {
                                if(userFire.getPhone().equalsIgnoreCase(businessMan.getPhone())) {
                                    Toast.makeText(SignUpBusiness.this, "חשבון זה כבר קיים במערכת", Toast.LENGTH_SHORT).show();
                                    okCreate = false;
                                }
                            }
                            if(okCreate) {
                                Log.d("vvv", "business name: " + businessMan.getBusinessName());
                                MyFirebase.setBusiness(businessMan);
                                Toast.makeText(SignUpBusiness.this, "חשבון נוצר בהצלחה", Toast.LENGTH_SHORT).show();
                                Intent signIntent = new Intent(SignUpBusiness.this,Login.class);
                                startActivity(signIntent);
                                SignUpBusiness.this.finish();
                            }
                        }

                        @Override
                        public void error() {

                        }
                    });





                }
            }
        });

    }


    public void err(EditText editText, String message) {
        editText.setError(message);
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    boolean isUserNameValid(String username)  {
        return (username != null) && username.matches("[A-Za-z0-9_]+");
    }

    boolean isPhoneValid(String phone) {
            if(!Pattern.matches("[a-zA-Z]+", phone)) {
                return phone.length() > 6 && phone.length() <= 13;
            }
            return false;
        }

    boolean isEmpty(EditText editText) {
        CharSequence field = editText.getText().toString();
        return TextUtils.isEmpty(field);
    }



    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(SignUpBusiness.this,ChooseOption.class);
        startActivity(backIntent);
        SignUpBusiness.this.finish();
    }



}


