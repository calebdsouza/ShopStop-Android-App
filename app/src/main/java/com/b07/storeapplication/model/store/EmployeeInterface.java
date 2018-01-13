package com.b07.storeapplication.model.store;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.exceptions.NotAuthenticatedException;
import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.model.users.Employee;
import com.b07.storeapplication.model.users.UserAuthenticate;

import java.io.Serializable;
import java.sql.SQLException;

public class EmployeeInterface extends UserAuthenticate implements Serializable {

    private Employee currentEmployee;


    public EmployeeInterface(Employee employee, Inventory inventory) {
        super(employee);
        super.getEmployeeAuthenticate(employee);

    }

    public EmployeeInterface(Inventory inventory) {
        super(inventory);
    }

    /**
     * Sets the currentEmployee to employee
     *
     * @param employee the employee to be set as current employee
     * @throws NotAuthenticatedException if employee is not authenticated
     */
    public void setCurrentEmployee(Employee employee) throws NotAuthenticatedException,
            NullPointerException {
        if (employee == null) {
            throw new NullPointerException();
        }
        if (getEmployeeAuthenticate(employee)) {
            this.currentEmployee = employee;
        } else {
            throw new NotAuthenticatedException();
        }
    }

    /**
     * Checks if currentEmployee holds a value of an employee
     *
     * @return boolean true if contains current employee false otherwise
     */
    public boolean hasCurrentEmployee() {
        return this.currentEmployee != null;
    }

    /**
     * Restocks inventory by updating the inventory with an Item and quantity
     *
     * @param item     to be added or subtracted
     * @param quantity amount of Item to be added or subtracted
     * @return boolean if item contained in Inventory and quantity is correct
     */
    public boolean restockInventory(Item item, int quantity) {

        int oldQuantity = getInventory().getItemMap().get(item);
        getInventory().updateMap(item, quantity);
        return getInventory().getItemMap().containsKey(item) && getInventory().getItemMap().get(item)
                == oldQuantity + quantity;
    }

    /**
     * Creates a new customer with the information provided.
     *
     * @param name     name of customer
     * @param age      age of customer
     * @param address  address of customer
     * @param password of customer
     * @param context  the context of the activity which this method is called
     * @return userId of Customer if it is successful.
     * @throws SQLException            thrown upon error
     * @throws DatabaseInsertException thrown upon error
     */
    public int createCustomer(String name, int age, String address, String password, Context context)
            throws SQLException, DatabaseInsertException {
        int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password, context);
        String role = "CUSTOMER";
        int roleId = DatabaseInsertHelper.insertRole(role, context);
        DatabaseInsertHelper.insertUserRole(userId, roleId, context);
        return userId;
    }

    /**
     * Creates a new employee with the information provided.
     *
     * @param name     name of employee
     * @param age      age of employee
     * @param address  address of employee
     * @param password password of employee
     * @param context  the context of the activity which this method is called
     * @return userId of Employee if it is successfully created.
     * @throws SQLException            thrown upon error
     * @throws DatabaseInsertException thrown upon error
     */
    public int createEmployee(String name, int age, String address, String password, Context context)
            throws SQLException, DatabaseInsertException {
        int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password, context);
        String role = "EMPLOYEE";
        int roleId = DatabaseInsertHelper.insertRole(role, context);
        DatabaseInsertHelper.insertUserRole(userId, roleId, context);
        return userId;
    }

}
