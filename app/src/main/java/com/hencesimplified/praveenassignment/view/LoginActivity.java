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
import com.hencesimplified.praveenassignment.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private Button registerButton;
    private RadioGroup userType;
    private ProgressBar loadingView;
    private AuthViewModel authViewModel;
    private String TYPE_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        registerButton = findViewById(R.id.registerButton);
        userType = findViewById(R.id.userType);
        loadingView = findViewById(R.id.loadingView);
        loadingView.setVisibility(View.GONE);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.refresh();

        userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.doctorType:
                        TYPE_FLAG = "Doctor";
                        break;
                    case R.id.patientType:
                        TYPE_FLAG = "Patient";
                        break;
                }
            }
        });

        login.setOnClickListener(v -> authViewModel.loginWithEmail(username.getText().toString(), password.getText().toString()));

        registerButton.setOnClickListener(v -> startActivity(new Intent(this, RegistrationActivity.class)));

        observeViewModel();
    }

    private void observeViewModel() {

        authViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading != null) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        authViewModel.isSuccess.observe(this, isSuccess -> {
            if (isSuccess) {
                Intent homeIntent = new Intent(this, HomeActivity.class);
                homeIntent.putExtra("TYPE_FLAG", TYPE_FLAG);
                startActivity(homeIntent);
            }
        });
    }
}