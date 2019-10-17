package com.example.campusmarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    public static final String EXTRA_MESSAGE = "com.example.campusmarket.MESSAGE";
    public String test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Called when the user clicks to log in
    public void logIn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    // Called when the user clicks to sign up
    public void signUp(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

//    public void testButton(View view) {
//        Intent intent = new Intent(this, UserActivity.class);
//        startActivity(intent);
//    }

    public String testMockitoFunction(String s) {
        test = s;
        return s.toLowerCase();
    }
}
