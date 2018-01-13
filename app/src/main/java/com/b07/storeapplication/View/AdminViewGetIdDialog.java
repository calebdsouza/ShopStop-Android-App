package com.b07.storeapplication.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.b07.storeapplication.R;

public class AdminViewGetIdDialog extends AppCompatDialogFragment {

    private EditText getId;
    private AdminViewGetIdDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder buildDialog = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_admin_view_get_id_dialog, null);

        buildDialog.setView(view);

        buildDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        buildDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String id = getId.getText().toString();
                listener.applyGivenId(id);
            }
        });

        this.getId = view.findViewById(R.id.admin_view_get_id);

        return buildDialog.create();
    }

    public EditText getDialogEditText() {
        return this.getId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (AdminViewGetIdDialogListener) context;
        } catch (ClassCastException e) {
            Toast.makeText(context, "Dialog listener was not connected",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
