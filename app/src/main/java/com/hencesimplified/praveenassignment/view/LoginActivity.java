package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private Button login;
    private AuthViewModel authViewModel;
    ProgressBar loadingView;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        loadingView = findViewById(R.id.loadingView);
        loadingView.setVisibility(View.GONE);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.refresh();

        login.setOnClickListener(v -> authViewModel.loginWithEmail(username.getText().toString(), password.getText().toString()));

        observeViewModel();
    }

    private void observeViewModel() {

        authViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                loadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

        authViewModel.isSuccess.observe(this, isSuccess -> {
            if (isSuccess) {
                startActivity(new Intent(this, RegistrationActivity.class));
            }
        });
    }
}