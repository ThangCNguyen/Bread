package edu.sjsu.android.bread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BudgetManager extends AppCompatActivity {


    private int bld;
    private String url;
    private UserAccount user;

    public BudgetManager(int bld, String url, UserAccount user) {
        this.bld = bld;
        this.url = url;
        this.user = user;
    }

    private boolean addTransaction() {
        return false; //Modify
    }

    private boolean verifyTransaction() {
        return false; //Modify
    }

    private boolean deleteTransaction() {
        return false; //Modify
    }

    private boolean editTransaction() {
        return false; //Modify
    }

    /*private Transaction findTransaction(int id) {
        return 0; //Modify
    }*/

    private boolean resetBudget() {
        return false; //Modify
    }

    private boolean sendTimeReminder() {
        return false; //Modify
    }

    private boolean sendBudgetReminder() {
        return false; //Modify
    }

}
