package com.lucaslafarga.transactionviewer.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucaslafarga.transactionviewer.data.entities.Transaction;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TransactionRepository {
    private Context mContext;
    private List<Transaction> transactions;

    public TransactionRepository(Context mContext) {
        this.mContext = mContext;
        load();
    }

    private void load() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Transaction>>() {}.getType();
        transactions = gson.fromJson(loadJSONFromAsset("transactions1.json"), listType);

        if (transactions.isEmpty())
            throw new RuntimeException("Transactions file is empty");
    }

    public String loadJSONFromAsset(String file) {
        String json = null;

        try {
            InputStream is = mContext.getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public Map<String, Integer> getSkuOccurrences() {
        Map<String, Integer> occurrenceMap = new TreeMap<String, Integer>();

        for (Transaction transaction : transactions) {
            Integer numOccurrence = occurrenceMap.get(transaction.sku);
            if (numOccurrence == null) {
                // first count
                occurrenceMap.put(transaction.sku, 1);
            } else {
                occurrenceMap.put(transaction.sku, numOccurrence + 1);
            }
        }

        return occurrenceMap;
    }

    public ArrayList<Transaction> getTransactionsForProduct(String product) {
        ArrayList<Transaction> results = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.sku.equalsIgnoreCase(product)) {
                results.add(transaction);
            }
        }

        return results;
    }
}
