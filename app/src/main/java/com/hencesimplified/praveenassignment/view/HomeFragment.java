package com.hencesimplified.praveenassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.viewmodel.PatientListViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    SwipeRefreshLayout refreshLayout;
    ProgressBar progressBar;
    RecyclerView patientListView;
    private PatientListAdapter patientListAdapter = new PatientListAdapter(new ArrayList<>());
    private PatientListViewModel patientListViewModel;
    private String TYPE_FLAG;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        TYPE_FLAG = intent.getStringExtra("TYPE_FLAG");

        refreshLayout = view.findViewById(R.id.refreshLayout);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        patientListView = view.findViewById(R.id.patientListView);

        patientListViewModel = new ViewModelProvider(this).get(PatientListViewModel.class);
        patientListViewModel.refresh();

        if (TYPE_FLAG.equals("Doctor")) {
            patientListViewModel.getAllPatients();
        } else {
            patientListViewModel.getPatient();
        }

        patientListView.setLayoutManager(new LinearLayoutManager(getContext()));
        patientListView.setAdapter(patientListAdapter);

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(false);
        });

        observeViewModel();
    }

    private void observeViewModel() {

        patientListViewModel.patientList.observe(getActivity(), patients -> {
            if (patients != null) {
                patientListAdapter.updatePatientList(patients);
            }
        });

        patientListViewModel.isLoadingList.observe(getActivity(), isLoadingList -> {
            if (isLoadingList != null) {
                progressBar.setVisibility(isLoadingList ? View.VISIBLE : View.GONE);
            }
        });
    }

}