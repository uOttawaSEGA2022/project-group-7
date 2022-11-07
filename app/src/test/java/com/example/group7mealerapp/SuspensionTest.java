package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import UserJavaFiles.Cook;
import UserJavaFiles.Suspension;

class SuspensionTest {
    @Test
    public void testCreateSuspension(){
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            Suspension status2 = new Suspension(false, new Date(2022,12,01));
            assertNotNull(status);
            assertNotNull(status2);
        }catch(AssertionError e){
            e.printStackTrace();
            fail(e.toString());
        }catch(Exception e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void getBannedUntil() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            assertEquals("Thu Dec 01 00:00:00 EST 2022",status.getBannedUntil());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void getPerma() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            assertEquals(false,status.getPerma());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void setPerma() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            status.setPerma(true);
            assertEquals(true,status.getPerma());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    void setBannedUntil() {
        try {
            Suspension status = new Suspension(false, "2022-12-01");
            status.setBannedUntil("2022-12-02");
            assertEquals("2022-12-02",status.getBannedUntil());
        }catch(Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}