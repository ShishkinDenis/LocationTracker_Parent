package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class IdSingletonModule {
    @Provides
    @Singleton
    IdSingleton provideIdSingleton() {
        return new IdSingleton();
    }
}
