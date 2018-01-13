package com.b07.storeapplication.model.database;


import android.content.Context;

import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.store.ItemizedSale;
import com.b07.storeapplication.model.store.Sale;
import com.b07.storeapplication.model.store.SalesLog;
import com.b07.storeapplication.model.users.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DatabaseSerializer {

    public DatabaseSerializer() {
    }

    @SuppressWarnings("unchecked")
    public static Object deserialize(Context context) throws SQLException, DatabaseInsertException {

        List<Integer> accountIds;
        List<Integer> accountUserIds;
        HashMap<Integer, HashMap<Integer, Boolean>> accountStatuses;
        List<HashMap<Integer, Integer>> accountDetailList;
        Inventory invent;
        List<ItemizedSale> itemizedSales;
        List<Item> items;
        List<Integer> roleIds;
        SalesLog salesList;
        List<String> passwordList;
        List<Integer> userRoleIds;
        List<String> roleNames;
        List<User> userList;
        try {
            FileInputStream fileIn = context.openFileInput("database_copy.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            // read in all objects in the order received
            accountIds = (List<Integer>) in.readObject();
            accountUserIds = (List<Integer>) in.readObject();
            accountStatuses = (HashMap<Integer, HashMap<Integer, Boolean>>) in.readObject();
            accountDetailList = (List<HashMap<Integer, Integer>>) in.readObject();
            invent = (Inventory) in.readObject();
            itemizedSales = (List<ItemizedSale>) in.readObject();
            items = (List<Item>) in.readObject();
            roleIds = (List<Integer>) in.readObject();
            salesList = (SalesLog) in.readObject();
            passwordList = (List<String>) in.readObject();
            userRoleIds = (List<Integer>) in.readObject();
            roleNames = (List<String>) in.readObject();
            userList = (List<User>) in.readObject();

            in.close();
            fileIn.close();


            // return null if there is an error in retrieving data from database_copy.ser
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }

        DatabaseDriverAndroid db = new DatabaseDriverAndroid(context);
        db.onUpgrade(db.getWritableDatabase(), 0, 1);


        // use userId to get account status
        int accountIdCounter = 1;
        for (int userId : accountUserIds) {
            HashMap<Integer, Boolean> accountIdsAndStatus = accountStatuses.get(userId);
            DatabaseInsertHelper.insertAccount(userId, accountIdsAndStatus.get(accountIdCounter),
                    context);
            accountIdCounter++;
        }

        accountIdCounter = 1;
        for (HashMap<Integer, Integer> map : accountDetailList) {
            for (int itemId : map.keySet())
                DatabaseInsertHelper.insertAccountLine(accountIdCounter, itemId, map.get(itemId),
                        context);
            accountIdCounter++;
        }

        for (Item item : invent.getItemMap().keySet()) {
            DatabaseInsertHelper.insertInventory(item.getId(), invent.getItemMap().get(item),
                    context);
        }

        for (Item item : items) {
            DatabaseInsertHelper.insertItem(item.getName(), item.getPrice(), context);
        }

        for (String roleName : roleNames) {
            DatabaseInsertHelper.insertRole(roleName, context);
        }

        List<Sale> sales = salesList.getSalesList();
        for (Sale sale : sales) {
            DatabaseInsertHelper.insertSale(sale.getUser(context).getId(),
                    sale.getTotalPrice(), context);
        }

        int userIdCounter = 1;
        for (int userRole : userRoleIds) {
            DatabaseInsertHelper.insertUserRole(userIdCounter, userRole, context);
            userIdCounter++;
        }


        for (int i = 0; i < userList.size(); i++) {

            User usr = userList.get(i);
            String hashedPass = passwordList.get(i);
            DatabaseInsertHelper.insertNewUser(usr.getName(), usr.getAge(), usr.getAddress(),
                    hashedPass, context);
            DatabaseUpdateHelper.updateUserPassword(hashedPass, usr.getId(), context);
        }
        return null;

    }


    /**
     * Reads to a file serialized information contained in the database.
     *
     * @throws Exception
     */
    public static void serialize( Context context) throws IOException,SQLException {
        List<User> userList;
        userList = DatabaseSelectHelper.getUsersDetails(context);
        HashMap<Integer, Boolean> accountIdToActiveStatus = new HashMap<Integer, Boolean>();
        HashMap<Integer, HashMap<Integer, Boolean>> accountStatuses =
                new HashMap<Integer, HashMap<Integer, Boolean>>();
        List<Integer> accountIds = new ArrayList<Integer>();
        List<Integer> accountUserIds = new ArrayList<Integer>();
        List<HashMap<Integer, Integer>> accountDetailList = new ArrayList<HashMap<Integer, Integer>>();
        SalesLog salesList = DatabaseSelectHelper.getSales(context);
        List<String> passwordsList = new ArrayList<String>();
        List<Integer> roleIds = new ArrayList<Integer>();
        List<Integer> userIds = new ArrayList<Integer>();
        List<String> roleNames = new ArrayList<String>();
        FileOutputStream fileOut = context.openFileOutput("database_copy.ser",
                Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        // outputs a list of account user ids and account ids in order it was stored
        for (User usr : userList) {
            for (int userId : DatabaseSelectHelper.getUserAccounts(usr.getId(), context)) {
                // add user id and account id at the same time so they can be related after
                // deserialization
                accountIds.add(userId);
                accountUserIds.add(usr.getId());
            }
            // get list of userIds for later
            userIds.add(usr.getId());

            List<Integer> active = DatabaseSelectHelper.getUserActiveAccounts(usr.getId(),
                    context);
            for (int activeStatus : active) {
                accountIdToActiveStatus.put(activeStatus, true);
                accountStatuses.put(usr.getId(), accountIdToActiveStatus);
            }
            List<Integer> inactive = DatabaseSelectHelper.getUserInactiveAccounts(usr.getId(),
                    context);
            for (int inactiveStatus : inactive) {
                accountIdToActiveStatus.put(inactiveStatus, false);
                accountStatuses.put(usr.getId(), accountIdToActiveStatus);
            }
        }
        out.writeObject(accountIds);
        out.writeObject(accountUserIds);
        out.writeObject(accountStatuses);
        // outputs the table with the summary of each account in order of the accountIds list
        for (int accountId : accountIds) {
            accountDetailList.add(DatabaseSelectHelper.getAccountDetails(accountId, context));
        }
        out.writeObject(accountDetailList);
        // outputs the database's inventory
        out.writeObject(DatabaseSelectHelper.getInventory(context));
        // outputs the table of itemized sales as a list<ItemizedSales>
        out.writeObject(DatabaseSelectHelper.getItemizedSales(salesList, context));
        // outputs the table of items as a list
        out.writeObject(DatabaseSelectHelper.getAllItems(context));
        // outputs a list of integers (roleIds) for roles table
        out.writeObject(DatabaseSelectHelper.getRoleIds(context));

        // list of sales stored as a saleslog object
        out.writeObject(salesList);
        // list of hashed passwords
        for (int userId : userIds) {
            passwordsList.add(DatabaseSelectHelper.getPassword(userId, context));
            roleIds.add(DatabaseSelectHelper.getUserRoleId(userId, context));
        }
        out.writeObject(passwordsList);
        // list of roleIds for the USERROLE table
        out.writeObject(roleIds);
        for (int roleId : roleIds) {
            roleNames.add(DatabaseSelectHelper.getRoleName(roleId, context));
        }
        // outputs a list of roleNames for the roles table
        out.writeObject(roleNames);
        // list of users
        out.writeObject(userList);



    }
}