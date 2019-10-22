package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccountSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button deleteAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        String welcomeMessage = UserActivity.loggedInUsername + "'s Profile";
        TextView welcome = findViewById(R.id.tvAccSettingsWelcome);
        welcome.setText(welcomeMessage);
        deleteAccount = findViewById(R.id.btnDeleteAccount);
        deleteAccount.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
