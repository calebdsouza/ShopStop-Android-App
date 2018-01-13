package com.b07.storeapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.store.ItemizedSale;
import com.b07.storeapplication.model.store.Sale;
import com.b07.storeapplication.model.store.SalesLog;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

public class AdminViewSalesHistory extends AppCompatActivity {
    private RecyclerView salesHistoryRecyclerView = null;
    private RecyclerView.Adapter salesHistoryAdapter = null;
    private RecyclerView.LayoutManager salesHistoryLayoutManager = null;
    private BigDecimal sumOfTotalPrice  = new BigDecimal(0);
    private List<ItemizedSale> itemizedSales;
    private ArrayList<Integer> quantityList = new ArrayList<>();
    private String inventorySaleLog;
    private TextView inventorySalesLogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_sales_history);

        this.inventorySalesLogTextView = (TextView) findViewById(R.id.inventory_sales_log);

        ArrayList<SalesHistoryItem> salesHistory = new ArrayList<>();
        salesHistory = createCustomerSalesHistory(salesHistory);
        this.inventorySaleLog = createInventorySalesHistory();
        this.inventorySalesLogTextView.setText(this.inventorySaleLog);



        // Get the
        this.salesHistoryRecyclerView = findViewById(R.id.sales_history_recycler_view);
        this.salesHistoryRecyclerView.setHasFixedSize(true);
        this.salesHistoryLayoutManager = new LinearLayoutManager(this);
        this.salesHistoryAdapter = new AdminViewSalesHistoryAdaptor(salesHistory);

        this.salesHistoryRecyclerView.setLayoutManager(this.salesHistoryLayoutManager);
        this.salesHistoryRecyclerView.setAdapter(this.salesHistoryAdapter);
    }

    public ArrayList<SalesHistoryItem> createCustomerSalesHistory(
            ArrayList<SalesHistoryItem> salesHistory) {

        try {
            SalesLog sales = DatabaseSelectHelper.getSales(this);
            this.itemizedSales = DatabaseSelectHelper.getItemizedSales(sales, this);

            for (Sale sale : sales.getSalesList()) {
                String customer = sale.getUser(this).getName();
                int purchase = sale.getId();
                BigDecimal totalPrice = sale.getTotalPrice().setScale(2,
                        BigDecimal.ROUND_HALF_EVEN);
                String itemized = "Itemized Breakdown: ";
                sumOfTotalPrice = sumOfTotalPrice.add(sale.getTotalPrice());
                for (ItemizedSale itemSale : itemizedSales) {
                    if (itemSale.getSaleId() == sale.getId()) {
                        itemized += "\nItem: " + DatabaseSelectHelper.getItem(itemSale
                                .getItemId(), this).getName() +
                                ". Quantity: " + itemSale.getItemQuantity();
                    }
                }
                salesHistory.add(new SalesHistoryItem(customer, "" + purchase,
                        totalPrice.toString(), itemized));
            }
        } catch (SQLException e) {
            Toast.makeText(this, "SQL Error: unable to get customer sale info" +
                            " from db", Toast.LENGTH_SHORT).show();
        }

        return salesHistory;
    }

    public String createInventorySalesHistory() {
        String result = "";
        int quantitySum = 0;
        int itemNumber = 1;
        try {

            while (itemNumber < DatabaseSelectHelper.getAllItems(this).size()) {
                for (ItemizedSale itemSale : itemizedSales) {
                    if (itemSale.getItemId() == itemNumber) {
                        this.quantityList.add(itemSale.getItemQuantity());
                    }
                }
                    result += "\nNumber " + DatabaseSelectHelper.getItem(itemNumber,
                            this).getName();
                    for (int i = 0; i < this.quantityList.size(); i++ ) {
                        quantitySum = this.quantityList.get(i);
                    }
                    result += "\n\tSold: " + quantitySum;

                    itemNumber++;

            }

            result += "\nTOTAL SALES: " + sumOfTotalPrice.setScale(2,
                    BigDecimal.ROUND_HALF_EVEN);

        } catch (SQLException e) {
            Toast.makeText(this, "SQL Error: unable to get inventory sale info" +
                            " from db", Toast.LENGTH_SHORT).show();
        }

        return result;
    }
}
