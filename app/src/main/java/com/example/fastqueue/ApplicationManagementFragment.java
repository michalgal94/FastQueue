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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class ApplicationManagementFragment extends Fragment {


    private View view = null;
    private Switch simpleSwitch;
    private Button btnSubmit;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.activity_application_management_fragment, container, false);
        }

        findViews();


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String statusSwitch;
                if (simpleSwitch.isChecked())
                    statusSwitch = simpleSwitch.getTextOn().toString();
                else
                    statusSwitch = simpleSwitch.getTextOff().toString();

                Toast.makeText(getContext(), "Switch5 :" + statusSwitch, Toast.LENGTH_LONG).show(); // display the current state for switch
            }
        });


        return view;
    }


    private void findViews() {
        simpleSwitch = view.findViewById(R.id.simpleSwitch);
        btnSubmit = view.findViewById(R.id.btnSubmit);
    }

}
