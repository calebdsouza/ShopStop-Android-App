package com.b07.storeapplication.View;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.CustomerViewAddItemOnClickController;
import com.b07.storeapplication.controller.CustomerViewCartOnClickController;
import com.b07.storeapplication.controller.CustomerViewCheckoutOnClickController;
import com.b07.storeapplication.controller.CustomerViewRemoveItemOnClickController;
import com.b07.storeapplication.controller.LogoutOnClickController;
import com.b07.storeapplication.model.store.ShoppingCart;
import com.b07.storeapplication.model.users.Customer;

/**
 * Created by cd on 2017-12-03.
 */

public class CustomerViewActivity extends AppCompatActivity {
    // Activity fields
    private Context context;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private int accountId;
    private Button shoppingCartBtn;
    private Button addItemBtn;
    private Button removeItemBtn;
    private Button checkoutBtn;
    private Button logoutBtn;
    private String topTen;
    private TextView topTenTextView;

    // Sensor fields
    private SensorManager sensorManager;
    private float accelerator;
    private float acceleratorCurrentState;
    private float acceleratorLastState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view);
        setTitle("Customer Mode");
        this.context = this;

        this.sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        this.sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.accelerator = 0.00f;
        this.acceleratorCurrentState = SensorManager.GRAVITY_EARTH;
        this.acceleratorLastState = SensorManager.GRAVITY_EARTH;


        Intent intent = getIntent();
        this.customer = (Customer) intent.getSerializableExtra("Customer");
        this.shoppingCart = (ShoppingCart) intent.getSerializableExtra("ShoppingCartToView");
        this.accountId = (int) intent.getIntExtra("AccountId", -1);
        this.shoppingCartBtn = (Button) findViewById(R.id.customer_view_cart_btn);
        this.addItemBtn = (Button) findViewById(R.id.customer_add_item_btn);
        this.removeItemBtn = (Button) findViewById(R.id.customer_remove_item_btn);
        this.checkoutBtn = (Button) findViewById(R.id.customer_checkout_btn);
        this.logoutBtn = (Button) findViewById(R.id.customer_logout_btn);


        this.shoppingCartBtn.setOnClickListener(new CustomerViewCartOnClickController(this,
                this.customer, this.shoppingCart));
        this.addItemBtn.setOnClickListener(new CustomerViewAddItemOnClickController(this,
                this.customer, this.shoppingCart));
        this.removeItemBtn.setOnClickListener(new CustomerViewRemoveItemOnClickController(
                this, this.customer, this.shoppingCart, this.accountId));
        this.checkoutBtn.setOnClickListener(new CustomerViewCheckoutOnClickController(this,
                this.customer, this.shoppingCart, accountId));
        this.logoutBtn.setOnClickListener(new LogoutOnClickController(this));

    }

    // Create an event sensor listener to listen to the phone's accelerometer
    private final SensorEventListener sensorListener = new SensorEventListener() {

        /**
         * Handle phone's position change.
         * @param sensorEvent get the sensor's sate
         */
        public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Get the last state of the accelerometer
            acceleratorLastState = acceleratorCurrentState;
            // Get teh current stat of teh accelerometer
            acceleratorCurrentState = (float) Math.sqrt((double) (x*x + y*y + z*z));
            // Calculate the position change of the phone
            float positionChange = acceleratorCurrentState - acceleratorLastState;
            // Add a lower sensitivity to change
            accelerator = accelerator * 0.05f + positionChange;
            System.out.println(accelerator);

            final int SHAKE_TRIGGER = 4;

            // Check if the phone has shaken
            if (Math.abs(accelerator) > SHAKE_TRIGGER) {
                //feature
                Intent topTenIntent = new Intent(context,
                        TopTenItems.class);
                context.startActivity(topTenIntent);
                Toast.makeText(context, "Phone has shaken.", Toast.LENGTH_LONG).show();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        this.sensorManager.registerListener(this.sensorListener,
                this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        this.sensorManager.unregisterListener(sensorListener);
        super.onPause();
    }

}