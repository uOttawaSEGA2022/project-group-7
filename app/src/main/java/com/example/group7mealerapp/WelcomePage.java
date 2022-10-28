package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.TextView;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.User;

public class WelcomePage extends AppCompatActivity {
    //sign out button
    Button buttonSignOut;
    Button buttonComplaint;
    TextView text;
    User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        //button to sign out
        text = (TextView)findViewById(R.id.textView6);
        if (user.getClass() == Cook.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a cook.");
        }
        if (user.getClass() == Client.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are a client.");
        }
        if (user.getClass() == Administrator.class ){
            text.setText("welcome," +user.getFirstName()+' '+user.getLastName()+ ", you are the administrator.");
        }



        if (user.getClass() == Administrator.class ){
            buttonComplaint.setVisibility(View.VISIBLE);
        }
        if (user.getClass() == Cook.class){
            buttonComplaint.setVisibility(View.VISIBLE);
        }
        buttonComplaint = (Button)findViewById(R.id.btnCO);
        buttonComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComplaint();
            }
        });

        buttonSignOut = (Button)findViewById(R.id.btnSO);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });


    }
    //Method to take the user back to login page when they sign out
    public void openLogin(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    public void openComplaint(){
        if (user.getClass() == Administrator.class ){
            Intent intent = new Intent(this, ComplaintPage2.class);
            startActivity(intent);
        } else if (user.getClass()==Cook.class){
            //make a activity specifically for cook.

        }


    }

}