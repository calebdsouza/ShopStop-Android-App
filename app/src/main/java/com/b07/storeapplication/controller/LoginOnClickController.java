package com.b07.storeapplication.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.View.AdminViewActivity;
import com.b07.storeapplication.View.CustomerViewActivity;
import com.b07.storeapplication.View.CustomerViewLoadAccount;
import com.b07.storeapplication.View.EmployeeViewActivity;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;
import com.b07.storeapplication.model.users.User;

import java.sql.SQLException;

import static com.b07.storeapplication.model.security.PasswordHelpers.comparePassword;
import com.b07.storeapplication.View.ContentShoppingCartRecyclerView;
/**
 * Created by cd on 2017-11-28.
 */

public class LoginOnClickController implements View.OnClickListener {
    private Context appContext;
    private EditText userId = null;
    private EditText password = null;

    /**
     * Construct an login button on click controller.
     *
     * @param context the activity context
     */
    public LoginOnClickController(Context context) {
        this.appContext = context;
        // Set the
        this.userId = (EditText) ((AppCompatActivity) appContext).findViewById(R.id.login_user_id);
        this.password = ((EditText)
                ((AppCompatActivity) appContext).findViewById(R.id.login_password));
    }

    /**
     * Helper function to convert user input to int from String
     *
     * @param input a String from the User
     * @return input now in integer form.
     */
    private static int inputConverter(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    /**
     * Overrides the onClick handler for this Button's activity view.
     *
     * @param view the view of the button which was clicked
     */
    @Override
    public void onClick(View view) {
        // Create a place to store a new intent to change the activity
        Intent afterLoginIntent = null;

        // Get string representation of the enter input
        String strId = this.userId.getText().toString();
        String strPassword = this.password.getText().toString();

        // Check if the given user id is valid else show error message
        if (this.validateUserId(strId)) {
            // Check if the given user password is valid else show error message
            if (this.validatePassword(strId, strPassword)) {
                // Try to construct user from db and change activity to correct user mood
                try {

                    // Construct user and get their role
                    User user = DatabaseSelectHelper.getUserDetails(Integer.parseInt(strId),
                            this.appContext);
                    String role = DatabaseSelectHelper.getRoleName(user.getRoleId(),
                            this.appContext);

                    // Check the user's role and change to the respective activity
                    if (role.equals("ADMIN")) {
                        afterLoginIntent = new Intent(this.appContext, AdminViewActivity.class);
                        //Admin admin = (Admin) user;
                        afterLoginIntent.putExtra("Admin", user);

                        this.appContext.startActivity(afterLoginIntent);
                        ((AppCompatActivity)this.appContext).finish();
                    } else if (role.equals("EMPLOYEE")) {
                        afterLoginIntent = new Intent(this.appContext, EmployeeViewActivity.class);
                        //Employee employee = (Employee) user;
                        afterLoginIntent.putExtra("Employee", user);
                        this.appContext.startActivity(afterLoginIntent);
                    } else if (role.equals("CUSTOMER")) {
                        ShoppingCart customerCart = new ShoppingCart((Customer)user);
                        if (DatabaseSelectHelper.getUserAccounts(user.getId(),
                                this.appContext).size() > 0) {
                            afterLoginIntent = new Intent(this.appContext,
                                    CustomerViewLoadAccount.class);
                        } else {
                            afterLoginIntent = new Intent(this.appContext,
                                    CustomerViewActivity.class);
                            afterLoginIntent.putExtra("ShoppingCartToView", customerCart);
                        }
                        //Customer customer = (Customer) user;
                        afterLoginIntent.putExtra("Customer", user);
                        this.appContext.startActivity(afterLoginIntent);
                        ((AppCompatActivity)this.appContext).finish();
                    }

                    // Clear input fields
                    this.userId.setText("");
                    this.password.setText("");

                } catch (SQLException e) {
                    Toast.makeText(appContext,"SQL Error: Unable to get user's info",
                            Toast.LENGTH_SHORT).show();
                }

            } else {
                this.password.setError("Invalid Password");
            }

        } else {
            this.userId.setError("Invalid Id");
        }
    }

    /**
     * This method checks if a User exists within the database and returns a boolean
     *
     * @param id the given id
     * @return true if user exists, false otherwise
     * @throws SQLException if a failure occurs
     */
    private boolean userExistence(String id) throws SQLException {
        // Find user with userId
        User user = null;
        int userId = -1;
        try {
            userId = inputConverter(id);
            user = DatabaseSelectHelper.getUserDetails(userId, this.appContext);
        } catch (NumberFormatException e) {
            Toast.makeText(appContext,"Value Error: Id contains only numbers",
                    Toast.LENGTH_SHORT).show();
        } finally {
            return user != null;
        }
    }

    /**
     * Return true if this user id is valid, otherwise return false.
     *
     * @param id the given id
     * @return true if this user id is valid
     */
    private boolean validateUserId(String id) {
        boolean isValid = false;
        try {
            // Check for a valid user id
            if (!TextUtils.isEmpty(id)) {
                if (
                        this.userExistence(id)) {
                    isValid = true;
                }
            } else {
                Toast.makeText(appContext,"Value Error: User id cannot be empty",
                        Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            // Set error message
            Toast.makeText(appContext,"SQL Error: Unable to check ID",
                    Toast.LENGTH_SHORT).show();
        } finally {
            return isValid;
        }
    }

    /**
     * Return true if the given passwords is correct for the given id, otherwise return false.
     *
     * @param id            the given user id corresponding to the given password
     * @param givenPassword the given password corresponding to the given id
     * @return true if the given passwords is correct for the given id
     */
    private boolean validatePassword(String id, String givenPassword) {
        // Create password check
        boolean isValid = false;
        int givenId = Integer.parseInt(this.userId.getText().toString());
        // Determine if the given password is correct
        try {
            if (!TextUtils.isEmpty(givenPassword)) {
                String dbPassword = DatabaseSelectHelper.getPassword(givenId, this.appContext);
                isValid = comparePassword(dbPassword, givenPassword);
            } else {
                Toast.makeText(appContext,"Value Error: Password cannot be empty",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            // Set error message
            Toast.makeText(appContext,"SQL Error: Unable to check password",
                    Toast.LENGTH_SHORT).show();
        } finally {
            return isValid;
        }
    }
}