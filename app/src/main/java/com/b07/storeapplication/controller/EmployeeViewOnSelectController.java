package com.b07.storeapplication.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.store.EmployeeInterface;
import com.b07.storeapplication.model.users.Employee;

/**
 * Created by cd on 2017-12-01.
 */

public class EmployeeViewOnSelectController implements NumberPicker.OnValueChangeListener {
    private Context appContext;
    private NumberPicker actionSelector = null;
    private String[] selectorOptions = new String[] {"Create Account", "Create Customer",
            "Create Employee", "Restock Inventory", "Authenticate New Employee"};
    private EditText idInput = null;
    private EditText quantityInput = null;
    private EditText nameInput = null;
    private EditText ageInput = null;
    private EditText addressInput = null;
    private EditText passwordInput = null;
    private EmployeeInterface employeeInterface = null;
    private Employee employee = null;

    /**
     * Constructor to create a new employee view on select controller.
     * @param context the activity context
     * @param options the options of the number picker
     */
    public EmployeeViewOnSelectController(Context context, String[] options,
                                          EmployeeInterface empInter, Employee employee) {
        this.appContext = context;
        //this.actionSelector = (NumberPicker) ((AppCompatActivity) appContext).findViewById(
        //        R.id.account_type);
        this.idInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_id);
        this.quantityInput = (EditText) ((AppCompatActivity) appContext).findViewById(
                R.id.input_quantity);
        this.nameInput = ((AppCompatActivity) appContext).findViewById(
                R.id.input_name);
        this.ageInput = ((AppCompatActivity) appContext).findViewById(
                R.id.input_age);
        this.addressInput = ((AppCompatActivity) appContext).findViewById(
                R.id.input_address);
        this.passwordInput = ((AppCompatActivity) appContext).findViewById(
                R.id.input_password);
        this.selectorOptions = options;
        this.employeeInterface = empInter;
        this.employee = employee;
    }


    @Override
    public void onValueChange(NumberPicker picker, int oldOption, int newOption){
        Button actionBtn = (Button) ((AppCompatActivity) appContext).findViewById(
                R.id.employee_action_btn);
        actionBtn.setText(selectorOptions[newOption]);

        if (this.selectorOptions[newOption].equals("Create Account")) {
            idInput.setVisibility(View.VISIBLE);
            quantityInput.setVisibility(View.GONE);
            nameInput.setVisibility(View.GONE);
            ageInput.setVisibility(View.GONE);
            addressInput.setVisibility(View.GONE);
            passwordInput.setVisibility(View.GONE);

            actionBtn.setOnClickListener(new EmployeeViewOnClickController(this.appContext,
                    this.selectorOptions[newOption], this.employeeInterface, this.employee));
        } else if (selectorOptions[newOption].equals("Create Customer")) {
            idInput.setVisibility(View.GONE);
            quantityInput.setVisibility(View.GONE);
            nameInput.setVisibility(View.VISIBLE);
            ageInput.setVisibility(View.VISIBLE);
            addressInput.setVisibility(View.VISIBLE);
            passwordInput.setVisibility(View.VISIBLE);

            actionBtn.setOnClickListener(new EmployeeViewOnClickController(this.appContext,
                    this.selectorOptions[newOption], this.employeeInterface, this.employee));
        } else if (selectorOptions[newOption].equals("Create Employee")) {
            idInput.setVisibility(View.GONE);
            quantityInput.setVisibility(View.GONE);
            nameInput.setVisibility(View.VISIBLE);
            ageInput.setVisibility(View.VISIBLE);
            addressInput.setVisibility(View.VISIBLE);
            passwordInput.setVisibility(View.VISIBLE);

            actionBtn.setOnClickListener(new EmployeeViewOnClickController(this.appContext,
                    this.selectorOptions[newOption], this.employeeInterface, this.employee));
        } else if (selectorOptions[newOption].equals("Restock Inventory")) {
            idInput.setVisibility(View.VISIBLE);
            quantityInput.setVisibility(View.VISIBLE);
            nameInput.setVisibility(View.GONE);
            ageInput.setVisibility(View.GONE);
            addressInput.setVisibility(View.GONE);
            passwordInput.setVisibility(View.GONE);

            actionBtn.setOnClickListener(new EmployeeViewOnClickController(this.appContext,
                    this.selectorOptions[newOption], this.employeeInterface, this.employee));
        } else if (selectorOptions[newOption].equals("Authenticate New Employee")) {
            idInput.setVisibility(View.VISIBLE);
            quantityInput.setVisibility(View.GONE);
            nameInput.setVisibility(View.GONE);
            ageInput.setVisibility(View.GONE);
            addressInput.setVisibility(View.GONE);
            passwordInput.setVisibility(View.VISIBLE);

            actionBtn.setOnClickListener(new EmployeeViewOnClickController(this.appContext,
                    this.selectorOptions[newOption], this.employeeInterface, this.employee));
        }

    }
}