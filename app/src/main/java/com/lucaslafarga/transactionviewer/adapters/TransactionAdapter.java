package com.lucaslafarga.transactionviewer.adapters;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.lucaslafarga.transactionviewer.R;
import com.lucaslafarga.transactionviewer.data.TransactionWithConversion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<TransactionWithConversion> transactions = new ArrayList<>();

    public TransactionAdapter(List<TransactionWithConversion> transactions) {
        this.transactions.addAll(transactions);
    }

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TransactionWithConversion transaction = transactions.get(holder.getAdapterPosition());
        String amountOriginal = transaction.amount.setScale(2, RoundingMode.UP).toPlainString() + " " + transaction.currency;
        holder.originalCurrency.setText(amountOriginal);

        String amountInPounds = transaction.conversion.setScale(2, RoundingMode.UP).toPlainString() + " GBP";
        holder.pounds.setText(amountInPounds);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView originalCurrency;
        public TextView pounds;

        public ViewHolder(View itemView) {
            super(itemView);

            originalCurrency = (TextView) itemView.findViewById(R.id.item_title);
            pounds = (TextView) itemView.findViewById(R.id.item_subtext);
        }
    }

}
