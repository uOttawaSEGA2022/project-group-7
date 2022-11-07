package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import android.app.Activity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import UserJavaFiles.Client;
import UserJavaFiles.Complaint;
import UserJavaFiles.ComplaintList;
import UserJavaFiles.CreditCard;

class ComplaintListTest {
    @Test
    public void testCreateComplaintList(){
        try {
            ArrayList list = new ArrayList();
            list.add(new Complaint("a", "b"));
            list.add(new Complaint("foo", "bar"));
            list.add(new Complaint("some", "thing"));
            Activity act = new Activity();
            ComplaintList complaintList = new ComplaintList(act, list);
            assertNotNull(complaintList);
        }catch(AssertionError e){
        e.printStackTrace();
        fail(e.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}