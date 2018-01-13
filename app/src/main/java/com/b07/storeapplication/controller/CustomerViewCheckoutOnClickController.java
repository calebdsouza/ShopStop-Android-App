package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

import java.sql.SQLException;

/**
 * Created by cd on 2017-12-04.
 */

public class CustomerViewCheckoutOnClickController implements View.OnClickListener {
    private Context appContext;
    private Customer customer;
    private int accountId;
    private ShoppingCart shoppingCart;

    /**
     * Constructs checkout button on click controller.
     *
     * @param context the activity context
     */
    public CustomerViewCheckoutOnClickController(Context context, Customer customer,
                                                 ShoppingCart shoppingCart, int accountId) {
        this.appContext = context;
        this.customer = customer;
        this.accountId = accountId;
        this.shoppingCart = shoppingCart;

    }

    @Override
    public void onClick(View view) {
        boolean wasCheckOut = false;
        try {
            wasCheckOut = ShoppingCart.checkOut(this.shoppingCart, this.appContext);

            if (wasCheckOut) {
                Toast.makeText(this.appContext, "Checkout complete",
                        Toast.LENGTH_SHORT).show();

                // Make Account inactive
                DatabaseUpdateHelper.updateAccountStatus(this.accountId, false,
                        this.appContext);

                Intent toCustomerViewIntent = new Intent(this.appContext,
                        CustomerViewActivity.class);
                toCustomerViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                toCustomerViewIntent.putExtra("ShoppingCartToView", this.shoppingCart);
                toCustomerViewIntent.putExtra("Customer",
                        DatabaseSelectHelper.getUserDetails(this.customer.getId(),
                                this.appContext));
                this.appContext.startActivity(toCustomerViewIntent);
            } else {
                Toast.makeText(this.appContext, "Could not checkout",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL Error: Unable to insert, select, or update" +
                    " the db", Toast.LENGTH_SHORT).show();
        } catch (DatabaseInsertException ie) {
            Toast.makeText(this.appContext, "SQL Error: unable to insert new account" +
                    "into db", Toast.LENGTH_SHORT).show();
        }
    }
}