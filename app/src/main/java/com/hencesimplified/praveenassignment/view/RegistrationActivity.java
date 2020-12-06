package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.RegistrationViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private TextView name;
    private TextView emailId;
    private TextView newPassword;
    private TextView confirmPassword;
    private RadioGroup reason;
    private LinearLayout registrationLayout;
    private static Animation shakeAnimation;
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
        setContentView(R.layout.activity_test);

        name = findViewById(R.id.name);
        emailId = findViewById(R.id.userEmailId);
        newPassword = findViewById(R.id.newPassword);
        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        userSelect = findViewById(R.id.userSelect);
        registrationLayout = findViewById(R.id.registrationLayout);
        signUpButton = findViewById(R.id.signUpButton);
        reason = findViewById(R.id.reason);
        reason.setVisibility(View.GONE);
        registerLoading = findViewById(R.id.registerLoading);
        registerLoading.setVisibility(View.GONE);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);

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

            String getName = name.getText().toString().trim();
            String getEmailId = emailId.getText().toString().trim();
            String getPassword = confirmPassword.getText().toString().trim();
            String getConfirmPassword = confirmPassword.getText().toString().trim();

            int getFlag = isValid(getName, getEmailId, getPassword, getConfirmPassword);

            switch (getFlag) {
                case 1:
                    registrationLayout.startAnimation(shakeAnimation);
                    Snackbar.make(v, "Enter required fields ", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#534BAE"))
                            .setTextColor(Color.parseColor("#ffffff")).show();
                    break;
                case 2:
                    registrationLayout.startAnimation(shakeAnimation);
                    Snackbar.make(v, "Enter valid Email ", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#534BAE"))
                            .setTextColor(Color.parseColor("#ffffff")).show();
                    break;
                case 3:
                    registrationLayout.startAnimation(shakeAnimation);
                    Snackbar.make(v, "Password doesn't match ", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#534BAE"))
                            .setTextColor(Color.parseColor("#ffffff")).show();
                    break;
                case 4:
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
                    break;
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
                Intent homeIntent = new Intent(this, NotUsedHomeActivity.class);
                homeIntent.putExtra("TYPE_FLAG", USER_FLAG);
                startActivity(homeIntent);
            }
        });
    }

    public int isValid(String getName, String getEmailId, String getPassword, String getConfirmPassword) {

        int flag = 0;
        String regEx = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(getEmailId);

        if (getName.equals("") || getName.length() == 0 || getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0 || getConfirmPassword.equals("") || getConfirmPassword.length() == 0)
            flag = 1;
        else if (!m.find())
            flag = 2;
        else if (!getConfirmPassword.equals(getPassword))
            flag = 3;
        else
            flag = 4;

        return flag;
    }
}