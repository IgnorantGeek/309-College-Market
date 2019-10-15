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

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSubmitPost;
    private EditText etName, etPrice, etCondition, etCategory;
    private String TAG = NewPostActivity.class.getSimpleName();

    //private SeekBar priceBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSubmitPost = findViewById(R.id.btnSubmitPost);
        btnSubmitPost.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCondition = findViewById(R.id.etCondition);
        etCategory = findViewById(R.id.etCategory);

//        priceBar.findViewById(R.id.priceBar);
//        priceBar.setMax(200); // 200 maximum value for the Seek bar
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitPost:
                postItem();
                startActivity(new Intent(NewPostActivity.this,
                        DashboardActivity.class));
                break;
            default:
                break;

        }
    }

    public void postItem(){
        //make json object
        //make json object req
        JSONObject js = new JSONObject();
        try {
            js.put("name", (etName.getText()).toString());
            js.put("price", (etPrice.getText()).toString());
            js.put("condition", (etCondition.getText()).toString());
            js.put("category", (etCategory.getText()).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Make request for JSONObject
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, Const.URL_NEW_ITEM, js,
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
                //change names
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", (etName.getText()).toString());
                params.put("price", (etPrice.getText()).toString());
                params.put("condition", (etCondition.getText()).toString());
                params.put("category", (etCategory.getText()).toString());
//                params.put("seller", ("YEET"));
                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "jobj_req");

    }

}
