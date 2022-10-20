package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;


import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Date;


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
    //this POJO object will be used to store data within firebase in a digestible manner
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

    //Helper Methods

    /**
     * Method to check if String contains only letters
     * This method will be used to ensure First and Last name are valid name entries
     * @param name represents String checked
     * @return true or false
     */
    public boolean isAlpha(String name)

    {
        return name.matches("[a-zA-Z]+");
    }


    /**
     * This method registers the user into the database.
     * To register, it is required to fill out a First name, Last name, Email, Password and Role
     * Additional fields will be required once Role is selected (Description, Address, Credit Card, Void Cheque)
     * TODO: add address field and make sure type is listened to also hook up the radio buttons
     * TODO: properly navigate user out of this page at the end
     * TODO: implement isAlpha helper method
     * TODO: Make sure email format is checked
     * Validity of the field entries are also checked in this method
     */
    public void RegisterButtonClick(View view)
    {
        EditText Fname = (EditText) findViewById(R.id.FirstNameField);
        //Get user entered String in FirstName text field
        String strFname = Fname.getText().toString();

        EditText Lname = (EditText) findViewById(R.id.LastNameField);
        //Get user entered String in LastName text field
        String strLname = Lname.getText().toString();

        EditText Email = (EditText) findViewById(R.id.EmailField);
        //Get user entered String in Email text field
        String strEmail = Email.getText().toString();

        EditText Password = (EditText) findViewById(R.id.PasswordField);
        //Get user entered String in Password text field
        String strPassword = Password.getText().toString();

        EditText rePassword = (EditText) findViewById(R.id.ConfirmPasswordField);
        //Get user entered String in Confirm Password text field
        String strrePassword = rePassword.getText().toString();

//        //create an instance of RadioGroup
//        RadioGroup radioGroup;
//        //create an instance of RadioButton
//        RadioButton radioButton;
//
//
//        radioGroup = findViewById(R.id.RoleButtons);
//        int radioID = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioID);
//        //Store user's Role from radio buttons as String
//        String type = (String) radioButton.getText();

        String type = "Client";

        //Store user entered String for Chefs Description
        String Description = "Krusty Krab employee since 1998";

        //Store user entered String for Client Address
        String Address = "2360 ajax" ;

        //dummy date to be added to credit card expr
        Date date= new Date(6);

        //dummy var for credit card when you make a client
        CreditCard card = new CreditCard(strFname,strLname,Address,1234567,123,date);

        //dummy bitmap for the blank cheque pic
        //IMPORTANT NOTE, bitmaps are not storeable in firebase so store bitmap as ID or something else
        Bitmap cheque = null;


        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strFname))
        {
            //Display error message
            Fname.setError("This field cannot be empty");
        }

        //Make sure last name field isn't empty
        if(TextUtils.isEmpty(strLname))
        {
            //Display error message
            Lname.setError("This field cannot be empty");
        }

        //Make sure email field isn't empty
        if(TextUtils.isEmpty(strEmail))
        {
            //Display error message
            Email.setError("This field cannot be empty");
        }

        //Make sure password field isn't empty
        if(TextUtils.isEmpty(strPassword))
        {
            //Display error message
            Password.setError("This field cannot be empty");
        }

        //Make sure re-enter password field isn't empty
        if(TextUtils.isEmpty(strrePassword))
        {
            //Display error message
            rePassword.setError("This field cannot be empty");
        }

        //Make sure password and re-enter password match
        if(!strPassword.equals(strrePassword))
        {
            //Display error messages
            Password.setError("Passwords must match");
            rePassword.setError("Passwords must match");
            //Will not register if any field is invalid
            return;
        }

        //creates a POJO user with a type, type will be used to specify Address, type, Description, card, cheque);
        //we push onto the database under UserInfo all our information
        databaseReference.push().setValue(user);

//        //call either the convert to client OR the convert to cook depending on type
//        if(type.equals("Client"))
//        {
//            currentUser = user.convertToClient();
//        }
//        if(type.equals("Cook"))
//        {
//            currentUser = user.convertToCook();
//        }
        currentUser = user.convertToClient();
        System.out.println(currentUser.getAddress());
        //if there are no problems ie error catch if fields are blank,
        //then you can navigate to welcome screen WITH THE CURRENTUSER DATA IN TOW
    }


}