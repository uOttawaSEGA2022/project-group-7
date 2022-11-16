package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import UserJavaFiles.Complaint;
import UserJavaFiles.Cook;
import UserJavaFiles.Meal;
import UserJavaFiles.Suspension;

public class MealTest {
    @Test
    void createMeal(){
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5);

        try{
            assertNotNull(testMeal);

        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    void getDescription() {
        try {
            Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5);
            assertEquals("verygoood",testMeal.getDescription());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    void getName() {
        try {
            Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5);
            assertEquals("John",testMeal.getName());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
    @Test
    void getMealType() {
        try {
            Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5);
            assertEquals("hot",testMeal.getMealType());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}

