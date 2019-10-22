package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = AccountSettingsActivity.class.getSimpleName();
    private Button deleteAcc;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        String welcomeMessage = UserActivity.loggedInUsername + "'s Profile";
        TextView welcome = findViewById(R.id.tvAccSettingsWelcome);
        welcome.setText(welcomeMessage);
        deleteAcc = findViewById(R.id.btnDeleteAccount);
        deleteAcc.setOnClickListener(this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Finds the ID of the user with the given username.
     * Once it finds it, it calls deleteAccount() to delete the user.
     * @param username the username of the user to be deleted
     */
    public void findByID(String username)
    {
        String url = Const.URL_USER_USERNAME + "/" + username;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " found");
                        hideProgressDialog();
                        // we found the user, now delete it from the DB
                        try {
                            deleteAccount(response.getString("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
    }

    /**
     * Deletes the account of the user with the id
     * @param ID the ID of the user to delete
     */
    public void deleteAccount(String ID)
    {
        String url = Const.URL_USER_DELETE + "/" + ID;
        showProgressDialog();
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " deleted");
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

        // Account is delete, go back to main page
        startActivity(new Intent(AccountSettingsActivity.this,
                MainActivity.class));
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnDeleteAccount) {
            findByID(UserActivity.loggedInUsername);
        }

    }
}
