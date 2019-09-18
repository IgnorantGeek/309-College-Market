package com.example.loginexperiment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etUniversity = (EditText) findViewById(R.id.etUniversity);

        final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);



    }
}
