package com.example.campusmarket;

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
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity that represents a page to post a new item.
 */
public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitPost;
    private EditText etName, etPrice, etCondition, etCategory;
    private String TAG = NewPostActivity.class.getSimpleName();

    /**
     * Creates instance of NewPostActivity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSubmitPost = findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(this);

        // to make a new post the fields must be editable:
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCondition = findViewById(R.id.etCondition);
        etCategory = findViewById(R.id.etCategory);
    }

    /**
     * When the user clicks to submit their post, calls postItem()
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitPost) {
            postItem();
            startActivity(new Intent(NewPostActivity.this,
                    DashboardActivity.class));
        }
    }

    /**
     * Posts the new item to the database with the information
     * that the user filled in on the page.
     * Called once they click "Post"
     */
    public void postItem(){
        // make json object
        String url = Const.URL_ITEM_NEW + "?sessid=" + UserActivity.sessionID;
        JSONObject js = new JSONObject();
        try {
            js.put("name", (etName.getText()).toString());
            js.put("price", (etPrice.getText()).toString());
            js.put("condition", (etCondition.getText()).toString());
            js.put("category", (etCategory.getText()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make post request for JSONObject using the url:
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, js,
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
             * Passing some request headers in
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
                params.put("name", (etName.getText()).toString());
                params.put("price", (etPrice.getText()).toString());
                params.put("condition", (etCondition.getText()).toString());
                params.put("category", (etCategory.getText()).toString());
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

    }

}
