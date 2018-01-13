package com.b07.storeapplication.View;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.LoginOnClickController;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    // UI references.
    private EditText userIdView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ShopStop Login");

        userIdView = (EditText) findViewById(R.id.login_user_id);
        passwordView = (EditText) findViewById(R.id.login_password);
        Button signUpBtn = (Button) findViewById(R.id.sign_in_button);

        // Set up the login form
        signUpBtn.setOnClickListener(new LoginOnClickController(this));

    }
}

