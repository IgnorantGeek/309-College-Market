package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private TextView msgResponse;
    private String  tag_json_arry = "jarray_req";
    private List<DashItemsActivity> itemList = new ArrayList<DashItemsActivity>();
    ArrayList<String> items;
    ArrayAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        msgResponse = findViewById(R.id.msgDashboardResponse);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        listView = findViewById(R.id.listView);
        items = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, items);
        listView.setAdapter(adapter);

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
                String name = obj.getString("name");
                message += name;
                message += "\n";
//                String price = obj.getString("price");
//                message += price;
//                message += "\n";
                // add the items to the array list

                items.add(name);
//                items.add(price);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            // this line is the key --> we use the adapter to render the item names in their own sections individually
            adapter.notifyDataSetChanged();
        }

//        msgResponse.setText(message); --> // we no longer want the whole message to display since items are not their own entities
    }
}
