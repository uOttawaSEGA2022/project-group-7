package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import UserJavaFiles.CreditCard;

class CreditCardTest {

    @Test
    void createCreditCard(){
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
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
            CreditCard firstNameWrong = new CreditCard("", "Doe", "114 514 Drive", 1145141919, 114, new Date(0, 1, 1));
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard lastNameWrong = new CreditCard("Jane","","114 514 Drive",1145141919,114,new Date(0,1,1));
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard addressWrong = new CreditCard("Jane","Doe","",1145141919,114,new Date(0,1,1));
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard numberWrong = new CreditCard("Jane","Doe","114 514 Drive",114,114,new Date(0,1,1));
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard pinWrong = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114514,new Date(0,1,1));
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void getFirstName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        //try{
            assertEquals("Jane",testCC.getFirstName());
        //}catch(AssertionError e){
        //    e.printStackTrace();
        //    fail(e.toString());
        //}
    }

    @Test
    void setFirstName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        testCC.setFirstName("John");
        assertEquals("John",testCC.getFirstName());
    }

    @Test
    void getLastName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        assertEquals("Doe",testCC.getLastName());
    }

    @Test
    void setLastName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        testCC.setLastName("Dove");
        assertEquals("Dove",testCC.getLastName());
    }

    @Test
    void getAddress() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        assertEquals("114 514 Drive",testCC.getAddress());
    }

    @Test
    void setAddress() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        testCC.setAddress("1919 Drive");
        assertEquals("1919 Drive",testCC.getAddress());
    }

    @Test
    void getNumber() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        assertEquals((long)1145141919,testCC.getNumber());
    }

    @Test
    void setNumber() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        testCC.setNumber(1919114514);
        assertEquals((long)1919114514,testCC.getNumber());
    }

    @Test
    void getPin() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        assertEquals(114,testCC.getPin());
    }

    @Test
    void setPin() {
        CreditCard testCC = new CreditCard("Jane", "Doe", "114 514 Drive", 1145141919, 114, new Date(0, 1, 1));
        testCC.setPin(514);
        assertEquals(514, testCC.getPin());
    }
}