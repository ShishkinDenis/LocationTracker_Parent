package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.shishkindenis.locationtracker_parent.singletons.DateSingleton;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DateSingletonModule {
    @Provides
    @Singleton
    DateSingleton provideDateSingleton() {
        return new DateSingleton();
    }
}
