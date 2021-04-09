package com.shishkindenis.locationtracker_parent.singletons;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class FirebaseUserSingleton {
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId;

//    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();

    @Inject
    public FirebaseUserSingleton() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FirebaseAuth getFirebaseAuth() {
        return auth;
    }

//    public FirebaseFirestore getFirestoreDataBase(){
//        return firestoreDataBase;
//    }
}
