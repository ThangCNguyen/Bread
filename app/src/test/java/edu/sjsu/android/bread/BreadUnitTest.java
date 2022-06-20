package edu.sjsu.android.bread;

import static org.junit.Assert.*;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.service.autofill.FieldClassification;

@RunWith(AndroidJUnit4.class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BreadUnitTest {
    DatabaseHelper db;
    Budget budget;
    ArrayList<Budget> budgets;
    String budgetName = "Rent";
    int numDays = 30;
    int spendingLimit = 1000; //dollars
    BudgetAdapter ba;
    Context context;
    Transaction transaction;

    @Before
    public void create() {
        this.context = ApplicationProvider.getApplicationContext();
        //Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.db = new DatabaseHelper(context);
        //budget and budget adapter used for testing
        this.budget = new Budget(budgetName, numDays, spendingLimit);
        this.budgets = new ArrayList<Budget>();
        this.ba = new BudgetAdapter(budgets, context);
        this.transaction = new Transaction("Gas", 50, "04/20/22","Work");
    }

    @Test
    public void budgetAmountIsPositive() {
        assertFalse("Budget Amount is Negative!!!", this.budget.getSpendingLimit() < 0);
        System.out.println("Budget amount is positive!");
    }

    // budgetName.length > 3
    // 3 is an arbitrary number
    @Test
    public void budgetNameValidator() {
        assertTrue("Budget Name is Blank!",this.budget.getBudgetName().length() > 3);
        System.out.println("Budget Name Test passed!");
    }

    // budgetAmount > totalTransactions
    @Test
    public void budgetAmountAboveTransactions() {
        assertTrue("Budget amount exceeded!", this.budget.getSpendingLimit() > this.transaction.getCost());
    }

    // no duplicate usernames in database
    @Test
    public void uniqueUsername() {
        this.db.Insert("jared","Pr0grammNC");
        this.db.Insert("jared1","Pr000gram");
        String username = "jared";
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT username FROM user WHERE username=?", new String[]{username});
        assertFalse("Duplicate Username", cursor.getCount() > 1);
        System.out.println("Unique Username Passed!");
        cursor.close();
        db.close();
    }

    // transaction amount is above 0
    @Test
    public void transactionAboveZero() {
        assertTrue("Transaction cannot be negative", this.transaction.getCost() > 0);
    }

    // make sure the date of the transaction is within the budget duration
    @Test
    public void transactionDateExists() {
        // since getPurchaseDate() converts the date into a string, just make sure that the string isn't empty
        // which proves that there IS a date for the transaction
        assertTrue("Every transaction must be dated", this.transaction.getPurchaseDate().length() > 0);
    }

    // make sure transaction amount is less than what's in the budget
    @Test
    public void transactionAmountLimited() {
        assertTrue("Transaction must be below the budget", this.transaction.getCost() < this.budget.getSpendingLimit());
    }

    @Test
    // testing for user credential registration
    public void RegisterUser()
    {
        //assert true that a user has been added to the database
        assertEquals(true,this.db.Insert("jar","1342"));
        System.out.println("Register case passed!");
        db.close();

    }

    @Test
    // testing for user credential registration
    public void LoginUser ()
    {

        this.db.Insert("jar","1342");
        //assert true that a user has been added to the database
        assertEquals(true, this.db.CheckLogin("jar","1342"));
        System.out.println("Login case passed!");
        db.close();

    }

    // test case for adding a budget
    // will need to rework this testcase
    // checks the reference, but does not actually update the array list as it is private
    @Test
    public void AddBudget () {
        ArrayList<Budget> blist = this.ba.getBudgetList();
        blist.clear();
        blist.add(budget);
        assertTrue("Inserted Budget not Found!", blist.contains(budget));
        System.out.println("Add Budget Test Pass!");
    }
    // test case for deleting a budget
    @Test
    public void DeleteBudget () {
        ArrayList<Budget> blist = this.ba.getBudgetList();
        blist.remove(budget);
        assertFalse("Deleted Budget Still Found!", blist.contains(budget));
        System.out.println("Delete Budget Test Pass!");
    }

    // no duplicate passwords
   @Test
    public void isUniquePassword() {
        String password = "Thang123$";
        String uppercaseRegex = "^.*[A-Z].*$";
        String lowercaseRegex = "^.*[a-z].*$";
        String specialCharacterRegex = "^.*[$&+,:;=\\\\?@#|/'<>.^*()%!-].*$";
        String numberRegex = "^.*[0-9].*$";

        if(password.length() < 8) {
            assertFalse("password too short", password.length() < 8);
        }
        else if(password.length() > 15) {
            assertFalse("password too long", password.length() > 15);
        }
        else if(password.contains(" ")) {
            assertFalse("password can't have space", password.contains(" "));
        }
        else if(!password.matches(lowercaseRegex)) {
            assertFalse("password doesn't contain lowercase letter", !password.matches(lowercaseRegex));
        }
        else if(!password.matches(uppercaseRegex)) {
            assertFalse("password doesn't contain upppercase letter", !password.matches(uppercaseRegex));
        }
        else if(!password.matches(specialCharacterRegex)) {
            assertFalse("password doesn't contain special character", !password.matches(specialCharacterRegex));
        }
        else if(!password.matches(numberRegex)) {
            assertFalse("password doesn't contain a number", !password.matches(numberRegex));
        }
    }


}
