package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import UserJavaFiles.Client;
import UserJavaFiles.CreditCard;

//Testing for the Client class. Since the User class is abstract, its tests are included in here as well
class ClientTest {
    @Test
    public void testCreateClient(){
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        try{
            assertNotNull(testClient);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
    }

    //Testing to see if illegal params for the constructor throws errors.
    @Test
    void testCreateException(){
        try {
            CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
            Client testClient = new Client("","Doe","something@mail.com","114514","114 514 Drive",testCC);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
            Client testClient = new Client("Jane","","something@mail.com","114514","114 514 Drive",testCC);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
            Client testClient = new Client("Jane","Doe","","114514","114 514 Drive",testCC);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
            Client testClient = new Client("Jane","Doe","something@mail.com","","114 514 Drive",testCC);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }

        try {
            CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
            Client testClient = new Client("Jane","Doe","something@mail.com","114514","",testCC);
            fail();
        }catch(IllegalArgumentException e){

        }catch(Exception e){
            fail(e.toString());
        }
    }

    @Test
    void getFirstName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        assertEquals("Jane",testClient.getFirstName());
    }

    @Test
    void getLastName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        assertEquals("Doe",testClient.getLastName());
    }

    @Test
    void getEmail() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        assertEquals("something@mail.com",testClient.getEmail());
    }

    @Test
    void getPassword() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        assertEquals("114514",testClient.getPassword());
    }

    @Test
    void getAddress() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        assertEquals("114 514 Drive",testClient.getAddress());
    }

    @Test
    void setFirstName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        testClient.setFirstName("John");
        assertEquals("John",testClient.getFirstName());
    }

    @Test
    void setLastName() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        testClient.setLastName("Dove");
        assertEquals("Dove",testClient.getLastName());
    }

    @Test
    void setEmail() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        testClient.setEmail("foo@gmail.com");
        assertEquals("foo@gmail.com",testClient.getEmail());
    }

    @Test
    void setPassword() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        testClient.setPassword("IHopeTheTADoesntGoThroughAllTestCases");
        assertEquals("IHopeTheTADoesntGoThroughAllTestCases",testClient.getPassword());
    }

    @Test
    void setAddress() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        testClient.setAddress("OutOfIdeas");
        assertEquals("OutOfIdeas",testClient.getAddress());
    }
}