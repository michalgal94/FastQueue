package com.example.fastqueue;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ClientsList extends AppCompatActivity {

    private MySharedPreferences mySharedPreferences;
    private BusinessMan myBusinessMan;

    private RecyclerView contactListRecycleView;
    private ContactListAdapter adapter;
    private ArrayList<Contact> clientsPicked;
//    private boolean goodInput;

    private ImageView remove_client_BTN;
    private ImageView add_client_BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients_list);

        remove_client_BTN = findViewById(R.id.remove_client_BTN);
        add_client_BTN = findViewById(R.id.add_client_BTN);

        mySharedPreferences = new MySharedPreferences(this);
        myBusinessMan = new Gson().fromJson(mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, ""), BusinessMan.class);

        clientsPicked = new ArrayList<Contact>();
        contactListRecycleView = findViewById(R.id.clients_view);
        contactListRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactListAdapter(this, myBusinessMan.getClientsList());
        adapter.setClickListener(itemClickListener);
        contactListRecycleView.setAdapter(adapter);
//        goodInput = false;

        add_client_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCreateNewClientDialog();
            }
        });

        remove_client_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteClientsWithAlert();
                //refreshRecycler();
            }
        });


    }

    private void callCreateNewClientDialog() {
        CreateNewClientDialog createNewClientDialog = new CreateNewClientDialog();
        createNewClientDialog.show(getSupportFragmentManager(), "create new client dialog");
        createNewClientDialog.setCallBack_clientAdded(new CallBack_ClientAdded() {
            @Override
            public void clientAdded(Contact contact) {
                ArrayList<Contact> myClients = myBusinessMan.getClientsList();
                myClients.add(contact);
                myBusinessMan.setClientsList(myClients);

                String businessJson = new Gson().toJson(myBusinessMan);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, businessJson);
                MyFirebase.setBusiness(myBusinessMan);

                adapter.setMapValByKey(contact.getName(), false);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void deleteClientsWithAlert() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("מחיקת לקוחות")
                .setMessage("האם אתה בטוח שברצונך למחוק?")
                .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Contact> myClients = myBusinessMan.getClientsList();
                        myClients.removeAll(clientsPicked);
                        myBusinessMan.setClientsList(myClients);

                        String businessJson = new Gson().toJson(myBusinessMan);
                        mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, businessJson);
                        MyFirebase.setBusiness(myBusinessMan);

                        clientsPicked.clear();
                        adapter.notifyDataSetChanged();


                    }
                })
                .setNegativeButton("ביטול", null)
                .create();
        dialog.show();
    }


//    private void createNewContact() {
//
//        final EditText createClient_EDT_name;
//        final EditText createClient_EDT_phone;
//        final EditText createClient_EDT_email;
//        final Button createClient_BTN_addClient;
//
//        final MySharedPreferences mySharedPreferences;
//        final BusinessMan myBusinessMan;
//
//        mySharedPreferences = new MySharedPreferences(this);
//        myBusinessMan = new Gson().fromJson(mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, ""), BusinessMan.class);
//
//        createClient_EDT_name = findViewById(R.id.createClient_EDT_name);
//        createClient_EDT_phone = findViewById(R.id.createClient_EDT_phone);
//        createClient_EDT_email = findViewById(R.id.createClient_EDT_email);
//        createClient_BTN_addClient = findViewById(R.id.createClient_BTN_addClient);
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View cutomProfileMsg = inflater.inflate(R.layout.activity_create_client, null);
//
//       final AlertDialog dialog = new AlertDialog.Builder(this)
//                //dialog.setView(cutomProfileMsg)
//                .setTitle("הוספת לקוח חדש")
//                .setPositiveButton("אישור", null)
//                .setNegativeButton("סגור", null)
//                .show();
//        dialog.setView(cutomProfileMsg);
//        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        positiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String clientName = "";
//                String clientPhone = "";
//                String clientEmail = "";
//                    clientName = createClient_EDT_name.getText().toString();
//                    clientPhone = createClient_EDT_phone.getText().toString();
//                    clientEmail = createClient_EDT_email.getText().toString();
//                    if (!(clientName.trim().equals("") && clientPhone.trim().equals(""))) {
//                        createClient_EDT_name.setError("יש להכניס שם לקוח");
//                        createClient_EDT_phone.setError("יש להכניס טלפון לקוח");
//                        Toast.makeText(ClientsList.this, "הכנס שם לקוח וטלפון", Toast.LENGTH_SHORT).show();
//                        goodInput = true;
//                    }
//
//                if (goodInput) {
//                    Contact contact = new Contact(clientName, clientPhone, "", clientEmail);
//                    ArrayList<Contact> tempList = myBusinessMan.getClientsList();
//                    tempList.add(contact);
//                    myBusinessMan.setClientsList(tempList);
//                    mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, new Gson().toJson(myBusinessMan));
//                    MyFirebase.setBusiness(myBusinessMan);
//                    goodInput = false;
//                }
//
//            }
//
//
//        });
//    }





    private ContactListAdapter.ItemClickListener itemClickListener = new ContactListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Contact contact = ContactListAdapter.getItem(position);
            adapter.setMapValByKey(contact.getName(), !adapter.getMap().get(contact.getName()));
            adapter.notifyItemChanged(position);
            if(adapter.getMap().get(contact.getName())) {
                clientsPicked.add(contact);
            } else {
                clientsPicked.remove(contact);
            }
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(ClientsList.this,MenuActivityBusiness.class);
        startActivity(backIntent);
        ClientsList.this.finish();
    }
}
