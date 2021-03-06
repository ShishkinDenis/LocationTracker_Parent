package com.shishkindenis.locationtracker_parent;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomModule {
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Provides
    FirebaseAuth provideAuth(){
        return auth;
    }

//    @Provides
//    @Singleton
//    IdSingleton provideIdSingleton(){
//        return new IdSingleton();
//    }
}
