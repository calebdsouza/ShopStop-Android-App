package com.b07.storeapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.InitializeAdminOnClickController;
import com.b07.storeapplication.controller.InstructionNextOnClickController;

public class InitializeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_admin);
        setTitle("Initialize Admin");

        Button admin = (Button) findViewById(R.id.initialize_admin_btn);
        admin.setOnClickListener(new InitializeAdminOnClickController(this));
    }
}
