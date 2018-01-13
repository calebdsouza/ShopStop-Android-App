package com.b07.storeapplication.model.inventory;

import java.util.HashMap;

public interface Inventory {

    /**
     * returns the items that are in the Inventory.
     *
     * @return ItemMap showcasing all the items mapped to their respective quantities in stock.
     */
    public HashMap<Item, Integer> getItemMap();

    /**
     * sets the ItemMap showing the items in the Inventory mapped to their respective quantities.
     *
     * @param ItemMap HashMap of the items hashed to their quantities.
     */
    public void setItemMap(HashMap<Item, Integer> ItemMap);

    /**
     * updates the Inventory after a Sale of Purchase.
     *
     * @param item  new Item to be added or subtracted from Inventory.
     * @param value amount of Item to be added or removed from Inventory.
     */
    public void updateMap(Item item, Integer value);

    /**
     * returns the total number of distinct items in an Inventory
     *
     * @return number specifying number of Items in Inventory.
     */
    public int getTotalItems();

    /**
     * sets the total number of Items in an Inventory.
     *
     * @param total the number of Items in an Inventory.
     */
    public void setTotalItems(int total);

    /**
     * searches the HashMap for the Item and returns its value (quantity)
     *
     * @param item the Item you want the quantity returned for
     * @return int the quantity of the item
     */
    public int getQuantity(Item item);
}