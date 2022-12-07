package com.example.group7mealerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.Order;
import UserJavaFiles.Rating;
import UserJavaFiles.User;
import codeModules.Modules;
import listViewFiles.OrderList;

public class cook_profile extends AppCompatActivity
{
    Button btnSignOut, btnPendingOrders;
    TextView name, description, rating,email,address,numberSold;
    User user;
    ImageView blankCheque;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profile);

        //initialize buttons
        btnSignOut = findViewById((R.id.btnSignOut));
        btnPendingOrders = findViewById(R.id.btnPendingOrders);

        //initialize text fields
        name = (TextView)findViewById(R.id.txtName);
        rating = (TextView)findViewById(R.id.txtRating);
        description = (TextView)findViewById(R.id.txtDescription);
        email = (TextView)findViewById((R.id.txtEmail));
        address = (TextView)findViewById(R.id.txtAddress);
        blankCheque = (ImageView)findViewById(R.id.imgBlankCheque);
        numberSold = (TextView)findViewById(R.id.txtNumberSold);
        //get user info from welcome page
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());


        if(user.getClass() == Cook.class)
        {
            Cook cook = (Cook) user;
            //Display cook name
            name.setText("Name: " + user.getFirstName() + " " + user.getLastName());
            description.setText("description: " + cook.getDescription());
            email.setText(("email: " + cook.getEmail()));
            address.setText("Address: " + cook.getAddress());
            byte[] imageString = Base64.getDecoder().decode(cook.getBlankCheque());
            Bitmap image = BitmapFactory.decodeByteArray(imageString,0,imageString.length);
            blankCheque.setImageBitmap(image);
            //Display cook rating
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Ratings");
            databaseReference.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    Rating temp = new Rating();
                    double total = 0;
                    int size = 0;
                    for (DataSnapshot child : children) {
                        temp = child.getValue(Rating.class);
                        if (temp.getEmail().equals(user.getEmail())) {
                            total += temp.getRating();
                            size++;
                        }
                        temp = null;
                    }

                    rating.setText("Cook Rating: " + (double)Math.round((total/size) * 10)/10);
                    if(size == 0)
                        rating.setText("unrated");
                    databaseReference.removeEventListener(this);
                }
                //no need for this function but must be overridden
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            DatabaseReference databaseReferenceOrders = firebaseDatabase.getReference("Orders");
            databaseReferenceOrders.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int noSold = 0;
                    for(DataSnapshot postSnapshot : snapshot.getChildren()){
                        Order order = postSnapshot.getValue(Order.class);
                        if(order.getCookEmail().equals(user.getEmail()) && order.getState().equals("ACCEPTED")){
                            noSold++;
                        }
                    }
                    numberSold.setText("number of meals sold " + noSold);
                    databaseReferenceOrders.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //Set up buttons

        //Sign out and return to main login screen
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = null;
                openLogin();
            }
        });

        //Open screen to view pending orders
        btnPendingOrders.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {openPurchases();}
        });

    } //end of onCreate

    //Sign user out and return to main screen
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        //clear user
        user = null;
        startActivity(intent);
    }

    public void openPurchases(){
        Intent intent = new Intent(this, pending_orders.class);
        Cook cook = (Cook) user;
        intent.putExtra("Cook", cook);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
}
