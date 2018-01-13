package com.b07.storeapplication.model.inventory;

import java.io.Serializable;
import java.util.HashMap;

public class InventoryImpl implements Inventory, Serializable {
    private HashMap<Item, Integer> itemMap;
    private int length;

    public InventoryImpl() {
        this.itemMap = new HashMap<>();
        this.length = this.itemMap.size();
    }

    /**
     * Return a HashMap of all the Items and their respective quantity in this Inventory.
     *
     * @return HashMap of all the Items and their respective quantity in this Inventory.
     */
    @Override
    public HashMap<Item, Integer> getItemMap() {

        return this.itemMap;
    }

    /**
     * Set a HashMap of all the Items and their respective quantity for this Inventory.
     *
     * @param itemMap - the map of Item to their respective quantity for this Inventory.
     */
    @Override
    public void setItemMap(HashMap<Item, Integer> itemMap) { this.itemMap = itemMap; }

    /**
     * Add the given item and it's quantity to this Inventory if the item is not
     * in the inventory. Otherwise if the item is in the inventory replace the quantity
     *
     * @param item  - the item to this Inventory.
     * @param value - the value of given item to be added to this Inventory.
     */
    @Override
    public void updateMap(Item item, Integer value) {
        // Check if this item map is null
        if (this.itemMap == null) {
            this.setItemMap(new HashMap<Item, Integer>());
        }
        // If Item already exists in Inventory have it update the quantity.
        if (this.itemMap.containsKey(item)) {
            // Replace it's old quantity with the given quantity
            this.itemMap.put(item, this.itemMap.get(item) + value);
            // If quantity is negative set to zero
            if (this.itemMap.get((item)) < 0) {
                this.itemMap.put(item, 0);
            }

        }
        // If Item is new in Inventory
        else {
            // If quantity is not negative add givens to the this ItemMap
            if (value > -1) {
                this.itemMap.put(item, value);
            } else {
                this.itemMap.put(item, 0);
            }
        }

    }


    /**
     * Return the total number of Items in this Inventory.
     *
     * @return total number of items this Inventory.
     */
    @Override
    public int getTotalItems() {
        return this.length;
    }

    /**
     * Return the set the total number of Items in this Inventory.
     *
     * @param total - the total number of Items to be set for this Inventory.
     */
    @Override
    public void setTotalItems(int total) {
        // specify size of Inventory
        if (total == 0) {
            this.itemMap.clear();
            this.length = 0;
        }
        // set larger size of inventory
        if (total > this.length) {
            this.length = total;
        }
    }

    /**
     * Searches the HashMap for the Item and returns its value (quantity)
     *
     * @param item the Item you want the quantity returned for
     * @return int the quantity of the item
     */
    @Override
    public int getQuantity(Item item) {
        return this.itemMap.get(item);
    }
}