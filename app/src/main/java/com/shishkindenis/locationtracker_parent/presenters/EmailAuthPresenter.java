package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {
    FirebaseUserSingleton firebaseUserSingleton;
    private String userId;

    @Inject
    public EmailAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public void createAccount(String email, String password) {
        firebaseUserSingleton.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                    } else {
                        getViewState().showToast(R.string.signing_up_failed);
                    }
                });
    }

    public void signIn(String email, String password) {
        firebaseUserSingleton.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseUserSingleton.getFirebaseAuth().getCurrentUser();
                        userId = user.getUid();
                        firebaseUserSingleton.setUserId(userId);
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToCalendarActivity();
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
