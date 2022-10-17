package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;

import java.util.Date;

import UserJavaFiles.Client;
import UserJavaFiles.CreditCard;
import UserJavaFiles.User;
import UserJavaFiles.UserPOJO;

/**
 * The registration page takes the basics as of now (first and last name, email, password, address)
 * and makes the other fields null ie credit card info and blank cheque which will later be
 * implemented
 */
public class Registration extends AppCompatActivity
{
    //current user is going to be passed to the welcome page same with login page
    User currentUser;
    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    //this POJO object will be used to store data within firebase in a digestable manner
    UserPOJO user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //gets the instance on firebase ie the entire database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //make sure we write our info under UserInfo database
        databaseReference = firebaseDatabase.getReference("UserInfo");
    }


    /**
     * This method registers the user into the database.
     * To register, it is required to fill out a First name, Last name, Email, Password and Role
     * TODO: add address field and make sure type is listened to!
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
        //temporary address var make sure to implement so this is not generic
        String dummyAddress = "2360 ajax" ;
        //make sure type is being listened to from the radio buttons!
        String type = "Client";
        //dummy date to be added to credit card expr
        Date date= new Date(6);
        //dummy var for credit card when you make a client
        CreditCard card = new CreditCard(strFname,strLname,dummyAddress,1234567,123,date);
        //dummy bitmap for the blank cheque pic
        //IMPORTANT NOTE, bitmaps are not storeable in firebase so store bitmap as ID or something else
        Bitmap cheque = null;
        //creates a POJO user with a type, type will be used to specify what object to create
        user = new UserPOJO(strFname, strLname, strEmail, strPassword, dummyAddress,type,card,cheque);
        //we push onto the database under UserInfo all our information
        databaseReference.push().setValue(user);
        //call either the convert to client OR the convert to cook depending on type
        currentUser = user.convertToClient();
        //if there are no problems ie error catch if fields are blank,
        //then you can navigate to welcome screen WITH THE CURRENTUSER DATA IN TOW
    }


}