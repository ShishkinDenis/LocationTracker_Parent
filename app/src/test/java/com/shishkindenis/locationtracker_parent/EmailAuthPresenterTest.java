package com.shishkindenis.locationtracker_parent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailAuthPresenterTest {
    EmailAuthPresenter emailAuthPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    FirebaseAuth auth;
    @Mock
    Task task;
    @Mock
    EmailAuthView$$State emailAuthView$$State;

    @Before
    public void setUp() {
        emailAuthPresenter = new EmailAuthPresenter(firebaseUserSingleton);
        emailAuthPresenter.setViewState(emailAuthView$$State);
    }

    @Test
    public void createUserWithEmailAndPasswordIsCalled() {
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
        when(auth.createUserWithEmailAndPassword(email, password)).thenReturn(task);
        emailAuthPresenter.createAccount(auth, email, password);
        verify(auth).createUserWithEmailAndPassword(email, password);
    }

    @Test
    public void signInWithEmailAndPasswordIsCalled() {
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
        when(auth.signInWithEmailAndPassword(email, password)).thenReturn(task);
        emailAuthPresenter.signIn(auth, email, password);
        verify(auth).signInWithEmailAndPassword(email, password);
    }

    @Test
    public void signingUpFailedToastIsCalled() {
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
        when(auth.createUserWithEmailAndPassword(email, password)).thenReturn(task);
        emailAuthPresenter.createAccount(auth, email, password);
        ArgumentCaptor<OnCompleteListener<AuthResult>> listenerCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        verify(task).addOnCompleteListener(listenerCaptor.capture());
        OnCompleteListener<AuthResult> onCompleteListener = listenerCaptor.getValue();
        onCompleteListener.onComplete(task);
        verify(emailAuthView$$State).showToast(R.string.signing_up_failed);
    }

    @Test
    public void authenticationToastIsCalled() {
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
        when(auth.signInWithEmailAndPassword(email, password)).thenReturn(task);
        emailAuthPresenter.signIn(auth, email, password);
        ArgumentCaptor<OnCompleteListener<AuthResult>> listenerCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        verify(task).addOnCompleteListener(listenerCaptor.capture());
        OnCompleteListener<AuthResult> onCompleteListener = listenerCaptor.getValue();
        onCompleteListener.onComplete(task);
        verify(emailAuthView$$State).showToast(R.string.authentication_failed);
    }

    private String randomInt() {
        return String.valueOf(((new Random()).nextInt(100000)));
    }

}