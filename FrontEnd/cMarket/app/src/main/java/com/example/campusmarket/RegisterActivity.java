package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText etFirstName;
    EditText etLastName;
    EditText etUsername;
    EditText etPassword;
    EditText etUniversity;
    EditText etEmail;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etUniversity = findViewById(R.id.etUniversity);
        etEmail = findViewById(R.id.etEmail);

        bRegister = findViewById(R.id.bRegister);
    }


//        // Starts the button's onCLick validation process when button is clicked
//        bRegister.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                checkDataEntry();
//            }
//        });
//    }
//
//    boolean isEmail(EditText text) {
//        CharSequence etEmail = text.getText().toString();
//        return (!TextUtils.isEmpty(etEmail) && Patterns.EMAIL_ADDRESS.matcher(etEmail).matches());
//    }
//
//    // Checks for empty field / no entry
//    boolean isEmpty(EditText text) {
//        CharSequence str = text.getText().toString();
//        return TextUtils.isEmpty(str);
//    }
//
//
//
//
//    // Goes through each implemented field and determines validity - then prints a message if invalid
//    void checkDataEntry() {
//        if (isEmpty(etFirstName)) {
//            etFirstName.setError("Enter first name");
//        }
//
//        if (isEmpty(etLastName)) {
//            etLastName.setError("Last name is required");
//        }
//
//        if (isEmail(etEmail) == false) {
//            etEmail.setError("Enter a valid email");
//        }
//
//    }


    // Called when user finishes signing up
    public void finishSignUp(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        EditText editText = (EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}

