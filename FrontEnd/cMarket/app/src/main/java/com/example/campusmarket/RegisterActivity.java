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

// Recommended validation library:
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

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

        // initializing awesomeValidation library
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // assigning regex expressions for each login field
        awesomeValidation.addValidation(this, R.id.etFirstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.firstNameError);
        awesomeValidation.addValidation(this, R.id.etLastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.lastNameError);
        awesomeValidation.addValidation(this, R.id.etUsername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.userNameError);
        // Password regex: at least one lower case, one upper case, and one number - must be at least 8 characters long
        awesomeValidation.addValidation(this, R.id.etPassword, "^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$", R.string.passwordError);
        awesomeValidation.addValidation(this, R.id.etUniversity, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.universityError);
        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailError);
        // For mobile number in future: awesomeValidation.addValidation(this, R.id.etPhone, "^[2-9]{2}[0-9]{8}$", R.string.mobileError);


        bRegister.setOnClickListener(this);

    }

    private void validateForm() {
        // first validate the form, then move ahead
        // if this becomes true, validation is successful
        if (awesomeValidation.validate()) {
            // now make the user info into json object & push to database.
            // then, send the user to the "start a json request" page
            finishSignUp();
        }
    }

    @Override
    public void onClick(View view) {
        if (view == bRegister) {
            // call the validateForm function to finish user sign up and load the next page
            validateForm();
        }
    }

    public boolean make_register_request(final JSONObject js) {

        final boolean[] success = {false};
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Const.URL_NEW_USER, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        success[0] = true;
                        Log.d(TAG, response.toString() + " posted");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                success[0] = false;
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                try {
                    params.put("username", js.getString("username"));
                    params.put("password", js.getString("password"));
                    params.put("firstname", js.getString("firstname"));
                    params.put("lastname", js.getString("lastname"));
                    params.put("email", js.getString("email"));
                    params.put("university", js.getString("university"));
                    params.put("admin", "false");
                }  catch (JSONException e) {
                e.printStackTrace();
            }
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

        return success[0];

    }
    // Called when user finishes signing up
    public void finishSignUp() {

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

        // Post the new user
        make_register_request(js);

        // sending user to the next page by creating a new intent
        Intent intent = new Intent(this, UserActivity.class);
        EditText editText = (EditText) findViewById(R.id.etUsername);
        String message = editText.getText().toString();
        // stores username and displays it in a welcome message on the next page;
        intent.putExtra(MainActivity.EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}