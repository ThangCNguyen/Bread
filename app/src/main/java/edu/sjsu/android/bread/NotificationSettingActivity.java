package edu.sjsu.android.bread;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import java.time.format.DateTimeFormatter;
import java.time.*;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

// notification setting page that controls all of the notification
// user set what notification they want to display
public class NotificationSettingActivity extends AppCompatActivity {

    BudgetAdapter bAdapter;
    Switch lowBudget, budgetEnds;
    ImageView backArrowImage;

    NotificationCompat.Builder builder;
    private ArrayList<Budget> budgets;
    private ArrayList<Boolean> budgetsLowTriggerList;
    DateTimeFormatter dtf;
    String dummyDate;
    String today;
    //private ArrayList<NotificationData> notificationList;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationsetting);

        lowBudget = findViewById(R.id.lowbudgetswitch);
        budgetEnds = findViewById(R.id.budgetEndSwitch);
        backArrowImage = (ImageView) findViewById(R.id.backArrow);

        //sharedPreferences = this.getSharedPreferences("save", 0);
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        lowBudget.setChecked(sharedPreferences.getBoolean("value", false));
        budgetEnds.setChecked(sharedPreferences.getBoolean("value2", false));
        //editor = sharedPreferences.edit();

        loadBudgets();

        dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        dummyDate = "05/06/2022";
        today = dtf.format(LocalDateTime.now());
        //Intent for user to go back to mainpage after clicking on notification
        Intent mainPageIntent = new Intent(NotificationSettingActivity.this, HomePageActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(mainPageIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(NotificationSettingActivity.this, "My Notification");

        backArrowImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(NotificationSettingActivity.this, HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Back to Home Page!", Toast.LENGTH_SHORT).show();
            }
        });

        lowBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lowBudget.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    lowBudget.setChecked(true);
                    //store the budgets that has low spending limit
                    budgetsLowTriggerList = checkBudget();

                    for(int i = 0; i < budgetsLowTriggerList.size(); i++) {
                        if(budgetsLowTriggerList.get(i) == true) {
                            String budgetName = budgets.get(i).getBudgetName();
                            builder.setContentTitle("Bread's Notification");
                            builder.setContentText(budgetName + " is under $50!");
                            builder.setSmallIcon(R.drawable.breadicon);
                            builder.setAutoCancel(true);
                            builder.setContentIntent(resultPendingIntent);
                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationSettingActivity.this);
                            managerCompat.notify((int) System.currentTimeMillis(), builder.build());

                            String catchPhrase = "Get to baking!";
                            double getSpendingLimit = budgets.get(i).getSpendingLimit();

                        }
                    }
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    lowBudget.setChecked(false);
                }
            }
        });

        budgetEnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(budgetEnds.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("value2", true);
                    editor.apply();
                    budgetEnds.setChecked(true);
                    //budgetsEndTriggerList = checkBudgetDate();
                    String budgetName = budgets.get(0).getBudgetName();

                    if(checkBudgetDate() == true) {
                        builder.setContentTitle("Bread's Notification");
                        builder.setContentText(budgetName + " budget is about to end in 5 days or less!");
                        builder.setSmallIcon(R.drawable.breadicon);
                        builder.setAutoCancel(true);

                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationSettingActivity.this);
                        managerCompat.notify((int) System.currentTimeMillis(), builder.build());
                    }
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("value2", false);
                    editor.apply();
                    budgetEnds.setChecked(false);
                }
            }
        });
    }

    //loads data(budgets) stored in sharedpreferences
    private void loadBudgets() {
        SharedPreferences sP = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sP.getString("budgets",null);
        //get type of arraylist (Budget)

        Type type = new TypeToken<ArrayList<Budget>>() {}.getType();
        //load data from gson to json
        budgets = gson.fromJson(json,type);

        if(budgets == null) {
            budgets = new ArrayList<>();
        }

    }

    //check budget's spending limit that falls below $50
    private ArrayList<Boolean> checkBudget() {
        ArrayList<Boolean> budgetWarningList = new ArrayList<Boolean>();
        for(int i = 0; i < budgets.size(); i++) {
            if(budgets.get(i).getSpendingLimit() < 50) {
                budgetWarningList.add(true);
            }
            else {
                budgetWarningList.add(false);
            }
        }
        return budgetWarningList;
    }

    //method to get the days
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkDaysUntilEnds(LocalDate date1, LocalDate date2) {
        //LocalDate date1 = LocalDate.parse(budgets.get(0).getDateCreated(), dtf);
        long numDays = ChronoUnit.DAYS.between(date1, date2);
        long numDays2 = ChronoUnit.DAYS.between(date1, date2) * -1;

        if(numDays <= 5 || numDays2 <= 5) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean checkBudgetDate() {
        String date = budgets.get(0).getDateCreated();
        LocalDate dateCreated = LocalDate.parse(date, dtf);
        LocalDate todayDate = LocalDate.parse(today, dtf);
        if(checkDaysUntilEnds(todayDate, dateCreated) == true){
            return true;
        }
        return false;
    }

}