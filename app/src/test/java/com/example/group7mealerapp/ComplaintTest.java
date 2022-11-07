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
        Complaint testComplaint3 = new Complaint("something@mail.com","Food too spicy","Jane Doe");
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
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        assertEquals("something@mail.com",testComplaint.getEmail());
    }

    @Test
    void setEmail() {
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        testComplaint.setEmail("ihatetestcases@mail.com");
        assertEquals("ihatetestcases@mail.com",testComplaint.getEmail());
    }

    @Test
    void getComplaint() {
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        assertEquals("Food too spicy",testComplaint.getComplaint());
    }

    @Test
    void setComplaint() {
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        testComplaint.setComplaint("Rude attitude, expects to speak with manager");
        assertEquals("Rude attitude, expects to speak with manager",testComplaint.getComplaint());
    }

    @Test
    void getId() {
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        assertEquals("Jane Doe",testComplaint.getId());
    }

    @Test
    void setId() {
        Complaint testComplaint = new Complaint("something@mail.com","Food too spicy","Jane Doe");
        testComplaint.setId("UserName");
        assertEquals("UserName",testComplaint.getId());
    }
}