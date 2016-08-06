package com.lucaslafarga.transactionviewer.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.lucaslafarga.transactionviewer.R;
import com.lucaslafarga.transactionviewer.adapters.ProductAdapter;
import com.lucaslafarga.transactionviewer.repository.TransactionRepository;
import com.lucaslafarga.transactionviewer.widgets.DividerItemDecorator;

public class MainActivity extends AppCompatActivity implements ProductAdapter.MainActivityInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TransactionRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(getString(R.string.products));

        repository = new TransactionRepository(getApplicationContext());
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecorator(this));

        mAdapter = new ProductAdapter(this, repository.getSkuOccurrences());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void itemClicked(View view, String product) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_KEY, product);
        intent.putParcelableArrayListExtra(DetailActivity.TRANSACTIONS_KEY, repository.getTransactionsForProduct(product));

        startActivity(intent);
    }
}