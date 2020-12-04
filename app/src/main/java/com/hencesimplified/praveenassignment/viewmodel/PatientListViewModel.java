package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;

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

import java.util.List;

public class PatientListViewModel extends AndroidViewModel {

    public MutableLiveData<List<Patient>> patients = new MutableLiveData<>();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private DatabaseReference databaseReference;

    public PatientListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getPatient() {
        databaseReference = firebaseDatabase.getReference("patientDetails");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
