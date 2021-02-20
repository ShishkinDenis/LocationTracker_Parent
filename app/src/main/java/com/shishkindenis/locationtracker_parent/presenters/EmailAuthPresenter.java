 package com.shishkindenis.locationtracker_parent.presenters;

 import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.activities.CalendarActivity;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {
    public static String userID;

    public EmailAuthPresenter() {}

    public void createAccount(FirebaseAuth auth,String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getViewState().showToast("User with email: " + email + " was signed up");
                    } else {
                        getViewState().showToast("Signing up failed. Check your internet connection");
                    }
                });
    }

    public void signIn(FirebaseAuth auth,String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
//                        if user !=null
//                        userID = user.getUid();



                        getViewState().showToast("Authentication successful");
                        getViewState().goToAnotherActivity(CalendarActivity.class,"abc3","abc3");
                    } else {
                        getViewState().showToast("Authentication failed");
                    }
                });
    }

    public void signOut(FirebaseAuth auth) {
        //        если вход совершен
        auth.signOut();
        getViewState().showToast("Sign out successful");
        //         Если вход не совершен
    }

}
