package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by cd on 2017-12-03.
 */

public class CustomerViewLoadAccountOnClickController  implements View.OnClickListener {
    public Context appContext;
    private Customer customer;

    /**
     * Constructs load account button on click controller.
     * @param context the activity context
     */
    public CustomerViewLoadAccountOnClickController(Context context, Customer customer) {
        this.appContext = context;
        this.customer = customer;

    }

    @Override
    public void onClick(View view) {
        int id = this.customer.getId();
        try {
            int accountNumber = Collections.max(DatabaseSelectHelper.getUserActiveAccounts(
                    id, this.appContext));
            ShoppingCart previousCart = new ShoppingCart(this.customer);
            HashMap<Integer, Integer> itemMap = DatabaseSelectHelper.getAccountDetails
                    (accountNumber, this.appContext);
            for (int itemId : itemMap.keySet()) {
                Item item = DatabaseSelectHelper.getItem(itemId, this.appContext);
                previousCart.addItem(item, itemMap.get(itemId));
            }
            Intent customerIntent = new Intent(this.appContext, CustomerViewActivity.class);
            customerIntent.putExtra("Customer", this.customer);
            customerIntent.putExtra("ShoppingCartToView", previousCart);
            customerIntent.putExtra("AccountId", accountNumber);
            this.appContext.startActivity(customerIntent);
            Toast.makeText(appContext, "Previous Session has been restored.",
                    Toast.LENGTH_SHORT).show();
            ((AppCompatActivity)this.appContext).finish();
        } catch(SQLException e) {
            Toast.makeText(appContext, "SQL Error: could not get accounts from db",
                    Toast.LENGTH_SHORT).show();
        }
    }
}