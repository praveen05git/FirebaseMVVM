package com.hencesimplified.praveenassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.hencesimplified.praveenassignment.model.FirebaseHelper;

public class AuthViewModel extends AndroidViewModel {

    //public MutableLiveData<User> authenticatedUser = new MutableLiveData<>();
    private FirebaseHelper firebaseHelper = new FirebaseHelper();
    private FirebaseAuth firebaseAuth = firebaseHelper.firebaseInstantiate();

    public AuthViewModel(@NonNull Application application) {
        super(application);
    }

    private void Login(String emailId, String password) {
        firebaseAuth.signInWithEmailAndPassword(emailId, password);

    }


}
