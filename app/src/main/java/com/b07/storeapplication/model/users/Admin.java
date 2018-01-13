package com.b07.storeapplication.model.users;

import android.content.Context;

import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.database.DatabaseUpdateHelper;

import java.io.Serializable;
import java.sql.SQLException;

public class Admin extends Employee implements Serializable {

    /**
     * Given id, name, age and address, creates a new instance of Admin.
     *
     * @param id      - the User id of this Admin
     * @param name    - the User Name of this Admin
     * @param age     - the user's age for this Admin
     * @param address - the user's address for this Admin
     * @param context the context of the activity which this method is called
     */

    public Admin(int id, String name, int age, String address, Context context) throws
            SQLException {
        super(id, name, age, address, context);
        setRoleId(Roles.ADMIN, context);
    }

    /**
     * Given id, name, age and address, and authenticated, creates a new instance of Admin.
     *
     * @param id            - the User id of this Admin
     * @param name          - the User Name of this Admin
     * @param age           - the user's age for this Admin
     * @param address       - the user's address for this Admin
     * @param authenticated - the authenticated state for this Admin
     * @param context       the context of the activity which this method is called
     */

    public Admin(int id, String name, int age, String address, boolean authenticated,
                 Context context) {
        super(id, name, age, address, authenticated, context);
        setRoleId(Roles.ADMIN, context);
        setAuthenticated(authenticated);
    }

    /**
     * Promote the given Employee to an Admin.
     *
     * @param employee - the Employee to be made into an Admin
     * @param context  the context of the activity which this method is called
     * @return true if the employee was made into a Admin, false otherwise
     */
    public boolean promoteEmployee(Employee employee, Context context) throws SQLException {
        DatabaseUpdateHelper.updateRoleName("ADMIN", this.getRoleId(), context);
        DatabaseUpdateHelper.updateUserRole(this.getRoleId(), employee.getId(), context);
        if ((DatabaseSelectHelper.getUserDetails(employee.getId(), context)) instanceof Admin) {
            // create new instance of Employee in memory.
            employee = new Admin(employee.getId(), employee.getName(), employee.getAge(), employee
                    .getAddress(), context);
            return true;
        }
        return false;
    }

}
