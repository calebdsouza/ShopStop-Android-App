package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

/**
 * Created by cd on 2017-12-03.
 */

public class CustomerVeiwDoNotLoadAccountOnClickController  implements View.OnClickListener {
    private Context appContext;
    private Customer customer;
    private Button doNotLoadAccountBtn;

    /**
     * Constructs load account button on click controller.
     *
     * @param context the activity context
     */
    public CustomerVeiwDoNotLoadAccountOnClickController(Context context, Customer customer) {
        this.appContext = context;
        this.customer = customer;
        this.doNotLoadAccountBtn = ((AppCompatActivity) this.appContext).findViewById(
                R.id.do_not_load_account_btn);

    }

    @Override
    public void onClick(View view) {
        ShoppingCart customerCart = new ShoppingCart(this.customer);
        Intent customerIntent = new Intent(this.appContext, CustomerViewActivity.class);
        customerIntent.putExtra("Customer", this.customer);
        customerIntent.putExtra("ShoppingCartToView", customerCart);
        this.appContext.startActivity(customerIntent);
        Toast.makeText(appContext, "New Shopping Cart Has Been Created",
                Toast.LENGTH_SHORT).show();
        ((AppCompatActivity)this.appContext).finish();
    }
}