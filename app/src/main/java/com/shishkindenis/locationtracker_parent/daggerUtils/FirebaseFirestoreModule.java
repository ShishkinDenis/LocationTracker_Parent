package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseFirestoreModule {

    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();

    @Provides
    @Singleton
    FirebaseFirestore provideFirebaseFirestore() {
        return firestoreDataBase;
    }
}
