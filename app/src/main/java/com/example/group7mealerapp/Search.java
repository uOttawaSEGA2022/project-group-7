package com.example.group7mealerapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;


import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.Meal;

import UserJavaFiles.Order;
import UserJavaFiles.User;
import UserJavaFiles.UserPOJO;
import codeModules.Modules;
import listViewFiles.ComplaintList;
import listViewFiles.MealList;

/**
 * this class is essentially two parts. To reduce on the reusage of code this meal list
 * is used to display the search for users and admin and it is also used to display
 * the cooks current menu. This stops us from making another adapter and another layout
 */
public class Search extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //for setting the list view properly
    ListView listViewMeals;
    List<Meal> meals;
    Meal meal;
    //to be transferred to this page
    User user;
    Boolean isCook;
    Button buttonAddMeal;



    protected void onCreate(Bundle savedInstanceState) {
        //get the user from welcome
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Meals");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonAddMeal = (Button)findViewById(R.id.btnAddMeal);
        buttonAddMeal.setEnabled(false);
        listViewMeals = (ListView) findViewById(R.id.listViewMeals);

        meals = new ArrayList<>();
        if (user.getClass() == Cook.class){
            isCook = true;

            buttonAddMeal.setEnabled(true);
            SearchView searchBar = findViewById(R.id.search);
            searchBar.setVisibility(View.GONE);
            buttonAddMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("why are you here for christ sake 3232");
                    btnAddMealClick(view);
                }
            });
        }
        else{
            isCook = false;
            buttonAddMeal.setVisibility((View.GONE));
            buttonAddMeal.setEnabled(false);
            searchMeal();
            //sets the onclick for the list
        }
        listViewMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);

                showInfoDialog(meal);
            }
        });
    }

    public void btnAddMealClick(View view){
        System.out.println("why are you here for christ sake");
        Intent switchPage = new Intent(this, AddMeal.class);
        Cook cook = null;
        if(isCook)
            cook = (Cook) user;
        switchPage.putExtra("Cook",cook);

        setResult(RESULT_OK, switchPage);
        startActivity(switchPage);
    }

    protected void onStart() {
        super.onStart();
        if(isCook){
            Cook cook = (Cook) user;
            databaseReference.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    meals.clear();
                    //calls an iterator on the children in the database IE all users stored
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    Meal temp = new Meal();
                    //this loop iterates through the DB under the userInfo block
                    for (DataSnapshot child: children){
                        //no logic just stores the value onto user
                        temp = child.getValue(Meal.class);

                        //comparing the email and password from the database with the inputted text fields
                        if (temp.getEmail().equals(cook.getEmail())){
                            meals.add(temp);
                        }
                        temp = null;
                    }

                    MealList mealListAdapter = new MealList(Search.this, meals,user);
                    listViewMeals.setAdapter(mealListAdapter);
                }
                //no need for this function but must be overridden
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }
    }

    /**
     * opens up the meals info. Client and Admin get the cooks info and the meal info plus
     * the option to purchase food
     * see the meals info plus the set active button
     * @param meal
     */
    private void showInfoDialog(Meal meal) {

        //build the dialogue
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_meal_information_dialoge, null);
        dialogBuilder.setView(dialogView);

        //SETTING UP THE TEXT FIELDS AND BUTTONS
        TextView editTextViewName  = dialogView.findViewById(R.id.textViewCookName);
        TextView editTextViewRating  = dialogView.findViewById(R.id.textViewCookRating);
        TextView editTextViewMealName  = dialogView.findViewById(R.id.textViewMealName);
        TextView editTextViewMealDescription  = dialogView.findViewById(R.id.textViewMealDescription);
        TextView editTextCookDescription = dialogView.findViewById((R.id.textViewCookDescription));
        //if iscook then purchase is invis and setactive is visible and vice versa
        Button buttonPurchase = (Button) dialogView.findViewById(R.id.buttonPurchase);
        Button buttonSetActive = (Button) dialogView.findViewById(R.id.buttonSetActive);
        Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDelete);
        if (isCook){
            final AlertDialog b = dialogBuilder.create();

            buttonPurchase.setVisibility(View.GONE);
            editTextCookDescription.setVisibility(View.GONE);
            editTextViewName.setVisibility(View.GONE);
            editTextViewRating.setVisibility(View.GONE);

            buttonSetActive.setVisibility(View.VISIBLE);
            editTextViewMealName.setText(meal.getName());
            editTextViewMealDescription.setText(meal.getDescription());
            if(!meal.isOffered())
                buttonSetActive.setText("Set Active");
            else
                buttonSetActive.setText("set Inactive");

            b.show();
            buttonSetActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    meal.setOfferedDB(meal);
                    Toast.makeText(getApplicationContext(), "Meal activity set" , Toast.LENGTH_LONG).show();
                    b.dismiss();

                }
            });
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!meal.isOffered()) {
                        meal.mealDeleteDB(meal);
                        Toast.makeText(getApplicationContext(), "Meal removed from menu", Toast.LENGTH_LONG).show();
                        b.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Cannot remove meal from menu, set to inactive first", Toast.LENGTH_LONG).show();
                        b.dismiss();
                    }
                }
            });
        }
        else{
            buttonPurchase.setVisibility(View.VISIBLE);
            buttonSetActive.setVisibility(View.GONE);
            buttonDelete.setVisibility(View.GONE);
        }
        //set them
        //COOK CREDENTIALS
        if(!isCook) {
            DatabaseReference cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
            cookDatabaseReference.addValueEventListener(new ValueEventListener() {
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //calls an iterator on the children in the database IE all users stored
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    UserPOJO temp = new UserPOJO();
                    //this loop iterates through the DB under the userInfo block
                    for (DataSnapshot child : children) {
                        //no logic just stores the value onto user
                        temp = child.getValue(UserPOJO.class);
                        //comparing the email and password from the database with the inputted text fields
                        if (temp.getEmail().equals(meal.getEmail())) {
                            UserPOJO tempUser = child.getValue(UserPOJO.class);
                            Cook currentCook = tempUser.convertToCook();

                            dialogBuilder.setTitle(meal.getEmail());
                            editTextViewName.setText("Name: " + currentCook.getFirstName());
                            editTextCookDescription.setText("Cook Desc: " + currentCook.getDescription());
                            editTextViewRating.setText("Rating: " + "5");//needs to go in cook finder
                            editTextViewMealName.setText("Meal: " + meal.getName());
                            editTextViewMealDescription.setText("Meal Desc: " + meal.getDescription());
                            final AlertDialog b = dialogBuilder.create();
                            b.show();
                            break;
                        }
                        temp = null;
                    }

                }

                //no need for this function but must be overridden
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            buttonPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Client client = (Client) user;
                    client.addOrder(meal.getEmail(),meal);
                    Toast.makeText(getApplicationContext(), "meal purchased please await cooks response", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    /**
     * method that searches for the meal ONLY USED BY CLIENT AND ADMIN!
     */
    private void searchMeal(){
        SearchView searchBar = findViewById(R.id.search);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        meals.clear();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            //population is done in this loop!
                            Meal meal = postSnapshot.getValue(Meal.class);
                            //check if the male name, cusine type or meal type equal then search is complete
                            //also make sure that the length is above 3 chars before we start quering
                            if( s.length() >= 3) {
                                if (meal.isOffered()) {
                                    if (meal.getName().toLowerCase().contains(s.toLowerCase())
                                            || meal.getCusineType().toLowerCase().contains(s.toLowerCase())
                                            || meal.getMealType().toLowerCase().contains(s.toLowerCase())) {
                                        meals.add(meal);
                                    }
                                }
                            }
                        }

                        //set the adapter and the rest
                        MealList mealListAdapter = new MealList(Search.this, meals,user);
                        listViewMeals.setAdapter(mealListAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
                return false;
            }
        });

    }




}
