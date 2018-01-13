package com.b07.storeapplication.model.users;

import android.content.Context;

import java.io.Serializable;

public class Customer extends User implements Serializable {

    /**
     * Creates a Customer without authentication stated.
     *
     * @param id      the id of this customer
     * @param name    the name of this customer
     * @param age     the age of this customer
     * @param address the address of this address
     * @param context the context of the activity which this method is called
     */
    public Customer(int id, String name, int age, String address, Context context) {
        setId(id);
        setName(name);
        setAge(age);
        setAddress(address);
        setRoleId(Roles.CUSTOMER, context);
    }

    /**
     * Creates a Customer with authentication stated.
     *
     * @param id            the id of this Customer
     * @param name          the name of this Customer
     * @param age           the age of this Customer
     * @param address       the address of this Customer
     * @param authenticated the authentication of this state for this Customer
     * @param context the context of the activity which this method is called
     */
    public Customer(int id, String name, int age, String address, boolean authenticated,
                    Context context) {
        setId(id);
        setName(name);
        setAge(age);
        setAddress(address);
        setRoleId(Roles.CUSTOMER, context);
        setAuthenticated(authenticated);

    }
}