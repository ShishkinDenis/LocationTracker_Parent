package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseUserSingletonModule {

    @Provides
    @Singleton
    FirebaseUserSingleton provideFirebaseUser() {
        return new FirebaseUserSingleton();
    }
}

