package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import android.os.Bundle;

import java.util.Date;

import OtherJavaFiles.Client;
import OtherJavaFiles.CreditCard;
import OtherJavaFiles.User;

public class Registration extends AppCompatActivity
{
    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    //reference to the user object will be made into info
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
    }


    /**
     * This method registers the user into the database.
     * To register, it is required to fill out a First name, Last name, Email, Password and Role
     *
     * Validity of the field entries are also checked in this method
     */
    public void RegisterButtonClick(View view)
    {
        //Check if all fields are filled

        //Make sure first name field isn't empty
        EditText Fname = (EditText) findViewById(R.id.FirstNameField);
        String strFname = Fname.getText().toString();


        if(TextUtils.isEmpty(strFname))
        {
            Fname.setError("This field cannot be empty");
        }

        //Make sure last name field isn't empty
        EditText Lname = (EditText) findViewById(R.id.LastNameField);
        String strLname = Lname.getText().toString();
        if(TextUtils.isEmpty(strLname))
        {
            Lname.setError("This field cannot be empty");
        }

        //Make sure email field isn't empty
        EditText Email = (EditText) findViewById(R.id.EmailField);
        String strEmail = Email.getText().toString();
        if(TextUtils.isEmpty(strEmail))
        {
            Email.setError("This field cannot be empty");
        }

        //Make sure password field isn't empty
        EditText Password = (EditText) findViewById(R.id.PasswordField);
        String strPassword = Password.getText().toString();
        if(TextUtils.isEmpty(strPassword))
        {
            Password.setError("This field cannot be empty");
        }

        //Make sure re-enter password field isn't empty
        EditText rePassword = (EditText) findViewById(R.id.ConfirmPasswordField);
        String strrePassword = rePassword.getText().toString();
        if(TextUtils.isEmpty(strrePassword))
        {
            rePassword.setError("This field cannot be empty");
        }

        //Make sure email is a valid address

        //Make sure password and re-enter password match
        if(!strPassword.equals(strrePassword))
        {
            Password.setError("Passwords must match");
            rePassword.setError("Passwords must match");
            return;
        }
        //Write to file
        //dont need these at the time but implementing anyways
        String dummyAdress = "2360 ajax" ;
        Date date = new Date(20);
        CreditCard card = new CreditCard(strFname,strLname,dummyAdress,1000,123,date);
        //creates a new user of CLIENT should implement so button listens properly!!
        user = new Client(strFname, strLname, strEmail, strPassword, dummyAdress,card);
        databaseReference.setValue(user);


    }


}