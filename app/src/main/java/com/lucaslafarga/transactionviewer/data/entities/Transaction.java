package com.lucaslafarga.transactionviewer.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Transaction implements Parcelable {
    @SerializedName("amount")
    @Expose
    public BigDecimal amount;
    @SerializedName("sku")
    @Expose
    public String sku;
    @SerializedName("currency")
    @Expose
    public String currency;

    protected Transaction(Parcel in) {
        amount = (BigDecimal) in.readValue(BigDecimal.class.getClassLoader());
        sku = in.readString();
        currency = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(amount);
        dest.writeString(sku);
        dest.writeString(currency);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
