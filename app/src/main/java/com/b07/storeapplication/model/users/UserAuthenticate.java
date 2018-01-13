package com.b07.storeapplication.model.users;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.inventory.Inventory;

import java.sql.SQLException;

/**
 * Purpose of this class is to authenticate a User. It can also authenticate and designate its role.
 */
public class UserAuthenticate {

    private Employee currentEmployee;
    private Inventory inventory;

    public UserAuthenticate(Employee employee) {
        employee.getAuthenticated();
    }

    public UserAuthenticate(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * method to help initiate specific type of User
     *
     * @param type    the Role to be initiated
     * @param id      the User Id
     * @param name    the User name
     * @param age     the User's age
     * @param address the address of the User
     * @param context the context of the activity which this method is called
     * @return User a specified type
     */
    public static User roleInitiate(String type, int id, String name, int age, String address,
                                    Context context)
            throws SQLException {
        switch (type) {
            case "ADMIN": {
                User role = new Admin(id, name, age, address, context);
                return role;
            }
            case "EMPLOYEE": {
                User role = new Employee(id, name, age, address, context);
                return role;
            }
            default: {
                User role = new Customer(id, name, age, address, context);
                return role;
            }
        }
    }

    /**
     * Method to return the Inventory of a UserAuthenticate.
     *
     * @return inventory of items in store.
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Method returns the result of getAuthenticated of an Employee.
     *
     * @param employee to check for authentication
     * @return boolean true if authenticated, false if otherwise
     */
    public boolean getEmployeeAuthenticate(Employee employee) {
        return employee.getAuthenticated();
    }

    /**
     * Method sets the Employee to authenticated.
     *
     * @param employee must be in Database.
     * @param context  the context of the activity which this method is called
     */
    public void setEmployeeAuthenticate(Employee employee, Context context) throws SQLException {
        if (DatabaseSelectHelper.getUserDetails(employee.getId(), context) != null) {
            employee.setAuthenticated(true);
        } else {
            employee.setAuthenticated(false);
        }
    }

    /**
     * Method returns the result of getAuthenticated of a Customer.
     *
     * @param customer to check for authentication
     * @return boolean true if authenticated, false if otherwise
     */
    public boolean getCustomerAuthenticate(Customer customer) {
        return customer.getAuthenticated();
    }

    /**
     * Method sets the Customer to authenticated.
     *
     * @param customer must be in Database.
     * @param context  the context of the activity which this method is called
     */
    public void setCustomerAuthenticate(Customer customer, Context context) throws SQLException {
        if (DatabaseSelectHelper.getUserDetails(customer.getId(), context) != null) {
            customer.setAuthenticated(true);
        } else {
            customer.setAuthenticated(false);
        }
    }

}