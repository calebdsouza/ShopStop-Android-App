package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.EmployeeViewOnSelectController;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;
import com.b07.storeapplication.model.exceptions.NotAuthenticatedException;
import com.b07.storeapplication.model.inventory.Inventory;
import com.b07.storeapplication.model.store.EmployeeInterface;
import com.b07.storeapplication.model.users.Employee;

import java.sql.SQLException;

public class EmployeeViewActivity extends AppCompatActivity {
    private String[] selectorOptions = new String[] {"Create Account", "Create Customer",
            "Create Employee", "Restock Inventory", "Authenticate New Employee"};
    private Button authNewEmplBtn;
    private Button makeNewUserBtn;
    private Button makeNewAccountBtn;
    private Button makeNewEmplBtn;
    private Button restockInventBtn;
    private Inventory inventory = null;
    private EmployeeInterface employeeInterface = null;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_view);
        setTitle("Employee Mode");
        NumberPicker empActionSelector = findViewById(R.id.emp_action_type);
        Button empActionBtn = (Button) findViewById(R.id.employee_action_btn);

        Intent intent = getIntent();
        this.employee = (Employee) intent.getSerializableExtra("Employee");

        empActionSelector.setMinValue(0);
        empActionSelector.setMaxValue(selectorOptions.length - 1);
        empActionSelector.setDisplayedValues(selectorOptions);
        empActionSelector.setWrapSelectorWheel(true);

        try {
            this.inventory = DatabaseSelectHelper.getInventory(this);
        } catch (SQLException e) {

        }

        this.employeeInterface = new EmployeeInterface(this.inventory);

        try {
            this.employeeInterface.setCurrentEmployee(this.employee);
        } catch (NotAuthenticatedException e) {
            Toast.makeText(this, "Employee is not authenticated",
                    Toast.LENGTH_SHORT).show();
        } catch (NullPointerException ne) {
            Toast.makeText(this, "Error: null employee",
                    Toast.LENGTH_SHORT).show();
        }

        empActionSelector.setOnValueChangedListener(new EmployeeViewOnSelectController(
                this, selectorOptions, employeeInterface, this.employee));
    }



}
