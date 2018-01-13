package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.users.Admin;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
/**
 * Created by cd on 2017-12-03.
 */

public class AdminViewGetItemDialogAfterOnClickedController {

    private EditText givenItemName = null;
    private EditText givenItemPrice = null;
    private Admin currentAdmin = null;
    private Context appContext;

    public AdminViewGetItemDialogAfterOnClickedController(Context context, Admin admin) {
        this.appContext = context;
        this.currentAdmin = admin;
        this.givenItemName = (EditText) ((AppCompatActivity) this.appContext).findViewById(
                R.id.admin_view_get_item_name);
        this.givenItemPrice = (EditText) ((AppCompatActivity) this.appContext).findViewById(
                R.id.admin_view_get_item_price);
    }

    /**
     * Given an item name and it's price insert an new item into the database and the inventory.
     * @param itemName the name of the item to be inserted
     * @param price the price ov the item to be inserted
     */
    public void addNewItem(String itemName, String price) {
        // Create a check for the item's existence
        boolean itemExits = false;

        List<Item> inventoryList = null;
        int itemId = -1;

        if (itemName.matches("([A-Z]|_)*")) {
            // Try to determine if the given item already exists
            try {
                inventoryList = DatabaseSelectHelper.getAllItems(this.appContext);
                for (Item item : inventoryList) {
                    if (item.getName().equals(itemName)) {
                        Toast.makeText(this.appContext, "Item already exists",
                                Toast.LENGTH_LONG).show();
                        itemExits = true;
                    }
                }
            } catch (SQLException e) {
                Toast.makeText(this.appContext, "SQL Error: could not verify if item is" +
                        " already in db ", Toast.LENGTH_SHORT).show();
            }

            // Check if the given item already exists
            if (!itemExits) {
                // Check if the given item price is valid {
                if (price.matches("([0-9]|.)*")) {

                    // Verify the price of the given item
                    BigDecimal itemPrice;
                    if (price.matches("([0-9]|.)*")) {
                        itemPrice = new BigDecimal(price);
                    } else {
                        itemPrice = new BigDecimal(inputConverter(price));
                    }
                    // Try to insert the item into the database
                    try {
                        itemId = DatabaseInsertHelper.insertItem(itemName, itemPrice,
                                this.appContext);
                    } catch (SQLException e) {
                        Toast.makeText(this.appContext, "SQL Error: could not insert" +
                                " item in db", Toast.LENGTH_SHORT).show();
                    } catch (DatabaseInsertException ie) {
                        Toast.makeText(this.appContext, "Value Error: could not insert" +
                                " item in db", Toast.LENGTH_SHORT).show();
                    }

                    // Check if the given item was inserted into the database
                    if (itemId > 0) {
                        // Try to insert the item in the inventory of the database
                        try {
                            DatabaseInsertHelper.insertInventory(itemId, 0,
                                    this.appContext);

                            Toast.makeText(this.appContext, "New Item Was Added. Item Id:" +
                                    itemId, Toast.LENGTH_SHORT).show();
                        } catch (SQLException e) {
                            Toast.makeText(this.appContext, "SQL Error: could not insert" +
                                    " item in db", Toast.LENGTH_SHORT).show();
                        } catch (DatabaseInsertException ie) {
                            Toast.makeText(this.appContext, "Value Error: could not insert" +
                                    " item in db", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } else {
            Toast.makeText(this.appContext, "Invalid given item name." +
                    " Item must match regex:[A-Z]|_", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Helper function to convert user input to int from String
     *
     * @param input a String from the User
     * @return input now in integer form.
     */
    private int inputConverter(String input) {
        // Check if input was converted
        int convertedValue = 0;
        // Try to convert input
        try {
            convertedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this.appContext, "Price cannot be empty, negative or" +
                    " contain letters", Toast.LENGTH_SHORT).show();
        }
        // Return converted input
        return convertedValue;
    }
}