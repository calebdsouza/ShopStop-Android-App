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
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.inventory.ItemTypes;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerViewAddItem extends AppCompatActivity {
    private TextView pickItemTitle;
    public NumberPicker addQuantity;
    private Button addItemBtn;
    private AutoCompleteTextView nameOfItemToAdd;
    private List<Item> items;
    public Toast outOfStock;
    public Context context;
    private Customer customer;
    private ShoppingCart shoppingCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_add_item);
        this.context = this;
        this.outOfStock = new Toast(this);
        Intent intent = getIntent();
        this.customer = (Customer) intent.getSerializableExtra("CustomerAddItem");
        this.shoppingCart = (ShoppingCart) intent.getSerializableExtra(
                "ShoppingCartToAddItem");

        this.pickItemTitle = (TextView) findViewById(R.id.customer_pick_item_title);
        final TextView pickQuantityTitle = (TextView) findViewById(
                R.id.customer_pick_quantity_title);
        this.addItemBtn = (Button) findViewById(R.id.customer_add_item_quantity_btn);
        this.addItemBtn.setVisibility(View.GONE);
        // Get a reference to the AutoCompleteTextView in the layout
        this.nameOfItemToAdd = ((AutoCompleteTextView)
                findViewById(R.id.customer_auto_item_name));
        this.addQuantity = (NumberPicker) findViewById(R.id.customer_add_quantity);
        this.addQuantity.setVisibility(View.GONE);
        //Set the selector wheel to wrap if the min/max value is reached.
        this.addQuantity.setWrapSelectorWheel(true);

        // Set the upper and lower limit for the NumberPicker
        this.addQuantity.setMinValue(0);
        this.addQuantity.setMaxValue(10);

        //Create listener for NumberPicker to change the corresponding text view
        this.addQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldQuant, int newQuant){
                //Display the newly selected number from picker
                pickQuantityTitle.setText("Qauntity Selected:  " + newQuant);
            }
        });

        this.addItemBtn.setOnClickListener(new AddItemViewAddOnClickController(this,
                this.customer.getId(), this.shoppingCart));

        Toast.makeText(this,"Click check mark on keyboard to enter item name",
                Toast.LENGTH_LONG).show();
        try {
            this.items = DatabaseSelectHelper.getAllItems(this);
            // Get the string array
            String[] itemNames = new String[this.items.size()];
            for (int index = 0; index < this.items.size(); index++) {
                itemNames[index] = this.items.get(index).getName();
            }
            // Create the adapter and set it to the AutoCompleteTextView
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                            itemNames);
            this.nameOfItemToAdd.setAdapter(adapter);

            this.nameOfItemToAdd.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        int quantity = getInventoryItemQuantity();
                        if (quantity > 0) {
                            addQuantity.setMinValue(1);
                            addQuantity.setMaxValue(quantity);
                        } else {
                            addQuantity.setVisibility(View.GONE);
                            addItemBtn.setVisibility(View.GONE);
                            outOfStock.makeText(context, "This Item Is Out Of Stock",
                                    Toast.LENGTH_LONG).show();
                        }
                        handled = true;
                    }
                    return handled;
                }
            });

        } catch (SQLException e) {
            Toast.makeText(this,"SQL Error: unable to get items from db",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public int getInventoryItemQuantity() {
        int quantity = 0;
        boolean isValidName = false;
        String itemName = this.nameOfItemToAdd.getText().toString();

        for (int index = 0; index < this.items.size(); index++) {
            if (itemName.equals(this.items.get(index).getName())) {
                try {
                    quantity = DatabaseSelectHelper.getInventoryQuantity(
                            this.items.get(index).getId(), this);
                    this.addQuantity.setVisibility(View.VISIBLE);
                    this.addItemBtn.setVisibility(View.VISIBLE);
                    isValidName = true;
                } catch (SQLException e) {
                    Toast.makeText(this,"SQL Error: unable to get item quantity" +
                                    " from db", Toast.LENGTH_SHORT).show();
                }

            }
        }
        if(!isValidName) {
            this.nameOfItemToAdd.setError("Invalid Item Name");
            Toast.makeText(this,"Value Error: please select an item from the" +
                    " auto complete suggested", Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Click check mark on keyboard to enter item name",
                    Toast.LENGTH_LONG).show();
        }
        return quantity;
    }

}

