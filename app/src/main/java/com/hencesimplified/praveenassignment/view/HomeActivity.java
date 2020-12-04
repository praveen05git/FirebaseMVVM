package com.hencesimplified.praveenassignment.view;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hencesimplified.praveenassignment.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    RecyclerView patientListView;
    private PatientListAdapter patientListAdapter = new PatientListAdapter(new ArrayList<>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        refreshLayout = findViewById(R.id.refreshLayout);
        progressBar = findViewById(R.id.progressBar);
        patientListView = findViewById(R.id.patientListView);

        patientListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        patientListView.setAdapter(patientListAdapter);
    }
}