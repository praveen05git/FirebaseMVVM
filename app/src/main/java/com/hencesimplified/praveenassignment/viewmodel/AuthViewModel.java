package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;

public class AuthViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> isSuccess = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    public void refresh() {
        isLoading.setValue(false);
        isSuccess.setValue(false);
    }

    public void loginWithEmail(String emailId, String password) {
        isLoading.setValue(true);
        isSuccess.setValue(false);
        firebaseAuth.signInWithEmailAndPassword(emailId, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                isLoading.setValue(false);
                isSuccess.setValue(true);
            } else {
                isLoading.setValue(false);
                isSuccess.setValue(false);
                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
