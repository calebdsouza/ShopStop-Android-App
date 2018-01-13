package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by cd on 2017-12-03.
 */

public class ContentShoppingCartRecyclerView extends AppCompatActivity {
    private ShoppingCart shoppingCart;
    private HashMap<Item, Integer> shoppingCartMap;
    private List<Item> shoppingCartList;
    private BigDecimal totalPrice;
    private TextView totalPriceView;
    private RecyclerView shoppingCartRecyclerView = null;
    private RecyclerView.Adapter shoppingCartAdapter = null;
    private RecyclerView.LayoutManager shoppingCartLayoutManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_fragment_item_list);
        Intent intent = getIntent();
        setTitle("Shopping Cart");

        this.shoppingCart = (ShoppingCart) intent.getSerializableExtra(
                "ShoppingCartToCartView");
        if (this.shoppingCart != null) {
            this.shoppingCartMap = this.shoppingCart.getItemMap();
            this.shoppingCartList = this.shoppingCart.getItems();

            this.totalPrice = this.shoppingCart.getTotal();
            this.totalPriceView = findViewById(R.id.shopping_cart_total_price);
            this.totalPriceView.setText("Total Price: " + this.totalPrice.toString());


            this.shoppingCartRecyclerView = findViewById(R.id.cart_recycler_view);
            this.shoppingCartRecyclerView.setHasFixedSize(true);
            this.shoppingCartLayoutManager = new LinearLayoutManager(this);
            this.shoppingCartAdapter = new CustomerViewItemListAdapter(this.shoppingCartMap,
                    this.shoppingCartList);

            this.shoppingCartRecyclerView.setLayoutManager(this.shoppingCartLayoutManager);
            this.shoppingCartRecyclerView.setAdapter(this.shoppingCartAdapter);
        } else {
            Toast.makeText(this, "Error: Shopping cart was not passed to this view",
                    Toast.LENGTH_SHORT).show();
        }


    }

}
