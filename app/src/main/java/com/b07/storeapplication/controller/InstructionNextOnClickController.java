package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.View.InitializeAdmin;
import com.b07.storeapplication.View.LoginActivity;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseDriverAndroid;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.inventory.ItemTypes;
import com.b07.storeapplication.model.users.Roles;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cd on 2017-11-30.
 */

public class InstructionNextOnClickController implements View.OnClickListener {

    private TextView errorMsg = null;

    private Context appContext;

    /**
     * Construct an instruction next button on click controller.
     *
     * @param context the activity context
     */
    public InstructionNextOnClickController(Context context) {
        this.appContext = context;
        this.errorMsg = (TextView) ((AppCompatActivity) appContext).findViewById(
                R.id.initialize_instructions_error_msg);
    }

    /**
     * Overrides the onClick handler for this Button's activity view.
     *
     * @param view the view of the button which was clicked
     */
    @Override
    public void onClick(View view) {

        this.initiateDatabaseInfo();



        Intent intent = new Intent(appContext, InitializeAdmin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        ((AppCompatActivity)this.appContext).finish();


    }

    public void initiateDatabaseInfo() {
        try {
            boolean roleExists = false;

            // Add all the Roles to the Database
            for (Roles r : Roles.values()) {
                List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(appContext);
                for (int id : roleIds) {
                    if (DatabaseSelectHelper.getRoleName(id, appContext).equals(r.name())) {
                        roleExists = true;
                    }
                }

                if (!roleExists) {
                    DatabaseInsertHelper.insertRole(r.name(), appContext);
                } else {
                    roleExists = false;
                }
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL Error inserting roles in db",
                    Toast.LENGTH_SHORT).show();
        } catch (DatabaseInsertException e) {
            Toast.makeText(this.appContext, "Insertion error, inserting roles in db",
                    Toast.LENGTH_SHORT).show();
        }

        // Add all items to the Database
        // Current price starts at $1 and increases per Item $1
        int price = 1;
        try {
            if (DatabaseSelectHelper.getAllItems(this.appContext).size() <
                    ItemTypes.values().length) {
                for (ItemTypes items : ItemTypes.values()) {
                    BigDecimal itemPrice = BigDecimal.valueOf(price);
                    try {
                        DatabaseInsertHelper.insertItem(items.name(), itemPrice, appContext);
                    } catch (SQLException e) {
                        Toast.makeText(this.appContext, "SQL Error inserting roles in db",
                                Toast.LENGTH_SHORT).show();
                    } catch (DatabaseInsertException e) {
                        Toast.makeText(this.appContext, "Insertion error, inserting roles" +
                                " in db", Toast.LENGTH_SHORT).show();
                    }
                    price++;
                }
            }
        } catch (SQLException e) {

        }
        // Add an Inventory with all items currently in the database at quantity of 0
        try {
            if (DatabaseSelectHelper.getInventory(this.appContext).getItemMap().size() <
                    DatabaseSelectHelper.getAllItems(appContext).size()) {
                for (Item item : DatabaseSelectHelper.getAllItems(appContext)) {
                    DatabaseInsertHelper.insertInventory(item.getId(), 0, appContext);
                }
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL error, getting items from db or inserting " +
                    "inventory in db", Toast.LENGTH_SHORT).show();
        } catch (DatabaseInsertException e) {
            Toast.makeText(this.appContext, "Insertion error, inserting roles in db",
                    Toast.LENGTH_SHORT).show();
        }
    }
}