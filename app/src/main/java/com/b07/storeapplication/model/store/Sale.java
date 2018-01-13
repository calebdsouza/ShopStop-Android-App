package com.b07.storeapplication.model.store;

import android.content.Context;

import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.users.User;

import java.math.BigDecimal;
import java.util.HashMap;

public interface Sale {

    /**
     * returns the Id of a Sale.
     *
     * @return number of the Sale's Id.
     */
    public int getId();

    /**
     * sets the Id of a Sale.
     *
     * @param id the unique identifier number.
     */
    public void setId(int id);

    /**
     * returns the User of the Sale.
     *
     * @param context the context of the activity which this method is called
     * @return a User abstract that is on the Sale.
     */
    public User getUser(Context context);

    /**
     * sets the User of the Sale.
     *
     * @param user a User which represents the one involved in the Sale.
     */
    public void setUser(User user);

    /**
     * returns the total price of a Sale
     *
     * @return BigDecimal of the total price of the Sale.
     */
    public BigDecimal getTotalPrice();

    /**
     * sets the total price of a Sale.
     *
     * @param price the price a Sale will be set to in the form of a BigDecimal.
     */
    public void setTotalPrice(BigDecimal price);

    /**
     * returns the items that are a part of the Sale.
     *
     * @return ItemMap showcasing all the items mapped to their respective quantities.
     */
    public HashMap<Item, Integer> getItemMap();

    /**
     * sets the ItemMap showing the items in the sale mapped to their respective quantities.
     *
     * @param ItemMap HashMap of the items hashed to their quantities.
     */
    public void setItemMap(HashMap<Item, Integer> ItemMap);

}