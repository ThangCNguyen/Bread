package edu.sjsu.android.bread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private ArrayList<Transaction> transactionArrayList;
    private Context context;

    public TransactionAdapter(ArrayList<Transaction> theList, Context c) {
        this.transactionArrayList = theList;
        this.context = c;
    }

    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Transaction transaction = transactionArrayList.get(position);
        holder.purchaseLabel.setText(transaction.getPurchaseLabel());
        holder.cost.setText("$" + Double.toString(transaction.getCost()));
        holder.date.setText("Date: " + transaction.getPurchaseDate());
        holder.category.setText("Category: " + transaction.getCategory());
    }


    @Override
    public int getItemCount()
    {
        return transactionArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView purchaseLabel, date, cost, category;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseLabel = itemView.findViewById(R.id.id_purchaseLabel);
            cost = itemView.findViewById(R.id.id_cost);
            date = itemView.findViewById(R.id.id_date);
            category = itemView.findViewById(R.id.id_category);
        }
    }
}