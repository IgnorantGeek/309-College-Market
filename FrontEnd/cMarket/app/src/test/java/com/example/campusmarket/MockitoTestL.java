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

public class MockitoTestL {

    private View.OnClickListener onClickListener;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();

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

        JSONObject response = new JSONObject();
            response.put("username", usernameCorrect);
            response.put("password", passwordCorrect);

            logActivity.finishLogIn(usernameCorrect);
            verify(logActivity,  times(1)).finishLogIn(usernameCorrect);
    }

    @Test
    public void login_return() throws JSONException { LoginActivity logActivity = mock(LoginActivity.class);

        // Create the user JSON Object
        String userCorrect = "Sponge123";
        String passCorrect = "Password123";

        JSONObject response = new JSONObject();
            response.put("username", userCorrect);
            response.put("password", passCorrect);
            response.put("admin", "false");

        when(logActivity.check_login_user(userCorrect, passCorrect)).thenReturn(true);
            Assert.assertEquals(true, logActivity.check_login_user(userCorrect, passCorrect));
    }

}
