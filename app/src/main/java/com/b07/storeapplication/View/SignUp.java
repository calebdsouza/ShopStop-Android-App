package com.b07.storeapplication.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Button;

import com.b07.storeapplication.R;

public class SignUp extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText inputName = (EditText) findViewById(R.id.input_name);
        final Button createUserBtn = (Button) findViewById(R.id.create_user);
        NumberPicker accountTypes = (NumberPicker) findViewById(R.id.account_type);
        //inputName.setText("Disable");
        //inputName.setEnabled(true);
        //Set the selector wheel to wrap if the min/max value is reached.
        final String[]  userTypes = new String[] {"Create Account", "Create Admin",
                "Create Employee", "Restock Inventory", "Authenticate New Employee"};
        accountTypes.setMinValue(0);
        accountTypes.setMaxValue(userTypes.length - 1);

        accountTypes.setDisplayedValues(userTypes);
        accountTypes.setWrapSelectorWheel(true);
        accountTypes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldUserTitle, int newUserTitle){

                if (userTypes[newUserTitle].equals("Account")) {
                    //inputName.setEnabled(false);
                    inputName.setVisibility(View.GONE);
                } else {
                    inputName.setVisibility(View.VISIBLE);
                }
                //Display the newly selected value from picker
                createUserBtn.setText(userTypes[newUserTitle]);
            }
        });
        //accountTypes.setMinValue(displayedValues.size()-1);

    }
}
