package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.Date;

import UserJavaFiles.Administrator;
import UserJavaFiles.Client;
import UserJavaFiles.Cook;
import UserJavaFiles.CreditCard;
import UserJavaFiles.Suspension;
import UserJavaFiles.UserPOJO;

class UserPOJOTest {

    @Test
    void convertToClient() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Suspension status = new Suspension(false,new Date(0,1,1));
        Client testClient = new Client("Jane","Doe","something@mail.com","114514","114 514 Drive",testCC);
        UserPOJO user = new UserPOJO("Jane","Doe","something@mail.com","114514","114 514 Drive","Client","boring person",testCC,null,status);
        if(!testClient.equalsTo(user.convertToClient())){
            fail("Failed convertToClient");
        }
    }

    @Test
    void convertToCook() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Suspension status = new Suspension(false,new Date(0,1,1));
        Cook testCook = new Cook("Jane","Doe","something@mail.com","114514","114 514 Drive","boring person",status);
        UserPOJO user = new UserPOJO("Jane","Doe","something@mail.com","114514","114 514 Drive","Client","boring person",testCC,null,status);
        if(!testCook.equalsTo(user.convertToCook())){
            fail("Failed convertToCook");
        }
    }

    @Test
    void convertToAdministrator() {
        CreditCard testCC = new CreditCard("Jane","Doe","114 514 Drive",1145141919,114,new Date(0,1,1));
        Suspension status = new Suspension(false,new Date(0,1,1));
        Administrator testAdmin = new Administrator("Jane","Doe","something@mail.com","114514","114 514 Drive");
        UserPOJO user = new UserPOJO("Jane","Doe","something@mail.com","114514","114 514 Drive","Client","boring person",testCC,null,status);
        if(!testAdmin.equalsTo(user.convertToAdministrator())){
            fail("Failed convertToAdministrator");
        }
    }
}