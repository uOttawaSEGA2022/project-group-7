package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.UserPOJO;

public class Login extends AppCompatActivity {


    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    EditText email,password = null;

    // Variable for sending user
    int type = 0;
    Button btnLoginCLick;
    //User variable to send
    UserPOJO user = new UserPOJO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //see comments in registration for explanation

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        email =  findViewById(R.id.loginEmail);
        password =  findViewById(R.id.loginPassword);



        btnLoginCLick = (Button)findViewById(R.id.btnLogin);
        btnLoginCLick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLoginClick(view);
            }
        });
    }
    //navigate to registration page
    public void btnRegisterClick(View view)
    {
        Intent intent = new Intent(this,Registration.class);
        startActivity(intent);

    }

    private void btnLoginClick(View view){

        //text field variables


        //listens to the database in real time
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            //method called on start and whenever data is changed
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("ENTERED BTN LOGIN CLICKED");
                //Variables for toast display
                boolean flag = false;
                Context context = getApplicationContext();
                String error = "Incorrect email or password";
                int duration = Toast.LENGTH_LONG;
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                //going to iterate through the data and if email matches, login user and save it
                //in userPOJO
//                UserPOJO user = new UserPOJO();
                UserPOJO temp = new UserPOJO();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){
                    //no logic just stores the value onto user
                    temp = child.getValue(UserPOJO.class);
                    //comparing the email and password from the database with the inputted text fields
                    if (temp.getEmail().equals(email.getText().toString())
                            && temp.getPassword().equals(password.getText().toString()))
                    {
                        //since user and pass match the user logging in is this child from the database

                        user = temp;

                        //setting flag to true meaning the account matches the input
                        flag = true;
                        //Temporary code for debugging


                        //Setting type to whichever type is correct
                        if(user.getType().equals("Client"))
                        {
                            type = 1;
                            sendUser(type);

                        }
                        else if(user.getType().equals("Cook"))
                        {
                            type = 2;
                            sendUser(type);
                        }

                        else if(user.getType().equals("Admin"))
                        {

                            type = 3;
                            sendUser(type);
                        }
                        /*
                        this simple line of code removes the bug of auto logging in
                        when the user logins then goes back to register, cause the event
                        listener is still going it listens to change and auto logs on
                        make VERY sure every time ur done listening to event
                        that the event listener is remove from firebase!!!!
                         */
                        databaseReference.removeEventListener(this);
                        break;
                    }


                }

                //Ensuring both text fields are not blank
                if(email.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                {
                    email.setError("This field cannot be empty");
                    password.setError("This field cannot be empty");
                }

                // Ensuring just password is not blank
                else if(password.getText().toString().isEmpty())
                {
                    password.setError("This field cannot be empty");
                }
                // Ensuring just email is not blank
                else if(email.getText().toString().isEmpty())
                {
                    email.setError("This field cannot be empty");
                }

                // All these conditions are to check if email and password are valid
                else if(!flag && email.getText().toString().isEmpty() != true)
                {
                    email.setError("Incorrect email or password");
                    password.setError("Incorrect email or password");
                    Toast.makeText(context, error, duration).show();

                }
                else if(!flag && password.getText().toString().isEmpty() != true)
                {
                    email.setError("Incorrect email or password");
                    password.setError("Incorrect email or password");
                    Toast.makeText(context, error, duration).show();
                }




                }

            //no need for this function but must be overridden
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void sendUser(int type)
    {
       Intent switchPage = new Intent(Login.this, WelcomePage.class);
        //call either convert to client or convert to cook here or convert to admin

         //type 1 is for client so convert user into client
        if(type == 1)
        {
            Client currentUser = user.convertToClient();//surround in try and catch block
           switchPage.putExtra("Client",currentUser);
        }

        //Type 2 is cook so convert user into cook
        else if(type == 2)
        {
            Cook currentUser = user.convertToCook();//surround in try and catch block
            switchPage.putExtra("Cook",currentUser);

        }


       //type 3 is admin
        else if (type == 3)
       {

           Administrator currentUser = user.convertToAdministrator();

           switchPage.putExtra("Admin",currentUser);
       }
        System.out.println("SEND USER ENTERED");

        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);

    }

}