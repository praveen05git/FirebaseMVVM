package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.AuthViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private LinearLayout loginLayout;
    private TextView loginEmailId;
    private TextView loginPassword;
    private TextView createAccount;
    private Button loginButton;
    private RadioGroup userType;
    private ProgressBar loadingView;
    private CheckBox showHidePassword;
    private Animation shakeAnimation;
    private AuthViewModel authViewModel;
    private String TYPE_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailId = findViewById(R.id.loginEmailId);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        createAccount = findViewById(R.id.createAccount);
        showHidePassword = findViewById(R.id.showHidePassword);
        loginLayout = findViewById(R.id.loginLayout);
        userType = findViewById(R.id.userType);
        loadingView = findViewById(R.id.loadingView);
        loadingView.setVisibility(View.GONE);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.refresh();

        showHidePassword.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                showHidePassword.setText("Hide Password");
                loginPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                showHidePassword.setText("Show Password");
                loginPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                loginPassword.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
            }
        });

        userType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.doctorType:
                    TYPE_FLAG = "Doctor";
                    break;
                case R.id.patientType:
                    TYPE_FLAG = "Patient";
                    break;
            }
        });

        loginButton.setOnClickListener(v -> {

            String getEmailId = loginEmailId.getText().toString().trim();
            String getPassword = loginPassword.getText().toString().trim();

            int getFlag = isValid(getEmailId, getPassword);

            switch (getFlag) {
                case 1:
                    loginLayout.startAnimation(shakeAnimation);
                    Snackbar.make(v, "Enter Email and Password ", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#534BAE"))
                            .setTextColor(Color.parseColor("#ffffff")).show();
                    break;
                case 2:
                    loginLayout.startAnimation(shakeAnimation);
                    Snackbar.make(v, "Enter valid Email", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#534BAE"))
                            .setTextColor(Color.parseColor("#ffffff")).show();
                    break;
                case 3:
                    authViewModel.loginWithEmail(loginEmailId.getText().toString(), loginPassword.getText().toString());
            }
        });

        createAccount.setOnClickListener(v -> startActivity(new Intent(this, RegistrationActivity.class)));

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

    public int isValid(String getEmailId, String getPassword) {

        int flag = 0;
        String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0)
            flag = 1;
        else if (!m.find())
            flag = 2;
        else
            flag = 3;

        return flag;
    }
}