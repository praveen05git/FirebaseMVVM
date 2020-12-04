package com.hencesimplified.praveenassignment.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.RegistrationViewModel;

public class RegistrationActivity extends AppCompatActivity {

    private TextView name;
    private TextView emailId;
    private TextView newPassword;
    private TextView confirmPassword;
    private RadioGroup userSelect;
    private Button signUpButton;
    private ProgressBar registerLoading;
    private RegistrationViewModel registrationViewModel;
    private int USER_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        emailId = findViewById(R.id.emailId);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        userSelect = findViewById(R.id.userSelect);
        signUpButton = findViewById(R.id.signupButton);
        registerLoading = findViewById(R.id.registerLoading);
        registerLoading.setVisibility(View.GONE);

        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        userSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.doctorSelect:
                        USER_FLAG = 1;
                        break;
                    case R.id.patientSelect:
                        USER_FLAG = 2;
                        break;
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (USER_FLAG == 1) {
                    registrationViewModel.registerUser(emailId.getText().toString(), confirmPassword.getText().toString(), name.getText().toString());
                } else {

                }
            }
        });

    }
}