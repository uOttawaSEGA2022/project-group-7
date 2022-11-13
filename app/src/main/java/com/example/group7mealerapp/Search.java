package com.example.group7mealerapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


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



import UserJavaFiles.Cook;
import UserJavaFiles.Meal;

import UserJavaFiles.User;
import UserJavaFiles.UserPOJO;
import codeModules.Modules;
import listViewFiles.MealList;

public class Search extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //for setting the list view properly
    ListView listViewMeals;
    List<Meal> meals;
    Meal meal;
    //to be transferred to this page
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        //REMOVE In BETWEEN THESE COMMENTS ONLY FOR TESTING!!
        //Meal meal = new Meal("Yakisoba", "Dinner", "Japanese", "japanese stirfry using buckwheat noodles and a slew of vegetables","cook@gmail.com", 5.25);
        //meal.setOffered(true,meal);
        //meal = new Meal("Pad Thai", "Lunch", "Thai","spicy stirfry with thai spices and thick noodles","nocturne@gmail.com", 5.49);
        //meal.setOffered(true,meal);
        //REMOVE UP TO HERE INCLUDING THIS LINE

        //get the user from login
        Modules modules = new Modules();
        user = modules.catchUser(getIntent());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ActiveMeals");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //grab the listview so we can populate
        listViewMeals = (ListView) findViewById(R.id.listViewMeals);
        meals = new ArrayList<>();
        searchMeal();
        //sets the onclick for the list
        listViewMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);

                showInfoDialog("cook@gmail.com",meal.getName(),meal.getDescription());
            }
        });

    }
    private void showInfoDialog(String email, String mealName, String description) {

        //build the diologue
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
        Button buttonPurchase = (Button) dialogView.findViewById(R.id.buttonPurchase);
        //set them
        //COOK CREDENTIALS
        DatabaseReference cookDatabaseReference = firebaseDatabase.getReference("UserInfo");
        cookDatabaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //calls an iterator on the children in the database IE all users stored
                Iterable<DataSnapshot> children = snapshot.getChildren();
                UserPOJO temp = new UserPOJO();
                //this loop iterates through the DB under the userInfo block
                for (DataSnapshot child: children){
                    //no logic just stores the value onto user
                    temp = child.getValue(UserPOJO.class);
                    //comparing the email and password from the database with the inputted text fields
                    if (temp.getEmail().equals(email)){
                        UserPOJO tempUser = child.getValue(UserPOJO.class);
                        Cook currentCook = tempUser.convertToCook();
                        System.out.println(currentCook.getFirstName());
                        dialogBuilder.setTitle(email);
                        editTextViewName.setText("Name: " + currentCook.getFirstName());
                        editTextCookDescription.setText("Cook Desc: " + currentCook.getDescription());
                        editTextViewRating.setText("Rating: " + "5");//needs to go in cook finder
                        editTextViewMealName.setText("Meal: " + mealName);
                        editTextViewMealDescription.setText("Meal Desc: " +description);
                        final AlertDialog b = dialogBuilder.create();
                        b.show();

                        break;
                    }
                    temp = null;
                }

            }
            //no need for this function but must be overridden
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }
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
                            if(s.length() >= 3){
                                if(meal.getName().toLowerCase().contains(s.toLowerCase())
                                        || meal.getCusineType().toLowerCase().contains(s.toLowerCase())
                                        || meal.getMealType().toLowerCase().contains(s.toLowerCase())){
                                    meals.add(meal);
                                }
                            }
                        }
                        //set the adapter and the rest
                        MealList mealListAdapter = new MealList(Search.this, meals);
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
