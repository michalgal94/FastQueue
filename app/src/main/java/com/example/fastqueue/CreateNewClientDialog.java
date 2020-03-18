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

import com.google.gson.Gson;

import java.util.ArrayList;

public class CreateNewClientDialog extends AppCompatDialogFragment {

    private EditText createClient_EDT_name;
    private EditText createClient_EDT_phone;
    private EditText createClient_EDT_email;
    private Button createClient_BTN_addClient;

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
        createClient_BTN_addClient = getActivity().findViewById(R.id.createClient_BTN_addClient);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View cutomProfileMsg = inflater.inflate(R.layout.layout_create_client, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(cutomProfileMsg)
                .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean goodInput = false;
                        String clientName = "";
                        String clientPhone = "";
                        String clientEmail = "";
                        while (goodInput) {
                            clientName = createClient_EDT_name.getText().toString();
                            clientPhone = createClient_EDT_phone.getText().toString();
                            clientEmail = createClient_EDT_email.getText().toString();
                            if (!(clientName.trim().equals("") && clientPhone.trim().equals(""))) {
                                createClient_EDT_name.setError("יש להכניס שם לקוח");
                                createClient_EDT_phone.setError("יש להכניס טלפון לקוח");
                                Toast.makeText(getActivity(), "הכנס שם לקוח וטלפון", Toast.LENGTH_SHORT).show();
                                goodInput = true;
                            }
                        }
                        if (goodInput) {
                            Contact contact = new Contact(clientName, clientPhone, "", clientEmail);
                            ArrayList<Contact> tempList = myBusinessMan.getClientsList();
                            tempList.add(contact);
                            myBusinessMan.setClientsList(tempList);
                            mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, new Gson().toJson(myBusinessMan));
                            MyFirebase.setBusiness(myBusinessMan);
                        }


                    }
                }).setNegativeButton("סגור", null);


        return builder.create();
    }
}
