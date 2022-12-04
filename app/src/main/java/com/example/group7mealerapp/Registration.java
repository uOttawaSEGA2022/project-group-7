package com.example.group7mealerapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DatabaseReference;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

import UserJavaFiles.CreditCard;
import UserJavaFiles.Suspension;
import UserJavaFiles.UserPOJO;


import android.os.Bundle;


/**
 * The registration page initially takes the basic info of a User (First name, Last name, Email,
 * Password, Address and Role.
 * Upon selecting a role, role specific fields are shown and required as well (Client - Credit Card
 * information / Cook - Description)
 */
public class Registration extends Activity
{
    //Global variables

    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    //this POJO object will be used to store data within firebase in a digestible manner
    UserPOJO user;
    private static final int CAMERA_REQUEST = 1888;
    private static final int CAMERA_PERMISSION_CODE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //gets the instance on firebase ie the entire database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //make sure we write our info under UserInfo database
        databaseReference = firebaseDatabase.getReference("UserInfo");

        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        //TextField of credit card first name
        EditText CCfirstname = (EditText) findViewById(R.id.CCfname);
        //TextField of credit card last name
        EditText CClastname = (EditText) findViewById(R.id.CClname);
        //TextField of Client Credit Card Number
        EditText CCnumber = (EditText) findViewById(R.id.ClientCC);
        //TextField of Client Credit Card Expiry Date
        EditText expDate = (EditText) findViewById(R.id.expDate);
        //TextField of Client Credit Card CVV
        EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);

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
        //Text Field for first name

        //TextField of CookDescription
        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        //TextField of credit card first name
        EditText CCfirstname = (EditText) findViewById(R.id.CCfname);
        //TextField of credit card last name
        EditText CClastname = (EditText) findViewById(R.id.CClname);
        //TextField of Client Credit Card Number
        EditText CCnumber = (EditText) findViewById(R.id.ClientCC);
        //TextField of Client Credit Card Expiry Date
        EditText expDate = (EditText) findViewById(R.id.expDate);
        //TextField of Client Credit Card CVV
        EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);

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

        //TextField of credit card first name
        EditText CCfirstname = (EditText) findViewById(R.id.CCfname);
        //TextField of credit card last name
        EditText CClastname = (EditText) findViewById(R.id.CClname);
        //TextField of CookDescription
        EditText CookDescription = (EditText) findViewById(R.id.CookDescription);
        //TextField of Client Credit Card Number
        EditText CCnumber = (EditText) findViewById(R.id.ClientCC);
        //TextField of Client Credit Card Expiry Date
        EditText expDate = (EditText) findViewById(R.id.expDate);
        //TextField of Client Credit Card CVV
        EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);
        //Since Address and Description are dependent on role, launch app with TextFields hidden

        CCfirstname.setVisibility(View.GONE);
        CClastname.setVisibility(View.GONE);
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
        //TextField of Client Credit Card Expiry Date
        EditText expDate = (EditText) findViewById(R.id.expDate);
        //Get client credit card expiry date as string for now
        String strexpDate = expDate.getText().toString();
        //TextField of Client Credit Card CVV
        EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);
        //Get client credit card's cvv
        String strCCcvv = CCcvv.getText().toString();

        //If f remains true, all registration fields have been acceptably filled.
        //Otherwise display error messages until fixed, and do not append to Database
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
            btnClient.setError("All fields must be filled");
            f = false;
        }
        if(btnClient.isChecked())
        {
            CreditCard creditCard = null;
            try{
                creditCard = new CreditCard(strCCfirstname,strCClastname,strAddress,Integer.parseInt(strCCnumber),Integer.parseInt(strCCcvv),strexpDate);
            }catch(ParseException e){
                f = false;
                btnClient.setError("incorrect date format");
                Toast.makeText(getApplicationContext(), "exp date format wrong", Toast.LENGTH_LONG).show();
            }catch(IllegalArgumentException e) {
                f = false;
                if(e.getMessage().equals("Credit card number is either too long or too short"))
                    Toast.makeText(getApplicationContext(), "credit card number is invalid", Toast.LENGTH_LONG).show();
                else if(e.getMessage().equals("credit card pin is invalid"))
                    Toast.makeText(getApplicationContext(), "credit card pin is invalid", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                f = false;
                btnClient.setError("credit card is expired!");
                Toast.makeText(getApplicationContext(), "credit card expired " + e, Toast.LENGTH_LONG).show();
            }
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
        //TextField of Client Credit Card Expiry Date
        EditText expDate = (EditText) findViewById(R.id.expDate);
        //Get client credit card expiry date as string for now
        String strexpDate = expDate.getText().toString();
        //TextField of Client Credit Card CVV
        EditText CCcvv = (EditText) findViewById(R.id.ClientCVV);
        //Get client credit card's cvv
        String strCCcvv = CCcvv.getText().toString();
        //Store Credit card number as type Long
        long CreditCardnumber = 0;
        //Store Credit card cvv as type int
        int CVV = 0;

        //Error handling
        try
        {
            //Convert credit card number to type long
            CreditCardnumber = Long.parseLong(strCCnumber);
        }
        catch(Exception e)
        {
            System.out.println(e + "e");
        }
        //Error handling
        try {
            //Convert cvv number to type int
            CVV = Integer.parseInt(strCCcvv);
        }
        catch(Exception e)
        {
            System.out.println(e + "e");
        }
        CreditCard card = null;
        try{
            card = new CreditCard(strCCfirstname, strCClastname, strAddress, CreditCardnumber, CVV, strexpDate);
        }catch(Exception e){}



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
            Intent switchPage = new Intent(this, Login.class);

            if(type.equals("Client"))
            {
                //creates a POJO user with a type, type will be used to specify what object to create
                user = new UserPOJO(strFname, strLname, strEmail, strPassword, strAddress, type,
                        strCookDescription, card, null,null);

            }
            if(type.equals("Cook"))
            {
                //creates a POJO user with a type, type will be used to specify what object to create
                user = new UserPOJO(strFname, strLname, strEmail, strPassword, strAddress, type,
                        strCookDescription, null, null,new Suspension(false, (Date) null));
                //ask for permission to use camera first this is done automatically through android
                //where it asks the user for perms
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                }
                else
                {

                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);

                }
                return;

            }
            //we push all our information onto the database under UserInfo
            databaseReference.push().setValue(user);

            //Clear text fields and let user know registration was complete
            Toast.makeText(getApplicationContext(), "Registration Successful. Log in now", Toast.LENGTH_LONG).show();
            Fname.setText(null);
            Lname.setText(null);
            Address.setText(null);
            Email.setText(null);
            Password.setText(null);
            //Identify Pasword Confirmation textbox and clear it
            CCfirstname.setText(null);
            CClastname.setText(null);
            CCnumber.setText(null);
            CCcvv.setText(null);
            expDate.setText(null);
            //go back to login
            startActivity(switchPage);


        }
    }

    /**
     * method that verifies the correct permissions and handles the cases
     * @param requestCode if request code is 100 (camera permission code) allows access else denies
     * @param permissions
     * @param grantResults when results are permission granted then uses camera
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("starting camera" + requestCode + " " + CAMERA_PERMISSION_CODE);
        if (requestCode == CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * gets the results of the camera picture being taken, saves it into a bitmap that is then
     * encoded in base64 and saved in the database
     * @param requestCode
     * @param resultCode
     * @param data bitmap that is encoded
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = databaseReference = firebaseDatabase.getReference("UserInfo");
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            //convert bitmap saved into a base 64 to be stored in Firebase
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            String encodedimg = Base64.getEncoder().encodeToString(byteArray);

            user.setBlankCheque(encodedimg);
            System.out.println("this is the blank cheque" + user.getBlankCheque());
            databaseReference.push().setValue(user);
            Intent switchPage = new Intent(this, Login.class);
            Toast.makeText(getApplicationContext(), "Registration Successful. Log in now", Toast.LENGTH_LONG).show();
            startActivity(switchPage);
        }
    }

}