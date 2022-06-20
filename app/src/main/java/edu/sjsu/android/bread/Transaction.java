package edu.sjsu.android.bread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private int id;
    private String purchaseLabel;
    private double cost;
    private String purchaseDate;
    private String category;
    private String notes;

    public Transaction(String purchaseLabel, double cost,  String purchaseDate, String category) {
        this.purchaseLabel = purchaseLabel;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.category = category;
    }

//    public int getId() { return this.id; }
    public String getPurchaseLabel() { return this.purchaseLabel; }
    public double getCost() { return this.cost; }
    public String getPurchaseDate() {
//        String pattern = "MM/dd/yyyy HH:mm:ss";
//        DateFormat df = new SimpleDateFormat(pattern);
//        Date today = Calendar.getInstance().getTime();
//        String todayAsString = df.format(today);
//
//        return todayAsString;
        return this.purchaseDate;
    }
    public String getCategory() { return this.category; }
//    public String getNotes() { return this.notes; }
}
