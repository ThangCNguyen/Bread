package edu.sjsu.android.bread;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginPageInstrumentedTest.class,
        HomePageInstrumentedTest.class,
        BudgetPageInstrumentedTest.class,
        TransactionPageInstrumentedTest.class
        })
public class BreadTestSuite { }
