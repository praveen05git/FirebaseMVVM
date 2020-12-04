package com.hencesimplified.praveenassignment.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    public FirebaseAuth firebaseInstantiate() {
        return firebaseAuth = FirebaseAuth.getInstance();
    }

    public FirebaseDatabase databaseInstantiate() {
        return firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public FirebaseUser verifyUser() {
        return firebaseAuth.getCurrentUser();
    }

}
