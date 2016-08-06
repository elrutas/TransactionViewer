package com.lucaslafarga.transactionviewer.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.lucaslafarga.transactionviewer.R;
import com.lucaslafarga.transactionviewer.adapters.TransactionAdapter;
import com.lucaslafarga.transactionviewer.data.entities.Transaction;
import com.lucaslafarga.transactionviewer.data.TransactionWithConversion;
import com.lucaslafarga.transactionviewer.graph.ConversionGraph;
import com.lucaslafarga.transactionviewer.repository.ConversionRateRepository;
import com.lucaslafarga.transactionviewer.utils.Constants;
import com.lucaslafarga.transactionviewer.widgets.DividerItemDecorator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getName();

    public static final String PRODUCT_KEY = "product";
    public static final String TRANSACTIONS_KEY = "transactions";

    private ConversionGraph conversionGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_detail);

        TextView totalAmountView = (TextView) findViewById(R.id.total_amount);

        Bundle extras = getIntent().getExtras();
        String product = extras.getString(PRODUCT_KEY);
        ArrayList<Transaction> transactions = extras.getParcelableArrayList(TRANSACTIONS_KEY);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.product_transactions, product));

        ConversionRateRepository repository = new ConversionRateRepository(this);
        conversionGraph = new ConversionGraph(repository.getRates());

        List<TransactionWithConversion> convertedTransactions = convertTransactions(transactions, Constants.GBP);

        setupRecyclerView(convertedTransactions);

        totalAmountView.setText(getString(R.string.total_amount,
                calculateTotal(convertedTransactions).setScale(2, RoundingMode.UP).toPlainString()));
    }

    private List<TransactionWithConversion> convertTransactions(List<Transaction> transactions, String currency) {
        ArrayList<TransactionWithConversion> convertedTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.currency.equalsIgnoreCase(currency)) {
                // No need to convert
                convertedTransactions.add(new TransactionWithConversion(transaction, transaction.amount));
            } else {
                BigDecimal convertedAmount = transaction.amount.multiply(conversionGraph.getRate(transaction.currency, currency));
                convertedTransactions.add(new TransactionWithConversion(transaction, convertedAmount));
            }
        }

        return convertedTransactions;
    }

    private BigDecimal calculateTotal(List<TransactionWithConversion> transactions) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TransactionWithConversion transaction : transactions) {
            totalAmount = totalAmount.add(transaction.conversion);
        }
        return totalAmount;
    }

    private void setupRecyclerView(List<TransactionWithConversion> convertedTransactions) {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecorator(this));

        TransactionAdapter mAdapter = new TransactionAdapter(convertedTransactions);
        mRecyclerView.setAdapter(mAdapter);
    }
}
