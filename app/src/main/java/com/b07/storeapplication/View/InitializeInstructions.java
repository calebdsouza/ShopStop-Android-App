package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.InstructionNextOnClickController;
import com.b07.storeapplication.model.database.DatabaseSelectHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class InitializeInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize_instructions);
        setTitle("Welcome");

        Button next = (Button) findViewById(R.id.instructions_next_btn);
        next.setOnClickListener(new InstructionNextOnClickController(this));

        try {
            if (DatabaseSelectHelper.getRoleIds(this).size() > 1 &&
                    DatabaseSelectHelper.getAllItems(this).size() > 1 &&
                    DatabaseSelectHelper.getInventory(this).getItemMap().size() > 1) {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                finish();
            }
        } catch (SQLException e) {
            Toast.makeText(this, "Could not read from db",
                    Toast.LENGTH_SHORT).show();
        }


    }

}
