package com.b07.storeapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.store.ItemizedSale;
import com.b07.storeapplication.model.store.SalesLog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopTenItems extends AppCompatActivity {
    private TextView topTenView;
    private RecyclerView salesHistoryRecyclerView = null;
    private RecyclerView.Adapter salesHistoryAdapter = null;
    private RecyclerView.LayoutManager salesHistoryLayoutManager = null;
    private List<ItemizedSale> itemizedSales;
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private HashMap<Integer, Integer> totalQuantities = new HashMap<>();
    private String inventorySaleLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten_items);

        this.topTenView = findViewById(R.id.top_ten_view);
        this.topTenView.setText(getTopTen());
    }

    public String getTopTen() {
        String result = "";

        int quantitySum = 0;
        int itemNumber = 1;
        int count = 1;
        Map.Entry<Integer, Integer> maxEntry;
        try {
            SalesLog sales = DatabaseSelectHelper.getSales(this);
            this.itemizedSales = DatabaseSelectHelper.getItemizedSales(sales, this);
            while (itemNumber < DatabaseSelectHelper.getAllItems(this).size()) {
                for (ItemizedSale itemSale : this.itemizedSales) {
                    if (itemSale.getItemId() == itemNumber) {
                        this.quantityList.add(itemSale.getItemQuantity());
                    }
                }

                for (int i = 0; i < this.quantityList.size(); i++ ) {
                    quantitySum = this.quantityList.get(i);
                }
                totalQuantities.put(itemNumber, quantitySum);
                itemNumber++;
                quantityList.clear();
            }
            result += "\n\t=================";
            result += "\n\tTop 10 Items Sold";
            result += "\n\t=================";
            itemNumber = 1;
            while (count < 10 && totalQuantities.size() > 0) {
                int maxKey = -1;
                int maxValue = (Collections.max(totalQuantities.values()));
                for (Map.Entry<Integer, Integer> entry: totalQuantities.entrySet()) {
                    if (entry.getValue() == maxValue) {
                        maxKey = entry.getKey();
                    }
                }
                result += "\n\t#" + count + " - " + DatabaseSelectHelper.getItem(maxKey,
                        this).getName()
                        + " - Item ID: " + maxKey;
                totalQuantities.remove(maxKey);
                count++;
            }
        } catch (SQLException e) {
            Toast.makeText(this, "SQL Error: unable to get inventory sale info" +
                    " from db", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException ne) {
            Toast.makeText(this, "Could not get itemized sales",
                    Toast.LENGTH_SHORT).show();
        }

        return result;
    }

}