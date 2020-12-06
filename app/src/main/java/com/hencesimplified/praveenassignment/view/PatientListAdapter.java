package com.hencesimplified.praveenassignment.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hencesimplified.praveenassignment.R;
import com.hencesimplified.praveenassignment.model.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {

    private ArrayList<Patient> patientList;

    public PatientListAdapter(ArrayList<Patient> patientList) {
        this.patientList = patientList;
    }

    public void updatePatientList(List<Patient> newPatientList) {
        patientList.clear();
        patientList.addAll(newPatientList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        TextView patientName = holder.itemView.findViewById(R.id.patientName);
        TextView patientId = holder.itemView.findViewById(R.id.patientId);
        TextView patientCause = holder.itemView.findViewById(R.id.patientCause);
        TextView patientEmail = holder.itemView.findViewById(R.id.patientEmail);

        patientName.setText(patientList.get(position).getPatientName());
        patientId.setText(patientList.get(position).getPatientId());
        patientCause.setText(patientList.get(position).getCause());
        patientEmail.setText(patientList.get(position).getEmailId());
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {

        public View itemView;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }
}
