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

        //TextField of Client Address
        EditText ClientAddress = (EditText) findViewById(R.id.ClientAddress);
        //TextField of CookDescription
        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);

        //Since Address and Description are dependent on role, launch app with TextFields hidden
        ClientAddress.setVisibility(View.GONE);
        CookDescription.setVisibility(View.GONE);
    }

    /**
     * Opens the field to enter Client Address and Credit Card if the client role is selected in the registration screen
     * @param view
     */
    public void OpenClientAddress(View view)
    {
        //If Client Role is selected, bring up address text field
        EditText ClientAddress = (EditText) findViewById(R.id.ClientAddress);
        ClientAddress.setVisibility(View.VISIBLE);

        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        CookDescription.setVisibility(View.GONE);
    }

    /**
     * Opens the field to enter Cook Description and Address if the Cook role is selected in the registration screen
     */
    public void OpenCookDescription(View view)
    {
        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        CookDescription.setVisibility(View.VISIBLE);

        EditText ClientAddress = (EditText) findViewById(R.id.ClientAddress);
        ClientAddress.setVisibility(View.GONE);
    }

    /**
     * Checks all the possible errors in the Registration process and displays the appropriate errors when they occur
     * @return true if registration fields are acceptable and false otherwise
     */
    public boolean RegistrationErrors()
    {
        boolean f = true;

        EditText Fname = (EditText) findViewById(R.id.FirstNameField);
        //Get First Name of user
        String strFname = Fname.getText().toString();

        EditText Lname = (EditText) findViewById(R.id.LastNameField);
        //Get Last Name of user
        String strLname = Lname.getText().toString();

        EditText Email = (EditText) findViewById(R.id.EmailField);
        //Get email of user
        String strEmail = Email.getText().toString();

        EditText Password = (EditText) findViewById(R.id.PasswordField);
        //Get password of user
        String strPassword = Password.getText().toString();

        EditText rePassword = (EditText) findViewById(R.id.ConfirmPasswordField);
        //Confirm password of user
        String strrePassword = rePassword.getText().toString();

        //TextField of Client Address
        EditText ClientAddress = (EditText) findViewById(R.id.ClientAddress);
        //Get address of client
        String strAddress = ClientAddress.getText().toString();

        //TextField of CookDescription
        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        //Get description of cook
        String strCookDescription = CookDescription.getText().toString();

        //Initialize Radio buttons to allow picking user type:
        //Radio button to store selection of client role
        RadioButton btnClient;
        //Connect variable to Client Radio Button
        btnClient = (RadioButton) findViewById(R.id.ClientButton);
        //Radio button to store selection of cook role
        RadioButton btnCook;
        //Connect variable to Cook Radio Button
        btnCook = (RadioButton) findViewById(R.id.CookButton);

        //Stores user role
        String type = "";

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strFname))
        {
            Fname.setError("This field cannot be empty");
            f = false;
        }
        //Make sure last name field isn't empty

        if(TextUtils.isEmpty(strLname))
        {
            Lname.setError("This field cannot be empty");
            f = false;
        }
        //Make sure email field isn't empty
        if(TextUtils.isEmpty(strEmail))
        {
            Email.setError("This field cannot be empty");
            f = false;
        }
        //Make sure password field isn't empty
        if(TextUtils.isEmpty(strPassword))
        {
            Password.setError("This field cannot be empty");
            f = false;
        }
        //Make sure re-enter password field isn't empty
        if(TextUtils.isEmpty(strrePassword))
        {
            rePassword.setError("This field cannot be empty");
            f = false;
        }

        //Displays error message if Password and ConfirmPassword do no match
        if(!strPassword.equals(strrePassword))
        {
            Password.setError("Passwords must match");
            rePassword.setError("Passwords must match");
            f = false;
        }

        //If no role is selected
        if(!btnClient.isChecked() && !btnCook.isChecked())
        {
            f = false;
        }

        //If client is selected but address is not filed
        if(btnClient.isChecked() && strAddress.isEmpty())
        {
            f = false;
        }

        //If cook is selected but either or address or description is empty
        if(btnCook.isChecked() && (strAddress.isEmpty() || strCookDescription.isEmpty()))
        {
            f = false;
        }

        return f;
    }

    /**
     * This method registers the user into the database.
     * To register, it is required to fill out a First name, Last name, Email, Password and Role
     * Upon selecting a Role, addition Role specific fields are required - Address, Description and Credit Card
     * TODO: properly navigate user out of this page at the end
     */
    public void RegisterButtonClick(View view)
    {
        //Variables
        currentUser = null;
        EditText Fname = (EditText) findViewById(R.id.FirstNameField);
        //Get First Name of user
        String strFname = Fname.getText().toString();

        EditText Lname = (EditText) findViewById(R.id.LastNameField);
        //Get Last Name of user
        String strLname = Lname.getText().toString();

        EditText Email = (EditText) findViewById(R.id.EmailField);
        //Get email of user
        String strEmail = Email.getText().toString();

        EditText Password = (EditText) findViewById(R.id.PasswordField);
        //Get password of user
        String strPassword = Password.getText().toString();

        EditText ClientAddress = (EditText) findViewById(R.id.ClientAddress);
        //Get address of client
        String strAddress = ClientAddress.getText().toString();

        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        //Get description of cook
        String strCookDescription = CookDescription.getText().toString();

        //Initialize Radio buttons to allow picking user type:
        //Radio button to store selection of client role
        RadioButton btnClient;
        //Connect variable to Client Radio Button
        btnClient = (RadioButton) findViewById(R.id.ClientButton);
        //Radio button to store selection of cook role
        RadioButton btnCook;
        //Connect variable to Cook Radio Button
        btnCook = (RadioButton) findViewById(R.id.CookButton);

        //Stores user role
        String type = "";
        //dummy date to be added to credit card expr
        Date date= new Date(6);
        //dummy var for credit card when you make a client
        CreditCard card = new CreditCard(strFname,strLname,strAddress,1234567,123,date);
        //dummy bitmap for the blank cheque pic
        //IMPORTANT NOTE, bitmaps are not storable in firebase so store bitmap as ID or something else
        Bitmap cheque = null;

        /*
         * Set type of user based on which button is pressed
         * Store user's Role from radio buttons as String
         * Pop up a textField to ask Role specific data based on which Radio Button is pressed
         */
        if(btnClient.isChecked())
        {
            //Store user as Client
            type = "Client";
            strCookDescription = "N/A";
        }

        if(btnCook.isChecked())
        {
            //Store user as Cook
            type = "Cook";
        }

        if(RegistrationErrors())
        {
            //creates a POJO user with a type, type will be used to specify what object to create
            user = new UserPOJO(strFname, strLname, strEmail, strPassword, strAddress, type,
                        strCookDescription, card, cheque);
            //we push all our information onto the database under UserInfo
            databaseReference.push().setValue(user);
            //call either the convert to client OR the convert to cook depending on type
            if(type.equals("Client"))
            {
                currentUser = user.convertToClient();
            }
            if(type.equals("Cook"))
            {
                currentUser = user.convertToCook();
            }
            //if there are no problems ie error catch if fields are blank,
            //then you can navigate to welcome screen WITH THE CURRENTUSER DATA IN TOW
        }
    }
}