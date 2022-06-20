package edu.sjsu.android.bread;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionPageInstrumentedTest {

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(TransactionPageActivity.class);

    @Test
    public void transactionLaunch() {
        //check if all icons in activity are being Displayed
        onView(withId(R.id.transaction)).check(matches(isDisplayed()));
        onView(withId(R.id.transactionBackArrow)).check(matches(isDisplayed()));
        onView(withId(R.id.transactionCornerBread)).check(matches(isDisplayed()));
        onView(withId(R.id.et_purchaseLabel)).check(matches(isDisplayed()));
        onView(withId(R.id.et_cost)).check(matches(isDisplayed()));
        onView(withId(R.id.et_date)).check(matches(isDisplayed()));
        onView(withId(R.id.et_category)).check(matches(isDisplayed()));
        onView(withId(R.id.transactionAddButton)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView4)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView5)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView7)).check(matches(isDisplayed()));
        onView(withId(R.id.imageView8)).check(matches(isDisplayed()));
    }

    public void addTransaction()
    {
        onView(withId(R.id.et_purchaseLabel)).perform(replaceText("Test"));
        onView(withId(R.id.et_cost)).perform(replaceText("30"));
        onView(withId(R.id.et_date)).perform(replaceText("04202022"));
        onView(withId(R.id.et_category)).perform(replaceText("test"));
        onView(withId(R.id.transactionAddButton)).perform(click());
    }

}
