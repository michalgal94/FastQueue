package com.example.fastqueue;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
    private boolean goodInput;

    private MySharedPreferences mySharedPreferences;
    private BusinessMan myBusinessMan;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mySharedPreferences = new MySharedPreferences(getActivity());
        myBusinessMan = new Gson().fromJson(mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, ""), BusinessMan.class);

        createClient_EDT_name = getActivity().findViewById(R.id.createClient_EDT_name);
        createClient_EDT_phone = getActivity().findViewById(R.id.createClient_EDT_phone);
        createClient_EDT_email = getActivity().findViewById(R.id.createClient_EDT_email);
        goodInput = false;
//        createClient_BTN_addClient = getActivity().findViewById(R.id.createClient_BTN_addClient);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View cutomProfileMsg = inflater.inflate(R.layout.activity_create_client, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(cutomProfileMsg)
                .setPositiveButton("אישור", positiveButtonListener)
                .setNegativeButton("סגור", null)
                .show();

        return dialog.create();
    }

    public DialogInterface.OnClickListener positiveButtonListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String clientName = "";
            String clientPhone = "";
            String clientEmail = "";
            clientName = createClient_EDT_name.getText().toString();
            clientPhone = createClient_EDT_phone.getText().toString();
            clientEmail = createClient_EDT_email.getText().toString();
            if (!(clientName.trim().equals("") && clientPhone.trim().equals(""))) {
                createClient_EDT_name.setError("יש להכניס שם לקוח");
                createClient_EDT_phone.setError("יש להכניס טלפון לקוח");
                Toast.makeText(getActivity(), "הכנס שם לקוח וטלפון", Toast.LENGTH_SHORT).show();
                goodInput = true;
            }

            if (goodInput) {
                Contact contact = new Contact(clientName, clientPhone, "", clientEmail);
                ArrayList<Contact> tempList = myBusinessMan.getClientsList();
                tempList.add(contact);
                myBusinessMan.setClientsList(tempList);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, new Gson().toJson(myBusinessMan));
                MyFirebase.setBusiness(myBusinessMan);
                goodInput = false;
                dismiss();
            }
        }
    };

}