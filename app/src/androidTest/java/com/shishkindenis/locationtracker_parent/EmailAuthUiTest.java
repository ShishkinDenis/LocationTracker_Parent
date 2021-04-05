package com.shishkindenis.locationtracker_parent;

import android.content.Intent;
import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_parent.activities.EmailAuthActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EmailAuthUiTest {
//    @Rule
//    public ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
//    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);
FirebaseAuth auth = FirebaseAuth.getInstance();
//    сделать sign out предварительно
//String email = "user" + randomInt() + "@example.com";
//String password = "password" + randomInt();
//@Before
//    public void signUp(){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.createUserWithEmailAndPassword(email, password);
//    }

    @Test
    public void repeatedSigningUpFailed() {
//        включить интернет
//        signUp();
        auth.signOut();
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password);

//вынести сон в метод,сократить до двух-трех
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//naming
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivity2TestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivity2TestRule.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(replaceText(email));
        onView(withId(R.id.etPassword)).perform(replaceText(password));
        onView(withId(R.id.btnRegister)).perform(click());
        onView(withText("Signing up failed")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));

    }

    @Test
    public void instanceOfEmailActivityIsSavedWhenRotating() {
//сначала выйти в before
         ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(replaceText("sh-kin@mail.ru"));
       emailAuthActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

        onView(withId(R.id.etEmail)).check(matches(withText("sh-kin@mail.ru")));
    }


    @Test
    public void requiredEmailErrorIsDisplayed(){
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etEmail)).check(matches(hasErrorText("Required email")));

    }

    @Test
    public void requiredPasswordErrorIsDisplayed(){
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.etPassword)).check(matches(hasErrorText("Required password")));

    }





    @Test
    public void emailAuthenticationFailed() {
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());

        String email = "test@test.com";
        String password = "123456";

        // Make sure we're signed out
//        signOutIfPossible();

        // Enter email
//        enterEmail(email);
        onView(withId(R.id.etEmail)).perform(replaceText(email));

        // Enter password
//        enterPassword(password);
        onView(withId(R.id.etPassword)).perform(replaceText(password));

//        click в метод
        onView(withId(R.id.btnLogin)).perform(click());

//        checkToast в метод
        onView(withText("Authentication failed")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));

    }

//    @Test
//    public void signingUpFailed(){
//        выключить интернет
//    }

    @Test
    public void signingUpSuccessful() {
//        включить интернет
        auth.signOut();
        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule3 = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule3.launchActivity(new Intent());

//        включить random
        String email = "user" + randomInt() + "@example.com";
        String password = "password" + randomInt();

        // Make sure we're signed out
//        signOutIfPossible();

        // Enter email
//        enterEmail(email);
        onView(withId(R.id.etEmail)).perform(replaceText(email));

        // Enter password
//        enterPassword(password);
        onView(withId(R.id.etPassword)).perform(replaceText(password));

//        click в метод
        onView(withId(R.id.btnRegister)).perform(click());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("User with email: " + email + " was signed up ")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));

    }

    @Test
    public void signingInSuccessful() {
//        включить интернет
//        signUp();
        auth.signOut();
        String email1 = "user" + randomInt() + "@example.com";
        String password1 = "password" + randomInt();
//        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email1, password1);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(replaceText(email1));
        onView(withId(R.id.etPassword)).perform(replaceText(password1));
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withText("Authentication successful")).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
    }
//

    private void enterEmail(String email) {
//      ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
//        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(replaceText(email));
    }

    private void enterPassword(String password) {
//        ActivityTestRule<EmailAuthActivity> emailAuthActivityActivityTestRule = new ActivityTestRule<>(EmailAuthActivity.class);
//        EmailAuthActivity emailAuthActivity = emailAuthActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.etPassword)).perform(replaceText(password));
    }

    private String randomInt() {
        return String.valueOf(((new Random()).nextInt(100000)));
    }

//    private void signOutIfPossible() {
//        try {
//            onView(allOf(withId(R.id.signOutButton), withText(R.string.sign_out), isDisplayed()))
//                    .perform(click());
//        } catch (NoMatchingViewException e) {
//             Ignore
//        }
//
//    }


//проверка появления диалога в календаре
//    toast there is no track
//    повторная регистрация?-добавить метод в код?
//проверить,что CalendarActivity открывается при входе
//    string resources
//  toast  "User with email: " + email + " was signed up "
//    toast "signed up failed"
//  toast  authentication_successful
//    toast authentication failed
//     выбранная дата в календаре сохранена при перевороте
//    кнопка verify isShown
//    проверить с емейл,но без пароля-комбинации
//    переворот calendarActivity
//    setError
//    toast calendarActivity
}
