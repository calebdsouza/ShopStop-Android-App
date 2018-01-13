package com.b07.storeapplication.model.users;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.InvalidAttributeValueException;
import com.b07.storeapplication.model.security.PasswordHelpers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class User implements Serializable {
    private int id;
    private String name;
    private int age;
    private String address;
    private int roleId;
    private boolean authenticated;

    /**
     * returns the id of a User.
     *
     * @return the id of the User.
     */

    public int getId() {
        return this.id;
    }

    /**
     * sets the id of a User to an integer value.
     *
     * @param id for User to be set to.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the name of a User.
     *
     * @return the name of the User.
     */

    public String getName() {
        return this.name;
    }

    /**
     * sets the name of a User.
     *
     * @param name for User to be named.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the age of a User.
     *
     * @return the age of the User as an int.
     */

    public int getAge() {
        return this.age;
    }

    /**
     * sets the age of a User.
     *
     * @param age for User to be set to.
     */

    public void setAge(int age) {
        if (age < 0) {
            try {
                throw new InvalidAttributeValueException();
            } catch (InvalidAttributeValueException e) {
                e.printStackTrace();
            }
        } else {
            this.age = age;
        }
    }

    /**
     * returns the roleId of a User.
     *
     * @return the roleID of the User as an int.
     */

    public int getRoleId() {
        return this.roleId;
    }

    /**
     * returns True if the inputted string password matches a hashed password in database.
     *
     * @param password that is entered by user but is not hashed.
     * @param context  the context of the activity which this method is called
     * @return True or False
     */
    public final boolean authenticate(String password, Context context) throws SQLException {
        String hashedPass = DatabaseSelectHelper.getPassword(this.getId(), context);
        this.authenticated = PasswordHelpers.comparePassword(hashedPass, password);
        return this.authenticated;
    }

    /**
     * Method to return whether a User is authenticated.
     *
     * @return boolean True if User is authenticated. False if otherwise.
     */
    protected boolean getAuthenticated() {
        return this.authenticated;
    }

    /**
     * Method that sets the authentication for a User.
     *
     * @param authenticated boolean if User is authenticated.
     */
    protected void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    /**
     * Method to set the role of a User
     *
     * @param name    the name from Roles enum
     * @param context the context of the activity which this method is called
     */
    protected void setRoleId(Roles name, Context context) {
        try {
            List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(context);
            for (int id : roleIds) {
                if (Objects.equals(name.toString(), DatabaseSelectHelper.getRoleName(id,
                        context))) {
                    this.roleId = id;
                }
            }
        } catch (SQLException e) {
            this.roleId = 0;
        }
    }

    /**
     * Method to return the address of a User
     *
     * @return String address of User.
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Method to set the address of a User.
     *
     * @param address of the User.
     */
    protected void setAddress(String address) {
        this.address = address;
    }

}