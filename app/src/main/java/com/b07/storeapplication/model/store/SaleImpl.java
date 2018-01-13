package com.b07.storeapplication.model.store;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.users.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;


public class SaleImpl implements Sale, Serializable {
    private int saleId;
    private int userId;
    private BigDecimal totalPrice;
    private HashMap<Item, Integer> itemMap;

    public SaleImpl(int userId, BigDecimal totalPrice) {
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    public SaleImpl(int saleId, int userId, BigDecimal totalPrice) {
        this.saleId = saleId;
        this.userId = userId;
        this.totalPrice = totalPrice;
    }

    /**
     * Return the id for this Sale.
     *
     * @return int id for this Sale.
     */
    @Override
    public int getId() {
        return this.saleId;
    }

    /**
     * Set the id for this Sale.
     *
     * @param id - the id to be set for this Sale.
     */
    @Override
    public void setId(int id) {
        this.saleId = id;
    }

    /**
     * Return the User who completed the process for this Sale.
     *
     * @param context the context of the activity which this method is called
     * @return User for this Sale.
     */
    @Override
    public User getUser(Context context) {
        try {
            return DatabaseSelectHelper.getUserDetails(this.userId, context);
        } catch (SQLException e) {
            System.out.println("User cannot be found.");
        }
        return null;
    }

    /**
     * Set the User for this Sale.
     *
     * @param user - the user to be set for this Sale
     */
    @Override
    public void setUser(User user) {
        this.userId = user.getId();

    }

    /**
     * Return the total price for this Sale.
     *
     * @return total price for this Sale.
     */
    @Override
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * Set the total price for this Sale.
     *
     * @param price - the total price to be set for this Sale.
     */
    @Override
    public void setTotalPrice(BigDecimal price) {
        this.totalPrice = price;

    }

    /**
     * returns the items that are a part of the Sale.
     *
     * @return ItemMap showcasing all the items mapped to their respective quantities.
     */
    @Override
    public HashMap<Item, Integer> getItemMap() {
        return this.itemMap;
    }

    /**
     * sets the ItemMap showing the items in the sale mapped to their respective quantities.
     *
     * @param ItemMap HashMap of the items hashed to their quantities.
     */
    @Override
    public void setItemMap(HashMap<Item, Integer> ItemMap) {
        this.itemMap = ItemMap;
    }
}