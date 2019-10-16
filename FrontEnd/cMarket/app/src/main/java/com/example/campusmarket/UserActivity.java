package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserActivity extends Activity implements OnClickListener {
    private Button btnJson, btnDashboard, btnNewPost, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       // final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        //final EditText etUniversity = (EditText) findViewById(R.id.etUniversity);
       // final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);

        // Get their name from the database
        // String url = "http://coms-309-jr-1.misc.iastate.edu:8080/users/username/" + message;

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.usernameWelcome);
        textView.setText(message);


        btnJson = (Button) findViewById(R.id.btnJsonRequest);
        btnJson.setOnClickListener(this);
        btnDashboard = (Button) findViewById(R.id.btnGoToDashboard);
        btnDashboard.setOnClickListener(this);
        btnNewPost = (Button) findViewById(R.id.btnNewPost);
        btnNewPost.setOnClickListener(this);
        btnProfile = (Button) findViewById(R.id.btnGoToProfile);
        btnProfile.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJsonRequest:
                startActivity(new Intent(UserActivity.this,
                        JsonRequestActivity.class));
                break;
            case R.id.btnGoToDashboard:
                startActivity(new Intent(UserActivity.this,
                        DashboardActivity.class));
                break;
            case R.id.btnNewPost:
                startActivity(new Intent(UserActivity.this,
                        NewPostActivity.class));
                break;
            case R.id.btnGoToProfile:
                startActivity(new Intent(UserActivity.this,
                        ProfileActivity.class));
                break;
            default:
                break;
        }
    }
}
