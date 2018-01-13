package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.database.CursorIndexOutOfBoundsException;

import com.b07.storeapplication.View.AdminViewGetIdDialog;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.users.Admin;
import com.b07.storeapplication.model.users.Employee;
import com.b07.storeapplication.model.users.User;
import com.b07.storeapplication.model.users.Roles;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cd on 2017-12-03.
 */

public class AdminVeiwGetIdDialogAfterClickedController {
    private EditText givenId = null;
    private Admin currentAdmin = null;
    private Context appContext;

    /**
     * Construct the get id dialog after promote button was clicked controller.
     * @param context the activity context
     * @param admin the Admin Object who logged in
     */
    public AdminVeiwGetIdDialogAfterClickedController(Context context, Admin admin) {
        this.appContext = context;
        this.currentAdmin = admin;

        this.givenId = (EditText) ((AppCompatActivity) this.appContext).findViewById(
                R.id.admin_view_get_id);
    }

    /**
     * Given a customer's id get a list of all their inactive accounts.
     * @param id the customer's id
     * @return the list of all active accounts
     */
    public String viewActiveAccounts(String id) {
        String result = "This customer has no accounts";
        List <Integer> activeAccounts = new ArrayList<>();
        try {
            User user = DatabaseSelectHelper.getUserDetails(inputConverter(id),
                    this.appContext);
            if (user != null) {
                String roleName = DatabaseSelectHelper.getRoleName(user.getRoleId(),
                        appContext);
                if (roleName.equals("CUSTOMER")) {
                    activeAccounts = DatabaseSelectHelper.getUserActiveAccounts(
                            inputConverter(id), this.appContext);
                    if (activeAccounts.size() > 0) {
                        result = "\nAccountIds of Active Accounts: ";
                        for (int index = 0; index < activeAccounts.size(); index++) {
                            result += "\n" + activeAccounts.get(index).toString();
                        }
                    }
                } else {
                    Toast.makeText(this.appContext, "Value Error: Given id is not a customer",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                result = "null";

                Toast.makeText(this.appContext, "Value Error: Given id does not exist",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL Error: Could not get customer's active" +
                    " accounts form db", Toast.LENGTH_SHORT).show();
        } catch (CursorIndexOutOfBoundsException ex) {
            Toast.makeText(this.appContext, "SQL Error: There are no customers",
                    Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    /**
     * Given a customer's id get a list of all their inactive accounts.
     * @param id the customer's id
     * @return the list of all inactive accounts
     */
    public String viewInactiveAccounts(String id) {
        String result = "This Customer has no accounts";
        List <Integer> inactiveAccounts = new ArrayList<>();
        try {
            User user = DatabaseSelectHelper.getUserDetails(inputConverter(id),
                    this.appContext);
            if (user != null) {
                String roleName = DatabaseSelectHelper.getRoleName(user.getRoleId(),
                        appContext);
                if (roleName.equals("CUSTOMER")) {
                    inactiveAccounts = DatabaseSelectHelper.getUserInactiveAccounts(
                            inputConverter(id), this.appContext);

                    result = "AccountIds of Inactive Accounts: ";
                    for (int index = 0; index < inactiveAccounts.size(); index++) {
                        result += "\n" + inactiveAccounts.get(index).toString();
                    }
                } else {
                    Toast.makeText(this.appContext, "Value Error: Given id is not a customer",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                result = "null";
                Toast.makeText(this.appContext, "Value Error: Given id does not exist",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL Error: Could not get customer's active" +
                    " accounts form db", Toast.LENGTH_SHORT).show();
        } catch (CursorIndexOutOfBoundsException ex) {
            Toast.makeText(this.appContext, "SQL Error: There are no customers",
                    Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    /**
     * Given the id entered in the admin view dialog, verify given id is an valid Employee
     * and change employee's role to admin.
     * @param id the employee to promote
     */
    public void promoteEmployee(String id) {
        Employee employee = null;

        //check employee not already admin
        if (userExistence(id)) {
            // Try to get the given employee's info from the database
            try {
                employee = ((Employee)
                        DatabaseSelectHelper.getUserDetails(inputConverter(id), this.appContext));
            } catch (SQLException e) {
                Toast.makeText(this.appContext, "SQL Error: Could not get employee from db",
                        Toast.LENGTH_SHORT).show();
            }

            // Check if there is a current admin
            assert this.currentAdmin != null;

            // Try to change the given user's role form employee to an admin
            try {
                // Determine if the given employee was promoted to admin
                boolean promoted = this.currentAdmin.promoteEmployee(employee, this.appContext);
                // Check if the given employee was promoted to admin
                if (promoted) {
                    Toast.makeText(this.appContext, "Employee Was Promoted!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this.appContext, "Employee was not promoted",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (SQLException e) {
                Toast.makeText(this.appContext, "SQL Error: Could not change employee to" +
                        " admin in db", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this.appContext, "The given id does not exist",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Helper function to convert user input to int from String
     *
     * @param input a String from the User
     * @return input now in integer form.
     */
    private int inputConverter(String input) {
        // Check if input was converted
        int convertedValue = 0;
        // Try to convert input
        try {
            convertedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this.appContext, "ID cannot be empty" +
                    " or contain letters", Toast.LENGTH_SHORT).show();
        }
        // Return converted input
        return convertedValue;
    }

    /**
     * This method checks if a User exists within the database and returns a boolean
     *
     * @param id the given id
     * @return true if user exists, false otherwise
     * @throws SQLException if a failure occurs
     */
    private boolean userExistence(String id) {
        // Find user with userId
        User user = null;
        int roleId = -1;
        int userId = -1;
        boolean isEmployee = false;

        // try to determine if the given user exists
        try {
            // Get user's information from database
            userId = inputConverter(id);
            roleId = DatabaseSelectHelper.getUserRoleId(userId, this.appContext);

            // Determine if the given user's role
            for (Roles role : Roles.values()) {
                if (role.toString().equals(DatabaseSelectHelper.getRoleName(roleId,
                        this.appContext))){
                    isEmployee = true;
                }
            }

            // Check  if given user is an Employee
            if (isEmployee) {
                user = DatabaseSelectHelper.getUserDetails(userId, this.appContext);
            } else {
                Toast.makeText(this.appContext, "Given id is not an employee",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException sqle) {
            this.givenId.setError("Invalid ID");
            Toast.makeText(this.appContext, "SQL Error: Can not get user info from db",
                    Toast.LENGTH_SHORT).show();
        }catch (NumberFormatException e) {
            this.givenId.setError("ID should contain only numbers");
        } finally {
            return user != null;
        }
    }
}