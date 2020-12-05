package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.PatientListViewModel;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    RecyclerView patientListView;
    private PatientListAdapter patientListAdapter = new PatientListAdapter(new ArrayList<>());
    private PatientListViewModel patientListViewModel;
    private String TYPE_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        TYPE_FLAG = intent.getStringExtra("TYPE_FLAG");

        refreshLayout = findViewById(R.id.refreshLayout);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        patientListView = findViewById(R.id.patientListView);

        patientListViewModel = new ViewModelProvider(this).get(PatientListViewModel.class);
        patientListViewModel.refresh();

        if (TYPE_FLAG.equals("Doctor")) {
            patientListViewModel.getAllPatients();
        } else {
            patientListViewModel.getPatient();
        }

        patientListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        patientListView.setAdapter(patientListAdapter);

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel() {

        patientListViewModel.patientList.observe(this, patients -> {
            if (patients != null) {
                patientListAdapter.updatePatientList(patients);
            }
        });

        patientListViewModel.isLoadingList.observe(this, isLoadingList -> {
            if (isLoadingList != null) {
                progressBar.setVisibility(isLoadingList ? View.VISIBLE : View.GONE);
            }
        });
    }
}