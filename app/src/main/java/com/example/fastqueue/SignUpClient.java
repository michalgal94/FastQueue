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

public class SignUpClient extends AppCompatActivity {

    private EditText edit_TXT_username;
    private EditText edit_TXT_email;
    private EditText edit_TXT_pass;
    private EditText edit_TXT_verify;
    private EditText edit_TXT_phone;
    private Button main_BTN_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_client);
        edit_TXT_username = findViewById(R.id.edit_TXT_username);
        edit_TXT_email = findViewById(R.id.edit_TXT_email);
        edit_TXT_pass = findViewById(R.id.edit_TXT_pass);
        edit_TXT_verify = findViewById(R.id.edit_TXT_verify);
        edit_TXT_phone =findViewById(R.id.edit_TXT_phone);
        main_BTN_sign = findViewById(R.id.main_BTN_sign);


        main_BTN_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userNameOk = false;
                boolean passwordOk = false;
                boolean emailOk = false;
                boolean verifyOk = false;
//                String pass = edit_TXT_pass.getText().toString();
                Log.d("vvv","clicked sign");
                if(!isUserNameValid(edit_TXT_username.getText().toString())) {
                    err(edit_TXT_username ,"אנא הכנס שם חוקי");
                } else {
                    userNameOk = true;
                }
                if(!isEmailValid(edit_TXT_email.getText().toString())) {
                    err(edit_TXT_email ,"אנא הכנס אימייל חוקי");
                } else {
                    emailOk = true;
                }

                if(isEmpty(edit_TXT_pass)) {
                    err(edit_TXT_pass ,"אנא הכנס סיסמה חוקית");
                } else {
                    if(edit_TXT_pass.getText().toString().length() < 8) {
                        err(edit_TXT_pass, "אנא הכנס סיסמה בעלת 8 תווים לפחות");
                    } else {
                            passwordOk = true;
                    }

                }

                if(isEmpty(edit_TXT_verify)) {
                    err(edit_TXT_verify, "אנא הכנס אימות חוקי");
                } else {
//                    if(edit_TXT_verify.getText().toString().length() != edit_TXT_pass.getText().toString().length()) {
//                        err(edit_TXT_verify, "אנא הכנס סיסמה בעלת 8 תווים לפחות");
                        if (!edit_TXT_verify.getText().toString().equals(edit_TXT_pass.getText().toString())) {
                            err(edit_TXT_verify, "הסיסמאות אינן תואמות ! אנא נסה שנית");
                        } else {
                            verifyOk = true;
                        }
//                    }

                }

//              User user = new User("MichalGal", "michal12758@gmail.com" , 12345);
                Log.d("vvv","info");
                if(userNameOk && passwordOk && emailOk && verifyOk) {
                    MyFirebase.getUsers(new CallBack_UsersReady() {
                        User user = new User(edit_TXT_username.getText().toString(),edit_TXT_email.getText().toString(), edit_TXT_pass.getText().toString(), edit_TXT_verify.getText().toString(), edit_TXT_phone.getText().toString(), true);
                        boolean okCreate = true;
                        @Override
                        public void usersReady(ArrayList<User> users) {
                            for(User userFire : users) {
                                if(userFire.getPhone().equalsIgnoreCase(user.getPhone())) {
                                    Toast.makeText(SignUpClient.this, "אימייל זה כבר שמור במערכת", Toast.LENGTH_SHORT).show();
                                    okCreate = false;
                                }
                            }
                            if(okCreate) {
                                MyFirebase.setUser(user);
                                Toast.makeText(SignUpClient.this, "חשבון נוצר בהצלחה", Toast.LENGTH_SHORT).show();
                                Intent signIntent = new Intent(SignUpClient.this, Login.class);
                                startActivity(signIntent);
                                SignUpClient.this.finish();
                            }
                        }

                        @Override
                        public void error() {

                        }
                    });
//                    Log.d("vvv","user created good");
//                    User user = new User(edit_TXT_username.getText().toString(),edit_TXT_email.getText().toString(), edit_TXT_pass.getText().toString(), edit_TXT_verify.getText().toString(), edit_TXT_phone.getText().toString(), true);
////                    Log.d("vvv","email:" + user.getEmail());
////                    Log.d("vvv","pass:" + user.getPassword());
////                    Log.d("vvv","pass valid:" + user.getVerify());
////                    Log.d("vvv","username:" + user.getUsername());
//                    MyFirebase.setUser(user);
//                    Toast.makeText(SignUpClient.this, "חשבון נוצר בהצלחה", Toast.LENGTH_SHORT).show();
//                    Intent signIntent = new Intent(SignUpClient.this, Login.class);
//                    startActivity(signIntent);
//                    SignUpClient.this.finish();
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

    boolean isEmpty(EditText editText) {
        CharSequence field = editText.getText().toString();
        return TextUtils.isEmpty(field);
    }


}
