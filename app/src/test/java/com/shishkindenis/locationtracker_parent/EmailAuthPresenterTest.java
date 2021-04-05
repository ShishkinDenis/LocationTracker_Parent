package com.shishkindenis.locationtracker_parent;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.EmailAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
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

//    сработало,если when(auth.createUserWithEmailAndPassword(email,password)).thenReturn(task);
    @Test
    public void createUserWithEmailAndPasswordIsCalled(){
//        вынести в SetUP
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
//        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(auth.createUserWithEmailAndPassword(email,password)).thenReturn(task);
       emailAuthPresenter.createAccount(auth,email,password);
       verify(auth).createUserWithEmailAndPassword(email,password);
    }

    @Test
    public void signInWithEmailAndPasswordIsCalled(){
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
//        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(auth.signInWithEmailAndPassword(email,password)).thenReturn(task);
        emailAuthPresenter.signIn(auth,email,password);
        verify(auth).signInWithEmailAndPassword(email,password);
    }

//    вынести в Utils
    private String randomInt() {
        return String.valueOf(((new Random()).nextInt(100000)));
    }


}