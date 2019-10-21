package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// Recommended validation library
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = LoginActivity.class.getSimpleName();
    private AwesomeValidation awesomeValidation;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextView registerLink, valid_user;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize instance variables
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.bLogin);
        registerLink = findViewById(R.id.tvRegisterHere);
        valid_user = findViewById(R.id.log_in_response);

        // initializing awesomeValidation library
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this, R.id.etUsername, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.userNameError);
        // Password regex: at least one lower case, one upper case, and one number - must be at least 8 characters long
        awesomeValidation.addValidation(this, R.id.etPassword, "^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{4,}$", R.string.passwordError);

        btnLogin.setOnClickListener(this);

        // creating a new intent to handle page redirection to register for the app when link is clicked
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);

            }
        });
    }

    /**
     * Shows progress dialog during request
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Hides progress dialog during request
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Checks to see if this user exists in the DB with the matching password
     * If it matches, calls finishLogIn()
     * If it does not match, outputs an error message to screen
     * @param username The inputted username
     * @param password The inputted password
     */
    public boolean check_login_user(final String username, final String password)
    {
        final boolean[] success = {false};

        String url = Const.URL_USER_USERNAME + "/" + username;
        // Make the request
        showProgressDialog();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        String responsePassword = "";
                        try {
                            responsePassword = response.getString("password");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // check if password matches
                       if (responsePassword.equals(password))
                        {
                            valid_user.setText("");
                            finishLogIn(username);
                            success[0] = true;
                        }
                        else
                        {
                            valid_user.setText("Username / Password incorrect");
                            success[0] = false;

                        }
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                valid_user.setText("Username / Password incorrect");
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        String tag_json_obj = "jobj_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                tag_json_obj);

        return success[0];
    }

    /**
     * Checks if the username and password fields meet our syntax requirements
     * If they do, check if the user exists in the DB
     * If they don't, tell the user what they need to change
     */
    private void validateForm() {
        // first validate the form then move ahead
        // if this becomes true that means validation is successful
        if (awesomeValidation.validate()) {
            check_login_user((etUsername.getText()).toString(), (etPassword.getText()).toString());
        }
    }

    /**
     * When the user tries to click "Log In", make sure the fields are valid
     * @param view
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bLogin) {
            validateForm();
        }
    }

    /**
     * Starts the new UserActivity and passes the username.
     * Called when the log in has valid syntax and username / password is correct.
     * @param username User's username
     */
    public void finishLogIn(String username) {
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(MainActivity.EXTRA_MESSAGE, username);
        startActivity(intent);
    }
}
