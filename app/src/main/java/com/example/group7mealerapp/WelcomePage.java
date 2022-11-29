package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Base64;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;

import UserJavaFiles.Meal;
import UserJavaFiles.Order;
import UserJavaFiles.Rating;
import UserJavaFiles.User;
import codeModules.Modules;

/**
 * java class for the welcome page, does a multitude of things dependent on what user is
 * logging in. simply displays a welcome msg for now
 */
public class WelcomePage extends AppCompatActivity {
    //sign out button
    Button buttonSignOut, complaintBtn, searchBtn, buttonMenu;
    TextView text;
    User user;
    ImageView blankCheque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);
        //set up the visibilities and buttons
        complaintBtn = findViewById((R.id.btnComplaint));
        complaintBtn.setVisibility(View.GONE);
        searchBtn = findViewById((R.id.btnSearch));
        searchBtn.setVisibility((View.GONE));
        buttonSignOut = (Button)findViewById(R.id.btnSO);
        buttonMenu = (Button)findViewById(R.id.btnEditMenu);
        buttonMenu.setVisibility((View.GONE));
        blankCheque = (ImageView)findViewById(R.id.imageViewBlankCheque);
        //blankCheque.setVisibility((View.GONE));

        //TESTING THE RATING (PUSHING A RATING)
        /*Rating rating;
        try {
            rating = new Rating(5, "remy@gmail.com");
            rating.setRatingDB();
        }catch (Exception e){
            System.out.println(e + " should be illegal argument!");
        }*/

        //END OF TESTING
        //get the user from login
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        //setting specifics based on type of user
        text = (TextView)findViewById(R.id.textView6);
        if (user.getClass() == Cook.class){
            complaintBtn.setVisibility(View.VISIBLE);
            buttonMenu.setVisibility(View.VISIBLE);
            Cook cook = (Cook) user;
            //for now cooks cant search and purchase meals for themselves
            try{
                if (cook.getSuspension().getPerma() == true){
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are currently banned until further notice!");
                    buttonMenu.setVisibility((View.GONE));
                }
                else if (!cook.getSuspension().getBannedUntil().equals("OKAY") ){
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are currently suspended until " + cook.getSuspension().getBannedUntil());
                    buttonMenu.setVisibility((View.GONE));

                }
                //EXAMPLE CODE HERE FOR GETTING THE AVERAGE OF A COOKS RATING!
                else {
                    /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Ratings");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> children = snapshot.getChildren();
                            Rating temp = new Rating();
                            double total = 0;
                            int size = 0;
                            for (DataSnapshot child : children) {
                                temp = child.getValue(Rating.class);
                                if (temp.getEmail().equals(cook.getEmail())) {
                                    total += temp.getRating();
                                    size++;
                                }
                                temp = null;
                            }
                            //set the text within the function or else errors will occur!
                            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+
                                    ", you are a cook with rating : " + total/size);
                            databaseReference.removeEventListener(this);
                        }
                        //no need for this function but must be overridden
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });*/
                    System.out.println("blank cheque" + cook.getBlankCheque());
                    byte[] imageString = Base64.getDecoder().decode(cook.getBlankCheque());
                    Bitmap image = BitmapFactory.decodeByteArray(imageString,0,imageString.length);
                    blankCheque.setImageBitmap(image);
                    blankCheque.setVisibility(View.VISIBLE);
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
                }
                //END OF EXAMPLE CODE
            }catch(Exception e){
                text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
            }
        }
        if (user.getClass() == Client.class ){
            //TEST CODE FOR ORDERS CHECK HERE FOR REFERENCE (PUSHING ORDER TO DB)
            /*Client client = (Client) user;
            Meal meal = new Meal("sphagetti","Dinner","Itallian","sphagetti","remy@gmail.com",12.00,false);
            client.addOrder("remy@gmail.com", meal);*/
            //END OF TEST CODE
            searchBtn.setVisibility(View.VISIBLE);
            //if Client logs on then the complaint button is invisible
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a client.");


        }
        if (user.getClass() == Administrator.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are the administrator.");
            complaintBtn.setVisibility(View.VISIBLE);
            searchBtn.setVisibility(View.VISIBLE);
        }

        //setting up buttons
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = null;
                openLogin();
            }
        });

        complaintBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnComplaintClick();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {btnSearchClick();}
        });
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {btnMenuClick();}
        });
    }
    //button click methods
    public void btnComplaintClick()
    {
        Intent switchPage = new Intent(this, complaints_page.class);
        if(user.getClass() == Cook.class){

            Cook cook = (Cook) user;

            switchPage.putExtra("Cook",cook);
        }

        else if(user.getClass() == Administrator.class)
            switchPage.putExtra("Admin",user);



        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);


    }
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        //clear user
        user = null;
        startActivity(intent);

    }
    public void btnSearchClick(){
        Intent switchPage = new Intent(this, Search.class);
        if(user.getClass() == Administrator.class)
            switchPage.putExtra("Admin",user);
        else if(user.getClass() == Client.class){
            Client client = (Client) user;
            System.out.println(client.getCreditCardInfo());
            switchPage.putExtra("Client", client);
        }
        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);

    }
    public void btnMenuClick(){
        Intent switchPage = new Intent(this, Search.class);
        Cook cook = (Cook) user;
        switchPage.putExtra("Cook",cook);
        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);
    }

}