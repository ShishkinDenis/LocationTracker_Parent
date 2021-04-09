package com.shishkindenis.locationtracker_parent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.shishkindenis.locationtracker_parent.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneAuthPresenterTest {
    PhoneAuthPresenter phoneAuthPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    FirebaseAuth auth;
    @Mock
    PhoneAuthCredential credential;
    @Mock
    Task task;
    @Mock
    PhoneAuthView$$State phoneAuthView$$State;

//    @Mock
//    PhoneAuthProvider phoneAuthProvider;

    @Before
    public void setUp() {
        phoneAuthPresenter = new PhoneAuthPresenter(firebaseUserSingleton);
        phoneAuthPresenter.setViewState(phoneAuthView$$State);
    }

    @Test
    public void signInWithCredentialIsCalled() {
        when(auth.signInWithCredential(credential)).thenReturn(task);
        phoneAuthPresenter.signInWithPhoneAuthCredential(auth, credential);
        verify(auth).signInWithCredential(credential);
    }

    @Test
    public void authenticationFailedToastIsCalled() {
        when(auth.signInWithCredential(credential)).thenReturn(task);
        phoneAuthPresenter.signInWithPhoneAuthCredential(auth, credential);
        ArgumentCaptor<OnCompleteListener<AuthResult>> listenerCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        verify(task).addOnCompleteListener(listenerCaptor.capture());
        OnCompleteListener<AuthResult> onCompleteListener = listenerCaptor.getValue();
        onCompleteListener.onComplete(task);
        verify(phoneAuthView$$State).showToast((R.string.authentication_failed));
    }

//    PowerMockito.mockStatic(TestClass.class);
// when(TestClass.getString()).thenReturn("HelloWorld!");

    @Test
    public void verifyPhoneNumberWithCodeTest(){
        String phoneVerificationId = "555";
        String code = "777";
//        when(PhoneAuthProvider.getCredential(phoneVerificationId, code)).thenReturn(credential);
        phoneAuthPresenter.verifyPhoneNumberWithCode(auth,code);
    }






    @Test
    public void testSomeMethod(){
//        when(Log.d("1","1")).thenReturn();
//        doNothing().when(Log.d("1","1"));
        phoneAuthPresenter.someMethod();
    }


}
