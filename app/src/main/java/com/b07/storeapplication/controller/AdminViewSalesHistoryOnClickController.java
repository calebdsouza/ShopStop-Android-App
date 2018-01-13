package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.b07.storeapplication.View.AdminViewSalesHistory;


/**
 * Created by cd on 2017-12-03.
 */

public class AdminViewSalesHistoryOnClickController implements View.OnClickListener {
    public Context appContext;

    /**
     * Constructs sales history button on click controller.
     *
     * @param context the activity context
     */
    public AdminViewSalesHistoryOnClickController(Context context) {
        this.appContext = context;

    }

    @Override
    public void onClick(View view) {
        Intent salesHistoryIntent = new Intent(appContext, AdminViewSalesHistory.class);

        appContext.startActivity(salesHistoryIntent);
    }

}