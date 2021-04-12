package com.shishkindenis.locationtracker_parent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_parent.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_parent.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_parent.views.PhoneAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PhoneAuthProvider.class)
public class PhoneAuthPresenterTestPowerMockito {

    PhoneAuthPresenter phoneAuthPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    FirebaseAuth auth;
    @Mock
    PhoneAuthCredential credential;
    @Mock
    PhoneAuthView$$State phoneAuthView$$State;

    @Before
    public void setUp() {
        phoneAuthPresenter = new PhoneAuthPresenter(firebaseUserSingleton);
        phoneAuthPresenter.setViewState(phoneAuthView$$State);
    }

    @Test
    public void verifyPhoneNumberWithCodeTest(){
        String phoneVerificationId = "555";
        String code = "777";
        PowerMockito.mockStatic(PhoneAuthProvider.class);
        Mockito.when(PhoneAuthProvider.getCredential(phoneVerificationId, code)).thenReturn(credential);
        phoneAuthPresenter.verifyPhoneNumberWithCode(auth,code);
    }

}
