package edu.sjsu.android.bread;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    RecyclerView budgetRV;
    private static double transactionTotal;
    ActivityResultLauncher<Intent> launcher;
    private FloatingActionMenu fam;
    private FloatingActionButton fabLogout, fabDelete, fabAdd, fabViewTrans, fabNotificationSetting;
    private ArrayList<Transaction> transactions;
    private ImageView resetBudget;
    private ImageView bell;

    //data structure to store and display budgets with shared preferences
    private BudgetAdapter budgetAdapter;
    private ArrayList<Budget> budgets;
    private final static int LAUNCHCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        budgetRV = findViewById(R.id.Budgets);
        resetBudget = findViewById(R.id.reloadImage);
        bell = findViewById(R.id.bellImage);

        resetBudget.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                resetBudgets();
            }
        });

        bell.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                showToast("Notification Setting clicked");
                Intent intent = new Intent(HomePageActivity.this, NotificationSettingActivity.class);
                startActivity(intent);
            }
        });

        // load existing budgets
        loadBudgets();

        loadTransactions();

        // create Recycler Views
        buildRecyclerView();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result){
                        if(result.getData() != null && result.getResultCode() == 1) {
                            //add onActivityResult code here
                            String budget_name = result.getData().getStringExtra("NAME");
                            String num_days = result.getData().getStringExtra("NUMDAYS");
                            String spend_limit = result.getData().getStringExtra("LIMIT");
                            //create new budget with returned results and add to arraylist, then save
                            Budget budget = new Budget(budget_name, Integer.parseInt(num_days), Double.parseDouble(spend_limit));
                            if (budgets.size() < 1) {
                                budgets.add(budget);
                                budgetAdapter.notifyItemInserted(budgets.size());
                                saveBudget();
                                showToast("Budget Added");
                            }
                            else {
                                showToast("A budget already exists! Budget was not added.");
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();
                        }
                    }
        });
        fabAdd = findViewById(R.id.fabAddBudget);
        fabDelete = findViewById(R.id.fabDeleteBudget);
        fabLogout = findViewById(R.id.fabLogout);
        fabViewTrans = findViewById(R.id.fabViewTransaction);
        fam = findViewById(R.id.fab_menu);

        // handling each floating action button clicked
        fabDelete.setOnClickListener(onButtonClick());
        fabLogout.setOnClickListener(onButtonClick());
        fabAdd.setOnClickListener(onButtonClick());
        fabViewTrans.setOnClickListener(onButtonClick());

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });
    }

    private void buildRecyclerView() {
        /*
        if (!transactions.isEmpty()) {
            computeTransTotal();
        }

        for (int i = 0; i < budgets.size(); i++) {
            budgets.get(i).setSpendingLimit(transactionTotal);
            saveBudget();
        }*/

        budgetAdapter = new BudgetAdapter(budgets, HomePageActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        budgetRV.setHasFixedSize(false);
        // set layout presentation of budgets and adapter
        budgetRV.setLayoutManager(linearLayoutManager);
        budgetRV.setAdapter(budgetAdapter);
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

    //saves data(budgets) stored in sharedpreferences
    private void saveBudget() {
        SharedPreferences sP = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(budgets);
        editor.putString("budgets",json);
        editor.apply();
        showToast("Budget saved");
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabAdd) {
                    showToast("Add Budget clicked");
                    //Create an intent to go to the Budget Page
                    Intent intent = new Intent(HomePageActivity.this, BudgetPageActivity.class);
                    launcher.launch(intent);
                } else if (view == fabDelete) {
                    showToast("Delete Budget clicked");
                    if(budgets.size() == 0){
                        showToast("No Budgets");
                    }
                    else {
                        budgets.remove(budgets.size() - 1);
                        budgetAdapter.notifyItemRemoved(budgets.size());
                        saveBudget();
                        showToast("Budget Removed");
                    }
                } else if (view == fabLogout) {
                    // Exit the system
                    Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    showToast("Logout clicked");
                }
                else if (view == fabViewTrans) {
                    showToast("View Transaction clicked");
                    Intent intent = new Intent(HomePageActivity.this, ViewTransactionActivity.class);
//                    launcher.launch(intent);
                    startActivity(intent);
                }
                fam.close(true);
            }
        };
    }

    //loads data(transactions) stored in sharedpreferences
    private void loadTransactions() {
        SharedPreferences sP = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();

        String json = sP.getString("transactions",null);
        //get type of arraylist (Transaction)

        Type type = new TypeToken<ArrayList<Transaction>>() {}.getType();
        //load data from gson to json
        transactions = gson.fromJson(json,type);

        if(transactions == null) {
            transactions = new ArrayList<>();
        }

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void computeTransTotal() {
        if (transactions == null) {
            transactionTotal = 0;
        }
        else {
            transactionTotal = 0;
            for (int i = 0; i < transactions.size(); i++) {
                transactionTotal += transactions.get(i).getCost();
            }
        }
    }

    public void resetBudgets(){
        if(budgets.size() == 0){
            showToast("No Budgets");
        }
        else {
            budgets.remove(budgets.size() - 1);
            budgetAdapter.notifyItemRemoved(budgets.size());
            saveBudget();
            showToast("Budget Removed");
        }
        Intent intent = new Intent(HomePageActivity.this, BudgetPageActivity.class);
        launcher.launch(intent);
    }
}
