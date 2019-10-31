package com.example.campusmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that represents the main page / welcome page / first page you go to when you open the app
 */
public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE = "com.example.campusmarket.MESSAGE";
    public String test;


    /**
     * Creates instance of MainActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks to log in.
     * Brings user to LoginActivity page
     * @param view
     */
    public void logIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks to sign up.
     * Brings user to RegisterActivity page
     * @param view
     */
    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Called when user clicks on "Test"
     * Brings user to page after Login/Register
     * For testing purposes only
     * @param view
     */
    public void testButton(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    /**
     * Function to test Mockito.
     * For testing purposes only
     * @param s
     * @return the  string
     */
    public String testMockitoFunction(String s) {
        test = s;
        return s.toLowerCase();
    }
}
