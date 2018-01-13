package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.model.database.DatabaseInsertHelper;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;
import com.b07.storeapplication.model.exceptions.DatabaseInsertException;
import com.b07.storeapplication.model.exceptions.NotAuthenticatedException;
import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.inventory.Item;
import com.b07.storeapplication.R;
import com.b07.storeapplication.model.store.EmployeeInterface;
import com.b07.storeapplication.model.users.Employee;
import com.b07.storeapplication.model.users.Roles;
import com.b07.storeapplication.model.users.User;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by cd on 2017-12-01.
 */

public class EmployeeViewOnClickController implements View.OnClickListener {
    // Instance Fields
    private Context appContext;
    private EditText idInput = null;
    private EditText quantityInput = null;
    private EditText nameInput = null;
    private EditText ageInput = null;
    private EditText addressInput = null;
    private EditText passwordInput = null;
    private String optionSelected = null;
    private EmployeeInterface empInterface = null;
    private Employee employee = null;

    /**
     * Constructor for the employee's view on click controller
     * @param context the activity context
     * @param option the employee's action selected
     */
    public EmployeeViewOnClickController(Context context, String option,
                                         EmployeeInterface empInter, Employee employee) {
        this.appContext = context;
        this.idInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_id);
        this.quantityInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_quantity);
        this.nameInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_name);
        this.ageInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_age);
        this.addressInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_address);
        this.passwordInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_password);
        this.optionSelected = option;
        this.empInterface = empInter;
        this.employee = employee;
    }

    @Override
    public void onClick(View view) {
        if (this.optionSelected.equals("Create Account")) {
            boolean wasCre = createAccount();
            if (wasCre) {
                Toast.makeText(this.appContext, "Account was created",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.appContext, "Account was not created",
                        Toast.LENGTH_SHORT).show();
            }
            this.idInput.setText("");
            this.quantityInput.setText("");
            this.nameInput.setText("");
            this.ageInput.setText("");
            this.addressInput.setText("");
            this.passwordInput.setText("");
        } else if (this.optionSelected.equals("Create Customer")) {
            boolean wasCre = createUser(Roles.CUSTOMER.toString());
            if (wasCre) {
                Toast.makeText(this.appContext, "Employee was created",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.appContext, "Employee was not created",
                        Toast.LENGTH_SHORT).show();
            }
            this.idInput.setText("");
            this.quantityInput.setText("");
            this.nameInput.setText("");
            this.ageInput.setText("");
            this.addressInput.setText("");
            this.passwordInput.setText("");
        } else if (this.optionSelected.equals("Create Employee")) {
            boolean wasCre = createUser(Roles.CUSTOMER.toString());
            if (wasCre) {
                Toast.makeText(this.appContext, "Employee was created",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.appContext, "Employee was not created",
                        Toast.LENGTH_SHORT).show();
            }
            this.idInput.setText("");
            this.quantityInput.setText("");
            this.nameInput.setText("");
            this.ageInput.setText("");
            this.addressInput.setText("");
            this.passwordInput.setText("");
        } else if (this.optionSelected.equals("Restock Inventory")) {
            boolean wasRe = restockInventory();
            if (wasRe) {
                Toast.makeText(this.appContext, "Inventory was restocked",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.appContext, "Inventory was not restocked",
                        Toast.LENGTH_SHORT).show();
            }
            this.idInput.setText("");
            this.quantityInput.setText("");
            this.nameInput.setText("");
            this.ageInput.setText("");
            this.addressInput.setText("");
            this.passwordInput.setText("");
        } else if (this.optionSelected.equals("Authenticate New Employee")) {
            boolean wasAuth = authenticateNewEmployee();
            if (wasAuth) {
                Toast.makeText(this.appContext, "Employee was authenticated",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.appContext, "Employee was not authenticated",
                        Toast.LENGTH_SHORT).show();
            }
            this.idInput.setText("");
            this.quantityInput.setText("");
            this.nameInput.setText("");
            this.ageInput.setText("");
            this.addressInput.setText("");
            this.passwordInput.setText("");
        }

    }

    private boolean createAccount() {
        boolean wasCreated = false;
        this.idInput.setHint("Enter Customer ID");
        String id = this.idInput.getText().toString();

        // check if customer exists
        if (inputConverter(id) > 0) {
            try {
                if (userExistence(id)) {
                    // check if User is a Customer
                    if (userType(DatabaseSelectHelper.getUserDetails(inputConverter(id),
                            this.appContext)).equals("CUSTOMER")) {
                        // check if Account already exists
                        if (DatabaseSelectHelper.getUserAccounts(inputConverter(id),
                                this.appContext).size() == 0) {
                            try {
                                int accountId = DatabaseInsertHelper.insertAccount(
                                        inputConverter(id), true, this.appContext);
                                Toast.makeText(this.appContext, "Account successfully created",
                                        Toast.LENGTH_SHORT).show();
                            } catch (SQLException sqle) {
                                Toast.makeText(this.appContext, "SQL Error: Can not insert" +
                                        " account into db", Toast.LENGTH_SHORT).show();
                            } catch (DatabaseInsertException e) {
                                Toast.makeText(this.appContext, "Value Error: Invalid input",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this.appContext, "Account already create for" +
                                            " this Customer",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        idInput.setError("ID is not a customer");
                    }
                } else {
                    idInput.setError("ID does not exist");
                }
            } catch (SQLException e) {
                idInput.setError("Invalid ID");
                Toast.makeText(this.appContext, "SQL Error: Can not get user info from db",
                        Toast.LENGTH_SHORT).show();
            }
        }
        return wasCreated;
    }

    /**
     * Method returns type of User as a String.
     *
     * @param user a User class object
     * @return string representing enum of Admin, Customer or Employee
     * @throws SQLException upon error
     */
    private String userType(User user) throws SQLException {
        return DatabaseSelectHelper.getRoleName(user.getRoleId(), this.appContext);
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
            this.idInput.setError("ID should contain only numbers");
        } finally {
            return user != null;
        }
    }

    /**
     * Authenticate the employee related to the given employee id.
     * @return true if the employee was authenticated, otherwise false
     */
    private boolean authenticateNewEmployee(){
        Employee employee;
        boolean wasAuthenticated = false;
        idInput.setHint("Enter Employee Id To Be Authenticated");
        // Try to get the given employee form the database
        try {
            employee = (Employee) DatabaseSelectHelper.getUserDetails(
                    inputConverter(this.idInput.getText().toString()), this.appContext);
            if (employee != null) {
                this.empInterface.setEmployeeAuthenticate(employee, this.appContext);
                this.employee = employee;
                this.empInterface.setCurrentEmployee(employee);
            } else {
                Toast.makeText(this.appContext, "Employee does not exist",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext, "SQL Error: Unable to"
                    + " get employee from db", Toast.LENGTH_SHORT).show();
        } catch (NotAuthenticatedException ne) {
            Toast.makeText(this.appContext, "User not authenticated",
                    Toast.LENGTH_SHORT).show();
        }

        return wasAuthenticated;
    }

    /**
     * Create a new User, Customer or Employee in the database depending on the given role name.
     * @param  role type of user to be created in the database
     * @return true if the a new Customer was successfully added to the database, otherwise false
     */
    private boolean createUser(String role) {
        int userId = -1;
        boolean wasCreated = false;
        String name = nameInput.getText().toString();
        int age = inputConverter(ageInput.getText().toString());
        String address = addressInput.getText().toString();
        String password = passwordInput.getText().toString();

        // Check if a valid name was entered
        if (!TextUtils.isEmpty(name)) {
            // Check if a valid age was entered
            if (age > 0 ) {
                // Check if address is empty
                if (!TextUtils.isEmpty(address)) {
                    // Check if address is less than 100 characters
                    if (address.length() < 101) {
                        // Check if the given password is empty
                        if (!TextUtils.isEmpty(password)) {

                            // Check if a Customer or Employee needs to be created
                            if (role.equals("CUSTOMER")) {
                                // Try to create an customer in the database
                                try {
                                    userId = this.empInterface.createCustomer(name, age,
                                            address, password, this.appContext);
                                    if (userId > 0) {
                                        Toast.makeText(appContext, "New Customer's ID: "
                                                + userId, Toast.LENGTH_SHORT).show();

                                        wasCreated = (userId > 0);
                                    }
                                } catch (SQLException sqle) {
                                    Toast.makeText(this.appContext, "SQL Error: Unable to"
                                            + " insert user", Toast.LENGTH_SHORT).show();
                                } catch (DatabaseInsertException die) {
                                    Toast.makeText(appContext, "Error: Unable to"
                                            + " insert User in db"
                                            + userId, Toast.LENGTH_SHORT).show();
                                }

                            } else if (role.equals("EMPLOYEE")) {
                                // Try to create an employee in the database
                                try {
                                    userId = this.empInterface.createEmployee(name, age,
                                            address, password, this.appContext);

                                    // Check if the user was inserted into the database
                                    if (userId > 0) {
                                        Toast.makeText(appContext, "New Employee's ID: "
                                                + userId, Toast.LENGTH_SHORT).show();
                                        wasCreated = (userId > 0);
                                    }
                                } catch (SQLException sqle) {
                                    Toast.makeText(this.appContext, "SQL Error: Unable to"
                                            + " insert user", Toast.LENGTH_SHORT).show();
                                } catch (DatabaseInsertException die) {
                                    Toast.makeText(appContext, "Error: Unable to"
                                            + " insert User in db"
                                            + userId, Toast.LENGTH_SHORT).show();
                                }
                            }

                           /* // Try to insert the given user into the database
                            try {
                                userId = DatabaseInsertHelper.insertNewUser(name, age,
                                        address, password, this.appContext);
                            } catch (SQLException e) {
                                Toast.makeText(this.appContext, "SQL Error: Unable to insert"
                                                + " user",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // Try to get the CUSTOMER role id and insert the user/role relation
                            // into the database
                            try{
                                List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(
                                        this.appContext);
                                for (int roleId : roleIds) {
                                    if (role.equals(
                                            DatabaseSelectHelper.getRoleName(roleId,
                                                    this.appContext))) {
                                        try {
                                            int userRole = DatabaseInsertHelper.insertUserRole
                                                    (userId, roleId, this.appContext);
                                            Toast.makeText(appContext, "New Employee's ID: "
                                                    + userId, Toast.LENGTH_SHORT).show();

                                            wasCreated = (userId > 0) && (userRole > 0);
                                        } catch (DatabaseInsertException e ) {
                                            Toast.makeText(appContext, "Error: Unable to"
                                                    + " insert User-Role relation"
                                                    + userId, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            } catch (SQLException e) {
                                Toast.makeText(this.appContext,"SQL",
                                        Toast.LENGTH_SHORT).show();
                            }*/
                        } else {
                            // Set empty error
                            passwordInput.setError("Password cannot be empty");
                        }
                    } else {
                        // Set too long address error
                        addressInput.setError("Address cannot be more than 100 characters");
                    }
                } else {
                    // Set empty error
                    addressInput.setError("Address cannot be empty");
                }
            }
        } else {
            // Set empty error
            nameInput.setError("Name cannot be empty");
        }

        return wasCreated;
    }

    /**
     * Restock the item of the inputted item id to account of the inputted quantity.
     * @return true if the input item was successfully restocked in the inventory, otherwise false
     */
    private boolean restockInventory() {
        boolean wasRestocked = false;
        ;
        Item itemToAdjust = null;

        // Updated input hints for corresponding action
        idInput.setHint("Enter Item ID");
        quantityInput.setHint("Enter Quantity of Item to re-stock");

        // Try to update the inventory of the items quantity
        try {
            // Get the inventory and set up the employee interface
            Inventory inventory = DatabaseSelectHelper.getInventory(appContext);
            this.empInterface = new EmployeeInterface(inventory);

            // Find the Item to be restocked
            HashMap<Item, Integer> inventoryStock = this.empInterface.getInventory().getItemMap();
            for (Item item : inventoryStock.keySet()) {
                if (item.getId() == inputConverter(idInput.getText().toString())) {
                    itemToAdjust = item;
                }
            }

            // Check if the item was found
            if(itemToAdjust != null) {
                // Restock the item in the employee interface
                wasRestocked = this.empInterface.restockInventory(itemToAdjust, inputConverter(
                        this.quantityInput.getText().toString()));
            } else {
                this.idInput.setError("Invalid ID");
                Toast.makeText(this.appContext,"Item was not found in inventory",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(this.appContext,"SQL Error: Unable to get inventory from database",
                    Toast.LENGTH_SHORT).show();
        }

        // Check if the item was successfully restocked in the employee interface level
        if (wasRestocked) {
            // Try to update the restocked item in the inventory of the Database level
            try {
                wasRestocked = DatabaseUpdateHelper.updateInventoryQuantity(
                        this.empInterface.getInventory().getQuantity(itemToAdjust),itemToAdjust.getId(),
                        appContext);
            } catch (SQLException e) {
                Toast.makeText(this.appContext,"SQL Error: Unable to update inventory in db",
                        Toast.LENGTH_SHORT).show();
            }

        }
        if (wasRestocked) {
            Toast.makeText(this.appContext,"Success, " + itemToAdjust.getName() + " was restocked! "
                            + "New quantity: "
                            + this.empInterface.getInventory().getQuantity(itemToAdjust),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.appContext,"Item was not restocked",
                    Toast.LENGTH_SHORT).show();
        }

        // Return whether the item was successful restocked
        return wasRestocked;
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
        try{
            convertedValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            this.idInput.setError("Invalid Input");
            this.quantityInput.setError("Invalid Input");
            Toast.makeText(this.appContext,"ID & Quantity cannot be empty or contain letters",
                    Toast.LENGTH_SHORT).show();
        }
        // Return converted input
        return convertedValue;
    }
}