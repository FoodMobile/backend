package com.foodmobile.server.datamodels;

import java.util.Calendar;
import java.util.Date;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class Transaction extends Entity {
    public static Transaction usdNow(double amount, Company business, User customer) {
        int amountInt = (int)Math.round(amount*100);
        int primary = amountInt/100;
        int fractional = amountInt%100;
        return usdNow(primary, fractional, business, customer);
    }

    public static Transaction usdNow(int primaryCurrencyAmount, int fractionalCurrencyAmount, Company business, User customer) {
        var trans = new Transaction();
        var now = Calendar.getInstance();
        trans.business = business;
        trans.customer = customer;
        trans.primaryCurrencyAmount = primaryCurrencyAmount;
        trans.fractionalCurrencyAmount = fractionalCurrencyAmount;
        trans.currencyCode = "USD";
        trans.transactionDate = String.format("%d-%02d-%02d", now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
        trans.transactionTime = String.format("%02d:%02d", now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
        return trans;
    }
    
    public String guid;

    public String currencyCode;

    public String transactionDate;

    public String transactionTime;

    public int primaryCurrencyAmount;

    public int fractionalCurrencyAmount;

    public Company business;

    public User customer;
}