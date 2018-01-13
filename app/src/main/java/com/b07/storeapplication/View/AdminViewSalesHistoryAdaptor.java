package com.b07.storeapplication.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.b07.storeapplication.R;

import java.util.ArrayList;

/**
 * Created by cd on 2017-12-03.
 */

public class AdminViewSalesHistoryAdaptor extends
        RecyclerView.Adapter<AdminViewSalesHistoryAdaptor.AdminViewSalesHistoryViewHolder> {
    private ArrayList<SalesHistoryItem> salesHistoryItemList;

    public static class AdminViewSalesHistoryViewHolder extends RecyclerView.ViewHolder {
        public TextView customer;
        public TextView purchaseNumber;
        public TextView totalPurchasePrice;
        public TextView itemizedBreakdown;

        public AdminViewSalesHistoryViewHolder(View itemView) {
            super(itemView);
            this.customer = (TextView) itemView.findViewById(R.id.sales_history_item_customer);
            this.purchaseNumber = (TextView) itemView.findViewById(
                    R.id.sales_history_item_purchase);
            this.totalPurchasePrice = (TextView) itemView.findViewById(
                    R.id.sales_history_item_price);
            this.itemizedBreakdown = (TextView) itemView.findViewById(
                    R.id.sales_history_item_itemized);
        }
    }

    public AdminViewSalesHistoryAdaptor(ArrayList<SalesHistoryItem> salesHistoryList) {
        this.salesHistoryItemList = salesHistoryList;

    }

    @Override
    public AdminViewSalesHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the sales history View
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.admin_view_sales_history_customer_item, parent, false);
        // Create a sales history view holder
        AdminViewSalesHistoryViewHolder viewHolder = new AdminViewSalesHistoryViewHolder(view);
        // Return sales history view holder
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdminViewSalesHistoryViewHolder holder, int position) {
        // Get the current sales history item to be displayed
        SalesHistoryItem currentSalesHistoryItemList = salesHistoryItemList.get(position);

        // Get information form sales history item and pass it to given view holder
        holder.customer.setText(currentSalesHistoryItemList.getCustomer());
        holder.purchaseNumber.setText(currentSalesHistoryItemList.getPurchaseNumber());
        holder.totalPurchasePrice.setText(currentSalesHistoryItemList.getTotalPurchasePrice());
        holder.itemizedBreakdown.setText(currentSalesHistoryItemList.getItemizedBreakdown());
    }


    @Override
    public int getItemCount() {
        // Get the number of all sale History items
        return salesHistoryItemList.size();
    }

}
