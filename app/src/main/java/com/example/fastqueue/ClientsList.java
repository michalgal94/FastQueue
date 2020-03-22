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
        if(myBusinessMan.getClientsList() == null) {
            adapter = new ContactListAdapter(this, new ArrayList<Contact>());
        } else {
            adapter = new ContactListAdapter(this, myBusinessMan.getClientsList());
        }
        adapter.setClickListener(itemClickListener);
        contactListRecycleView.setAdapter(adapter);


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

            }
        });


    }

    private void callCreateNewClientDialog() {
        CreateNewClientDialog createNewClientDialog = new CreateNewClientDialog();
        createNewClientDialog.show(getSupportFragmentManager(), "create new client dialog");
        createNewClientDialog.setCallBack_clientAdded(new CallBack_ClientAdded() {
            @Override
            public void clientAdded(Contact contact) {
                ArrayList<Contact> myClients;
                if(myBusinessMan.getClientsList() == null) {
                    myClients = new ArrayList<>();
                } else {
                    myClients = myBusinessMan.getClientsList();
                }
                myClients.add(contact);
                myBusinessMan.setClientsList(myClients);

                String businessJson = new Gson().toJson(myBusinessMan);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, businessJson);
                MyFirebase.setBusiness(myBusinessMan);

                adapter.setMapValByKey(contact.getName(), false);

                createContactList(myBusinessMan.getClientsList());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void createContactList(ArrayList<Contact> contacts) {
        contactListRecycleView = findViewById(R.id.clients_view);
        contactListRecycleView.setLayoutManager(new LinearLayoutManager(this));
        if(myBusinessMan.getClientsList() == null) {
            adapter = new ContactListAdapter(this, new ArrayList<Contact>());
        } else {
            adapter = new ContactListAdapter(this, contacts);
        }
        adapter.setClickListener(itemClickListener);
        contactListRecycleView.setAdapter(adapter);
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



    private ContactListAdapter.ItemClickListener itemClickListener = new ContactListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Contact contact = adapter.getItem(position);
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
