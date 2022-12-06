package com.example.group7mealerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import UserJavaFiles.Administrator;
import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.Order;
import UserJavaFiles.Suspension;
import UserJavaFiles.User;
import codeModules.Modules;
import listViewFiles.ComplaintList;
import listViewFiles.PendingPurchasesList;

public class pending_orders extends AppCompatActivity
{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, cookDatabaseReference;
    ListView listView;
    List<Order> orders;
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Orders");
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_orders);

        listView = (ListView)findViewById(R.id.list_orders);

        orders = new ArrayList<>();
        String id = databaseReference.push().getKey();

        //catching user information and loading it to page
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //only able to administer if you are an admin
                if(user.getClass() == Cook.class){
                    Order order = orders.get(i);
                    showOrderDialogue(order);
                }

            }
        });
    }

    //populate the list with orders from the DB
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Order order = postSnapshot.getValue(Order.class);
                    if(user.getClass() == Cook.class && user.getEmail().equals(order.getCookEmail())
                    && order.getState().equals("PENDING")){
                        orders.add(order);
                    }
                    else if (user.getClass() == Administrator.class){
                        orders.add(order);
                    }
                }
                PendingPurchasesList ordersAdapter = new PendingPurchasesList(pending_orders.this, orders);
                listView.setAdapter(ordersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showOrderDialogue(final Order order)
    {
        //build the diologue
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_pending_dialog, null);
        dialogBuilder.setView(dialogView);

        //SETTING UP THE TEXT FIELDS AND BUTTONS
        final TextView txtQuestion = (TextView) dialogView.findViewById(R.id.txtDialogQ);
        final Button btnAccept = (Button) dialogView.findViewById(R.id.btnAccept);
        final Button btnDeny = (Button) dialogView.findViewById(R.id.btnDeny);

        dialogBuilder.setTitle(order.getMeal().getName());
        final AlertDialog b = dialogBuilder.create();
        b.show();


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                order.setOrderState(order, "ACCEPTED");
                finish();
                b.dismiss();
            }
        });

        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.setOrderState(order, "REJECTED");
                finish();
                b.dismiss();
            }
        });
    }
}
