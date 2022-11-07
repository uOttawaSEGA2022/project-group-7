package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.CreditCard;
import UserJavaFiles.Suspension;

import android.graphics.Bitmap;

import java.util.Date;

class CookTest {

    @Test
    public void testCreateCook(){
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Cook testCook = new Cook("Jane", "Doe", "something@mail.com", "114514", "114 514 Drive", "Best poutine chef in Ottawa", status);
            assertNotNull(testCook);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getDescription() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Cook testCook = new Cook("Jane", "Doe", "something@mail.com", "114514", "114 514 Drive", "Best poutine chef in Ottawa", status);
            assertEquals("Best poutine chef in Ottawa",testCook.getDescription());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void setDescription() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Cook testCook = new Cook("Jane", "Doe", "something@mail.com", "114514", "114 514 Drive", "Best poutine chef in Ottawa", status);
            testCook.setDescription("Overworked Uottawa dining hall cook");
            assertEquals("Overworked Uottawa dining hall cook",testCook.getDescription());
        }catch(Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getSuspension() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Cook testCook = new Cook("Jane", "Doe", "something@mail.com", "114514", "114 514 Drive", "Best poutine chef in Ottawa", status);
            if(!status.equalsTo(testCook.getSuspension())){
                fail("Failed getSuspension");
            }
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void setSuspension() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Cook testCook = new Cook("Jane", "Doe", "something@mail.com", "114514", "114 514 Drive", "Best poutine chef in Ottawa", status);
            status = new Suspension(true, "2077-12-01");
            testCook.setSuspension(status);
            if(!status.equalsTo(testCook.getSuspension())){
                fail("Failed getSuspension");
            }
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}