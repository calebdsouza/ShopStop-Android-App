package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.storeapplication.View.ContentShoppingCartRecyclerView;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

/**
 * Created by cd on 2017-12-03.
 */

public class CustomerViewCartOnClickController implements View.OnClickListener {
    private Context appContext;
    private Customer customer;
    private ShoppingCart shoppingCart;

    /**
     * Constructs load account button on click controller.
     *
     * @param context the activity context
     */
    public CustomerViewCartOnClickController(Context context, Customer customer,
                                             ShoppingCart shoppingCart) {
        this.appContext = context;
        this.customer = customer;
        this.shoppingCart = shoppingCart;

    }

    @Override
    public void onClick(View view) {
        Intent cartIntent = new Intent(this.appContext, ContentShoppingCartRecyclerView.class);
        cartIntent.putExtra("Customer", this.customer);
        cartIntent.putExtra("ShoppingCartToCartView", this.shoppingCart);
        this.appContext.startActivity(cartIntent);

    }
}