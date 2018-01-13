package com.b07.storeapplication.model.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemImpl implements Item, Serializable {
    private int id;
    private String name;
    private BigDecimal price;

    /**
     * Create a item object, given a valid id, name, and price
     *
     * @param id
     * @param name
     * @param price
     */
    public ItemImpl(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * Returns the Id of an Item.
     *
     * @return number of the Item's Id.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Sets the Id of an Item.
     *
     * @param id the unique identifier number.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the Name of the Item.
     *
     * @return a String which represents the name of the Item.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of an Item.
     *
     * @param name a String which represents the name of the Item.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of an Item.
     *
     * @return BigDecimal of the price of the Item.
     */
    @Override
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Sets the price of an Item to a BigDecimal.
     *
     * @param price the price an Item will be set to.
     */
    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}