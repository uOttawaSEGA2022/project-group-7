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

public class MainActivity extends AppCompatActivity {


    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("UserInfo");
        databaseReference.child(("UserInfo")).addValueEventListener(new ValueEventListener() {

            //hover cntrl alt v and it will give proper object
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get all children at this level
                Iterable<DataSnapshot> children = snapshot.getChildren();

                for (DataSnapshot child:children) {



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void btnRegisterClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(),Registration.class);
        startActivityForResult(intent,0);

    }
    public void btnLoginClick(View view){
        EditText email = (EditText) findViewById(R.id.loginEmail);
        //replace the . as the database does not save the .
        String emailRemoved = email.toString().replace('.', ' ');
        emailRemoved = "ggf@gmail com";
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                UserPOJO user = new UserPOJO();
                for (DataSnapshot child: children){
                    user = child.getValue(UserPOJO.class);

                }
                Client currentUser = new Client(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getAddress(),null);
                System.out.println(currentUser.getEmail());
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}