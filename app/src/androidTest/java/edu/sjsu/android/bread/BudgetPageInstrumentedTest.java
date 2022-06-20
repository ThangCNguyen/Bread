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
import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
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
public class BudgetPageInstrumentedTest {
    @Rule public ActivityScenarioRule<BudgetPageActivity> rule = new ActivityScenarioRule<>(BudgetPageActivity.class);

    //confirms budget elements are displayed
    @Test
    public void budgetLaunch()
    {
        onView(withId(R.id.budget)).check(matches(isDisplayed()));
        onView(withId(R.id.et_budgetname)).check(matches(isDisplayed()));
        onView(withId(R.id.et_numDays)).check(matches(isDisplayed()));
        onView(withId(R.id.et_budgetlimit)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_add)).check(matches(isDisplayed()));
        onView(allOf(withParent(withId(R.id.backArrow)), withClassName(endsWith("ImageView")),isDisplayed()));
    }

    @Test
    public void budgetToHome()
    {
        budgetLaunch();
        onView(allOf(withId(R.id.backArrow))).perform(click());

    }


}
