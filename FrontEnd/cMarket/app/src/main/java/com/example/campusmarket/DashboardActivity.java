package com.example.campusmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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

/**
 * Activity that represents the market dashboard.
 * You can view all items for sale in the market on this page
 */
public class DashboardActivity extends AppCompatActivity {
    private String TAG = DashboardActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private String  tag_json_arry = "jarray_req";
    ListView listView;
    Activity activity;
    List<DashItemsActivity> ItemList;

    /**
     * Creates this instance of Dashboard
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        listView = findViewById(R.id.listView);
        ItemList = new ArrayList<>();

        makeJsonArryReq();

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
                DashItemsActivity item = new DashItemsActivity(demoObject.getString("name"),
                        demoObject.getString("price"), demoObject.getString("condition"),
                        demoObject.getString("category"), demoObject.getString("user") );
                ItemList.add(item); // adding all of these new items for display

                // setting up new adapter that will place items accordingly
                final DashAdapter adapter = new DashAdapter(ItemList, getApplicationContext());

                // actually calling the adapter
                listView.setAdapter(adapter);

//                Searchable dash feature:
//                etSearch.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                        DashboardActivity.this.arrayadapter.getFilter().filter(charSequence);
//                        adapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//                        // don't need to change anything here for now
//
//                    }
//                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // this line is the key --> we use the adapter to render the item names in their own sections individually
//            adapter.notifyDataSetChanged();
        }
//        msgResponse.setText(message); --> // we no longer want the whole message to display since items are not their own entities
    }


}
