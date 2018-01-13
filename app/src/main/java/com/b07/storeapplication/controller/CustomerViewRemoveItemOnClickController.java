package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.storeapplication.View.CustomerViewRemoveItem;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

/**
 * Created by cd on 2017-12-04.
 */

public class CustomerViewRemoveItemOnClickController implements View.OnClickListener {
    private Context appContext;
    private Customer customer;
    private int accountId;
    private ShoppingCart shoppingCart;

    /**
     * Constructs load account button on click controller.
     *
     * @param context the activity context
     */
    public CustomerViewRemoveItemOnClickController(Context context, Customer customer,
                                                   ShoppingCart shoppingCart, int accountId) {
        this.appContext = context;
        this.customer = customer;
        this.accountId = accountId;
        this.shoppingCart = shoppingCart;

    }

    @Override
    public void onClick(View view) {
        Intent removeIntent = new Intent(this.appContext, CustomerViewRemoveItem.class);
        removeIntent.putExtra("CustomerToRemoveItem", this.customer);
        removeIntent.putExtra("ShoppingCartToRemoveItem", this.shoppingCart);
        this.appContext.startActivity(removeIntent);

    }
}