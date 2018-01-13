package com.b07.storeapplication.model.inventory;


import java.math.BigDecimal;

public interface Item {

    /**
     * returns the Id of an Item.
     *
     * @return number of the Item's Id.
     */
    public int getId();

    /**
     * sets the Id of an Item.
     *
     * @param id the unique identifier number.
     */
    public void setId(int id);

    /**
     * returns the Name of the Item.
     *
     * @return a String which represents the name of the Item.
     */
    public String getName();

    /**
     * sets the name of an Item.
     *
     * @param name a String which represents the name of the Item.
     */
    public void setName(String name);


    /**
     * returns the price of an Item.
     *
     * @return BigDecimal of the price of the Item.
     */
    public BigDecimal getPrice();

    /**
     * sets the price of an Item to a BigDecimal.
     *
     * @param price the price an Item will be set to.
     */
    public void setPrice(BigDecimal price);

}