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
    Button button;
    Button complaintBtn;
    TextView text;
    User user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        try{
            user = (Client) getIntent().getSerializableExtra("Client");
            user.getFirstName();
        }catch (Exception e){
            System.out.println("error here1 " + e);
            try{
                user = (Cook) getIntent().getSerializableExtra("Cook");
                user.getFirstName();
            }catch (Exception g){
                System.out.println("error here2 " + e);
                try{
                    user = (Administrator) getIntent().getSerializableExtra("Admin");
                    user.getFirstName();
                }catch (Exception h){
                    System.out.println("error here3 " + e);
                }
            }
        }
        System.out.println(user.getFirstName());
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
        /*
        buttonComplaint = (Button)findViewById(R.id.btnCO);
        buttonComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openComplaint();
            }
        });

         */

        buttonSignOut = (Button)findViewById(R.id.btnSO);
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = null;
                openLogin();
            }
        });
        complaintBtn = findViewById((R.id.btnComplaint));
        complaintBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                btnComplaintClick();
            }
        });
    }
    public void btnComplaintClick()
    {
        Intent intent = new Intent(this, complaints_page.class);
        //clear user
        user = null;
        startActivity(intent);


    }
    //Method to take the user back to login page when they sign out
    public void openLogin(){
        Intent intent = new Intent(this, Login.class);
        //clear user
        user = null;
        startActivity(intent);

    }
    /*
    public void openComplaint()
        if (user.getClass() == Administrator.class ){
            Intent intent = new Intent(this, ComplaintPage2.class);
            startActivity(intent);
        } else if (user.getClass()==Cook.class){
            //make a activity specifically for cook.

        }


    }

     */


}