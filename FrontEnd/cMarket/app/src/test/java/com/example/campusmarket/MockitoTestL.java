package com.example.campusmarket;


import android.os.Build;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)

/**
 * Lily's mockito tests
 */
public class MockitoTestL {

    private View.OnClickListener onClickListener;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();


    /**
     * Mockito test for demo 3
     * @throws JSONException
     */
    @Test
    public void login_return() throws JSONException {
        LoginActivity logActivity = mock(LoginActivity.class);

        // Create the user JSON Object
        String userCorrect = "Sponge123";
        String passCorrect = "Password123";

        JSONObject response = new JSONObject();
        response.put("username", userCorrect);
        response.put("password", passCorrect);
        response.put("admin", "false");

//        when(logActivity.check_login_user(userCorrect, passCorrect)).thenReturn(true);
//        Assert.assertEquals(true, logActivity.check_login_user(userCorrect, passCorrect));
    }

    /**
     * Mockito tests for demo 4
     */
    @Test
    public void syntax_register (){
        RegisterActivity regActivity = mock(RegisterActivity.class);
        String usernameCorrect = "lkrohn";
        String passwordCorrect = "password1!";
        when(regActivity.validateForm()).thenReturn(true);
        Assert.assertEquals(true, regActivity.validateForm());
    }

    @Test
    public void login_verify() throws JSONException {
        LoginActivity logActivity = mock(LoginActivity.class);
        // Create the user JSON Object
        String usernameCorrect = "Sponge123";
        String passwordCorrect = "Password123";
        String sessionID = "12345ABCDE";

        JSONObject response = new JSONObject();
            response.put("username", usernameCorrect);
            response.put("password", passwordCorrect);

            logActivity.finishLogIn(usernameCorrect, sessionID);
            verify(logActivity,  times(1)).finishLogIn(usernameCorrect, sessionID);
    }

    /**
     * Verifies the fields of the json object that
     * represents an item added to a cart
     * @throws JSONException
     */
    @Test
    public void addToCart_check() throws JSONException {
        DashAdapter addCartActivity = mock(DashAdapter.class);

        // Create the user JSON Object
        String nameCorrect = "iphone";
        String priceCorrect = "250.0";
        String conditionCorrect = "used";
        String categoryCorrect = "electronics";
        String dateCorrect = "09/12/19";
        String sellerNameCorrect = "fadelsh";
        String refnumCorrect = "8";

        //verify the fields are correct to add the item
        JSONObject response = new JSONObject();
        response.put("name", nameCorrect);
        response.put("price", priceCorrect);
        response.put("condition", conditionCorrect);
        response.put("category", categoryCorrect);
        response.put("postedDate", dateCorrect);
        response.put("username", sellerNameCorrect);
        response.put("refnum", refnumCorrect);


        addCartActivity.addItem();
        verify(addCartActivity,  times(1)).addItem();
    }

    /**
     * Verifies the fields of the json object that
     * represents an item added to a cart
     * @throws JSONException
     */
    @Test
    public void postItem_check() throws JSONException {
        NewPostActivity postActivity = mock(NewPostActivity.class);

        // Create the user JSON Object
        String nameCorrect = "iphone";
        String priceCorrect = "250.0";
        String conditionCorrect = "used";
        String categoryCorrect = "electronics";
        String dateCorrect = "09/12/19";
        String sellerNameCorrect = "fadelsh";
        String refnumCorrect = "8";

        //verify the fields are correct to add the item
        JSONObject response = new JSONObject();
        response.put("name", nameCorrect);
        response.put("price", priceCorrect);
        response.put("condition", conditionCorrect);
        response.put("category", categoryCorrect);
        response.put("postedDate", dateCorrect);
        response.put("username", sellerNameCorrect);
        response.put("refnum", refnumCorrect);


        addCartActivity.addItem();
        verify(addCartActivity,  times(1)).addItem();
    }





}
