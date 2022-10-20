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
     * Helper Method
     *
     * Checks to see if any text field is empty and if it is, User will not be registered
     * @param strFname
     * @param strLname
     * @param strEmail
     * @param strPassword
     * @param dummyAddress
     * @param type
     * @param dummyDescription
     * @param card
     * @return
     */
    public boolean userValidity(String strFname, String strLname, String strEmail,
                                String strPassword, String dummyAddress, String type,
                                String dummyDescription, CreditCard card)
    {
        return strFname.isEmpty() || strLname.isEmpty() || strEmail.isEmpty() || strPassword.isEmpty() ||
                dummyAddress.isEmpty() || type.isEmpty() || dummyDescription.isEmpty() || card == null;
    }

    /**
     * This method registers the user into the database.
     * To register, it is required to fill out a First name, Last name, Email, Password and Role
     * TODO: add address field and make sure type is listened to also hook up the radio buttons
     * TODO: properly navigate user out of this page at the end
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

        //Make sure First Name field isn't empty
//        if(TextUtils.isEmpty(strFname) || TextUtils.isEmpty(strLname) || TextUtils.isEmpty(strEmail)
//            || TextUtils.isEmpty(strPassword) || TextUtils.isEmpty(strrePassword))
//        {
//            Fname.setError("This field cannot be empty");
//            Lname.setError("This field cannot be empty");
//            Email.setError("This field cannot be empty");
//            Password.setError("This field cannot be empty");
//            rePassword.setError("This field cannot be empty");
//            return;
//        }

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strFname))
        {
            Fname.setError("This field cannot be empty");
        }
        //Make sure last name field isn't empty

        if(TextUtils.isEmpty(strLname))
        {
            Lname.setError("This field cannot be empty");
        }
        //Make sure email field isn't empty
        if(TextUtils.isEmpty(strEmail))
        {
            Email.setError("This field cannot be empty");
        }
        //Make sure password field isn't empty
        if(TextUtils.isEmpty(strPassword))
        {
            Password.setError("This field cannot be empty");
        }
        //Make sure re-enter password field isn't empty
        if(TextUtils.isEmpty(strrePassword))
        {
            rePassword.setError("This field cannot be empty");
            return;
        }

        //Displays error message if Password and ConfirmPassword do no match
        if(!strPassword.equals(strrePassword))
        {
            Password.setError("Passwords must match");
            rePassword.setError("Passwords must match");
            return;
        }

        //Initialize
        RadioButton btnClient;
        btnClient = (RadioButton) findViewById(R.id.ClientButton);

        RadioButton btnCook;
        btnCook = (RadioButton) findViewById(R.id.CookButton);

        //make sure type is being listened to from the radio buttons!
        String type = "test";
        if(btnClient.isChecked())
        {
            type = "Client";
        }

        if(btnCook.isChecked())
        {
            type = "Cook";
        }
        //Store user's Role from radio buttons as String

        /*
         * TODO: Implement Address, Description and Type
         */
        String dummyDescription = "test";
        //temporary address var make sure to implement so this is not generic
        String dummyAddress = "test";

        //dummy date to be added to credit card expr
        Date date= new Date(6);
        //dummy var for credit card when you make a client
        CreditCard card = new CreditCard(strFname,strLname,dummyAddress,1234567,123,date);
//        card = null;
        //dummy bitmap for the blank cheque pic
        //IMPORTANT NOTE, bitmaps are not storable in firebase so store bitmap as ID or something else
        Bitmap cheque = null;

        //Uses helper method to determine if any fields have been left blank. If so, user will not be added to database
//        if(!userValidity(strFname, strLname, strEmail, strPassword, dummyAddress, type,
//                dummyDescription, card))
//        {
//            //creates a POJO user with a type, type will be used to specify what object to create
//            user = new UserPOJO(strFname, strLname, strEmail, strPassword, dummyAddress, type,
//                    dummyDescription, card, cheque);
//
//        }

        user = new UserPOJO(strFname, strLname, strEmail, strPassword, dummyAddress, type,
                dummyDescription, card, cheque);
        //we push onto the database under UserInfo all our information
        databaseReference.push().setValue(user);
        //call either the convert to client OR the convert to cook depending on type
        currentUser = user.convertToClient();
        System.out.println(currentUser.getAddress());
        //if there are no problems ie error catch if fields are blank,
        //then you can navigate to welcome screen WITH THE CURRENTUSER DATA IN TOW
    }
}