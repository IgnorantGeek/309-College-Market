package com.example.campusmarket;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.campusmarket.R.id;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DeletePost extends AppCompatActivity implements View.OnClickListener {

    private String TAG = DeletePost.class.getSimpleName();


//        // get the item's current information from the last activity
//        Intent intent = getIntent();
//        String response = intent.getStringExtra("ExtraMessage");
//
//        // create JSON Object from the response String
//        try {
//            objectToDelete = new JSONObject(response);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//    private EditText etName, etPrice, etCondition etCategory;
//    JSONObject objectToDelete;
    private Button btnJSONDelete;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);


//        etName = findViewById(R.id.etName);
//        etPrice = findViewById(R.id.etPrice);
//        etCondition = findViewById(R.id.etCondition);
//        etCategory = findViewById(R.id.etCategory);

        btnJSONDelete = findViewById(id.btnJSONDelete);
        btnJSONDelete.setOnClickListener(this);


    }


//    /**
//     * Initializes all of the edit text fields with the item's information
//     * @param obj  JSON Object representing the item
//     */
//    private void initializeEditTextFields(JSONObject obj) {
//        try {
//            etName.setText(obj.getString("name"));
//            etPrice.setText(obj.getString("price"));
//            etCondition.setText(obj.getString("condition"));
//            etCategory.setText(obj.getString("category"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * Called when the submit button is clicked.
//     * Posts the json item back to the database with the new name, price, category, and condition.
//     * Uses the old json's user and refnum fields.
//     * @param oldObject JSON object representing the item with OLD information
//     */
//    public void deleteItemInformation(JSONObject oldObject) {
//        // create the new json object
//        JSONObject toDelete = new JSONObject();
//        try {
//            toDelete.put("refnum", oldObject.getString("refnum"));
//            toDelete.put("name", (etName.getText()).toString());
//            toDelete.put("price", (etPrice.getText()).toString());
//            toDelete.put("category", (etCategory.getText()).toString());
//            toDelete.put("user", oldObject.getJSONObject("user"));
//            toDelete.put("condition", (etCondition.getText()).toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        // post the new json object
//        make_delete_request(toDelete);
//
//    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private void deleteItem()
    {
        String url = Const.URL_USER + "/" + UserActivity.loggedInUsername + "/items/delete";
        JSONObject js = new JSONObject();
        try {
            url += js.getString("refnum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showProgressDialog();
        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.DELETE, url, js,
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
//
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == id.btnJSONDelete) {
            deleteItem();
        }
    }
}

