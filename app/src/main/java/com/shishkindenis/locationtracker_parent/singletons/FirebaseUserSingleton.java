package com.shishkindenis.locationtracker_parent.singletons;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseUserSingleton {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public FirebaseAuth getFirebaseAuth(){
        return auth;
    }
}
