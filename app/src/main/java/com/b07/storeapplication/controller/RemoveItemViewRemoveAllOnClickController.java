package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.store.ShoppingCart;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by cd on 2017-12-04.
 */

public class RemoveItemViewRemoveAllOnClickController implements View.OnClickListener {
    private Context appContext;
    private int customerId;
    private int accountNumber;
    private ShoppingCart shoppingCart;

    /**
     * Constructs add item button on click controller.
     *
     * @param context the activity context
     */
    public RemoveItemViewRemoveAllOnClickController(Context context, int customerId,
                                                    ShoppingCart shoppingCart, int accountId) {
        this.appContext = context;
        this.customerId = customerId;
        this.accountNumber = accountId;
        this.shoppingCart = shoppingCart;


    }

    @Override
    public void onClick(View view) {
        if (this.shoppingCart != null)
            try {
                this.shoppingCart.clearCart();
                Toast.makeText(((AppCompatActivity) this.appContext),
                        "Item was removed from cart", Toast.LENGTH_LONG).show();

                Intent toCustomerViewIntent = new Intent(this.appContext,
                        CustomerViewActivity.class);
                toCustomerViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                toCustomerViewIntent.putExtra("ShoppingCartToView", this.shoppingCart);
                toCustomerViewIntent.putExtra("Customer",
                        DatabaseSelectHelper.getUserDetails(this.customerId, this.appContext));
                // save ShoppingCart in Database for shopper if account exists.
                if (accountExistence(customerId)) {
                    int accountId = Collections.max(DatabaseSelectHelper.getUserActiveAccounts(
                            customerId, this.appContext));
                    DatabaseUpdateHelper.updateAccountStatus(accountNumber, false,
                            this.appContext);
                    DatabaseInsertHelper.insertAccount(accountId + 1, true,
                            this.appContext);
                    toCustomerViewIntent.putExtra("AccountId", accountId + 1);

                }

                this.appContext.startActivity(toCustomerViewIntent);

            } catch (SQLException e) {
                Toast.makeText(this.appContext,"SQL Error: unable to send customer" +
                        " to customer menu activity", Toast.LENGTH_SHORT).show();
            } catch (DatabaseInsertException ie) {
                Toast.makeText(this.appContext,"SQL Error: unable to insert new account" +
                        "into db", Toast.LENGTH_SHORT).show();
            }
        else {
            Toast.makeText(this.appContext, "Error: Shopping cart was not passed to this " +
                    "activity", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method checks if there is an existing Account for a Customer within the database and
     * returns a boolean
     * @param userId the userId of a User
     * @return true if user exists, false otherwise
     * @throws SQLException if a failure occurs
     */
    private boolean accountExistence(int userId) throws SQLException {
        // Find user with userId
        if (DatabaseSelectHelper.getUserAccounts(userId, this.appContext).size() == 0) {
            return false;
        } else {
            return true;
        }
    }
}