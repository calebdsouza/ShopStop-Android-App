package com.b07.storeapplication.controller;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.users.User;

import java.sql.SQLException;

/**
 * Created by cd on 2017-12-02.
 */

public class InputHelper {

    /**
     * This method checks if a User exists within the database and returns a boolean
     *
     * @param id the given id
     * @return true if user exists, false otherwise
     * @throws SQLException if a failure occurs
     */
    private static boolean userExistence(String id, EditText editText, Context context) throws
            SQLException {
        // Find user with userId
        User user = null;
        int userId = -1;
        try {
            userId = inputConverter(id, editText, context);
            user = DatabaseSelectHelper.getUserDetails(userId,context);
        } catch (NumberFormatException e) {
            editText.setError("ID should contain only numbers");
        } finally {
            return user != null;
        }
    }

    /**
     * Helper function to convert user input to int from String
     *
     * @param input a String from the User
     * @return input now in integer form.
     */
    private static int inputConverter(String input, EditText editText, Context context) throws
            NumberFormatException {
        // Check if input was converted
        int convertedValue = 0;
        // Try to convert input
        try{
            convertedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            editText.setError("Invalid Input");
            Toast.makeText(context,"ID & Quantity cannot be empty or contain letters",
                    Toast.LENGTH_SHORT).show();
        }
        // Return converted input
        return convertedValue;
    }
}