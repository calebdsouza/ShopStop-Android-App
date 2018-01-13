package com.b07.storeapplication.View;

/**
 * Created by cd on 2017-12-03.
 */

public class SalesHistoryItem {
    private String customer;
    private String purchaseNumber;
    private String totalPurchasePrice;
    private String itemizedBreakdown;

    public SalesHistoryItem(String customer, String purchaseNum, String totPrice,
                            String itemized) {
        this.customer = customer;
        this.purchaseNumber = purchaseNum;
        this.totalPurchasePrice = totPrice;
        this.itemizedBreakdown = itemized;
    }

    public String getCustomer() {
        return this.customer;
    }

    public String getPurchaseNumber() {
        return this.purchaseNumber;
    }

    public String getTotalPurchasePrice() {
        return this.totalPurchasePrice;
    }

    public String getItemizedBreakdown() {
        return this.itemizedBreakdown;
    }
}
