package edu.sjsu.android.bread;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private ArrayList<Budget> budgetList;
    private Context context;

    public BudgetAdapter(ArrayList<Budget>bL, Context c) {
        this.budgetList = bL;
        this.context = c;
    }

    @NonNull
    @Override
    public BudgetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.budget_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Budget budget = budgetList.get(position);
        holder.budgetName.setText(budget.getBudgetName());
        int num_of_days = Integer.valueOf(budget.getNumOfDays());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Using today's date
        c.add(Calendar.DATE, num_of_days);
        String date_output = sdf.format(c.getTime());
        budget.setEndDate(date_output);
        holder.numDays.setText("Ends on " + date_output);
        double budgetAfterSpending = calculateBudget(budget.getSpendingLimit());
        holder.spendLimit.setText("$" + String.format("%.2f", budgetAfterSpending) + " remaining");

    }


    @Override
    public int getItemCount()
    {
        return budgetList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView budgetName, numDays, spendLimit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            budgetName = itemView.findViewById(R.id.id_budgetName);
            numDays = itemView.findViewById(R.id.id_numDays);
            spendLimit = itemView.findViewById(R.id.id_spendLimit);
        }
    }
    public double calculateBudget(double budget){
        return budget - ViewTransactionActivity.getTransactionTotal();
    }

    public ArrayList<Budget> getBudgetList() {
        return this.budgetList;
    }

}


