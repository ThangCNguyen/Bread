package edu.sjsu.android.bread;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewTransactionActivity extends AppCompatActivity {
    private static double transactionTotal;
    RecyclerView transactionsRV;
    ActivityResultLauncher<Intent> launcher;
    Button addButton;

    ImageView backArrow;
    ImageView delTrans;
    //data structure to store and display transactions with shared preferences
    private TransactionAdapter transactionAdapter;
    private ArrayList<Transaction> transactions;
//    double transactionTotal;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_viewtrans);
        transactionsRV = findViewById(R.id.Transactions);

        //Implemented to load existing transactions
        loadTransactions();
        //Implemented to create recycler views
        buildRecyclerView();

        backArrow = (ImageView) findViewById(R.id.viewTranBackArrow);
        delTrans = (ImageView) findViewById(R.id.trashTransaction);
        addButton = (Button) findViewById(R.id.addTransaction);

        backArrow.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(ViewTransactionActivity.this, HomePageActivity.class);
                intent.putExtra("Total", transactionTotal);
                startActivity(intent);
            }
        });

        delTrans.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(transactions.size() == 0){
//                    showToast("No Transactions");
                }
                else {
                    transactions.remove(transactions.size() - 1);
                    transactionAdapter.notifyItemRemoved(transactions.size());
                    saveTransactions();
//                    showToast("Transactions Removed");
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Add Transaction clicked");
                Intent intent = new Intent(ViewTransactionActivity.this, TransactionPageActivity.class);
                launcher.launch(intent);
            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result){
                if(result.getData() != null && result.getResultCode() == 2) {
                    String purchase_name = result.getData().getStringExtra("PURCHASELABEL");
                    String cost = result.getData().getStringExtra("COST");
                    String category = result.getData().getStringExtra("CATEGORY");
                    String date = result.getData().getStringExtra("DATE");
                    //create new transaction with returned results and add to arraylist, then save
                    Transaction transaction = new Transaction(purchase_name, Double.parseDouble(cost), date, category);
                    transactions.add(transaction);
                    transactionAdapter.notifyItemInserted(transactions.size());
                    saveTransactions();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Home Page", Toast.LENGTH_SHORT).show();
                }
            }
        });
        computeTransTotal();
    }
    private void buildRecyclerView() {
        transactionAdapter = new TransactionAdapter(transactions, ViewTransactionActivity.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        transactionsRV.setHasFixedSize(false);
        // set layout presentation of transactions and adapter
        transactionsRV.setLayoutManager(linearLayoutManager);
        transactionsRV.setAdapter(transactionAdapter);
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

    //saves data(transcations) stored in sharedpreferences
    private void saveTransactions() {
        SharedPreferences sP = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sP.edit();
        Gson gson = new Gson();
        String json = gson.toJson(transactions);
        editor.putString("transactions",json);
        editor.apply();
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

    public static double getTransactionTotal(){
        return transactionTotal;
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
