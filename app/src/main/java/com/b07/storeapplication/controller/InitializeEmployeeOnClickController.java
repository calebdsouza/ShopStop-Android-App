package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.View.LoginActivity;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.users.Roles;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by cd on 2017-11-30.
 */

public class InitializeEmployeeOnClickController implements View.OnClickListener {

    private Context appContext;
    private EditText givenAddress = null;
    private EditText givenAge = null;
    private EditText givenName = null;
    private EditText givenPassword = null;

    public InitializeEmployeeOnClickController (Context context) {
        this.appContext = context;
        this.givenAddress = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_address_emp);
        this.givenAge = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_age_emp);
        this.givenName = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_name_emp);
        this.givenPassword = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_password_emp);
    }

    /**
     * Overrides the onClick handler for this Button's activity view.
     *
     * @param view the view of the button which was clicked
     */
    @Override
    public void onClick(View view) {

        boolean wasInserted = false;
        Intent initializeAdminIntent = null;
        try {
            String srtAddress = this.givenAddress.getText().toString();
            int age = Integer.parseInt(this.givenAge.getText().toString());
            String strName = this.givenName.getText().toString();
            String strPassword = this.givenPassword.getText().toString();


            wasInserted = this.insertEmployee(strName, age, srtAddress, strPassword);


        } catch (NumberFormatException e) {
            this.givenAge.setError("Cannot be empty or contain letters");
        }  catch (SQLException e) {
            Toast.makeText(appContext,"SQL Error: Employee was not inserted",
                    Toast.LENGTH_SHORT).show();
        } catch (DatabaseInsertException e) {
            Toast.makeText(appContext,"Value Error: Employee was not inserted",
                    Toast.LENGTH_SHORT).show();
        }

        //System.out.print(wasInserted);
        if(wasInserted) {
            initializeAdminIntent = new Intent(appContext, LoginActivity.class);
            initializeAdminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            this.appContext.startActivity(initializeAdminIntent);
            ((AppCompatActivity)this.appContext).finish();

        } else {
            Toast.makeText(appContext,"Error: Employee was not inserted",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Insert given employee into the database
     *
     * @param name     the given name of this admin
     * @param age      the given age of this admin
     * @param address  the given address of this admin
     * @param password the given password of this admin
     * @return returns the role id of the user create
     */
    public boolean insertEmployee(String name, int age, String address, String password) throws
            SQLException, DatabaseInsertException {
        boolean wasCreated = false;

        int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password,
                this.appContext);
        List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(this.appContext);

        for (int roleId : roleIds) {
            if (Roles.EMPLOYEE.toString().equals(DatabaseSelectHelper.getRoleName(roleId,
                    this.appContext))) {
                int userRole = DatabaseInsertHelper.insertUserRole(userId, roleId,
                        this.appContext);
                Toast.makeText(appContext,"New Employee's ID: " + userId,
                        Toast.LENGTH_SHORT).show();

                wasCreated = (userId > 0) && (userRole > 0);
            }
        }
        return wasCreated;
    }

}
