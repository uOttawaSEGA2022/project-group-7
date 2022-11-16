package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import UserJavaFiles.User;
import UserJavaFiles.Cook;
import UserJavaFiles.Client;
import UserJavaFiles.Administrator;
import UserJavaFiles.Meal;
import UserJavaFiles.UserPOJO;
import codeModules.Modules;

public class AddMeal extends AppCompatActivity {
    //create an instance for firebase
    FirebaseDatabase firebaseDatabase;
    //reference to the actual database in firebase
    DatabaseReference cookDatabaseReference;
    //Meal will be stored in the database
    Meal meal;
    //Get the cook object passed from previous activity
    Modules module = new Modules();
    Cook cook = (Cook)module.catchUser(getIntent());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //gets the instance on firebase ie the entire database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //make sure we write our info under UserInfo database
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");

        //EditText for the name
        EditText name = findViewById(R.id.editTextName);
        //Get name from EditText
        String strName = name.getText().toString();
        //EditText for the price
        EditText price = findViewById(R.id.editTextPrice);
        //Get name from EditText
        String strPrice = price.getText().toString();
        //EditText for the cuisine type
        EditText cuisineType =  findViewById(R.id.editTextCuisineType);
        //Get name from EditText
        String strCuisineType = cuisineType.getText().toString();
        //EditText for the description
        EditText description = findViewById(R.id.editTextDescription);
        //Get name from EditText
        String strDescription = name.getText().toString();
        //Radio button for breakfast
        RadioButton btnBreakfast = findViewById(R.id.BreakfastButton);
        //Radio button for lunch
        RadioButton btnLunch = findViewById(R.id.LunchButton);
        //Radio button for dinner
        RadioButton btnDinner = findViewById(R.id.DinnerButton);
        //Radio button for choosing yes on offer option
        RadioButton btnYes = findViewById(R.id.OfferYesButton);
        //Radio button for choosing no on offer option
        RadioButton btnNo = findViewById(R.id.OfferNoButton);
        //Button for adding meal to database
        Button addMealBtn = (Button)findViewById(R.id.AddMealButton);

        //This onClickListener is incomplete, must be finished when a way is found to add or delete meals from Firebase
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String for meal type
                String mealType;
                //double for storing price
                double dblPrice;
                if (registrationErrors(strName, strPrice, strCuisineType, strDescription)) {
                    if (btnBreakfast.isChecked()) {
                        mealType = "Breakfast";
                    } else if (btnLunch.isChecked()) {
                        mealType = "Lunch";
                    } else {
                        mealType = "Dinner";
                    }
                    try {
                        dblPrice = Double.valueOf(strPrice);
                        try {
                            meal = new Meal(strName, mealType, strCuisineType, strDescription, cook.getEmail(), dblPrice);
                            cook.addMeal(meal);
                            //listens to the database in real time
                            cookDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                //method called on start and whenever data is changed
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //Variables for toast display
                                    boolean successful = false;
                                    Context context = getApplicationContext();
                                    String error = "Error when adding meal to database";
                                    int duration = Toast.LENGTH_LONG;
                                    //calls an iterator on the children in the database IE all users stored
                                    Iterable<DataSnapshot> children = snapshot.getChildren();
                                    //going to iterate through the data and if email matches, login user and save it
                                    //in userPOJO
                                    UserPOJO temp;
                                    //this loop iterates through the DB under the userInfo block
                                    for (DataSnapshot child : children) {
                                        //stores the value onto user
                                        temp = child.getValue(UserPOJO.class);
                                        //comparing the email from the database with the email of cook
                                        if (temp.getEmail().equals(cook.getEmail())){
                                            cookDatabaseReference.child(child.getKey()).child("menu").push(meal);
                                            successful = true;
                                        }
                                    }
                                }
                                //no need for this function but must be overridden
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (btnYes.isChecked()) {
                        meal.setOffered(true, meal);
                    }
                }
            }
        });
    }

    /* Checks if all the inputs are valid, returns true if they are and false if otherwise.
    * @param strName    The input of name converted to String
    * @param strPrice    The input of price converted to String
    * @param strCuisineType    The input of cuisineType converted to String
    * @param strDescription    The input of name description to String
     */
    private boolean registrationErrors(String strName, String strPrice, String strCuisineType,String strDescription){
        boolean noErrors = true;

        //EditText for the name
        EditText name = findViewById(R.id.editTextName);
        //EditText for the price
        EditText price = findViewById(R.id.editTextPrice);
        //EditText for the cuisine type
        EditText cuisineType =  findViewById(R.id.editTextCuisineType);
        //EditText for the description
        EditText description = findViewById(R.id.editTextDescription);
        //Radio button for breakfast
        RadioButton btnBreakfast = findViewById(R.id.BreakfastButton);
        //Radio button for lunch
        RadioButton btnLunch = findViewById(R.id.LunchButton);
        //Radio button for dinner
        RadioButton btnDinner = findViewById(R.id.DinnerButton);
        //Radio button for choosing yes on offer option
        RadioButton btnYes = findViewById(R.id.OfferYesButton);
        //Radio button for choosing no on offer option
        RadioButton btnNo = findViewById(R.id.OfferNoButton);

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strName))
        {
            name.setError("This field cannot be empty");
            noErrors = false;
        }
        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strPrice))
        {
            price.setError("This field cannot be empty");
            noErrors = false;
        }

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strCuisineType))
        {
            cuisineType.setError("This field cannot be empty");
            noErrors = false;
        }

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strDescription))
        {
            description .setError("This field cannot be empty");
            noErrors = false;
        }

        if(!btnBreakfast.isChecked() && !btnLunch.isChecked() && !btnDinner.isChecked())
        {
            Toast.makeText(getApplicationContext(), "A meal type must be selected", Toast.LENGTH_LONG).show();
            noErrors = false;
        }

        if(!btnYes.isChecked() && !btnNo.isChecked())
        {
            Toast.makeText(getApplicationContext(), "You must choose whether to make view visible to the public or not", Toast.LENGTH_LONG).show();
            noErrors = false;
        }

        return noErrors;
    }
}