package com.example.group7mealerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import UserJavaFiles.Client;
import UserJavaFiles.Order;
import UserJavaFiles.User;
import codeModules.Modules;

public class purchaseHistory extends AppCompatActivity {
    Button complain;
    User user;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);
        complain = findViewById(R.id.buttonComplain);
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());
        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order order = new Order();
                showInfoDialoge(order);
            }
        });
    }

    private void showInfoDialoge(Order order){
        //build the dialogue
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_add_complaint, null);
        dialogBuilder.setView(dialogView);

        //settings up views
        Button submit = (Button) dialogView.findViewById(R.id.buttonSubmit);
        EditText complaint = (EditText) dialogView.findViewById(R.id.editTextComplaintDescription);
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());
        //person submitting complaint MUST be client so instant cast it
        client = (Client) user;
        final AlertDialog b = dialogBuilder.create();
        b.show();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strComplaint = complaint.getText().toString();
                //will have to be added later but make sure cooks email is attached to this method
                client.addComplaintDB(strComplaint,"remy@gmail.com");
                Toast.makeText(getApplicationContext(), "complaint issued wait for a response from admin", Toast.LENGTH_LONG).show();
                b.dismiss();
            }
        });
    }
}
