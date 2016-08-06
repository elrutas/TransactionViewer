package com.lucaslafarga.transactionviewer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lucaslafarga.transactionviewer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<String> products = new ArrayList<>();
    private List<Integer> transactionAmounts = new ArrayList<>();
    private MainActivityInterface activityInterface;

    public interface MainActivityInterface {
        void itemClicked(View view, String product);
    }

    public ProductAdapter(MainActivityInterface activityInterface, Map<String, Integer> productMap) {
        this.activityInterface = activityInterface;

        for (Map.Entry<String, Integer> entry : productMap.entrySet()) {
            products.add(entry.getKey());
            transactionAmounts.add(entry.getValue());
        }
    }

    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
        int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String product = products.get(holder.getAdapterPosition());
        Integer count = transactionAmounts.get(holder.getAdapterPosition());

        holder.productName.setText(product);
        holder.transactionAmount.setText(holder.itemView.getContext().getString(R.string.transactions, String.valueOf(count)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityInterface.itemClicked(holder.itemView, products.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView transactionAmount;

        public ViewHolder(View itemView) {
            super(itemView);

            productName = (TextView) itemView.findViewById(R.id.item_title);
            transactionAmount = (TextView) itemView.findViewById(R.id.item_subtext);
        }
    }

}
