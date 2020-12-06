package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hencesimplified.praveenassignment.model.AudioVideoUrl;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AudioViewModel extends AndroidViewModel {

    public MutableLiveData<List<AudioVideoUrl>> urlList = new MutableLiveData<>();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseDatabase firebaseDatabase = firebaseHelper.databaseInstantiate();
    private DatabaseReference databaseReference;
    private AudioVideoUrl audioVideoUrls;
    private List<AudioVideoUrl> newUrlList = new ArrayList<>();

    public AudioViewModel(@NonNull Application application) {
        super(application);
    }

    public void getUrlList() {
        databaseReference = firebaseDatabase.getReference("audioUrls/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        audioVideoUrls = ds.getValue(AudioVideoUrl.class);
                        newUrlList.add(audioVideoUrls);
                        urlList.setValue(newUrlList);
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
