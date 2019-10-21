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

public class EditPost extends AppCompatActivity implements View.OnClickListener {

    private String TAG = EditPost.class.getSimpleName();
    EditText etName, etPrice, etCondition, etCategory;
    JSONObject objectToEdit;
    Button btnSubmit;
    Button btnDelete;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


        // get the item's current information from the last activity
        Intent intent = getIntent();
        String response = intent.getStringExtra("ExtraMessage");

        // create JSON Object from the response String
        try {
            objectToEdit = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // initialize text box variables
        etName = findViewById(R.id.etEditName);
        etPrice = findViewById(R.id.etEditPrice);
        etCondition = findViewById(R.id.etEditCondition);
        etCategory = findViewById(R.id.etEditCategory);
        initializeEditTextFields(objectToEdit);

        // make submit button clickable
        btnSubmit = findViewById(R.id.btnEditSubmit);
        btnSubmit.setOnClickListener(this);
        btnDelete = findViewById(R.id.btnEditDelete);
        btnDelete.setOnClickListener(this);
    }

    /**
     * Initializes all of the edit text fields with the item's information
     * @param obj  JSON Object representing the item
     */
    private void initializeEditTextFields(JSONObject obj) {
        try {
            etName.setText(obj.getString("name"));
            etPrice.setText(obj.getString("price"));
            etCondition.setText(obj.getString("condition"));
            etCategory.setText(obj.getString("category"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the submit button is clicked.
     * Posts the json item back to the database with the new name, price, category, and condition.
     * Uses the old json's user and refnum fields.
     * @param oldObject JSON object representing the item with OLD information
     */
    public void updateItemInformation(JSONObject oldObject) {
        // create the new json object
        JSONObject toAdd = new JSONObject();
        try {
            toAdd.put("refnum", oldObject.getString("refnum"));
            toAdd.put("name", (etName.getText()).toString());
            toAdd.put("price", (etPrice.getText()).toString());
            toAdd.put("category", (etCategory.getText()).toString());
            toAdd.put("user", oldObject.getJSONObject("user"));
            toAdd.put("condition", (etCondition.getText()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // post the new json object
        make_update_request(toAdd);

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
     * Puts the item to the new database
     * @param js JSON Object representing the item to post
     */
    private void make_update_request(final JSONObject js)
    {
        String url = Const.URL_USER + "/" + UserActivity.loggedInUsername + "/items/";
        try {
            url += js.getString("refnum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showProgressDialog();
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.PUT, url, js,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + " posted");
                        hideProgressDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hideProgressDialog();
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
                    params.put("refnum", js.getString("refnum"));
                    params.put("name", js.getString("name"));
                    params.put("price", js.getString("price"));
                    params.put("category", js.getString("category"));
                    params.put("user", js.getString("user"));
                    params.put("condition", js.getString("condition"));
                }  catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
    }

    private void deletePost()
    {
        String url = Const.URL_USER + "/" + UserActivity.loggedInUsername + "/items/";
        JSONObject js = new JSONObject();

        try {
            js.put("refnum", objectToEdit.getString("refnum"));
            js.put("name", objectToEdit.getString("name"));
            js.put("price", objectToEdit.getString("price"));
            js.put("category", objectToEdit.getString("category"));
            js.put("user", objectToEdit.getJSONObject("user"));
            js.put("condition", objectToEdit.getString("condition"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            url += js.getString("refnum") + "/delete";
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                try {
//                    params.put("refnum", js.getString("refnum"));
//                    params.put("name", js.getString("name"));
//                    params.put("price", js.getString("price"));
//                    params.put("category", js.getString("category"));
//                    params.put("user", js.getString("user"));
//                    params.put("condition", js.getString("condition"));
//                }  catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                return params;
//            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEditSubmit:
                updateItemInformation(objectToEdit);
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.btnEditDelete:
                deletePost();
                Intent intent2 = new Intent(this, ProfileActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
