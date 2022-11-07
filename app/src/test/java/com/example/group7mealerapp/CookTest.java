package com.example.group7mealerapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import UserJavaFiles.Cook;
import android.graphics.Bitmap;

class CookTest {

    @Test
    void getBlankCheque() {
        Cook testCook = new Cook("Jane","Doe","something@mail.com","114514","114 514 Drive","Best poutine chef in Ottawa");
        assertEquals(null,testCook.getBlankCheque());
    }

    @Test
    void getDescription() {
        Cook testCook = new Cook("Jane","Doe","something@mail.com","114514","114 514 Drive","Best poutine chef in Ottawa");
        assertEquals("Best poutine chef in Ottawa",testCook.getDescription());
    }

    //not implemented yet so this is blank
    @Test
    void setBlankCheque() {

    }

    @Test
    void setDescription() {
        Cook testCook = new Cook("Jane","Doe","something@mail.com","114514","114 514 Drive","Best poutine chef in Ottawa");
        testCook.setDescription("Overworked Uottawa dining hall cook");
        assertEquals("Overworked Uottawa dining hall cook",testCook.getDescription());
    }
}