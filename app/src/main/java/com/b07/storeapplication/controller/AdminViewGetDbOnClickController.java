package com.b07.storeapplication.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.model.database.DatabaseSerializer;

/**
 * Created by dhairyadave on 2017-12-04.
 */

public class AdminViewGetDbOnClickController implements View.OnClickListener {
    private Context appContext;

    public AdminViewGetDbOnClickController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View view) {

        try {
            DatabaseSerializer.deserialize(this.appContext);
        } catch (Exception e) {
            Toast.makeText(appContext, "Error", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(appContext, "Upload Database", Toast.LENGTH_SHORT).show();
    }
}