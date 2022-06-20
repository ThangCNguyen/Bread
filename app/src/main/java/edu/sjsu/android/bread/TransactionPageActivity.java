package edu.sjsu.android.bread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionPageActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    EditText enterPurchaseLabel, enterDate, enterCost, enterCategory;
    Button transaction_add;
    ImageView backArrowImage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        dbh = new DatabaseHelper(this);

        enterPurchaseLabel = (EditText) findViewById(R.id.et_purchaseLabel);
        enterDate = (EditText) findViewById(R.id.et_date);
        enterCost = (EditText) findViewById(R.id.et_cost);
        enterCategory = (EditText) findViewById(R.id.et_category);

        transaction_add = (Button) findViewById(R.id.transactionAddButton);
        backArrowImage = (ImageView) findViewById(R.id.transactionBackArrow);
        backArrowImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(TransactionPageActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        //verify that credentials are valid, if yes, upload to database with db helper,
        //not, use toast and ping user
        transaction_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purchaseLabel = enterPurchaseLabel.getText().toString();
                String cost = enterCost.getText().toString();
                String category = enterCategory.getText().toString();
                String date = enterDate.getText().toString();

                Intent intent = new Intent(TransactionPageActivity.this, ViewTransactionActivity.class);
                //store data back to homepage for transaction add
                intent.putExtra("PURCHASELABEL",purchaseLabel);
                intent.putExtra("COST",cost);
                intent.putExtra("CATEGORY", category);
                intent.putExtra("DATE", date);

                setResult(2, intent);
//              finish activity add form and return control to the homepage
//                Toast.makeText(getApplicationContext(), "Transaction Successfully Added", Toast.LENGTH_SHORT).show();
                finish();
//                startActivity(intent);
            }
        });
    }
}

