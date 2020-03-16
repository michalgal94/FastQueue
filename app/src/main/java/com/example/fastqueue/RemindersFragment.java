package com.example.fastqueue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class RemindersFragment extends Fragment {

    private View view = null;
    private Switch simpleSwitch1, simpleSwitch2 ,simpleSwitch3;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.activity_reminders_fragment, container, false);
        }


        findViews();



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch1, statusSwitch2 , statusSwitch3 ;
                if (simpleSwitch1.isChecked())
                    statusSwitch1 = simpleSwitch1.getTextOn().toString();
                else
                    statusSwitch1 = simpleSwitch1.getTextOff().toString();
                if (simpleSwitch2.isChecked())
                    statusSwitch2 = simpleSwitch2.getTextOn().toString();
                else
                    statusSwitch2 = simpleSwitch2.getTextOff().toString();


                if (simpleSwitch3.isChecked())
                    statusSwitch3 = simpleSwitch3.getTextOn().toString();
                else
                    statusSwitch3 = simpleSwitch3.getTextOff().toString();


                Toast.makeText(getContext(), "Switch1 :" + statusSwitch1 + "\n" + "Switch2 :" + statusSwitch2 +  "\n" + "Switch3 :" + statusSwitch3, Toast.LENGTH_LONG).show(); // display the current state for switch's
            }
        });


        return view;
    }


    private void findViews() {
        simpleSwitch1 = view.findViewById(R.id.simpleSwitch1);
        simpleSwitch2 = view.findViewById(R.id.simpleSwitch2);
        simpleSwitch3 = view.findViewById(R.id.simpleSwitch3);
        btnSubmit = view.findViewById(R.id.btnSubmit);

    }
}
