package com.b07.storeapplication.model.store;

public interface ItemizedSale {

    /**
     * returns the Id of the Sale associated to the ItemizedSale.
     * @return number of the Sale Id.
     */
    public int getSaleId();

    /**
     * returns the ItemId of the associated Item to the ItemizedSale.
     * @return an int representing the ItemId associated to the ItemizedSale.
     */
    public int getItemId();

    /**
     * gets the quantity of the ItemizedSale sold.
     * @return an int representing the numbers of the specific Item that was sold.
     */
    public int getItemQuantity();

}