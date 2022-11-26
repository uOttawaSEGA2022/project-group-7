package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.Meal;
import UserJavaFiles.Order;
import UserJavaFiles.User;
import codeModules.Modules;

public class WelcomePage extends AppCompatActivity {
    //sign out button
    Button buttonSignOut, complaintBtn, searchBtn, buttonMenu;
    TextView text;
    User user;

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
                else
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
            }catch(Exception e){
                text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
            }
        }
        if (user.getClass() == Client.class ){
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
    //Method to take the user back to login page when they sign out
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
            client.getCreditCardInfo().setExpirationDate(null);
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