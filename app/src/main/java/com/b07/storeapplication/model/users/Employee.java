package com.b07.storeapplication.model.users;

import android.content.Context;

import java.io.Serializable;

public class Employee extends User implements Serializable {

    /**
     * Creates an Employee without authentication stated.
     *
     * @param id      - the id
     * @param name    - the name
     * @param age     - the age
     * @param address - the address
     * @param context the context of the activity which this method is called
     */
    public Employee(int id, String name, int age, String address, Context context) {
        setId(id);
        setName(name);
        setAge(age);
        setAddress(address);
        setRoleId(Roles.EMPLOYEE, context);
    }

    /**
     * Creates an Employee with authentication stated.
     *
     * @param id            the id
     * @param name          the name
     * @param age           the age
     * @param address       the address
     * @param authenticated whether or not the Employee is authenticated
     * @param context the context of the activity which this method is called
     */
    public Employee(int id, String name, int age, String address, boolean authenticated,
                    Context context) {
        setId(id);
        setName(name);
        setAge(age);
        setAddress(address);
        setRoleId(Roles.EMPLOYEE, context);
        setAuthenticated(authenticated);
    }
}