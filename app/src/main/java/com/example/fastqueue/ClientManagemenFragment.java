package com.example.fastqueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ClientManagemenFragment extends Fragment {

    private View view = null;

    private Switch Switch1, Switch2 ,Switch3 ,Switch4;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.activity_client_managemen_fragment, container, false);
        }

        findViews();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2 , statusSwitch3, statusSwitch4 ;
                if (Switch1.isChecked())
                    statusSwitch1 = Switch1.getTextOn().toString();
                else
                    statusSwitch1 = Switch1.getTextOff().toString();
                if (Switch2.isChecked())
                    statusSwitch2 = Switch2.getTextOn().toString();
                else
                    statusSwitch2 = Switch2.getTextOff().toString();


                if (Switch3.isChecked())
                    statusSwitch3 = Switch3.getTextOn().toString();
                else
                    statusSwitch3 = Switch3.getTextOff().toString();


                if (Switch4.isChecked())
                    statusSwitch4 = Switch4.getTextOn().toString();
                else
                    statusSwitch4 = Switch4.getTextOff().toString();

                Toast.makeText(getContext(), "Switch1 :" + statusSwitch1 + "\n" + "Switch2 :" + statusSwitch2 +  "\n" + "Switch3 :" + statusSwitch3 + "\n" + "Switch4 :" + statusSwitch4, Toast.LENGTH_LONG).show(); // display the current state for switch's
            }
        });


        return view;
    }

    private void findViews() {
        Switch1 = view.findViewById(R.id.Switch1);
        Switch2 = view.findViewById(R.id.Switch2);
        Switch3 = view.findViewById(R.id.Switch3);
        Switch4 = view.findViewById(R.id.Switch4);
        btnSubmit = view.findViewById(R.id.btnSubmit);

    }

}