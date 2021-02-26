package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.activities.CalendarActivity;
import com.shishkindenis.locationtracker_parent.activities.MainActivity;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {

    public EmailAuthPresenter() {
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
//                        или user из MainActivity???
                        FirebaseUser user = auth.getCurrentUser();
                        MainActivity.userID = user.getUid();
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToAnotherActivity(CalendarActivity.class);
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
