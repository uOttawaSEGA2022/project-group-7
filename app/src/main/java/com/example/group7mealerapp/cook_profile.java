package com.example.group7mealerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import UserJavaFiles.Cook;
import UserJavaFiles.Rating;
import UserJavaFiles.User;
import codeModules.Modules;

public class cook_profile extends AppCompatActivity
{
    Button btnSignOut, btnPendingOrders, btnApprove, btnDecline;
    TextView name, description, rating;
    User user;

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

        //get user info from welcome page
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        if(user.getClass() == Cook.class)
        {
            //Display cook name
            name.setText("Name: " + user.getFirstName() + " " + user.getLastName());
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
                    //set the text within the function or else errors will occur!
//                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+
//                            ", you are a cook with rating : " + total/size);
                    rating.setText("Rating: " + total/size);
                    databaseReference.removeEventListener(this);
                }
                //no need for this function but must be overridden
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
