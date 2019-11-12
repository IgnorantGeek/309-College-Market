package com.example.campusmarket;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONArray;
import org.json.JSONObject;

public class CartActivity extends AppCompatActivity {

    private String TAG = AccountSettingsActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String  tag_json_arry = "jarray_req";

    /**
     * Creates this instance of Cart
     *
     * @param savedInstanceStates
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

    }
    /**
     * Shows the progress dialog while it's loading
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * Hides the progress dialog
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    /**
     * Making json array request post
     * */
    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_ITEM_ALL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        // calling add names function that handles displaying new items to the dashboard
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

    /**
     * Parse the JSON item array so you only add the item names.
     * @param response
     */
    private void addItemNames(JSONArray response) {
        for (int i = 0; i < response.length(); i++)
        {
            try {

                // declaring new json object
                JSONObject demoObject = response.getJSONObject(i);
                // declaring what parameters will be added
                String s = demoObject.getString("user");
                JSONObject seller = new JSONObject(s);
                DashItemsActivity item = new DashItemsActivity(demoObject.getString("name"),
                        demoObject.getString("price"), demoObject.getString("condition"),
                        demoObject.getString("category"), seller.getString("username") );
                ItemList.add(item); // adding all of these new items for display



                // setting up new adapter that will place items accordingly
                final DashAdapter adapter = new DashAdapter(ItemList, getApplicationContext());

            }
