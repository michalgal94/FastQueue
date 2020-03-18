package com.example.fastqueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class BusinessDetailsFragment extends Fragment {

    private View view = null;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.activity_business_details_fragment, container, false);
        }
        final TextView businessName = view.findViewById(R.id.business_name);
        final TextView businessAddress = view.findViewById(R.id.business_address);
        final TextView businessEmail = view.findViewById(R.id.email_address);
        final TextView businessPhone = view.findViewById(R.id.business_phone);

        findViews();
        final MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        String jsonUserBussiness = mySharedPreferences.getString(Constants.KEY_USER_PREFRENCES, "");
        final BusinessMan myBussinessman = new Gson().fromJson(jsonUserBussiness, BusinessMan.class);


        businessName.setText(myBussinessman.getBusinessName());
        businessAddress.setText(myBussinessman.getBusinessAddress());
        businessEmail.setText(myBussinessman.getEmail());
        businessPhone.setText(myBussinessman.getPhone());


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBussinessman.setBusinessName(businessName.getText().toString());
                myBussinessman.setBusinessAddress(businessAddress.getText().toString());
                myBussinessman.setEmail(businessEmail.getText().toString());
                myBussinessman.setPhone(businessPhone.getText().toString());
                String jsonUserBussinessUpdated = new Gson().toJson(myBussinessman);
                mySharedPreferences.putString(Constants.KEY_USER_PREFRENCES, jsonUserBussinessUpdated);
                MyFirebase.setBusiness(myBussinessman);
                Toast.makeText(getActivity(),"הפרטים עודכנו בהצלחה!",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    private void findViews() {
        btnSubmit = view.findViewById(R.id.btnSubmit);

    }
}
