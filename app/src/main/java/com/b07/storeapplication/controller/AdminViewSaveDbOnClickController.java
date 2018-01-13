package com.b07.storeapplication.controller;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.b07.storeapplication.model.database.DatabaseSerializer;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by dhairyadave on 2017-12-04.
 */

public class AdminViewSaveDbOnClickController implements View.OnClickListener {
    private Context appContext;

    public AdminViewSaveDbOnClickController(Context context) {
        this.appContext = context;
    }

    @Override
    public void onClick(View view) {

        try {
            DatabaseSerializer.serialize(this.appContext);
        } catch (IOException ioe) {
            Toast.makeText(appContext, "I/O Error", Toast.LENGTH_SHORT).show();
        } catch (SQLException sqle) {
            Toast.makeText(appContext, "SQL Error", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(appContext, "Backed-up Database", Toast.LENGTH_SHORT).show();
    }
}