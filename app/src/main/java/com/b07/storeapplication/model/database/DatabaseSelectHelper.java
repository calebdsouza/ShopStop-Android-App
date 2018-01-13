package com.b07.storeapplication.model.database;

import android.content.Context;
import android.database.Cursor;

import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.inventory.InventoryImpl;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.inventory.ItemImpl;
import com.b07.storeapplication.model.store.ItemizedSale;
import com.b07.storeapplication.model.store.ItemizedSaleImpl;
import com.b07.storeapplication.model.store.Sale;
import com.b07.storeapplication.model.store.SaleImpl;
import com.b07.storeapplication.model.store.SalesLog;
import com.b07.storeapplication.model.store.SalesLogImpl;
import com.b07.storeapplication.model.users.User;
import com.b07.storeapplication.model.users.UserAuthenticate;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseSelectHelper {

    /**
     * Return a List representing the ResultSet of role ids form the valid database.
     *
     * @param context the context of the activity which this method is called
     * @return List of Integer which is a list of all the role Ids from a valid Database.
     * @throws SQLException error regarding sql
     */
    public static List<Integer> getRoleIds(Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);

        // Get the result set of role ids from the database
        Cursor cursor = db.getRoles();
        // Create a place to store the role ids
        List<Integer> ids = new ArrayList<>();
        // Get each role id form the result set
        while (cursor.moveToNext()) {
            ids.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex("ID"))));
        }
        // Close the result set and db, return the list of role ids
        cursor.close();
        db.close();
        return ids;
    }

    /**
     * Return a string representation of the Role Name, from a valid Database,
     * to the given corresponding role id.
     *
     * @param roleId  an int which is the role id corresponding the respective role name wanted
     * @param context the context of the activity which this method is called
     * @return String representation of the Role Name for the corresponding given role id
     * @throws SQLException error regarding sql
     */
    public static String getRoleName(int roleId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the role name from the database
        String role = db.getRole(roleId);
        // Close the database and return the role name
        db.close();
        return role;
    }

    /**
     * Return the user role id, form a valid Database, responding to the given User id.
     *
     * @param userId  an int which is the Role id corresponding to the respective User id wanted.
     * @param context the context of the activity which this method is called
     * @return the role id for the corresponding given User id
     * @throws SQLException error regarding sql
     */
    public static int getUserRoleId(int userId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the given user's role id
        int roleId = db.getUserRole(userId);
        // Close the database and return the given user's role id
        db.close();
        return roleId;
    }

    /**
     * Return a List of Integers representing the ResultSet of the User ids, from a
     * valid Database, corresponding to the given Role id.
     *
     * @param roleId  int which is the role id corresponding to the Role of the
     *                of the wanted User ids.
     * @param context the context of the activity which this method is called
     * @return List of Integer which is a list of all the user ids from a valid Database.
     * @throws SQLException error regarding sql
     */
    public static List<Integer> getUsersByRole(int roleId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the result set of the users by their role id
        Cursor cursor = db.getUsersByRole(roleId);
        // Get a list of user ids from the given role id
        List<Integer> userIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            userIds.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex("USERID"))));
        }
        // Close the result set and database, return the list of user ids
        cursor.close();
        db.close();
        return userIds;

    }

    /**
     * Return a List of Users representing the ResultSet of all Users, from this valid
     * Database, in the USERS Table.
     *
     * @param context the context of the activity which this method is called
     * @return List of all the Users in the USERS Table in this valid Database
     * @throws SQLException error regarding sql
     */
    public static List<User> getUsersDetails(Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the result set of User details
        Cursor cursor = db.getUsersDetails();
        // Get a list of user in the database
        List<User> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            users.add(getUserDetails(cursor.getInt(cursor.getColumnIndex("ID")), context));
        }
        cursor.close();
        db.close();
        return users;
    }

    /**
     * Return the User, containing it's details from the USERS Table in this
     * Valid Database, corresponding to the given User id.
     *
     * @param userId  the id of the wanted User from the USERS table in this
     *                valid Database
     * @param context the context of the activity which this method is called
     * @return the User, containing it's details from the USERS Table, corresponding
     * to the given user id
     * @throws SQLException error regarding sql
     */
    public static User getUserDetails(int userId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        Cursor cursor = db.getUserDetails(userId);
        while (cursor.moveToNext()) {
            // find correct User
            int currentUserId = cursor.getInt(cursor.getColumnIndex("ID"));
            if (currentUserId == userId) {
                // Get the wanted User
                int roleId = getUserRoleId(userId, context);
                String roleName = getRoleName(roleId, context);
                User user = UserAuthenticate.roleInitiate(roleName, currentUserId,
                        cursor.getString(cursor.getColumnIndex("NAME")),
                        cursor.getInt(cursor.getColumnIndex("AGE")),
                        cursor.getString(cursor.getColumnIndex("ADDRESS")), context);
                cursor.close();
                db.close();
                return user;
            }
        }
        cursor.close();
        db.close();
        return null;
    }

    /**
     * Return the String representation of the password, from the USERPW Table
     * in this valid Database, corresponding to the given User id.
     *
     * @param userId  id of the User who's password is wanted
     * @param context the context of the activity which this method is called
     * @return the string representation of the password form this valid Database
     * @throws SQLException error regarding sql
     */
    public static String getPassword(int userId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the given user's hasded password form the database
        String password = db.getPassword(userId);
        // Close the database and return the wanted password
        db.close();
        return password;
    }

    /**
     * Returns a List of all Items, from the ITEMS Table in this valid Database.
     *
     * @param context the context of the activity which this method is called
     * @return List of all Items from ITEMS Tables in this valid Database
     * @throws SQLException error regarding sql
     */
    public static List<Item> getAllItems(Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the result set of all items
        Cursor cursor = db.getAllItems();
        // Create a list of all items in the database
        List<Item> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            items.add(new ItemImpl(cursor.getInt(cursor.getColumnIndex("ID")), cursor.getString(
                    cursor.getColumnIndex("NAME")),
                    new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE")))));
        }
        // Close the result set and database connection, return the list of items
        cursor.close();
        db.close();
        return items;
    }

    /**
     * Return the Item, containing it's details from the ITEMS Table in this
     * valid Database, corresponding to the given Item id.
     *
     * @param itemId  id of the Item wanted
     * @param context the context of the activity which this method is called
     * @return Item, form the ITEMS Table, corresponding to the given Item id
     * @throws SQLException error regarding sql
     */
    public static Item getItem(int itemId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the result set of wanted item info from the database
        Cursor cursor = db.getItem(itemId);
        // Create the wanted item
        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(cursor.getColumnIndex("ID"));
            if (currentId == itemId) {
                String itemName = cursor.getString(cursor.getColumnIndex("NAME"));
                String price = cursor.getString(cursor.getColumnIndex("PRICE"));
                cursor.close();
                db.close();
                return new ItemImpl(currentId, itemName,
                        new BigDecimal(price));
            }
        }
        // Close the the connection to the result set and database, return the wanted item
        cursor.close();
        db.close();
        return null;
    }

    /**
     * Return the Inventory ResultSet containing all the item data
     * from the INVENTROY Table in this valid Database.
     *
     * @param context the context of the activity which this method is called
     * @return Inventory containing all the item data from this valid Database
     * @throws SQLException error regarding sql
     */
    public static Inventory getInventory(Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the inventory result from the database
        Cursor cursor = db.getInventory();
        // Create an inventory with the wanted info from the database
        Inventory inventory = new InventoryImpl();
        while (cursor.moveToNext()) {
            inventory.updateMap(getItem(cursor.getInt(cursor.getColumnIndex("ITEMID")), context),
                    getInventoryQuantity(cursor.getInt(cursor.getColumnIndex("ITEMID")),
                            context));
        }
        // Close the result set and database connection, return inventory in the database
        cursor.close();
        db.close();
        return inventory;
    }

    /**
     * Return the quantity of inventory, from the INVENTORY Table in
     * the valid Database, corresponding to given Item id.
     *
     * @param itemId  id of the Item who quantity is wanted
     * @param context the context of the activity which this method is called
     * @return quantity corresponding to the Item of the given id
     * @throws SQLException error regarding sql
     */
    public static int getInventoryQuantity(int itemId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get the quantity of the given intem in the inventory
        int quantity = db.getInventoryQuantity(itemId);
        // Close database connection and return the wanted quantity
        db.close();
        return quantity;
    }

    /**
     * Return the SalesLog containing all the sales, from the SALES Table
     * in this valid Database.
     *
     * @param context the context of the activity which this method is called
     * @return SalesLog containing all the sales, from the SALES Table
     * @throws SQLException error regarding sql
     */
    public static SalesLog getSales(Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get all sales in the database
        Cursor cursor = db.getSales();
        // Create a sales log
        SalesLogImpl salesLog = new SalesLogImpl();
        while (cursor.moveToNext()) {
            Sale sale = new SaleImpl(cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getInt(cursor.getColumnIndex("USERID")), new BigDecimal
                    (cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
            salesLog.addSale(sale);
        }
        // Close result set and database connection, return sales log
        cursor.close();
        db.close();
        return salesLog;
    }

    /**
     * Return the Sale, from the SALES Table in this valid Database, corresponding
     * to the given Sale id.
     *
     * @param saleId  id of the wanted Sale
     * @param context the context of the activity which this method is called
     * @return the Sale corresponding to the given Sale id
     * @throws SQLException error regarding sql
     */
    public static Sale getSaleById(int saleId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        Cursor cursor = db.getSaleById(saleId);
        while (cursor.moveToNext()) {
            int currentSaleId = cursor.getInt(cursor.getColumnIndex("ID"));
            if (currentSaleId == saleId) {
                cursor.close();
                db.close();
                return new SaleImpl(saleId, cursor.getInt(cursor.getColumnIndex("USERID")),
                        new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE"))));
            }
        }
        return null;

    }

    /**
     * Get an ItemizedSale of the given sale id.
     *
     * @param saleId  the id of the sale to be itemized
     * @param context the context of the activity which this method is called
     * @return The ItemizedSale of the given sale id
     * @throws SQLException error regarding sql
     */
    public static ItemizedSale getItemizedSaleById(int saleId, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all sales
        Cursor cursor = db.getItemizedSaleById(saleId);
        // Find the wanted sale, close result set and database, and return the ItemizedSale
        while (cursor.moveToNext()) {
            int currentSaleId = cursor.getInt(cursor.getColumnIndex("SALEID"));
            if (currentSaleId == saleId) {
                int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
                int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
                cursor.close();
                db.close();
                return new ItemizedSaleImpl(currentSaleId, itemId, quantity);
            }
        }
        // Close connection to result set and database, return ItemizedSale
        cursor.close();
        db.close();
        return null;
    }

    public static List<ItemizedSale> getItemizedSales(SalesLog salesLog, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all sales
        Cursor cursor = db.getItemizedSales();
        // Get a list of ItemizedSale of sale in the given sales log
        List<ItemizedSale> allItemizedSales = new ArrayList<>();
        while (cursor.moveToNext()) {
            int currentSaleId = cursor.getInt(cursor.getColumnIndex("SALEID"));
            for (Sale sale : salesLog.getSalesList()) {
                if (currentSaleId == sale.getId()) {
                    ItemizedSale itemizedSale = new ItemizedSaleImpl(currentSaleId,
                            cursor.getInt(cursor.getColumnIndex("ITEMID")),
                            cursor.getInt(cursor.getColumnIndex("QUANTITY")));
                    allItemizedSales.add(itemizedSale);
                }
            }
        }
        // Close result set and database connection, return the list of itemized sales
        cursor.close();
        db.close();
        return allItemizedSales;
    }

    /**
     * Get a list of all accounts related to the given user id.
     *
     * @param userId  the user id
     * @param context the context of the activity which this method is called
     * @return list of all accounts related to the given user id
     * @throws SQLException error regarding sql
     */
    public static List<Integer> getUserAccounts(int userId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all user accounts
        Cursor cursor = db.getUserAccounts(userId);
        // Get a list of all id account related to the given user
        List<Integer> accountList = new ArrayList<>();
        while (cursor.moveToNext()) {
            accountList.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        return accountList;
    }

    /**
     * Get a map of all items and their quantity related to the given account id.
     *
     * @param accountId the account id
     * @param context   the context of the activity which this method is called
     * @return map of all items and their quantity
     * @throws SQLException error regarding sql
     */
    public static HashMap<Integer, Integer> getAccountDetails(int accountId, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all details for the given account
        Cursor cursor = db.getAccountDetails(accountId);
        // Get all the item and quantity details of the given account
        HashMap<Integer, Integer> accountDetails = new HashMap<>();
        while (cursor.moveToNext()) {
            accountId = cursor.getInt(cursor.getColumnIndex("ACCTID"));
            accountDetails.put(cursor.getInt(cursor.getColumnIndex("ITEMID")), cursor.getInt(
                    cursor.getColumnIndex("QUANTITY")));
        }
        // Close result set and database connection, return the map of items and their quantities
        cursor.close();
        db.close();
        return accountDetails;
    }

    /**
     * Given a user id, get a list of all their active accounts.
     *
     * @param userId  the user id
     * @param context the context of the activity which this method is called
     * @return list of all active account ids related to the given user id
     * @throws SQLException error regarding sql
     */
    public static List<Integer> getUserActiveAccounts(int userId, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all active accounts related to the given user id
        Cursor cursor = db.getUserActiveAccounts(userId);
        // Get a list of all active account ids related to the given user id
        List<Integer> activeAccounts = new ArrayList<>();
        while (cursor.moveToNext()) {
            activeAccounts.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        // Close result set and database connection, return the list of active accounts
        cursor.close();
        db.close();
        return activeAccounts;
    }

    /**
     * Given a user id, get a list of all their inactive accounts.
     *
     * @param userId  the user id
     * @param context the context of the activity which this method is called
     * @return list of all inactive account ids related to the given user id
     * @throws SQLException error regarding sql
     */
    public static List<Integer> getUserInactiveAccounts(int userId, Context context) throws
            SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all inactive accounts related to the given user id
        Cursor cursor = db.getUserInactiveAccounts(userId);
        // Get a list of inactive account ids related to the given user id
        List<Integer> inactiveAccounts = new ArrayList<>();
        while (cursor.moveToNext()) {
            inactiveAccounts.add(cursor.getInt(cursor.getColumnIndex("ID")));
        }
        // Close result set and database connection, return the list of inactive accounts
        cursor.close();
        db.close();
        return inactiveAccounts;
    }

    /**
     * Return a List of all Sales for a User, from the SALES Table in this
     * valid Database, corresponding to the given User id.
     *
     * @param userId  id of the User who List of all Sales is wanted
     * @param context the context of the activity which this method is called
     * @return List of all Sales for the given User id
     * @throws SQLException error regarding sql
     */
    public List<Sale> getSalesToUser(int userId, Context context) throws SQLException {
        // Create an instance of the database
        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        // Get result set of all sales of the given user
        Cursor cursor = db.getSalesToUser(userId);
        // Get a list of all sales related to the given user
        List<Sale> sales = new ArrayList<>();
        while (cursor.moveToNext()) {
            int currentSaleId = cursor.getInt(cursor.getColumnIndex("ID"));
            int currentUserId = cursor.getInt(cursor.getColumnIndex("USERID"));
            if (currentUserId == userId) {
                sales.add(new SaleImpl(currentSaleId, currentUserId, new BigDecimal(
                        cursor.getString
                                (cursor.getColumnIndex("TOTALPRICE")))));
            }
        }
        // Close connection to result set and database, return to list of sale
        cursor.close();
        db.close();
        return sales;
    }
}