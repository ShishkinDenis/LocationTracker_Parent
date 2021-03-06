package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.MyApplication;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.activities.CalendarActivity;
import com.shishkindenis.locationtracker_parent.singletons.IdSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {
//    SharedPreferences sharedPreferences;
//    public static final String APP_PREFERENCES = "MySettings";
//    public static final String APP_PREFERENCES_USER_ID = "UserID";
    @Inject
    IdSingleton idSingleton;

    String userId;

    public EmailAuthPresenter() {

    }

    public void createAccount(FirebaseAuth auth, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        hard code??
                        getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                    } else {
                        getViewState().showToast(R.string.signing_up_failed);
                    }
                });
    }

    public void signIn(FirebaseAuth auth, String email, String password) {
        MyApplication.appComponent.inject(this);

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
//                        idSingleton = IdSingleton.getInstance();
                        userId = user.getUid();
                        idSingleton.setUserId(userId);

                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToAnotherActivity(CalendarActivity.class);
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
