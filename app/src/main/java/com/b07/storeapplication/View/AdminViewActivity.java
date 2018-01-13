package com.b07.storeapplication.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.b07.storeapplication.R;
import com.b07.storeapplication.controller.AdminVeiwGetIdDialogAfterClickedController;
import com.b07.storeapplication.controller.AdminViewActiveOnClickController;
import com.b07.storeapplication.controller.AdminViewAddNewItemOnClickController;
import com.b07.storeapplication.controller.AdminViewGetItemDialogAfterOnClickedController;
import com.b07.storeapplication.controller.AdminViewInactiveOnClickController;
import com.b07.storeapplication.controller.AdminViewPromoteOnClickController;
import com.b07.storeapplication.controller.AdminViewSalesHistoryOnClickController;
import com.b07.storeapplication.controller.LogoutOnClickController;
import com.b07.storeapplication.model.users.Admin;


public class AdminViewActivity extends AppCompatActivity implements AdminViewGetIdDialogListener,
        AdminViewGetItemDialogListener {
    private Admin admin = null;
    private Button promoteBtn = null;
    private Button addNewItem = null;
    private Button salesHistoryBtn = null;
    private Button activeBtn = null;
    private Button inactiveBtn = null;
    private Button logoutBtn = null;
    private TextView adminAction = null;

    private AdminVeiwGetIdDialogAfterClickedController afterGetIdClickedController = null;
    private AdminViewGetItemDialogAfterOnClickedController afterGetItemClickedController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        setTitle("Admin Mode");

        this.adminAction = findViewById(R.id.admin_action);
        this.adminAction.setVisibility(View.GONE);

        this.promoteBtn = findViewById(R.id.promote_emp_btn);
        this.addNewItem = findViewById(R.id.add_new_item_btn);
        this.salesHistoryBtn = findViewById(R.id.view_sales_history_btn);
        this.activeBtn = findViewById(R.id.veiw_active_accounts_btn);
        this.inactiveBtn = findViewById(R.id.view_inactive_accounts_btn);
        this.logoutBtn = findViewById(R.id.admin_logout_btn);


        Intent intent = getIntent();
        this.admin = (Admin) intent.getSerializableExtra("Admin");

        this.promoteBtn.setOnClickListener(new AdminViewPromoteOnClickController(this));
        this.addNewItem.setOnClickListener(new AdminViewAddNewItemOnClickController(this));
        this.salesHistoryBtn.setOnClickListener(new AdminViewSalesHistoryOnClickController(
                this));
        this.activeBtn.setOnClickListener(new AdminViewActiveOnClickController(this));
        this.inactiveBtn.setOnClickListener(new AdminViewInactiveOnClickController(this));
        this.logoutBtn.setOnClickListener(new LogoutOnClickController(this));

    }


    @Override
    public void applyGivenId(String id) {
        String action = this.adminAction.getText().toString();
        this.afterGetIdClickedController = new AdminVeiwGetIdDialogAfterClickedController(this,
                admin);
        Intent accountIntent;
        if (action.equals("promote")) {
            this.afterGetIdClickedController.promoteEmployee(id);
        } else if (action.equals("active")) {
            String activeAccounts = this.afterGetIdClickedController.viewActiveAccounts(id);
            if (!activeAccounts.equals("null")) {
                if (!activeAccounts.equals("This Customer has no accounts")) {
                    accountIntent = new Intent(this,
                            AdminViewCustomerAccounts.class);
                    accountIntent.putExtra("accounts", activeAccounts);
                    this.startActivity(accountIntent);
                }
            }
        } else if (action.equals("inactive")) {
            String inactiveAccounts = this.afterGetIdClickedController.viewInactiveAccounts(id);
            if (!inactiveAccounts.equals("null")) {
                if (!inactiveAccounts.equals("This Customer has no accounts")) {
                    accountIntent = new Intent(this,
                            AdminViewCustomerAccounts.class);
                    accountIntent.putExtra("accounts", inactiveAccounts);
                    this.startActivity(accountIntent);
                }
            }
        }
    }


    @Override
    public void applyItemInfo(String itemName, String itemPrice) {
        this.afterGetItemClickedController = new AdminViewGetItemDialogAfterOnClickedController(
                this, admin);
        this.afterGetItemClickedController.addNewItem(itemName, itemPrice);
    }
}
