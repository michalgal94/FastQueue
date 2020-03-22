package com.example.fastqueue;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.fastqueue.BusinessMan;
import com.example.fastqueue.Constants;
import com.example.fastqueue.Contact;
import com.example.fastqueue.MyFirebase;
import com.example.fastqueue.MySharedPreferences;
import com.example.fastqueue.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CreateNewClientDialog extends AppCompatDialogFragment {


    private EditText createClient_EDT_name;
    private EditText createClient_EDT_phone;
    private EditText createClient_EDT_email;

    private MySharedPreferences mySharedPreferences;
    private BusinessMan myBusinessMan;

    private CallBack_ClientAdded callBack_clientAdded;

    public void setCallBack_clientAdded(CallBack_ClientAdded callBack_clientAdded) {
        this.callBack_clientAdded = callBack_clientAdded;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mySharedPreferences = new MySharedPreferences(getActivity());
        myBusinessMan = new Gson().fromJson(mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, ""), BusinessMan.class);


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View cutomProfileMsg = inflater.inflate(R.layout.activity_create_client, null);


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(cutomProfileMsg)
                .setPositiveButton("אישור", null)
                .setNegativeButton("סגור", null);

        return dialog.create();
    }

    @Override
    public void onStart() {
        super.onStart();

        final AlertDialog dialog = (AlertDialog) getDialog();
        if(dialog != null) {
            Button positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(clickListener);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String clientName = "";
            String clientPhone = "";
            String clientEmail = "";
            boolean nameOk;
            boolean phoneOk;

            createClient_EDT_name = getDialog().findViewById(R.id.createClient_EDT_name);
            if(createClient_EDT_name != null) {
                clientName = createClient_EDT_name.getText().toString();
            }
            createClient_EDT_phone = getDialog().findViewById(R.id.createClient_EDT_phone);
            if(createClient_EDT_phone != null) {
                clientPhone = createClient_EDT_phone.getText().toString();
            }
            createClient_EDT_email = getDialog().findViewById(R.id.createClient_EDT_email);
            if(createClient_EDT_email != null) {
                clientEmail = createClient_EDT_email.getText().toString();
            }

            if (clientName.trim().equals("")) {
                nameOk = false;
                createClient_EDT_name.setError("יש להכניס שם לקוח");
            } else {
                nameOk = true;
            }
            if(clientPhone.trim().equals("")) {
                phoneOk = false;
                createClient_EDT_phone.setError("יש להכניס טלפון לקוח");
            } else {
                phoneOk = true;
            }

            if (nameOk && phoneOk) {
                Contact contact = new Contact(clientName, clientEmail, "", clientPhone);
                callBack_clientAdded.clientAdded(contact);
                getDialog().dismiss();
            } else {
                Toast.makeText(getActivity(), "הכנס שם לקוח וטלפון", Toast.LENGTH_SHORT).show();
            }
        }
    };

}