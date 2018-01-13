package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.View.AdminViewGetIdDialog;
import com.b07.storeapplication.R;

/**
 * Created by cd on 2017-12-03.
 */

public class AdminViewInactiveOnClickController  implements View.OnClickListener {
    public Context appContext;
    private TextView adminAction;
    private AdminViewGetIdDialog adminViewGetIdDialog;

    /**
     * Constructs a view customer's inactive accounts button on click controller.
     *
     * @param context the activity context
     */
    public AdminViewInactiveOnClickController(Context context) {
        this.appContext = context;
        this.adminAction = ((AppCompatActivity) this.appContext).findViewById(
                R.id.admin_action);
        this.adminViewGetIdDialog = new AdminViewGetIdDialog();
    }


    @Override
    public void onClick(View view) {
        openAdminViewDialog();
        this.adminAction.setText("inactive");
        Toast.makeText(this.appContext, "Enter Customer Id Of Wanted Inactive Accounts",
                Toast.LENGTH_SHORT).show();
    }

    public void openAdminViewDialog() {
        adminViewGetIdDialog.show(
                ((AppCompatActivity) this.appContext).getSupportFragmentManager(), "Get id");
    }
}