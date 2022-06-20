package edu.sjsu.android.bread;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BudgetPageActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    EditText budget_name, num_Days, budget_limit;
    Button budget_add;
    ImageView backArrowImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgetpage);

        dbh = new DatabaseHelper(this);

        budget_name = (EditText) findViewById(R.id.et_budgetname);
        num_Days = (EditText) findViewById(R.id.et_numDays);
        budget_limit = (EditText) findViewById(R.id.et_budgetlimit);

        budget_add = (Button) findViewById(R.id.btn_add);
        backArrowImage = (ImageView) findViewById(R.id.backArrow);
        backArrowImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(BudgetPageActivity.this, HomePageActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "A budget was not made!", Toast.LENGTH_SHORT).show();
            }
        });

        //verify that credentials are valid, if yes, upload to database with db helper,
        //not, use toast and ping user
        budget_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budgetName = budget_name.getText().toString();
                String numOfDays = num_Days.getText().toString();
                String budgetLimit = budget_limit.getText().toString();

                Intent intent = new Intent(BudgetPageActivity.this, HomePageActivity.class);
                //store data back to homepage for budget add
                intent.putExtra("NAME",budgetName);
                intent.putExtra("NUMDAYS",numOfDays);
                intent.putExtra("LIMIT", budgetLimit);

                //intent.putExtras(bundle);
                //sets
                setResult(1, intent);

                // Build an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(BudgetPageActivity.this);

                // Set a title for alert dialog
                builder.setTitle("Select your answer.");

                // Ask the final question
                builder.setMessage("Are you sure the budget is correct?");

                // Set the alert dialog yes button click listener
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish activity add form and return control to the homepage
                        finish();
                    }
                });

                // Set the alert dialog no button click listener
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(),
                                "Please edit the budget and try again.",Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog dialog = builder.create();
                // Display the alert dialog on interface
                dialog.show();
            }
        });
    }

}
