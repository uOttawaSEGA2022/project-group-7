package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import UserJavaFiles.Complaint;
import UserJavaFiles.CreditCard;

class ComplaintTest {
    @Test
    void createComplaint(){
        Complaint testComplaint = new Complaint();
        Complaint testComplaint2 = new Complaint("something@mail.com","Food too spicy");
        Complaint testComplaint3 = new Complaint();
        try{
            assertNotNull(testComplaint);
            assertNotNull(testComplaint2);
            assertNotNull(testComplaint3);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void getEmail() {

    }

    @Test
    void setEmail() {
    }

    @Test
    void getComplaint() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }
}