package com.b07.storeapplication.model.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SalesLogImpl implements SalesLog, Serializable {
    private List<Sale> salesList;

    public SalesLogImpl() {
        this.salesList = new ArrayList<>();
    }

    public SalesLogImpl(List<Sale> salesList) {
        this.salesList = new ArrayList<Sale>();
    }


    /**
     * Adds the Sale to a SalesList.
     *
     * @param sale the Sale Object to be added.
     */
    @Override
    public void addSale(Sale sale) {
        this.salesList.add(sale);
    }

    /**
     * Get the Total Sale List.
     *
     * @return List of Sales showing all Sales.
     */
    @Override
    public List<Sale> getSalesList() {
        return this.salesList;
    }

    /**
     * Set the Sales List to a new value of Sales.
     *
     * @param salesList A list of Sales.
     */
    @Override
    public void setSalesList(List<Sale> salesList) {
        this.salesList = salesList;
    }
}