package edu.sjsu.android.bread;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginPageInstrumentedTest {
    //launches login activity for test methods to utilize
    @Rule
    public ActivityScenarioRule<LoginActivity> rule = new ActivityScenarioRule(LoginActivity.class);

    //test 1
    //creating/launching login activity inside function
    //verifies login activity is launched and displaying all members

    @Test
    public void loginLaunch() {
        //check if all icons in activity are being Displayed
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.breadicon)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.intro)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_lusername)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_lpassword)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_llogin)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).check(matches(isDisplayed()));
    }
    @Test
    public void lregisterLaunch(){
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).perform(click());
        onView(ViewMatchers.withId(R.id.register)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.breadicon)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.intro)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_username)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_password)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_register)).check(matches(isDisplayed()));
    }


    //test 2
    //test register functionality
    //Goes from Login Page -> Register -> fill with test credentials -> register -> back to Login
    @Test
    public void testRegisterButton() {
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).perform(click());
        onView(ViewMatchers.withId(R.id.et_username)).perform(replaceText("test"));
        onView(ViewMatchers.withId(R.id.et_password)).perform(replaceText("Test1234+"));
        onView(ViewMatchers.withId(R.id.et_cpassword)).perform(replaceText("Test1234+"));
        onView(ViewMatchers.withId(R.id.btn_register)).perform(click());
        onView(ViewMatchers.withId(R.id.btn_login)).perform(click());
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
    }
    //test 3
    //test register back to login functionality
    //Goes from Login Page -> Register -> back to Login
    @Test
    public void testRegisterToLogin(){
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_lregister)).perform(click());
        onView(ViewMatchers.withId(R.id.register)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.btn_login)).perform(click());
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
    }

    //test 4
    //test login functionality
    //Goes from login -> fill in registered test credentials -> verifies login and on homepage
    @Test
    public void testtLoginButton() {
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_lusername)).perform(replaceText("test"));
        onView(ViewMatchers.withId(R.id.et_lpassword)).perform(replaceText("Test1234+"));
        onView(ViewMatchers.withId(R.id.btn_llogin)).perform(click());
        onView(ViewMatchers.withId(R.id.home)).check(matches(isDisplayed()));
    }

    @Test
    //test 5:
    //Login -> fab_menu -> Logout -> Login screen
    public void testtLogoutFabButton() {
        onView(ViewMatchers.withId(R.id.login)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.et_lusername)).perform(replaceText("test"));
        onView(ViewMatchers.withId(R.id.et_lpassword)).perform(replaceText("Test1234+"));
        onView(ViewMatchers.withId(R.id.btn_llogin)).perform(click());
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")))).perform(click());
        onView(allOf(withId(R.id.fabLogout))).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }
}
