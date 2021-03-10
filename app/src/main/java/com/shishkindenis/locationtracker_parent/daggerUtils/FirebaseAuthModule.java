package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class FirebaseAuthModule {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Provides
    FirebaseAuth provideAuth() {
        return auth;
    }

}
