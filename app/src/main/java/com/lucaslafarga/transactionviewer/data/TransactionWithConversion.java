package com.lucaslafarga.transactionviewer.data;

import com.lucaslafarga.transactionviewer.data.entities.Transaction;

import java.math.BigDecimal;

public class TransactionWithConversion {
    public BigDecimal amount;
    public String sku;
    public String currency;
    public BigDecimal conversion;

    public TransactionWithConversion(Transaction transaction, BigDecimal conversion) {
        this.amount = transaction.amount;
        this.sku = transaction.sku;
        this.currency = transaction.currency;
        this.conversion = conversion;
    }
}
