package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import UserJavaFiles.Client;
import UserJavaFiles.UserPOJO;
//TODO: MUST REMAIN THIS TO LOGIN NOT MAINACTIVITY MAINACTIVITY IS WELCOMEPAGE
public class MainActivity extends AppCompatActivity {


    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //see comments in registration for explanation
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
    }
    //navigate to registration page
    public void btnRegisterClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Registration.class);
        startActivityForResult(intent,0);

    }
    /**
     * what is implemented now is just the grabbing of the user data and
     * storing it into a user object,
     * TODO: make sure to iterate and find the correct (if exists) email then check if password match
     * TODO: once done store in appropriate object dependent on type and send to next screen
     */
    public void btnLoginClick(View view){
        EditText email = (EditText) findViewById(R.id.loginEmail);
        EditText password = (EditText) findViewById(R.id.loginPassword);
        //listens to the database in real time
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            //method called on start and whenever data is changed
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                //going to iterate through the data and if email matches, login user and save it
                //in userPOJO
                UserPOJO user = new UserPOJO();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){
                    //no logic just stores the value onto user
                    user = child.getValue(UserPOJO.class);

                }
                //call either convert to client or convert to cook here or convert to admin
                Client currentUser = user.convertToClient();
                //checks if it got the right data for debugging
                System.out.println(currentUser.getEmail());
                }

            //no need for this function but must be overridden
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}