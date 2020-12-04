package com.hencesimplified.praveenassignment.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseHelper {

    FirebaseAuth firebaseAuth;

    public FirebaseAuth firebaseInstantiate() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseUser verifyUser() {
        return firebaseAuth.getCurrentUser();
    }

}
