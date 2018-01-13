package com.b07.storeapplication.model.store;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.users.Customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart implements Serializable {
    private final BigDecimal TAXRATE = new BigDecimal("1.13");
    private HashMap<Item, Integer> items;
    private Customer customer;
    private BigDecimal total = new BigDecimal(0.00);

    /**
     * Create a new Shopping cart.
     *
     * @param currCus the customer of this Shopping Cart being made
     */
    public ShoppingCart(Customer currCus) {
        this.customer = currCus;
        setItemMap();
    }

    /**
     * Given a ShoppingCart, validate there is a customer, and enough inventory.
     * Then process the sale and update the required Tables in the DB and
     * return true. If any operations fail, return false
     *
     * @param shoppingCart - the ShoppingCart to be checked out
     * @param context  the context of the activity which this method is called
     * @return true if the given ShoppingCart was checked out, otherwise false
     */
    public static boolean checkOut(ShoppingCart shoppingCart, Context context) throws SQLException,
            DatabaseInsertException {
        // Create a check for valid Customer
        boolean hasCustomer = shoppingCart.getCustomer() != null;
        // Create a place to store the total price after tax for this SHoppingCart
        BigDecimal total = null;
        // Create for having enough items in inventory
        boolean hasEnough = true;
        // Create a check to determine if the inventory was updated
        boolean wasUpdated = true;
        // Get the Item map of the given ShoppingCart
        HashMap<Item, Integer> itemMap = shoppingCart.getItemMap();
        // Check if  this ShoppingCart has a Customer
        if (hasCustomer) {
            // Get to the total price
            total = shoppingCart.getTotal().multiply(shoppingCart.getTaxRate());
            // Get a list of items
            List<Item> items = shoppingCart.getItems();
            // Check if there is enough inventory for each Item
            for (Item item : items) {
                // Get the current item's inventory quantity
                int quantityInventory = DatabaseSelectHelper.getInventoryQuantity(item.getId(),
                        context);
                // Check if there is not enough inventory
                if (itemMap.get(item) > quantityInventory) {
                    hasEnough = false;
                }
            }

            // Check if there is enough inventory
            if (hasEnough) {
                // Remove the quantity from inventory
                for (Item item : items) {
                    // Get the current item's inventory quantity
                    int quantityInventory = DatabaseSelectHelper.getInventoryQuantity(
                            item.getId(), context);
                    // Calculate the new quantity
                    int newQuant = quantityInventory - itemMap.get(item);
                    // Update inventory for this item
                    boolean isUpdated = DatabaseUpdateHelper.updateInventoryQuantity(newQuant,
                            item.getId(), context);
                    // Check in the INVENTORY Table was updated
                    if (isUpdated != true) {
                        wasUpdated = false;
                    }
                }
                // Get the user id of Customer for the given ShoppingCart
                int userId = shoppingCart.getCustomer().getId();
                // Insert the Sale of the given ShoppingCart into the SALES Table
                int saleId = DatabaseInsertHelper.insertSale(userId, total, context);
                // Insert each item into ItemizedSale Table
                for (Item item : items) {
                    DatabaseInsertHelper.insertItemizedSale(saleId, item.getId(),
                            itemMap.get(item), context);
                }
                // Clear the shopping cart
                shoppingCart.clearCart();
            }
        }
        // Return of the checkout was successful
        return hasCustomer && hasEnough;
    }

    /**
     * Sets the items HashMap initially.
     */
    private void setItemMap() {
        this.items = new HashMap<Item, Integer>();
    }

    /**
     * Return the hashmap of Items and their quantity.
     *
     * @return item map of Items and their respective quantity
     */
    public HashMap<Item, Integer> getItemMap() {
        return this.items;
    }

    /**
     * Return tax rate for this Shopping Cart.
     *
     * @return tax rate for this Shopping Cart
     */
    public BigDecimal getTaxRate() {
        return this.TAXRATE;
    }

    /**
     * Return the total price of this ShoppingCart.
     *
     * @return total price of this ShoppingCart
     */
    public BigDecimal getTotal() {
        return this.total;
    }

    /**
     * Return the this ShopingCart's current customer.
     *
     * @return this ShopingCart's customer
     */
    public Customer getCustomer() {
        return this.customer;
    }

    /**
     * Return the list of Items in this ShoppingCart.
     *
     * @return - the list of all Items in this ShoppingCart
     */
    public List<Item> getItems() {
        if (this.items == null) {
            setItemMap();
        }
        // Create a place to store the List of items
        List<Item> itemList = new ArrayList<Item>(this.items.keySet());
        // Return all Items
        return itemList;
    }

    /**
     * Add the quantity of item to the items list. Update the total accordingly
     *
     * @param item     - the Item to be added
     * @param quantity - the quantity of the item to be added
     */
    public void addItem(Item item, int quantity) {
        // Determine if the given item is not null and the quantity is not negative
        if (item != null && quantity > -1) {
            // Create a check to determine if the Item was already in the cart
            boolean itemExists = false;
            // Determine if the item was already in the cart
            Item itemToAdd = null;
            for (Item i : this.getItems()) {
                if (item.getId() == i.getId()) {
                    itemExists = true;
                    itemToAdd = i;
                }
            }
            // Check if the given item is already in this Cart
            if (itemExists) {
                // Get the existing quantity of the given Item in this Cart and add the new quantity
                int existingQuant = items.get(itemToAdd);
                int newQuant = existingQuant + quantity;
                this.items.replace(item, existingQuant, newQuant);
            } else {
                // Add quantity to items
                this.items.put(item, quantity);
            }
            // Update the total price for this ShoppingCart
            BigDecimal pricePurchase = item.getPrice().multiply(new BigDecimal(Integer.toString
                    (quantity)));
            this.total = total.add(pricePurchase);

        }
    }

    /**
     * Clear all Items in this ShoppingCart.
     */
    public void clearCart() {
        this.items.clear();
        this.total = new BigDecimal(0.00);
    }

    /**
     * Remove the quantity given of the item from items. If the number
     * becomes zero, remove it entirely from the items list. Update the total
     * accordingly.
     *
     * @param item     - the item who's quantity will be removed from
     * @param quantity - the quantity to be removed from the given Item
     */
    public void removeItem(Item item, int quantity) {
        // Calculate the change in quantity
        int change = this.getItemMap().get(item) - quantity;
        // Check if the quantity for the given item is not 0
        if (change > 0) {
            // Remove the given quantity
            this.items.put(item, change);
        } else {
            // Remove the item
            this.items.remove(item);
        }
        // Update the total price for this ShoppingCart
        this.total = total.subtract(item.getPrice().multiply(new BigDecimal(quantity)));
        System.out.println("Items have been removed from your cart.");
    }

    /**
     * Find an Item in ShoppingCart via its Id.
     *
     * @param itemId the Id of the Item
     * @return the Item instance with the corresponding Id.
     */
    public Item findItem(int itemId) {
        Item foundItem = null;
        for (Item item : this.getItems()) {
            if (itemId == item.getId()) {
                foundItem = item;
            }
        }
        return foundItem;
    }
}