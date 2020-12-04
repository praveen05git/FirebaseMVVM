package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hencesimplified.praveenassignment.model.Doctor;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;

public class RegistrationViewModel extends AndroidViewModel {

    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();
    private DatabaseReference databaseReference;
    private Doctor doctor;
    String doctorMap = " ";

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public void registerUser(String emailId, String password, String doctorName) {
        firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String doctorId = firebaseAuth.getCurrentUser().getUid();
                saveUser(emailId, doctorId, doctorName);
            } else {
                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveUser(String emailId, String doctorId, String doctorName) {
        databaseReference = firebaseDatabase.getReference("doctorDetails");
        doctor = new Doctor(emailId, doctorId, doctorName);
        try {
            databaseReference.child(doctorId).setValue(doctor);
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void registerPatient(String emailId, String password, String patientName, String doctorMap) {
        firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String patientId = firebaseAuth.getCurrentUser().getUid();
                saveUser(emailId, patientId, patientName);
            } else {
                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
