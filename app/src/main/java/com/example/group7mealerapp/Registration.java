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
import android.widget.Toast;

import java.util.Date;

import UserJavaFiles.CreditCard;
import UserJavaFiles.User;
import UserJavaFiles.UserPOJO;
/**
 * The registration page initially takes the basic info of a User (First name, Last name, Email,
 * Password, Address and Role.
 * Upon selecting a role, role specific fields are shown and required as well (Client - Credit Card
 * information / Cook - Description)
 */
public class Registration extends AppCompatActivity
{
    //Global variables

    //current user is going to be passed to the welcome page same with login page
    User currentUser;
    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    //this POJO object will be used to store data within firebase in a digestible manner
    UserPOJO user;
    //Text Field for first name
    EditText Fname = (EditText) findViewById(R.id.FirstNameField);
    //Get First Name of user
    String strFname = Fname.getText().toString();
    //Text field for last name
    EditText Lname = (EditText) findViewById(R.id.LastNameField);
    //Get Last Name of user
    String strLname = Lname.getText().toString();
    //Text field for email
    EditText Email = (EditText) findViewById(R.id.EmailField);
    //Get email of user
    String strEmail = Email.getText().toString();
    //Text field for password
    EditText Password = (EditText) findViewById(R.id.PasswordField);
    //Get password of user
    String strPassword = Password.getText().toString();
    //Text field for confirm password
    EditText rePassword = (EditText) findViewById(R.id.ConfirmPasswordField);
    //Get confirm password of user
    String strrePassword = rePassword.getText().toString();
    //TextField of Client and Cook Address
    EditText Address = (EditText) findViewById(R.id.Address);
    //Get address of client
    String strAddress = Address.getText().toString();
    //TextField of CookDescription
    EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
    //Get description of cook
    String strCookDescription = CookDescription.getText().toString();
    //Radio button to store selection of client role
    RadioButton btnClient = (RadioButton) findViewById(R.id.ClientButton);
    //Radio button to store selection of cook role
    RadioButton btnCook = (RadioButton) findViewById(R.id.CookButton);
    //Stores user role
    String type = "";
    //TextField of credit card first name
    EditText CCfirstname = (EditText) findViewById(R.id.CCfname);
    //Get credit card first name
    String strCCfirstname = CCfirstname.getText().toString();
    //TextField of credit card last name
    EditText CClastname = (EditText) findViewById(R.id.CClname);
    //Get credit card last name
    String strCClastname = CClastname.getText().toString();
    //TextField of Client Credit Card Number
    EditText CCnumber = (EditText) findViewById(R.id.ClientCC);
    //Get Client Credit Card number
    String strCCnumber = CCnumber.getText().toString();
    //Store Credit card number as type Long
    Long CreditCardnumber = Long.parseLong(strCCnumber);
    //TextField of Client Credit Card Expiry Date
    EditText expDate = (EditText) findViewById(R.id.expDate);
    //Get client credit card expiry date as string for now
    String strexpDate = expDate.getText().toString();
    //TextField of Client Credit Card CVV
    EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);
    //Get client credit card's cvv
    String strCCcvv = CCcvv.getText().toString();
    //Store Credit card cvv as type int
    int CVV = Integer.parseInt(strCCcvv);
    //TextField of expiration date
    EditText ccdate = (EditText) findViewById(R.id.expDate);
    //Store date as String
    String strccdate = ccdate.getText().toString();
    //Split date String by delimiter "/". Date String is now in a String array [month,year]
    String[] datearr = strccdate.split("/");
    //Store date as type date
    int month = Integer.parseInt(datearr[0]);
    int year = Integer.parseInt(datearr[1]);
    //Store date
    Date date = new Date(year,month,0);
    //Store Credit Card information as instance of CreditCard class
    CreditCard card = new CreditCard(strFname, strLname, strAddress,CreditCardnumber,CVV,date);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //gets the instance on firebase ie the entire database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //make sure we write our info under UserInfo database
        databaseReference = firebaseDatabase.getReference("UserInfo");

        //Credit card info is only required for a client so these fields launch hidden
        CCfirstname.setVisibility(View.GONE);
        CClastname.setVisibility(View.GONE);
        CCnumber.setVisibility(View.GONE);
        expDate.setVisibility(View.GONE);
        CCcvv.setVisibility(View.GONE);
        //Description info is only required for a client so the field launches hidden
        CookDescription.setVisibility(View.GONE);
    }

    /**
     * Opens the fields to enter Client's Credit Card if the Client role is selected in the registration screen
     */
    public void OpenClientFields(View view)
    {
        CCfirstname.setVisibility(View.VISIBLE);
        CClastname.setVisibility(View.VISIBLE);
        CCnumber.setVisibility(View.VISIBLE);
        expDate.setVisibility(View.VISIBLE);
        CCcvv.setVisibility(View.VISIBLE);

        CookDescription.setVisibility(View.GONE);
    }

    /**
     * Opens the field to enter Cook Description if the Cook role is selected in the registration screen
     */
    public void OpenCookFields(View view)
    {
        //Since Address and Description are dependent on role, launch app with TextFields hidden

        CCnumber.setVisibility(View.GONE);
        expDate.setVisibility(View.GONE);
        CCcvv.setVisibility(View.GONE);

        CookDescription.setVisibility(View.VISIBLE);
    }

    /**
     * Checks all the possible errors in the Registration process and displays the appropriate error
     * messages when they occur
     * @return true if registration fields are acceptable and false otherwise
     */
    public boolean RegistrationErrors()
    {
        boolean f = true;

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
        //Make sure Address field isn't empty
        if(TextUtils.isEmpty(strAddress))
        {
            Address.setError("This field cannot be empty");
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
            Toast.makeText(getApplicationContext(), "A Role must be selected", Toast.LENGTH_LONG).show();
            f = false;
        }

        //If client is selected but credit card info is not filled
        if(btnClient.isChecked() && (strCCcvv.isEmpty() || strCCnumber.isEmpty() || strexpDate.isEmpty()
        || strCCfirstname.isEmpty() || strCClastname.isEmpty()))
        {
            Toast.makeText(getApplicationContext(), "Credit Card Info must be filled", Toast.LENGTH_LONG).show();
            f = false;
        }

        //If cook is selected but description is empty
        if(btnCook.isChecked() && strCookDescription.isEmpty())
        {
            CookDescription.setError("Field cannot be empty");
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