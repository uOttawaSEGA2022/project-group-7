package com.example.myapplication;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTest {

    private static final String FAKE_STRING = "Login was successful";

    @Mock
    Context mMockContext;

    @Test
    public void readStringFromContext_LocalizedString() {

        LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);

        // ...when the string is returned from the object under test...
        String result = myObjectUnderTest.validate("admin","admin");


        // ...then the result should be the expected one.
        assertThat(result, is(FAKE_STRING));

    }

    @Test
    public void testValidUsername() {
        LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);
        boolean answer = myObjectUnderTest.isValidUserName("johdoe@gmail.com");

        assertThat(answer, is(true));
    }

    @Test
    public void testInvalidUsername() {
        LoginActivity myObjectUnderTest = new LoginActivity(mMockContext);
        boolean answer = myObjectUnderTest.isValidUserName("I hate CEG and CSI");

        assertThat(answer, is(false));
    }


}
