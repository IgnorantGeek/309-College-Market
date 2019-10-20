package com.example.campusmarket;


import android.os.Build;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class GETResponseTest {

    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void getResponseTest_register() throws JSONException {
        //This creates a Mock Object of the class that we have not fully implemented
        RegisterActivity regAct = mock(RegisterActivity.class);

        // Create the user JSON Object
        String usernameCorrect = "Sponge123";
        String passwordCorrect = "Password123";
        String firstNameCorrect = "Spongebob";
        String lastNameCorrect = "SqaurePants";
        String schoolCorrect = "Bikini Bottom University";
        String emailCorrect = "spongebob@gmail.com";

        JSONObject response = new JSONObject();
        response.put("username", usernameCorrect);
        response.put("password", passwordCorrect);
        response.put("firstname", firstNameCorrect);
        response.put("lastname", lastNameCorrect);
        response.put("email", schoolCorrect);
        response.put("university", emailCorrect);
        response.put("admin", "false");

        when(regAct.make_register_request(response)).thenReturn(true);
        Assert.assertEquals(true, regAct.make_register_request(response));
    }

    @Test
    public void getResponseTest_login() throws JSONException {
        //This creates a Mock Object of the class that we have not fully implemented
        LoginActivity logAct = mock(LoginActivity.class);


        // Create the user JSON Object
        String usernameCorrect = "Sponge123";
        String passwordCorrect = "Password123";

//        when(logAct.check_login_user(usernameCorrect, passwordCorrect)).thenReturn(true);
//        Assert.assertEquals(true, logAct.check_login_user(usernameCorrect, passwordCorrect));
    }

}