package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private AwesomeValidation awesomeValidation;
    EditText etUsername;
    EditText etPassword;
    Button bLogin;
    TextView registerLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        registerLink = findViewById(R.id.tvRegisterHere);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etUsername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.userNameError);
        awesomeValidation.addValidation(this, R.id.etPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passwordError);

        // ^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$ -- this may work better in future
        
        bLogin.setOnClickListener(this);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
    }

    private void validateForm() {
        // first validate the form then move ahead
        // if this becomes true that means validation is successful
        if (awesomeValidation.validate()) {
            Intent intent = new Intent(this, UserActivity.class);
            EditText editText = (EditText) findViewById(R.id.etUsername);
            String message = editText.getText().toString();
            intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
            startActivity(intent);
            // send the user to the "start a json request" page
        }
    }

    @Override
    public void onClick(View view) {
        if (view == bLogin) {
            validateForm();
        }
    }

    // Called when user finishes logging in
    public void finishLogIn(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        EditText editText = (EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
