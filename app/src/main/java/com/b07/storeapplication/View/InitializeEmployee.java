package com.b07.storeapplication.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.InitializeEmployeeOnClickController;

public class InitializeEmployee extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_employee);
        setTitle("Initialize Employee");

        Button employee = (Button) findViewById(R.id.initialize_emp_btn);
        employee.setOnClickListener(new InitializeEmployeeOnClickController(this));
    }
}
