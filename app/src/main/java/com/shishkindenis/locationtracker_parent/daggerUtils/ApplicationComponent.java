package com.shishkindenis.locationtracker_parent.daggerUtils;

import com.shishkindenis.locationtracker_parent.activities.CalendarActivity;
import com.shishkindenis.locationtracker_parent.activities.EmailAuthActivity;
import com.shishkindenis.locationtracker_parent.activities.MainActivity;
import com.shishkindenis.locationtracker_parent.activities.MapActivity;
import com.shishkindenis.locationtracker_parent.activities.PhoneAuthActivity;
import com.shishkindenis.locationtracker_parent.presenters.CalendarPresenter;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.presenters.MainPresenter;
import com.shishkindenis.locationtracker_parent.presenters.MapPresenter;
import com.shishkindenis.locationtracker_parent.presenters.PhoneAuthPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
//@Component(modules = {DateSingletonModule.class, FirebaseUserSingletonModule.class})
@Component(modules = {DateSingletonModule.class, FirebaseUserSingletonModule.class, FirebaseFirestoreModule.class})
public interface ApplicationComponent {
    void inject(EmailAuthActivity emailAuthActivity);

    void inject(PhoneAuthActivity phoneAuthActivity);

    void inject(MainActivity mainActivity);

    void inject(MapActivity mapActivity);

    void inject(CalendarActivity calendarActivity);

    void inject(MainPresenter mainPresenter);

    void inject(EmailAuthPresenter emailAuthPresenter);

    void inject(PhoneAuthPresenter phoneAuthPresenter);

    void inject(CalendarPresenter calendarPresenter);

    void inject(MapPresenter mapPresenter);
}
