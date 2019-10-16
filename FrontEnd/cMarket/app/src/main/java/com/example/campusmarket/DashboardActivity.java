package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView msgResponse;
    private String  tag_json_arry = "jarray_req";
    private Button btnProfile, btnSearchSpecific;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnProfile = (Button) findViewById(R.id.btnViewProfile);
        btnProfile.setOnClickListener(this);
        btnSearchSpecific = (Button) findViewById(R.id.btnSearchSpecific);
        btnSearchSpecific.setOnClickListener(this);


        msgResponse = (TextView) findViewById(R.id.msgDashboardResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        makeJsonArryReq();
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
        JsonArrayRequest req = new JsonArrayRequest(Const.URL_ITEM_ALL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        addItemNames(response);
//                        msgResponse.setText(response.toString());
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
        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    /*
    * Parse the JSON item array so you only add the item names.
     */
    private void addItemNames(JSONArray response) {
        String message = "";//msgResponse.toString();
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
        msgResponse.setText(message);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearchSpecific:
                startActivity(new Intent(DashboardActivity.this,
                        DropDownActivity.class));
                break;
            case R.id.btnViewProfile:
                startActivity(new Intent(DashboardActivity.this,
                        ProfileActivity.class));
                break;
            default:
                break;
        }
    }
}
