package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hencesimplified.praveenassignment.model.Doctor;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;
import com.hencesimplified.praveenassignment.model.Patient;

public class RegistrationViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> isRegistering = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRegistered = new MutableLiveData<>();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();
    private DatabaseReference databaseReference;
    private Doctor doctor;
    private Patient patient;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        isRegistering.setValue(false);
        isRegistered.setValue(false);
    }

    public void registerDoctor(String emailId, String password, String doctorName) {
        isRegistering.setValue(true);
        isRegistered.setValue(false);
        firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String doctorId = firebaseAuth.getCurrentUser().getUid();
                saveDoctor(emailId, doctorId, doctorName);
            } else {
                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveDoctor(String emailId, String doctorId, String doctorName) {
        databaseReference = firebaseDatabase.getReference("doctorDetails");
        doctor = new Doctor(emailId, doctorId, doctorName);
        try {
            databaseReference.child(doctorId).setValue(doctor);
            isRegistering.setValue(false);
            isRegistered.setValue(true);
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void registerPatient(String emailId, String password, String patientName, String doctorId, String cause) {
        isRegistering.setValue(true);
        isRegistered.setValue(false);
        firebaseAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String patientId = firebaseAuth.getCurrentUser().getUid();
                savePatient(emailId, patientId, patientName, doctorId, cause);
                patientMap(emailId, patientId, patientName, doctorId, cause);
            } else {
                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void savePatient(String emailId, String patientId, String patientName, String doctorId, String cause) {
        databaseReference = firebaseDatabase.getReference("patientDetails");
        patient = new Patient(emailId, patientId, patientName, doctorId, cause);
        try {
            databaseReference.child(patientId).setValue(patient);
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void patientMap(String emailId, String patientId, String patientName, String doctorId, String cause) {
        databaseReference = firebaseDatabase.getReference("doctorPatientMap");
        patient = new Patient(emailId, patientId, patientName, doctorId, cause);
        try {
            databaseReference.child(doctorId).child(patientId).setValue(patient);
            isRegistering.setValue(false);
            isRegistered.setValue(true);
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

}
