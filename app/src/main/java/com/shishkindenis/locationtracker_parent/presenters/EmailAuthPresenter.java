package com.shishkindenis.locationtracker_parent.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_parent.R;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {

//    @Inject
//    FirebaseUserSingleton firebaseUserSingleton;
//
    FirebaseUserSingleton firebaseUserSingleton;

    private String userId;

//    public EmailAuthPresenter() {
//        MyApplication.appComponent.inject(this);
//             Log.d("EmailAuthPresenter", "This is FirebaseUserSingleton injected");
//    }
    @Inject
    public EmailAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
     this.firebaseUserSingleton = firebaseUserSingleton;
//     Log.d("EmailAuthPresenter", "This is FirebaseUserSingleton injected");
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
//                        FirebaseUser user = firebaseUserSingleton.getFirebaseAuth().getCurrentUser();
                        FirebaseUser user = auth.getCurrentUser();
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
