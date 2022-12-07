package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UserJavaFiles.Meal;
import UserJavaFiles.Order;

class OrderTest {
    @Test
    void createOrder() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        try{
            assertNotNull(testMeal);

        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void getCookEmail() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals("cook@gmail.com",testOrder.getCookEmail());
    }

    @Test
    void setCookEmail() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        testOrder.setCookEmail("cooker@gmail.com");
        assertEquals("cooker@gmail.com",testOrder.getCookEmail());
    }

    @Test
    void getUserEmail() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals("client@gmail.com",testOrder.getUserEmail());
    }

    @Test
    void setUserEmail() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        testOrder.setUserEmail("cooker@gmail.com");
        assertEquals("cooker@gmail.com",testOrder.getUserEmail());
    }

    @Test
    void getState() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals("PENDING",testOrder.getState());
    }

    @Test
    void getMeal() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals(testMeal,testOrder.getMeal());
    }

    @Test
    void setMeal() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Meal testMeal2 = new Meal("Wilson", "Higgsbury", "goood", "verygood", "Wilson@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        testOrder.setMeal(testMeal2);
        assertEquals(testMeal2,testOrder.getMeal());
    }

    @Test
    void isRated() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals(false,testOrder.isRated());
    }

    @Test
    void setRated() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        testOrder.setRated(true);
        assertEquals(true,testOrder.isRated());
    }

    @Test
    void getId() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        assertEquals("Random Gibberish",testOrder.getId());
    }

    @Test
    void setId() {
        Meal testMeal = new Meal("John", "hot", "goood", "verygood", "@hotmail", 2.5, true,"","");
        Order testOrder = new Order("cook@gmail.com","client@gmail.com",testMeal,"Random Gibberish");
        testOrder.setId("Other Gibberish");
        assertEquals("Other Gibberish",testOrder.getId());
    }
}