package com.shishkindenis.locationtracker_parent.presenters;

import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.CalendarView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class CalendarPresenter extends MvpPresenter<CalendarView> {

    @Inject
    FirebaseUserSingleton firebaseUserSingleton;

    public CalendarPresenter() {
        MyApplication.appComponent.inject(this);
    }

    public void signOut() {
        firebaseUserSingleton.getFirebaseAuth().signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }
}
