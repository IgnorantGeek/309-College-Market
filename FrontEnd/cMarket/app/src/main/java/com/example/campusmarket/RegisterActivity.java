package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = RegisterActivity.class.getSimpleName();
    private AwesomeValidation awesomeValidation;

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

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etFirstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.firstNameError);
        awesomeValidation.addValidation(this, R.id.etLastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lastNameError);
        awesomeValidation.addValidation(this, R.id.etUsername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.userNameError);
        awesomeValidation.addValidation(this, R.id.etPassword, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passwordError);
        awesomeValidation.addValidation(this, R.id.etUniversity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.universityError);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailError);
//       awesomeValidation.addValidation(this, R.id.etPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileError);


        bRegister.setOnClickListener(this);

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
        if (view == bRegister) {
            validateForm();
        }
    }

    // Called when user finishes signing up
    public void finishSignUp(View view) {
        JSONObject js = new JSONObject();
        try {
            js.put("username", (etUsername.getText()).toString());
            js.put("password", (etPassword.getText()).toString());
            js.put("firstname", (etFirstName.getText()).toString());
            js.put("lastname", (etLastName.getText()).toString());
            js.put("email", (etEmail.getText()).toString());
            js.put("university", (etUniversity.getText()).toString());
            js.put("admin", "false");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Const.URL_NEW_USER, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", (etUsername.getText()).toString());
                params.put("password", (etPassword.getText()).toString());
                params.put("firstname", (etFirstName.getText()).toString());
                params.put("lastname", (etLastName.getText()).toString());
                params.put("email", (etEmail.getText()).toString());
                params.put("university", (etUniversity.getText()).toString());
                params.put("admin", "false");
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

        Intent intent = new Intent(this, UserActivity.class);
        EditText editText = (EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}