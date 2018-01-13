package com.b07.storeapplication.model.database;

import android.content.Context;

import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.users.Roles;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.sql.SQLException;

import static com.b07.storeapplication.model.users.Roles.values;


public class DatabaseInsertHelper {

    /**
     * Given the valid name of a new role, insert the Role into the ROLES Table
     * in this valid Database, and return the id generated for the new Role.
     *
     * @param name    - the name of the new Role to be inserted into the ROLES Table
     * @param context - the context of the activity which this method is called
     * @return the id generated for the new Role after it is inserted into the ROLES Table
     * @throws SQLException              - Error regarding sql
     * @throws DatabaseInsertException   - Error the given role could not be interest into the db
     * @throws InvalidParameterException - the given role name is not valid
     */
    public static int insertRole(String name, Context context) throws SQLException,
            DatabaseInsertException, InvalidParameterException {
        // Check if the the given name is null
        if (name == null) {
            throw new NullPointerException("Role cannot be null");
        }
        // Check if the given role name is in Enum
        for (Roles r : values()) {
            if (r.name().equals(name)) {
                // Create an instance of the database
                DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
                // Insert into the database and add get it's id key
                int roleId = (int) db.insertRole(name);
                // Close database and return the unique generated id key upon insertion
                db.close();
                return roleId;
            }
        } //Role not in enum than throw exception
        throw new InvalidParameterException("Role must be from enum Roles");
    }

    /**
     * Given valid User details for a new User, insert the Role into the USERS Table
     * in this valid Database, and return the id generated for the new Role.
     *
     * @param name     - the name for this User
     * @param age      - the age for this User
     * @param address  - the address of this User, up to 100 character long
     * @param password - this User's password (not hashed)
     * @param context  - the context of the activity which this method is called
     * @return return the id generated for the new User
     * @throws SQLException            - error regarding sql
     * @throws DatabaseInsertException - error the given user could not be inserted into the db
     */
    public static int insertNewUser(String name, int age, String address, String password,
                                    Context context) throws SQLException, InvalidParameterException
            , NullPointerException {

        // check all parameters are not null
        if (name == null || address == null || password == null) {
            throw new NullPointerException("User input cannot be null");
        }

        // if name is empty string throw exception
        if (name.length() == 0) {
            throw new InvalidParameterException("Please do not have User name empty.");
        }

        // ensure age is positive number
        if (age <= 0) {
            throw new InvalidParameterException("Please have age being greater than zero.");
        }

        // ensure address less than 100 characters
        if (address.length() > 100) {
            throw new InvalidParameterException("User address must be less than 100 characters.");
        }

        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert the given new user into the database
        int userId = (int) db.insertNewUser(name, age, address, password);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return userId;
    }

    /**
     * Insert the given relationship between a valid User id and  valid Role Id
     * in the USERROLE Table of this valid Database, and return the generated
     * unique key id for the given relationship.
     *
     * @param userId  - id of the User in the given UserRole relationship
     * @param roleId  - id of the Role in the given UserRole relationship
     * @param context - the context of the activity which this method is called
     * @return unique key for the given UserRole relationship generated after insertion in db
     * @throws SQLException            - error regarding sql
     * @throws DatabaseInsertException - error inserting the given user/role relation into the db
     */
    public static int insertUserRole(int userId, int roleId, Context context) throws SQLException,
            DatabaseInsertException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert the user-role relation into the database
        int userRoleId = (int) db.insertUserRole(userId, roleId);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return userRoleId;
    }

    /**
     * Given a valid name and valid price, insert a new Item into the ITEM Table of this
     * valid Database, and return the generated unique key id.
     *
     * @param name    - given name of Item to be inserted
     * @param price   - price given price of Item to be inserted
     * @param context - the context of the activity which this method is called
     * @return the unique key id for the item generated upon insertion
     * @throws SQLException            - error regarding sql
     * @throws DatabaseInsertException - error inserting the given item into the db
     */
    public static int insertItem(String name, BigDecimal price, Context context)
            throws SQLException, DatabaseInsertException {

        // check that name is not null
        if (name == null) {
            throw new NullPointerException("Item name cannot be null");
        }

        // name must be less than 64 characters
        if (name.length() >= 64) {
            throw new InvalidParameterException("Item name must be less than 64 characters.");
        }

        // ensure price is greater than zero
        BigDecimal x = BigDecimal.valueOf(0.00);
        if ((price.compareTo(x) == 0) || (price.compareTo(x) == -1)) {
            throw new InvalidParameterException("Price to charge a customer must be greater than zero.");
        }

        // ensuring price has two decimal points of precision
        price = price.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert the the given item into the database
        int itemId = (int) db.insertItem(name, price);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return itemId;
    }

    /**
     * Given a valid Item id and  it's valid quantity, insert it into the INVENTORY
     * Table of this valid Database, and return the generated unique key id.
     *
     * @param itemId   itemId the item id to be added to the INVENTORY
     * @param quantity quantity the quantity of corresponding Item id
     * @param context  the context of the activity which this method is called
     * @return the unique key id for the given Inventory generated upon insertion
     * @throws SQLException            error regarding sql
     * @throws DatabaseInsertException error inserting the given inventory into the db
     */
    public static int insertInventory(int itemId, int quantity, Context context)
            throws SQLException, DatabaseInsertException {

        //ensure quantity cannot be negative
        if (quantity < 0) {
            throw new InvalidParameterException("Quantity cannot be negative in an Inventory.");
        }

        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert the given inventory into the database
        int inventoryId = (int) db.insertInventory(itemId, quantity);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return inventoryId;
    }

    /**
     * Given a valid User id and it's valid total price, insert  the Sale into the SALES
     * Table of this valid Database, and return the generated unique key id.
     *
     * @param userId     id of the user for the Sale
     * @param totalPrice total price of the Sale
     * @param context    the context of the activity which this method is called
     * @return the unique key id generated upon insertion
     * @throws SQLException            error regarding sql
     * @throws DatabaseInsertException error inserting the given sale into the db
     */
    public static int insertSale(int userId, BigDecimal totalPrice, Context context)
            throws SQLException, DatabaseInsertException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert the given sale into the database
        int saleId = (int) db.insertSale(userId, totalPrice);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return saleId;
    }

    /**
     * Given a valid Sale id, Item id total price, and quantity, insert  the Sale
     * into the ITEMIZEDSALE Table of this valid Database, and return the
     * generated unique key id.
     *
     * @param saleId   the Sale id to be added to the Itemized
     * @param itemId   itemId the item id to be added to the ITEMIZEDSALE
     * @param quantity the quality of the times sold in the given Sale id
     * @param context  the context of the activity which this method is called
     * @return the unique key id generated upon insertion of the itemized sale
     * @throws SQLException
     * @throws DatabaseInsertException
     */
    public static int insertItemizedSale(int saleId, int itemId, int quantity, Context context)
            throws SQLException, DatabaseInsertException {
        // Check for a valid given argmuents
        if (saleId <= 0) {
            throw new InvalidParameterException("Sale id for a sale must be greater than 0");
        }
        if (itemId <= 0) {
            throw new InvalidParameterException("Item id for a sale must be greater than 0");
        }
        if (quantity <= 0) {
            throw new InvalidParameterException("Quantity for a sale must be greater than 0");
        }

        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert itemized sale into the database
        int itemizedId = (int) db.insertItemizedSale(saleId, itemId, quantity);
        // Close the database connection and return the unique generated id key upon insertion
        db.close();
        return itemizedId;
    }

    public static int insertAccount(int userId, boolean isActive, Context context) throws
            SQLException, DatabaseInsertException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Insert account into database
        int accountId = (int) db.insertAccount(userId, isActive);
        // Close the database and return the unique generated id key upon insertion
        db.close();
        return accountId;
    }

    public static int insertAccountLine(int accountId, int itemId, int quantity, Context context)
            throws SQLException, DatabaseInsertException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // insert given account line into the database
        int accountSummaryId = (int) db.insertAccountLine(accountId, itemId, quantity);
        // Close the database connection and return the unique generated id key upon insertion
        db.close();
        return accountSummaryId;
    }


}