package com.example.group7mealerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import UserJavaFiles.Administrator;
import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.Meal;
import UserJavaFiles.User;
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
        //REMOVE THBETWEEN THESE COMMENTS ONLY FOR TESTING!!
        Meal meal = new Meal("Yakisoba", "Dinner", "Japanese", 5.25);
        meal = new Meal("Pad Thai", "Lunch", "Thai", 5.49);
        //REMOVE UP TO HERE INCLUDING THIS LINE
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ActiveMeals");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //grab the listview so we can populate
        listViewMeals = (ListView) findViewById(R.id.listViewMeals);
        meals = new ArrayList<>();
        searchMeal();

        //THIS METHOD IS FOR FUTURE USE IF WE EVER NEED TO SET ONCLICK!
        listViewMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public void searchMeal(){
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
