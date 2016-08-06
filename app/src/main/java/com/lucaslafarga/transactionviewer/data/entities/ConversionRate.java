package com.lucaslafarga.transactionviewer.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class ConversionRate {
    @SerializedName("from")
    @Expose
    public String from;
    @SerializedName("rate")
    @Expose
    public BigDecimal rate;
    @SerializedName("to")
    @Expose
    public String to;
}
