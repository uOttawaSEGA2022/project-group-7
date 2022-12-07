package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UserJavaFiles.Client;
import UserJavaFiles.CreditCard;
import UserJavaFiles.Meal;
import UserJavaFiles.Order;
import UserJavaFiles.Rating;

class RatingTest {

    @Test
    void createRating() {
        Rating testRating = new Rating(5,"wilson@gmail.com");
        try{
            assertNotNull(testRating);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
        try {
            Rating testRating2 = new Rating(7,"wilson@gmail.com");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void getRating() {
        Rating testRating = new Rating(5,"wilson@gmail.com");
        assertEquals(5,testRating.getRating());
    }

    @Test
    void setRating() {
        Rating testRating = new Rating(5,"wilson@gmail.com");
        testRating.setRating(4);
        assertEquals(4,testRating.getRating());
        try {
            Rating testRating2 = new Rating(5,"wilson@gmail.com");
            testRating.setRating(6);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void getEmail() {
        Rating testRating = new Rating(5,"wilson@gmail.com");
        assertEquals("wilson@gmail.com",testRating.getEmail());
    }

    @Test
    void setEmail() {
        Rating testRating = new Rating(5,"wilson@gmail.com");
        testRating.setEmail("maxwell@gmail.com");
        assertEquals("maxwell@gmail.com",testRating.getEmail());
    }
}