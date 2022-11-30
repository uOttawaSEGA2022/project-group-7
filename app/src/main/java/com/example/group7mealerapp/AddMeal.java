package com.example.group7mealerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    User user;
    Cook cook;
    RadioButton btnYes, btnNo, btnBreakfast, btnLunch, btnDinner;
    //Radio button for choosing no on offer option


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        //gets the instance on firebase ie the entire database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //make sure we write our info under UserInfo database
        cookDatabaseReference = firebaseDatabase.getReference("UserInfo");

        Modules modules = new Modules();
        user = modules.catchUser(getIntent());
        cook = (Cook)user;

        RadioButton btnYes = findViewById(R.id.OfferYesButton);
        RadioButton btnNo = findViewById(R.id.OfferNoButton);
        RadioButton btnBreakfast = findViewById(R.id.BreakfastButton);
        RadioButton btnLunch = findViewById(R.id.LunchButton);
        RadioButton btnDinner = findViewById(R.id.DinnerButton);
        RadioButton btnClear = findViewById(R.id.ClearButton);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNo.setChecked(false);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnYes.setChecked(false);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDinner.setChecked(false);
                btnBreakfast.setChecked(false);
                btnLunch.setChecked(false);
            }
        });
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {btnClear.setChecked(false);}
        });
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {btnClear.setChecked(false);}
        });
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {btnClear.setChecked(false);}
        });
        //Button for adding meal to database
        Button addMealBtn = (Button)findViewById(R.id.AddMealButton);

        //This onClickListener is incomplete, must be finished when a way is found to add or delete meals from Firebase
        addMealBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addMeal();
            }
        });
    }
    /* Checks if all the inputs are valid, returns true if they are and false if otherwise.
    * @param strName    The input of name converted to String
    * @param strPrice    The input of price converted to String
    * @param strCuisineType    The input of cuisineType converted to String
    * @param strDescription    The input of name description to String
     */
    private boolean registrationErrors(String strName, String strPrice, String strCuisineType,String strDescription, String strIngredients, String strAllergens){
        boolean noErrors = true;
        //EditText for the Allergens
        EditText allergens = findViewById(R.id.editTextAllergens);
        //EditText for the ingredients
        EditText ingredients = findViewById(R.id.editTextIngredients);
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
        RadioButton btnClear = findViewById(R.id.ClearButton);

        //Make sure first name field isn't empty
        if(TextUtils.isEmpty(strName))
        {
            name.setError("This field cannot be empty");
            noErrors = false;
        }

        try {
            Double.parseDouble(strPrice);
        }catch (Exception e){
            price.setError("invalid price input");
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

        if(TextUtils.isEmpty(strIngredients))
        {
            ingredients .setError("This field cannot be empty");
            noErrors = false;
        }

        if(TextUtils.isEmpty(strAllergens))
        {
            allergens .setError("This field cannot be empty");
            noErrors = false;
        }

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
        if(btnClear.isChecked())
        {
            Toast.makeText(getApplicationContext(), "Meal type cannot be N/A", Toast.LENGTH_LONG).show();
            noErrors = false;
        }


        return noErrors;
    }
    private void addMeal(){
        //EditText for the Allergens
        EditText allergens = findViewById(R.id.editTextAllergens);
        //get the editText string
        String strAllergens = allergens.getText().toString();
        //EditText for the ingredients
        EditText ingredients = findViewById(R.id.editTextIngredients);
        //get the ingredients string
        String strIngredients = ingredients.getText().toString();
        //EditText for the name
        EditText name = findViewById(R.id.editTextName);
        //Get name from EditText
        String strName = name.getText().toString();
        //EditText for the price
        EditText price = findViewById(R.id.editTextPrice);
        //Get name from EditText
        String strPrice = price.getText().toString();
        EditText cuisineType =  findViewById(R.id.editTextCuisineType);
        //EditText for the cuisine type
        //Get name from EditText
        String strCuisineType = cuisineType.getText().toString();
        //EditText for the description
        EditText description = findViewById(R.id.editTextDescription);
        //Get name from EditText
        String strDescription = description.getText().toString();
        //Radio button for breakfast
        RadioButton btnBreakfast = findViewById(R.id.BreakfastButton);
        //Radio button for lunch
        RadioButton btnLunch = findViewById(R.id.LunchButton);
        //Radio button for dinner
        RadioButton btnDinner = findViewById(R.id.DinnerButton);
        //radio button for no choice
        RadioButton btnClear = findViewById(R.id.ClearButton);
        //Radio button for choosing yes on offer option
        RadioButton btnYes = findViewById(R.id.OfferYesButton);
        //Radio button for choosing no on offer option
        RadioButton btnNo = findViewById(R.id.OfferNoButton);

        //String for meal type
        String mealType = "";
        //double for storing price
        double dblPrice;
            if (btnBreakfast.isChecked()) {
                mealType = mealType + "Breakfast ";
            }if (btnLunch.isChecked()) {
                mealType = mealType + "Lunch ";
            }if (btnDinner.isChecked()) {
                mealType = mealType + "Dinner ";
            }if (btnClear.isChecked()){
                mealType = "";
            }

            if(registrationErrors(strName,strPrice,strCuisineType,strDescription,strIngredients,strAllergens)) {
                try {
                    dblPrice = Double.valueOf(strPrice);
                    try {
                        if (btnYes.isChecked()) {

                            cook.addMeal(strName, mealType, strCuisineType, strDescription, dblPrice, true,strIngredients,strAllergens);
                        }
                        else{
                            cook.addMeal(strName, mealType, strCuisineType, strDescription, dblPrice, false,strIngredients,strAllergens);
                        }
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
    }
}