package com.b07.storeapplication.model.database;

import android.content.Context;

import com.b07.storeapplication.model.users.Roles;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.b07.storeapplication.model.users.Roles.values;

public class DatabaseUpdateHelper {

    /**
     * Given a role id it's new Role name, Update the ROLES Table in this valid
     * Database, and return true if the name was updated. Otherwise returns false
     *
     * @param name    new name to update the ROLES Table with
     * @param id      the ROLE id who's Role name needs to be updated
     * @param context the context of the activity which this method is called
     * @return True if the Role name was updated, otherwise false
     * @throws SQLException              error regarding sql
     * @throws InvalidParameterException the given role name is not valid
     */
    public static boolean updateRoleName(String name, int id, Context context) throws SQLException,
            InvalidParameterException {
        // Check if given name is null
        if (name == null) {
            throw new NullPointerException("Role cannot be null");
        }
        // Check if given role name is in Enum
        for (Roles r : values()) {
            if (r.name().equals(name)) {
                // Create an instance of the database
                DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
                boolean complete = db.updateRoleName(name, id);
                db.close();
                return complete;
            }
        }
        // Throw exception if role is not in enum
        throw new InvalidParameterException("Role must be from enum Roles");
    }

    /**
     * Given a User id it's new User name, Update the USER Table in this valid
     * Database, and return true if the name was updated. Otherwise returns false
     *
     * @param name    new name to update the USER Table with
     * @param userId  the User id who's User name needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the User name was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateUserName(String name, int userId, Context context) throws SQLException {
        // checking name is not null
        if (name == null) {
            throw new NullPointerException("User input cannot be null");
        }

        // if name is empty string throw exception
        if (name.length() == 0) {
            throw new InvalidParameterException("Please do not have User name empty.");
        }

        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given role name was updated to database successfully
        boolean complete = db.updateUserName(name, userId);
        // Close database and return whether the role was updated to database successfully
        db.close();
        return complete;
    }

    /**
     * Given a valid User id and it's new age, Update the USER Table in this valid
     * Database, and return true if the age was updated. Otherwise returns false
     *
     * @param age     new age to update the USER Table with
     * @param userId  the User id who's User age needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the User age was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateUserAge(int age, int userId, Context context) throws SQLException {
        // ensure age is positive number
        if (age <= 0) {
            throw new InvalidParameterException("Please have age being greater than zero.");
        }
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given user age was updated to database successfully
        boolean complete = db.updateUserAge(age, userId);
        // Close database and return whether the age was updated to database successfully
        db.close();
        return complete;
    }

    /**
     * Given a valid User id and it's new address, Update the USER Table in this valid
     * Database, and return true if the address was updated. Otherwise returns false
     *
     * @param address new address to update the USER Table with
     * @param userId  the User id who's User address needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the User address was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateUserAddress(String address, int userId, Context context) throws
            SQLException {
        // ensure address less than 100 characters
        if (address.length() > 100) {
            throw new InvalidParameterException("User address must be less than 100 characters.");
        }
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given user address was updated to database successfully
        boolean complete = db.updateUserAddress(address, userId);
        // Close database and return whether the address was updated to database successfully
        db.close();
        return complete;

    }

    /**
     * Given a valid User id and it's new Role id, Update the USER Table in this valid
     * Database, and return true if the Role id was updated. Otherwise returns false
     *
     * @param roleId  new Role id to update the USER Table with
     * @param userId  the User id who's User address needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the User Role id was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateUserRole(int roleId, int userId, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given user's role was updated to database successfully
        boolean complete = db.updateUserRole(roleId, userId);
        // Close database and return whether the role was updated to database successfully
        db.close();
        return complete;

    }

    /**
     * Given a valid Item id and it's new name, Update the USER Table in this valid
     * Database, and return true if the name was updated. Otherwise returns false
     *
     * @param name    new name to update the ITEMS Table with
     * @param itemId  the Item id who's Item's name a needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the Item name was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateItemName(String name, int itemId, Context context) throws
            SQLException {
        // name must be less than 64 characters
        if (name.length() >= 64) {
            throw new InvalidParameterException("Item name must be less than 64 characters.");
        }
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given item's name was updated to database successfully
        boolean complete = db.updateItemName(name, itemId);
        // Close database and return whether the name was updated to database successfully
        db.close();
        return complete;

    }

    /**
     * Given a valid Item id and it's price name, Update the USER Table in this valid
     * Database, and return true if the price was updated. Otherwise returns false
     *
     * @param price   new price to update the ITEMS Table with
     * @param itemId  the Item id who's Item's price a needs to be updated
     * @param context the context of the activity which this method is called
     * @return true if the Item price was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateItemPrice(BigDecimal price, int itemId, Context context) throws SQLException {
        // ensure price is greater than zero
        BigDecimal x = BigDecimal.valueOf(0.00);
        if ((price.compareTo(x) == 0) || (price.compareTo(x) == -1)) {
            throw new InvalidParameterException("Price to charge a customer must be greater than zero.");
        }

        // ensuring price has two decimal points of precision
        price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given item's price was updated to database successfully
        boolean complete = db.updateItemPrice(price, itemId);
        // Close database and return whether the price was updated to database successfully
        db.close();
        return complete;
    }

    /**
     * Given a valid Item id and it's quantity, Update the USER Table in this valid
     * Database, and return true if the quantity was updated. Otherwise returns false
     *
     * @param quantity new quantity to update the ITEMS Table with
     * @param itemId   the Item id who's Item's price a needs to be updated
     * @param context  the context of the activity which this method is called
     * @return true if the Item quantity was updated, otherwise false
     * @throws SQLException error regarding sql
     */
    public static boolean updateInventoryQuantity(int quantity, int itemId, Context context) throws
            SQLException {
        //ensure quantity cannot be negative
        if (quantity < 0) {
            throw new InvalidParameterException("Quantity cannot be negative in an Inventory.");
        }
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if given item's quantity was updated to database successfully
        boolean complete = db.updateInventoryQuantity(quantity, itemId);
        // Close database and return whether the quantity was updated to database successfully
        db.close();
        return complete;
    }
    /**
     * Update the status of an account.
     * @param accountId the id of the account.
     * @param active the status the account should receive.
     * @param context  the context of the activity which this method is called
     * @return true if successful, false otherwise.
     */
    public static boolean updateAccountStatus(int accountId, boolean active, Context context)
            throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if the given account was updated
        boolean complete = db.updateAccountStatus(accountId, active);
        db.close();
        return complete;
    }

    /**
     * Update the status of an account.
     *
     * @param context  the context of the activity which this method is called
     * @return true if successful, false otherwise.
     */
    public static boolean updateUserPassword(String password, int userId, Context context)
            throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Determine if the given account was updated
        boolean complete = db.updateUserPassword(password, userId);
        db.close();
        return complete;
    }



}