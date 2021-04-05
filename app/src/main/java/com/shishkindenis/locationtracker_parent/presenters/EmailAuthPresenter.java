package com.shishkindenis.locationtracker_parent.presenters;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    FirebaseUserSingleton firebaseUserSingleton;
    private String userId;

    @Inject
    public EmailAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public void createAccount(FirebaseAuth auth, String email, String password) {
//        getViewState().logSomething();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        удалить
                        EmailAuthPresenter.this.getViewState().logSomething();
                        if (task.isSuccessful()) {
                            EmailAuthPresenter.this.getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                        } else {
                            EmailAuthPresenter.this.getViewState().showToast(R.string.signing_up_failed);
                        }
                    }
                });

    }


    public void signIn(FirebaseAuth auth, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            userId = user.getUid();
                            firebaseUserSingleton.setUserId(userId);
                            EmailAuthPresenter.this.getViewState().showToast(R.string.authentication_successful);
                            EmailAuthPresenter.this.getViewState().goToCalendarActivity();
                        } else {
                            EmailAuthPresenter.this.getViewState().showToast((R.string.authentication_failed));
                        }
                    }
                });
    }

}
