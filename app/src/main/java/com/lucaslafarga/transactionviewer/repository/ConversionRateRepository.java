package com.lucaslafarga.transactionviewer.repository;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lucaslafarga.transactionviewer.data.entities.ConversionRate;
import com.lucaslafarga.transactionviewer.data.entities.Transaction;

import android.content.Context;
import android.util.Log;

public class ConversionRateRepository {
    private Context mContext;
    private List<ConversionRate> rates;

    public ConversionRateRepository(Context mContext) {
        this.mContext = mContext;
        load();
    }

    private void load() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<ConversionRate>>() {}.getType();
        rates = gson.fromJson(loadJSONFromAsset("rates1.json"), listType);

        if (rates.isEmpty())
            throw new RuntimeException("Rates file is empty");
    }

    private String loadJSONFromAsset(String file) {
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

    public List<ConversionRate> getRates() {
        return rates;
    }
}
