package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.CustomerVeiwDoNotLoadAccountOnClickController;
import com.b07.storeapplication.controller.CustomerViewLoadAccountOnClickController;
import com.b07.storeapplication.model.users.Customer;

public class CustomerViewLoadAccount extends AppCompatActivity {
    private Button loadAccountBtn;
    private Button doNotloadAccountBtn;
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_load_account);

        this.loadAccountBtn = findViewById(R.id.load_account_btn);
        this.doNotloadAccountBtn = findViewById(R.id.do_not_load_account_btn);

        Intent intent = getIntent();

        this.customer = (Customer) intent.getSerializableExtra("Customer");

        this.loadAccountBtn.setOnClickListener(new CustomerViewLoadAccountOnClickController(
                this, this.customer));
        this.doNotloadAccountBtn.setOnClickListener(
                new CustomerVeiwDoNotLoadAccountOnClickController(this, this.customer));
    }
}
