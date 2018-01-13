package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.storeapplication.View.CustomerViewAddItem;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

/**
 * Created by cd on 2017-12-03.
 */

public class CustomerViewAddItemOnClickController implements View.OnClickListener {
    private Context appContext;
    private Customer customer;
    private ShoppingCart shoppingCart;

    /**
     * Constructs load account button on click controller.
     *
     * @param context the activity context
     */
    public CustomerViewAddItemOnClickController(Context context, Customer customer,
                                                ShoppingCart shoppingCart) {
        this.appContext = context;
        this.customer = customer;
        this.shoppingCart = shoppingCart;

    }

    @Override
    public void onClick(View view) {;
        Intent cartIntent = new Intent(this.appContext, CustomerViewAddItem.class);
        cartIntent.putExtra("CustomerAddItem", this.customer);
        cartIntent.putExtra("ShoppingCartToAddItem", this.shoppingCart);
        this.appContext.startActivity(cartIntent);

    }
}