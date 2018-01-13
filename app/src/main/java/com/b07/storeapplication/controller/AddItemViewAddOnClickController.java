package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.View.CustomerViewAddItem;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by cd on 2017-12-04.
 */

public class AddItemViewAddOnClickController implements View.OnClickListener {
    private Context appContext;
    private int customerId;
    private ShoppingCart shoppingCart;
    private Item item;
    private AutoCompleteTextView name;
    private List<Item> items;

    /**
     * Constructs add item button on click controller.
     *
     * @param context the activity context
     */
    public AddItemViewAddOnClickController(Context context, int customerId,
                                           ShoppingCart shoppingCart) {
        this.appContext = context;
        this.customerId = customerId;
        this.shoppingCart = shoppingCart;
        this.name = (AutoCompleteTextView) ((AppCompatActivity) this.appContext).findViewById(
                R.id.customer_auto_item_name);

    }

    @Override
    public void onClick(View view) {

        NumberPicker numberPicker = (NumberPicker) (
                (AppCompatActivity) this.appContext).findViewById(R.id.customer_add_quantity);
        String itemName = this.name.getText().toString();

        try {
            this.items = DatabaseSelectHelper.getAllItems(this.appContext);
            for (int index = 0; index < this.items.size(); index++) {
                if (itemName.equals(this.items.get(index).getName())) {

                    this.item = DatabaseSelectHelper.getItem(
                            this.items.get(index).getId(), this.appContext);
                }
            }

            if (this.item == null) {
                Toast.makeText(this.appContext, "Enter valid item name please",
                        Toast.LENGTH_LONG).show();
            } else {
                shoppingCart.addItem(this.item, numberPicker.getValue());
                Toast.makeText(((AppCompatActivity) this.appContext), "Item was added to cart",
                        Toast.LENGTH_LONG).show();
                Intent toCustomerViewIntent = new Intent(this.appContext, CustomerViewActivity.class);
                toCustomerViewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                toCustomerViewIntent.putExtra("ShoppingCartToView", this.shoppingCart);
                toCustomerViewIntent.putExtra("Customer", DatabaseSelectHelper.getUserDetails(
                        this.customerId, this.appContext));

                // save ShoppingCart in Database for shopper if account exists.
                if (accountExistence(customerId)) {
                    List<Integer> accountIds = DatabaseSelectHelper.getUserActiveAccounts(
                            customerId, this.appContext);
                    int accountId = Collections.max(accountIds);
                    DatabaseInsertHelper.insertAccountLine(accountId+1, this.item.getId()
                            , numberPicker.getValue(), this.appContext);
                    DatabaseInsertHelper.insertAccount(customerId, true, this.appContext);
                }
                this.appContext.startActivity(toCustomerViewIntent);
            }

        } catch (SQLException e) {
            Toast.makeText(this.appContext,"SQL Error: unable to get item" +
                    " from db", Toast.LENGTH_SHORT).show();
        } catch (DatabaseInsertException ex) {
            Toast.makeText(this.appContext,"Unable to insert item to customer's account",
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException ne) {
            Toast.makeText(this.appContext,"Please enter a valid item name",
                    Toast.LENGTH_SHORT).show();
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