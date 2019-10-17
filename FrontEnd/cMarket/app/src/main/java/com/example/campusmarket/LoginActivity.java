package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Recommended validation library
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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

        // initializing awesomeValidation library
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etUsername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.userNameError);
        // Password regex: at least one lower case, one upper case, and one number - must be at least 8 characters long
        awesomeValidation.addValidation(this, R.id.etPassword, "^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$", R.string.passwordError);

        bLogin.setOnClickListener(this);

        // creating a new intent to handle page redirection to register for the app when link is clicked
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
    }

    /* To be implemented function
     * Verify that user exists in the database.
     * Return true if they do, otherwise return false
     */
    public boolean check_login_user(String username, String password)
    {
        return false;
    }

    private void validateForm() {
        // first validate the form then move ahead
        // if this becomes true that means validation is successful
        if (awesomeValidation.validate()) {
            check_login_user((etUsername.getText()).toString(), (etPassword.getText()).toString());

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
            // call the validateForm function to finish user sign up and load the next page
            validateForm();
        }
    }

    // Called when user finishes logging in
    public void finishLogIn(View view) {
        // creating a new intent to signal page redirection
        Intent intent = new Intent(this, UserActivity.class);
        EditText editText = (EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
