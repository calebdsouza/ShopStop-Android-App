package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.View.AdminViewGetItemDialog;

/**
 * Created by cd on 2017-12-03.
 */

public class AdminViewAddNewItemOnClickController implements View.OnClickListener {
    public Context appContext;
    private AdminViewGetItemDialog adminViewGetItemDialog;

    /**
     * Constructs a promote employee button on click controller.
     *
     * @param context the activity context
     */
    public AdminViewAddNewItemOnClickController(Context context) {
        this.appContext = context;

        this.adminViewGetItemDialog = new AdminViewGetItemDialog();
    }

    @Override
    public void onClick(View view) {
        openAdminViewDialog();
        Toast.makeText(this.appContext, "Enter new item name to add with all caps and" +
                " underscore between words", Toast.LENGTH_LONG).show();
    }

    public void openAdminViewDialog() {
        adminViewGetItemDialog.show(
                ((AppCompatActivity) this.appContext).getSupportFragmentManager(), "Get item");
        //this.adminViewGetIdDialog.getDialogEditText().setText("Enter Employee Id To Promote");
    }
}