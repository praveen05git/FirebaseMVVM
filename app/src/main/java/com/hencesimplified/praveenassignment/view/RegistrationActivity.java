package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
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
    private RadioGroup reason;
    private RadioGroup userSelect;
    private Button signUpButton;
    private ProgressBar registerLoading;
    private RegistrationViewModel registrationViewModel;
    private String USER_FLAG;
    public String doctorId;
    public String cause;

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
        reason = findViewById(R.id.reason);
        reason.setVisibility(View.GONE);
        registerLoading = findViewById(R.id.registerLoading);
        registerLoading.setVisibility(View.GONE);

        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        registrationViewModel.refresh();

        userSelect.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.doctorSelect:
                    USER_FLAG = "Doctor";
                    break;
                case R.id.patientSelect:
                    USER_FLAG = "Patient";
                    reason.setVisibility(View.VISIBLE);
                    break;
            }
        });

        signUpButton.setOnClickListener(v -> {
            if (USER_FLAG.equals("Doctor")) {
                registrationViewModel.registerDoctor(emailId.getText().toString(), confirmPassword.getText().toString(), name.getText().toString());
            } else if (USER_FLAG.equals("Patient")) {
                reason.setOnCheckedChangeListener((group, checkedId) -> {

                    switch (checkedId) {
                        case R.id.headId:
                            doctorId = "RzsUH4uhHnQ9JepzvOaLP6f9mLy2";
                            cause = "Head ache";
                            break;
                        case R.id.feverId:
                            doctorId = "qUn92donhEUGFQj9kik4WoXVaax1";
                            cause = "Fever";
                            break;
                    }
                });
                registrationViewModel.registerPatient(emailId.getText().toString(), confirmPassword.getText().toString(), name.getText().toString(), "qUn92donhEUGFQj9kik4WoXVaax1", "Fever");
            }
        });
        observeViewModel();
    }

    private void observeViewModel() {
        registrationViewModel.isRegistering.observe(this, isRegistering -> {
            if (isRegistering != null) {
                registerLoading.setVisibility(isRegistering ? View.VISIBLE : View.GONE);
            }
        });

        registrationViewModel.isRegistered.observe(this, isRegistered -> {
            if (isRegistered) {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.putExtra("TYPE_FLAG", USER_FLAG);
                startActivity(homeIntent);
            }
        });
    }
}