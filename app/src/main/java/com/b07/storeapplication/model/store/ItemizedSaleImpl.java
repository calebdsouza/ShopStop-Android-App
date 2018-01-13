package com.b07.storeapplication.model.store;

import java.io.Serializable;

public class ItemizedSaleImpl implements ItemizedSale, Serializable {
    private int saleId;
    private int itemId;
    private int quantity;

    public ItemizedSaleImpl(int saleId, int itemId, int quantity) {
        this.saleId = saleId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    /**
     * returns the Id of the Sale associated to the ItemizedSale.
     *
     * @return number of the Sale Id.
     */
    public int getSaleId() {
        return this.saleId;
    }

    /**
     * returns the ItemId of the associated Item to the ItemizedSale.
     *
     * @return an int representing the ItemId associated to the ItemizedSale.
     */
    public int getItemId() {
        return this.itemId;
    }

    /**
     * gets the quantity of the ItemizedSale sold.
     *
     * @return an int representing the numbers of the specific Item that was sold.
     */
    public int getItemQuantity() {
        return this.quantity;
    }
}