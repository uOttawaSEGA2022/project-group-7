package com.example.group7mealerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.Order;
import UserJavaFiles.Suspension;
import UserJavaFiles.Rating;
import UserJavaFiles.User;
import codeModules.Modules;
import listViewFiles.OrderList;

public class purchaseHistory extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference ordersDatabaseReference, cookDatabaseReference;
    ListView listViewOrders;
    List<Order> orders;
    Button complain;
    User user;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        ordersDatabaseReference = firebaseDatabase.getReference("Orders");
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_purchase_history);
//        complain = findViewById(R.id.buttonComplain);
        setContentView(R.layout.activity_order_list);
        listViewOrders = (ListView) findViewById(R.id.orderList);
        orders = new ArrayList<>();
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());
//        complain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Order order = new Order();
//                showInfoDialoge(order);
//            }
//        });
        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //only able to rate and compain if you are client and the state of the order is accepted
                if(user.getClass() == Client.class && orders.get(i).getState().equals("ACCEPTED") && !orders.get(i).isRated()){
                    Order order = orders.get(i);
                    showOrderDialogue(order);

                }

            }
        });
    }

    //populate the list with orders from the DB
    protected void onStart() {
        super.onStart();

        ordersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orders.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Order order = postSnapshot.getValue(Order.class);
                    if(user.getClass() == Client.class && order.getUserEmail().equals(user.getEmail())){
                        orders.add(order);
                    }

                }
                OrderList ordersAdapter = new OrderList(purchaseHistory.this, orders);
                listViewOrders.setAdapter(ordersAdapter);
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
        final View dialogView = inflater.inflate(R.layout.activity_purchase_history, null);
        dialogBuilder.setView(dialogView);

        //SETTING UP THE TEXT FIELDS AND BUTTONS
        final EditText editTextRate  = (EditText) dialogView.findViewById(R.id.editTextRate);
        final Button buttonComplain = (Button) dialogView.findViewById(R.id.buttonComplain);
        final TextView buttonRate = (TextView) dialogView.findViewById(R.id.buttonRate);

        dialogBuilder.setTitle(order.getMeal().getName() + " (if you would like to rate click the button twice)");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(order.isRated())
                {
                    buttonRate.setError("This order was already rated");
                }

                else if(order.getState().equals("ACCEPTED"))
                {
                    Rating rating = null;
                    boolean flag = true;
                    Context context = getApplicationContext();
                    String error = "Enter a number between 1 and 5";
                    int duration = Toast.LENGTH_LONG;
                    String email = order.getCookEmail();

                    try {
                        double rate = Double.parseDouble(editTextRate.getText().toString());
                        rating = new Rating(rate,email);
                    }
                    catch (NumberFormatException e)
                    {
                        flag = false;
                        editTextRate.setError("Please enter a number");
                        Toast.makeText(context, error, duration).show();
                    }

                    catch (IllegalArgumentException ex)
                    {
                        flag = false;
                        editTextRate.setError(error);
                        Toast.makeText(context, error, duration).show();
                    }

                    if(flag)
                    {
                        order.setRatedState(order);
                        rating.setRatingDB();


                        finish();
                        b.dismiss();
                    }
                }

                else
                {
                    buttonRate.setError("This order was not accepted");
                }


            }
        });

        buttonComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                boolean flag = true;
                String strComplaint = complaint.getText().toString();
                String email = order.getCookEmail();

                if(strComplaint.isEmpty())
                {
                    complaint.setError("Please do not leave blank");
                    flag = false;
                }

                //will have to be added later but make sure cooks email is attached to this method
                if(flag && order.getState().equals("ACCEPTED"))
                {
                    client.addComplaintDB(strComplaint,email);
                    Toast.makeText(getApplicationContext(), "complaint issued wait for a response from admin", Toast.LENGTH_LONG).show();
                    b.dismiss();
                }
                else
                {
                    complaint.setError("The order has not been accepted you may not sumbit a complaint");
                    flag = false;
                }
            }
        });
    }
}
