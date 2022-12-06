package com.example.group7mealerapp;


import androidx.appcompat.app.AppCompatActivity;




import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.os.Bundle;

import android.widget.TextView;





import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;

import UserJavaFiles.User;
import codeModules.Modules;

/**
 * java class for the welcome page, does a multitude of things dependent on what user is
 * logging in. simply displays a welcome msg for now
 */
public class WelcomePage extends AppCompatActivity {
    //sign out button
    Button buttonSignOut, complaintBtn, searchBtn, buttonMenu, buttonOrderHistory, btnProfile;
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
        btnProfile = findViewById(R.id.btnProfile);
        btnProfile.setVisibility((View.GONE));



        buttonOrderHistory = (Button)findViewById(R.id.buttonOrderHistory);
        buttonOrderHistory.setVisibility((View.GONE));

        //get the user from login
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        //setting specifics based on type of user
        text = (TextView)findViewById(R.id.textView6);
        if (user.getClass() == Cook.class){
            complaintBtn.setVisibility(View.VISIBLE);
            buttonMenu.setVisibility(View.VISIBLE);
            btnProfile.setVisibility(View.VISIBLE);
            Cook cook = (Cook) user;
            //for now cooks cant search and purchase meals for themselves
            try{
                if (cook.getSuspension().getPerma() == true){
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are currently banned until further notice!");
                    buttonMenu.setVisibility((View.GONE));
                    btnProfile.setVisibility(View.GONE);
                }
                else if (!cook.getSuspension().getBannedUntil().equals("OKAY") ){
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are currently suspended until " + cook.getSuspension().getBannedUntil());
                    buttonMenu.setVisibility((View.GONE));
                    btnProfile.setVisibility(View.GONE);

                }
                //EXAMPLE CODE HERE FOR GETTING THE AVERAGE OF A COOKS RATING!
                else {
                    text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
                }
                //END OF EXAMPLE CODE
            }catch(Exception e){
                text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
            }
        }
        if (user.getClass() == Client.class ){
            searchBtn.setVisibility(View.VISIBLE);
            //if Client logs on then the complaint button is invisible
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a client.");
            buttonOrderHistory.setVisibility(View.VISIBLE);

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

        buttonOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {buttonOrderHistoryClick();}
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openCookProfile();}
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

    public void openCookProfile(){
        Intent intent = new Intent(this, cook_profile.class);
        Cook cook = (Cook) user;
        intent.putExtra("Cook", cook);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
    public void btnSearchClick(){
        Intent switchPage = new Intent(this, Search.class);
        if(user.getClass() == Administrator.class)
            switchPage.putExtra("Admin",user);
        else if(user.getClass() == Client.class){
            Client client = (Client) user;

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
    public void buttonOrderHistoryClick(){
        Intent switchPage = new Intent(this, purchaseHistory.class);
        Client client = (Client) user;
        switchPage.putExtra("Client",client);
        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);
    }

}
