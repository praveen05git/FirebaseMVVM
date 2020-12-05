package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;
import com.hencesimplified.praveenassignment.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Patient>> patientList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoadingList = new MutableLiveData<>();
    private List<Patient> newPatientList = new ArrayList<>();
    private Patient patients = new Patient();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private DatabaseReference databaseReference;

    public PatientListViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        isLoadingList.setValue(true);
    }

    public void getPatient() {
        String userId = firebaseHelper.verifyUser().getUid();
        databaseReference = firebaseDatabase.getReference("patientDetails/" + userId + "/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    patients = snapshot.getValue(Patient.class);
                    newPatientList.add(patients);
                    patientList.setValue(newPatientList);
                    isLoadingList.setValue(false);
                } catch (Exception e) {
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getAllPatients() {
        String userId = firebaseHelper.verifyUser().getUid();
        databaseReference = firebaseDatabase.getReference("doctorPatientMap/" + userId + "/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        patients = ds.getValue(Patient.class);
                        newPatientList.add(patients);
                        patientList.setValue(newPatientList);
                        isLoadingList.setValue(false);
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
