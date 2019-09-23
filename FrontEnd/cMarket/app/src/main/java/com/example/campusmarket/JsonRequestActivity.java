package com.example.campusmarket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.campusmarket.app.MyVolley;
import com.example.campusmarket.utils.Const;

import org.json.JSONArray;

public class JsonRequestActivity extends Activity implements View.OnClickListener {

    private String TAG = JsonRequestActivity.class.getSimpleName();
    private Button btnJsonArray;
    private TextView msgResponse;
    private ProgressDialog pDialog;
    private RequestQueue queue;

    // These tags will be used to cancel the requests
    private String  tag_json_arry = "jarray_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json_request);

        btnJsonArray = (Button) findViewById(R.id.btnJsonArray);
        msgResponse = (TextView) findViewById(R.id.msgResponse);
        queue = Volley.newRequestQueue(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        btnJsonArray.setOnClickListener(this);
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
     * Making json array request
     * */
    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_JSON_ARRAY,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        msgResponse.setText(response.toString());
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
//        AppController.getInstance().addToRequestQueue(req,
//                tag_json_arry);

        // Adding request to request queue
        queue.add(req);
        //MyVolley.getInstance(this).addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJsonArray:
                makeJsonArryReq();
                break;
        }

    }


}
