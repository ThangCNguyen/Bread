package edu.sjsu.android.bread;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
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
public class HomePageInstrumentedTest {
    //all tests share launched activity
    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(HomePageActivity.class);

    //test 1:
    //confirms all elements on home page are displayed
    @Test
    public void homePageLaunch()
    {
        onView(withId(R.id.home)).check(matches(isDisplayed()));
        onView(withId(R.id.Budgets)).check(matches(isDisplayed()));
        //onView(withId(R.id.currentBudget)).check(matches(isDisplayed()));
        onView(withId(R.id.fab_menu)).check(matches(isDisplayed()));
        //onView(withId(R.id.fabLogout)).check(matches(isDisplayingAtLeast(10)));

        //confirms that the buttons are in the view
        //fab menu is ViewGroup that associates each button as a child of the View Group
        //with the id: fab_menu
        onView(allOf(withParent(withId(R.id.fabLogout)), withClassName(endsWith("ImageView")),isDisplayed()));
        onView(allOf(withParent(withId(R.id.fabAddBudget)), withClassName(endsWith("ImageView")),isDisplayed()));
        onView(allOf(withParent(withId(R.id.fabDeleteBudget)), withClassName(endsWith("ImageView")),isDisplayed()));
        onView(allOf(withParent(withId(R.id.fabViewTransaction)), withClassName(endsWith("ImageView")),isDisplayed()));
    }

    //test 2:
    //add fill in test budget credentials -> add button -> confirm on home page
    //may need to fix this, technically this is process
    //homepage -> fab menu -> add budget -> budget page -> fill out credentials
    //-> add button -> budget page ends -> returns to previous activity(home page)
    @Test
    public void testAddBudgetFab() {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")))).perform(click());
        onView(allOf(withId(R.id.fabAddBudget))).perform(click());
        onView(withId(R.id.et_budgetname)).perform(replaceText("espresso"));
        onView(withId(R.id.et_numDays)).perform(replaceText("30"));
        onView(withId(R.id.et_budgetlimit)).perform(replaceText("500"));
        onView(withId(R.id.btn_add)).perform(click());
        //add budget -> home page -> display added budget
        onView(withId(R.id.home)).check(matches(isDisplayed()));
        onView(withId(R.id.Budgets)).check(matches(hasChildCount(1)));

    }

    //test 3:
    //fab_menu -> delete budget -> check if child count of recycler has been reduced by 1
    @Test
    public void testDeleteBudget()
    {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")))).perform(click());
        onView(allOf(withId(R.id.fabDeleteBudget))).perform(click());
        onView(withId(R.id.Budgets)).check(matches(hasChildCount(0)));
    }

    @Test
    public void testtViewPreviousTransaction() {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")))).perform(click());
        onView(allOf(withId(R.id.fabViewTransaction))).perform(click());
        onView(withId(R.id.viewtransaction)).check(matches(isDisplayed()));
    }

}
