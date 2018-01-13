package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.View.AdminViewGetIdDialog;
import com.b07.storeapplication.R;

import java.sql.SQLException;

/**
 * Created by cd on 2017-12-01.
 */

public class AdminViewPromoteOnClickController implements View.OnClickListener {
    public Context appContext;
    private TextView adminAction;
    private AdminViewGetIdDialog adminViewGetIdDialog;

    /**
     * Constructs a promote employee button on click controller.
     * @param context the activity context
     */
    public AdminViewPromoteOnClickController(Context context) {
        this.appContext = context;
        this.adminAction = ((AppCompatActivity) this.appContext).findViewById(
                R.id.admin_action);
        this.adminViewGetIdDialog = new AdminViewGetIdDialog();
    }

    @Override
    public void onClick(View view) {
        openAdminViewDialog();
        this.adminAction.setText("promote");
        Toast.makeText(this.appContext, "Enter Employee Id To Promote",
                Toast.LENGTH_SHORT).show();
    }

    public void openAdminViewDialog() {
        adminViewGetIdDialog.show(
                ((AppCompatActivity) this.appContext).getSupportFragmentManager(), "Get id");
        //this.adminViewGetIdDialog.getDialogEditText().setText("Enter Employee Id To Promote");
    }

}