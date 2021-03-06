package com.example.fastqueue;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ContactsList extends AppCompatActivity {

    private boolean hasContactsPremission;
    private RecyclerView contactListRecycleView;
    private ContactListAdapter adapter;
    private ArrayList<Contact> contactsToClientsList;

    private Button add_to_clients_list_BTN;
    private BusinessMan myBusinessman;

    private MySharedPreferences mySharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        mySharedPreferences = new MySharedPreferences(this);

        add_to_clients_list_BTN = findViewById(R.id.add_to_clients_list_BTN);
        contactsToClientsList = new ArrayList<>();

        myBusinessman = new Gson().fromJson(mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, ""), BusinessMan.class);

        if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 111);
            hasContactsPremission = true;
        } else if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            hasContactsPremission = true;
        } else {
            hasContactsPremission = false;
        }

        if (hasContactsPremission) {
            contactListRecycleView = findViewById(R.id.contacts_list);
            contactListRecycleView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ContactListAdapter(this, fetchContacts());
            adapter.setClickListener(itemClickListener);
            contactListRecycleView.setAdapter(adapter);

        }

        add_to_clients_list_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                add contacts to business.
                ArrayList<Contact> tempList;
                if(myBusinessman.getClientsList() == null) {
                    tempList = new ArrayList<>();
                } else {
                    tempList = myBusinessman.getClientsList();
                }
                tempList.addAll(contactsToClientsList);
                myBusinessman.setClientsList(tempList);
                Toast.makeText(getApplicationContext(), "הלקוחות נוספו בהצלחה!", Toast.LENGTH_SHORT).show();

                String jsonUserBussinessUpdated = new Gson().toJson(myBusinessman);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
                MyFirebase.setBusiness(myBusinessman);
            }
        });

    }


    private ContactListAdapter.ItemClickListener itemClickListener = new ContactListAdapter.ItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Contact contact = adapter.getItem(position);
            adapter.setMapValByKey(contact.getName(), !adapter.getMap().get(contact.getName()));
            adapter.notifyItemChanged(position);
            if(adapter.getMap().get(contact.getName())) {
                contactsToClientsList.add(contact);
            } else {
                contactsToClientsList.remove(contact);
            }
        }
    };


    public ArrayList<Contact> fetchContacts() {
//        if( getApplicationContext().checkSelfPermission( Manifest.permission.READ_CONTACTS ) != PackageManager.PERMISSION_GRANTED )
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 111);

        ArrayList<Contact> contancts = new ArrayList<>();
        String phoneNumber = "";
        String email = "";

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        StringBuffer output = new StringBuffer();

        ContentResolver contentResolver = getContentResolver();

        Cursor cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

        // Loop for every contact in the phone
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {

//                    output.append("\n First Name: " + name);

                    // Query and loop for every phone number of the contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                    while (phoneCursor.moveToNext()) {
                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        break;
                    }

                    phoneCursor.close();

                    // Query and loop for every email of the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                    email = "";
                    while (emailCursor.moveToNext()) {
                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        break;
                    }
                }
                Contact c = new Contact(name, email, contact_id, phoneNumber);
                contancts.add(c);

            }
        }
        return contancts;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 111: {
                // If request is cancelled, the result arrays are empty.
//                String phoneNumber = null;
//                String email = null;
//
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchContacts();

                } else {
                    // permission denied!
                }
                return;
            }

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(ContactsList.this, MenuActivityBusiness.class);
        backIntent.putExtra(Constants.KEY_BUSINESS_LOCATION, true);
        startActivity(backIntent);
        ContactsList.this.finish();
    }


}


