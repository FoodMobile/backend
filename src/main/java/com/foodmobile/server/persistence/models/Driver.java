package com.foodmobile.server.persistence.models;

import org.bson.Document;

public class Driver extends User {
    private FinancialInfo financialInfo;

    public Driver() {
        super();
    }

    public Driver(Document doc) {
        super(doc);
    }
}
