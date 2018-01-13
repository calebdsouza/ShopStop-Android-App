package com.b07.storeapplication.View;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ShoppingCart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


public class CustomerViewItemListAdapter extends
        RecyclerView.Adapter<CustomerViewItemListAdapter.CustomerViewItemListViewHolder> {
    private HashMap<Item, Integer> shoppingCart;
    private List<Item> shoppingCartItems;

    public static class CustomerViewItemListViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public TextView itemQuantity;
        public TextView itemPrice;

        public CustomerViewItemListViewHolder(View itemView) {
            super(itemView);
            this.itemName = (TextView) itemView.findViewById(R.id.item_name_text_view);
            this.itemQuantity = (TextView) itemView.findViewById(R.id.item_quantity_text_view);
            this.itemPrice = (TextView) itemView.findViewById(R.id.item_total_price_text_view);

        }
    }

    public CustomerViewItemListAdapter(HashMap<Item, Integer> shoppingCart, List<Item> items) {
        this.shoppingCart = shoppingCart;
        this.shoppingCartItems = items;


    }

    @Override
    public CustomerViewItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.customer_view_item_card_view, parent, false);
        CustomerViewItemListViewHolder viewHolder = new CustomerViewItemListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerViewItemListViewHolder holder, int position) {
        Item currentItem = shoppingCartItems.get(position);
        holder.itemName.setText(currentItem.getName());
        holder.itemQuantity.setText(this.shoppingCart.get(currentItem).toString());
        holder.itemPrice.setText(currentItem.getPrice().toString());
    }

    @Override
    public int getItemCount() {
        return this.shoppingCartItems.size();
    }

}



/*private String[] itemDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CustomerViewItemListAdapter(String[] itemDataset) {
        this.itemDataset = itemDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomerViewItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        // create a new view
        TextView itemNameTextView = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_view_item_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder viewHolder = new ViewHolder(itemNameTextView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(itemDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemDataset.length;
    }*/


