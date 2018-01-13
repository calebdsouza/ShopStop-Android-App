package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.b07.storeapplication.R;
import com.b07.storeapplication.model.users.Admin;

public class AdminViewCustomerAccounts extends AppCompatActivity {
    private TextView viewAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_customer_accounts);
        this.viewAccounts = findViewById(R.id.view_customer_accounts);
        setTitle("View Customer Accounts");
        Intent intent = getIntent();
        String text = intent.getStringExtra("accounts");

        this.viewAccounts.setText(text);


    }
}
