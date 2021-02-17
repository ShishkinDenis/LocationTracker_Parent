package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class PhoneAuthPresenter extends MvpPresenter<PhoneAuthView> {

    public void signOut(FirebaseAuth auth) {
        //        если вход совершен
        auth.signOut();
        getViewState().showToast("Sign out successful");
        //         Если вход не совершен
    }

}
