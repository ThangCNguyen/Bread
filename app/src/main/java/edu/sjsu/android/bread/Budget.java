
package edu.sjsu.android.bread;

public class Budget {
    private String bName;
    private String dateCreated;
    private int numOfDays;
    private double spendingLimit;
    private String endDate;

    public Budget(String bn, int numOfDays, double spendingLimit) {
        this.bName = bn;
        this.numOfDays = numOfDays;
        this.spendingLimit = spendingLimit;
    }

    public Budget(String bn, String dateCreated, int numOfDays, double spendingLimit) {
        this.bName = bn;
        this.dateCreated = dateCreated;
        this.numOfDays = numOfDays;
        this.spendingLimit = spendingLimit;
    }

    public String getBudgetName()
    {return this.bName;}
    public int getNumOfDays() { return this.numOfDays; }
    public double getSpendingLimit() {
        return this.spendingLimit;
    }
    public void setBudgetName(String bn){this.bName = bn;}
    public void setNumOfDays(int days) {
        this.numOfDays = days;
    }
    public void setSpendingLimit(double amountSpent) {
        double oldTotal = this.spendingLimit;
        this.spendingLimit = oldTotal - amountSpent;
    }

    public String getDateCreated() {
        return this.dateCreated;
    }

    public void setEndDate(String date) {
        this.endDate = date;
    }

    public boolean isAlmostTime(int days) {
        return false; //Subject to modify
    }

    public boolean isAlmostBudget(int spendingLimit) {
        return false; //Subject to modify
    }

    public void checkSpendingLimit(int amount) {
        this.spendingLimit += amount;
    }

}

