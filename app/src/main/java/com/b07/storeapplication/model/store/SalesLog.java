package com.b07.storeapplication.model.store;

import java.util.List;

public interface SalesLog {

    /**
     * Adds the Sale to a SalesList.
     *
     * @param sale the Sale Object to be added.
     */
    public void addSale(Sale sale);

    /**
     * Get the Total Sale List.
     *
     * @return List of Sales showing all Sales.
     */
    public List<Sale> getSalesList();

    /**
     * Set the Sales List to a new value of Sales.
     *
     * @param salesList A list of Sales.
     */
    public void setSalesList(List<Sale> salesList);

}