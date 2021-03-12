package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {

    @Inject
    IdSingleton idSingleton;

    private String userId;

    public EmailAuthPresenter() {
        MyApplication.appComponent.inject(this);
    }

    public void createAccount(FirebaseAuth auth, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                    } else {
                        getViewState().showToast(R.string.signing_up_failed);
                    }
                });
    }

    public void signIn(FirebaseAuth auth, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        userId = user.getUid();
                        idSingleton.setUserId(userId);
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToCalendarActivity();
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
