package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.b07.storeapplication.View.LoginActivity;

/**
 * Created by cd on 2017-12-03.
 */

public class LogoutOnClickController implements View.OnClickListener {
    public Context appContext;


    /**
     * Constructs a admin logout button on click controller.
     *
     * @param context the activity context
     */
    public LogoutOnClickController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View view) {
        Intent logoutIntent = new Intent(this.appContext, LoginActivity.class);
        this.appContext.startActivity(logoutIntent);
        ((AppCompatActivity)this.appContext).finish();
    }
}