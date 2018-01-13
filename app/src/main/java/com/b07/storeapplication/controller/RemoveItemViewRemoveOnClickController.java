package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by cd on 2017-12-04.
 */

public class RemoveItemViewRemoveOnClickController implements View.OnClickListener {
    private Context appContext;
    private int customerId;
    private int accountNumber;
    private ShoppingCart shoppingCart;
    private Item item;
    private AutoCompleteTextView name;
    private List<Item> items;

    /**
     * Constructs add item button on click controller.
     *
     * @param context the activity context
     */
    public RemoveItemViewRemoveOnClickController(Context context, int customerId,
                                                 ShoppingCart shoppingCart, int accoutId) {
        this.appContext = context;
        this.customerId = customerId;
        this.accountNumber = accoutId;
        this.shoppingCart = shoppingCart;
        this.name = (AutoCompleteTextView) ((AppCompatActivity) this.appContext).findViewById(
                R.id.auto_remove_item_name);

    }

    @Override
    public void onClick(View view) {

        NumberPicker numberPicker = (NumberPicker) (
                (AppCompatActivity) this.appContext).findViewById(R.id.remove_quantity);
        String itemName = this.name.getText().toString();

        try {
            this.items = this.shoppingCart.getItems();
            for (int index = 0; index < this.items.size(); index++) {
                if (itemName.equals(this.items.get(index).getName())) {

                    this.item = this.items.get(index);
                }
            }

            if (this.item == null) {
                this.name.setError("Invalid Name");
                Toast.makeText(this.appContext, "Enter valid item name please",
                        Toast.LENGTH_LONG).show();
            } else {
                shoppingCart.removeItem(this.item, numberPicker.getValue());
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
                    DatabaseInsertHelper.insertAccountLine(accountId +  1,
                            this.item.getId(), shoppingCart.getItemMap().get(this.item),
                            this.appContext);
                    toCustomerViewIntent.putExtra("AccountId", accountId + 1);
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