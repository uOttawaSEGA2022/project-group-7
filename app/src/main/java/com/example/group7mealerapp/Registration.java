package com.example.group7mealerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;

public class Registration extends AppCompatActivity
{

    //Separates each field that the user enters
    final String separator = ";";

    //Separates each user in csv file
    final String lineSeparator = ",";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    //Helper method

    /**
     * Helper Method
     *
     * Writes user registration info to csv
     * @param fileName of the file to append registration info to
     * @param content to be appended about users
     */
    public void writeToFile(String fileName, String content)
    {
        File path = getApplicationContext().getFilesDir();
        try
        {
            FileOutputStream w = new FileOutputStream(new File(path, fileName));
            w.write(content.getBytes());
            w.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

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
        String content = strFname + separator + strLname + separator + strEmail + separator
                + strPassword + separator + strrePassword + lineSeparator;
        writeToFile("reg.txt", content);
//        File f = new File("registration.txt");
//        try
//        {
//            FileWriter w = new FileWriter(f,true);
//            w.write(strFname + separator + strLname + separator + strEmail + separator
//                    + strPassword + separator + strrePassword + lineSeparator);
//            w.close();
//        }
//        catch(IOException e)
//        {
//            System.out.println("Error occurred. Unable to register account");
//        }

//        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//        startActivityForResult(intent,0);
    }


}