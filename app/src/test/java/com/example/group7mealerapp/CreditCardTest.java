package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import UserJavaFiles.CreditCard;

class CreditCardTest {
    Long num = Long.parseLong("1234123412341234");
    @Test
    void createCreditCard(){
        CreditCard testCC = null;
        try{
            testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        }
        catch (Exception e){
            System.out.println("what went wrong" + e);
        }
        try{
            assertNotNull(testCC);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing to see if illegal params for the constructor throws errors.
    @Test
    void testCreateException(){
        try {
            CreditCard firstNameWrong = new CreditCard("", "Doe", "114 514 Drive", num, 114, "01/24");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard lastNameWrong = new CreditCard("Jane","","114 514 Drive",num,114,"01/24");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard addressWrong = new CreditCard("Jane","Doe","",num,114,"01/24");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard numberWrong = new CreditCard("Jane","Doe","114 514 Drive",114,114,"01/24");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard pinWrong = new CreditCard("Jane","Doe","114 514 Drive",num,114514,"01/24");
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void getFirstName() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        //try{
            assertEquals("Jane",testCC.getFirstName());
        //}catch(AssertionError e){
        //    e.printStackTrace();
        //    fail(e.toString());
        //}
    }

    @Test
    void setFirstName() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        testCC.setFirstName("John");
        assertEquals("John",testCC.getFirstName());
    }

    @Test
    void getLastName() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        assertEquals("Doe",testCC.getLastName());
    }

    @Test
    void setLastName() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        testCC.setLastName("Dove");
        assertEquals("Dove",testCC.getLastName());
    }

    @Test
    void getAddress() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        assertEquals("114 514 Drive",testCC.getAddress());
    }

    @Test
    void setAddress() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        testCC.setAddress("1919 Drive");
        assertEquals("1919 Drive",testCC.getAddress());
    }

    @Test
    void getNumber() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        assertEquals((long)num,testCC.getNumber());
    }

    @Test
    void setNumber() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        testCC.setNumber(1919114514);
        assertEquals((long)1919114514,testCC.getNumber());
    }

    @Test
    void getPin() throws Exception {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",num,114,"01/24");
        assertEquals(114,testCC.getPin());
    }

    @Test
    void setPin() throws Exception {
        CreditCard testCC = new CreditCard("Jane", "Doe", "114 514 Drive", num, 114, "01/24");
        testCC.setPin(514);
        assertEquals(514, testCC.getPin());
    }
}