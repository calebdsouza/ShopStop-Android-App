package com.b07.storeapplication.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.AddItemViewAddOnClickController;
import com.b07.storeapplication.controller.RemoveItemViewRemoveAllOnClickController;
import com.b07.storeapplication.controller.RemoveItemViewRemoveOnClickController;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerViewRemoveItem extends AppCompatActivity {
    private TextView pickItemTitle;
    public NumberPicker removeQuantity;
    private Button removeItemBtn;
    private Button removeAllBtn;
    private AutoCompleteTextView nameOfItemToRemove;
    private List<Item> items;
    public Context context;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private int accountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_remove_item);
        setTitle("Remove Item");
        this.context = this;

        Intent intent = getIntent();
        this.customer = (Customer) intent.getSerializableExtra("CustomerToRemoveItem");
        this.shoppingCart = (ShoppingCart) intent.getSerializableExtra(
                "ShoppingCartToRemoveItem");
        this.accountId = (int) intent.getIntExtra("AccountId", -1);

        this.removeAllBtn = (Button) findViewById(R.id.remove_all_btn);
        this.pickItemTitle = (TextView) findViewById(R.id.pick_remove_item_title);
        final TextView pickQuantityTitle = (TextView) findViewById(
                R.id.pick_remove_item_title);
        this.removeItemBtn = (Button) findViewById(R.id.remove_item_btn);
        this.removeItemBtn.setVisibility(View.GONE);
        // Get a reference to the AutoCompleteTextView in the layout
        this.nameOfItemToRemove = ((AutoCompleteTextView)
                findViewById(R.id.auto_remove_item_name));
        this.removeQuantity = (NumberPicker) findViewById(R.id.remove_quantity);
        this.removeQuantity.setVisibility(View.GONE);
        //Set the selector wheel to wrap if the min/max value is reached.
        this.removeQuantity.setWrapSelectorWheel(true);

        // Set the upper and lower limit for the NumberPicker
        this.removeQuantity.setMinValue(0);
        this.removeQuantity.setMaxValue(10);

        //Create listener for NumberPicker to change the corresponding text view
        this.removeQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldQuant, int newQuant){
                //Display the newly selected number from picker
                pickQuantityTitle.setText("Qauntity Selected:  " + newQuant);
            }
        });

        this.removeItemBtn.setOnClickListener(new RemoveItemViewRemoveOnClickController(
                this, this.customer.getId(), this.shoppingCart, this.accountId));
        this.removeAllBtn.setOnClickListener(new RemoveItemViewRemoveAllOnClickController(
                this, this.customer.getId(), this.shoppingCart, this.accountId));

        Toast.makeText(this,"Click check mark on keyboard to enter item name",
                Toast.LENGTH_LONG).show();
            this.items = this.shoppingCart.getItems();
            // Get the string array
            String[] itemNames = new String[this.items.size()];
            for (int index = 0; index < this.items.size(); index++) {
                itemNames[index] = this.items.get(index).getName();
            }
            // Create the adapter and set it to the AutoCompleteTextView
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                            itemNames);
            this.nameOfItemToRemove.setAdapter(adapter);

            this.nameOfItemToRemove.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        int quantity = getShoppingCartItemQuantity();
                        if (quantity > 0) {
                            removeQuantity.setMinValue(1);
                            removeQuantity.setMaxValue(quantity);
                        } else {
                            removeQuantity.setVisibility(View.GONE);
                            removeQuantity.setVisibility(View.GONE);
                        }
                        handled = true;
                    }
                    return handled;
                }
            });

    }

    public int getShoppingCartItemQuantity() {
        int quantity = 0;
        boolean isValidName = false;
        String itemName = this.nameOfItemToRemove.getText().toString();

        for (int index = 0; index < this.items.size(); index++) {
            if (itemName.equals(this.items.get(index).getName())) {
                try {
                    quantity = this.shoppingCart.getItemMap().get(this.items.get(index));
                    this.removeQuantity.setVisibility(View.VISIBLE);
                    this.removeItemBtn.setVisibility(View.VISIBLE);
                    isValidName = true;
                } catch (NullPointerException e) {
                    Toast.makeText(this,"Error: Shopping cart is null",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
        if(!isValidName) {
            this.nameOfItemToRemove.setError("Invalid Item Name");
            Toast.makeText(this,"Value Error: please select an item from the" +
                    " auto complete suggested", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Click check mark on keyboard to enter item name",
                    Toast.LENGTH_LONG).show();
        }
        return quantity;
    }

}