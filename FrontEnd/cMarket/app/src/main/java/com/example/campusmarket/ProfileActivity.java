package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileItemResponse;
    private String TAG = ProfileActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String  tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileItemResponse = (TextView) findViewById(R.id.profileItemResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        showSoldItemsProfile();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /*
    Displays all of the items that this user has sold by making a request
    to the url "items/seller/their_username"
     */
    private void showSoldItemsProfile() {
        String url = Const.URL_ITEM_ALL;

        showProgressDialog();
        // what we actually want, once seller thing is active :)
//        String url = Const.URL_ITEM_SELLER;
//        url += "their_username";

        // make the request
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        addItemNames(response);
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req,
                tag_json_arry);
    }

    /*
     * Parse the JSON item array so you only add the item names.
     */
    private void addItemNames(JSONArray response) {
        String message = "";
        for (int i = 0; i < response.length(); i++)
        {
            try {
                JSONObject obj = response.getJSONObject(i);
                String add = obj.getString("name");
                message += add;
                message += "\n";
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        profileItemResponse.setText(message);
    }
}
