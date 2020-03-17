package com.example.fastqueue;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alamkanak.weekview.WeekViewEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class MyFirebase {

    public interface Callback_EventsReady{
        void eventsReady(List<WeekViewEvent> events);
        void onError();
    }

    public interface Callback_EventRemoved{
        void eventRemoved();
        void onError();
    }

    public static void setUser(User user) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(user.getPhone()).setValue(user);
    }

    public static void addEvent(final WeekViewEvent event){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        myRef.push().setValue(new Gson().toJson(event));
    }

    public static void getEvents(final Callback_EventsReady callback_eventsReady){
        final List<WeekViewEvent> events = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Events");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    callback_eventsReady.onError();
                    return;
                }
                Iterable<DataSnapshot> childs = dataSnapshot.getChildren();
                for(DataSnapshot d : childs) {
                    WeekViewEvent event = (new Gson().fromJson(d.getValue(String.class), WeekViewEvent.class));
                    if(event != null)
                        events.add(event);
                }
                callback_eventsReady.eventsReady(events);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback_eventsReady.onError();
            }
        });
    }

    public static void getUsers(final CallBack_UsersReady callBack_usersReady) {
        final ArrayList<User> users2 = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null)
                    callBack_usersReady.error();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    users2.add(user);
                }

                callBack_usersReady.usersReady(users2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack_usersReady.error();
            }
        });
    }

    public static void removeEvent(final WeekViewEvent event, final Callback_EventRemoved callback){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Events");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    String old = new Gson().toJson(event);
                    if(old.equals(data.getValue(String.class))) {
                        data.getRef().removeValue();
                        callback.eventRemoved();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    public static void setBusiness(BusinessMan businessMan) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");
        myRef.child(businessMan.getPhone()).setValue(businessMan);
    }

    public static void getBusiness(final CallBack_BussinessReady callBack_bussinessReady) {
        final ArrayList<BusinessMan> businesses = new ArrayList<>();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null)
                    callBack_bussinessReady.error();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    BusinessMan businessman = ds.getValue(BusinessMan.class);
                    businesses.add(businessman);
                }

                callBack_bussinessReady.businessReady(businesses);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callBack_bussinessReady.error();
            }
        });
    }

        }