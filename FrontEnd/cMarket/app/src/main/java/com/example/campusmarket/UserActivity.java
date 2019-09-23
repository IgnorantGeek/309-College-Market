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
    private Button btnJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       // final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        //final EditText etUniversity = (EditText) findViewById(R.id.etUniversity);
       // final TextView welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMsg);


        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.usernameWelcome);
        textView.setText(message);


        btnJson = (Button) findViewById(R.id.btnJsonRequest);
        btnJson.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJsonRequest:
                startActivity(new Intent(UserActivity.this,
                        JsonRequestActivity.class));
                break;
            default:
                break;
        }
    }
}
