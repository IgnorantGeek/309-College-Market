package com.example.campusmarket;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity that represents the page after a user logs in / signs up
 */
public class UserActivity extends Activity implements OnClickListener {
    private Button btnJson, btnDashboard, btnNewPost, btnProfile, btnWebSocket;
    public static String loggedInUsername;
    protected static String sessionID;

    /**
     * Creates this instance of UserActivity.
     * Display's "Welcome, Username" where username is from previous activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Stores the  user's username
        Intent intent = getIntent();
        loggedInUsername = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        sessionID = intent.getStringExtra("sessionID");
        Log.d("This is the sessionID: ", sessionID);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.usernameWelcome);
        textView.setText(loggedInUsername);

        btnJson = (Button) findViewById(R.id.btnJsonRequest);
        btnJson.setOnClickListener(this);
        btnDashboard = (Button) findViewById(R.id.btnGoToDashboard);
        btnDashboard.setOnClickListener(this);
        btnNewPost = (Button) findViewById(R.id.btnNewPost);
        btnNewPost.setOnClickListener(this);
        btnProfile = (Button) findViewById(R.id.btnGoToProfile);
        btnProfile.setOnClickListener(this);
        btnWebSocket = (Button) findViewById(R.id.btnWebSocket);
        btnWebSocket.setOnClickListener(this);
    }

    /**
     * Sees which button the user is going to click.
     * Almost acts as a navbar
     * @param v
     */
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
            case R.id.btnWebSocket:
            startActivity(new Intent(UserActivity.this,
                    WebSockets.class));
            break;
            default:
                break;
        }
    }
}
