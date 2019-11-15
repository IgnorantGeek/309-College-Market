package com.example.campusmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.campusmarket.app.AppController;
import com.example.campusmarket.utils.Const;

import java.util.List;

import static com.example.campusmarket.app.AppController.TAG;

public class CartAdapter extends ArrayAdapter<CartItemsActivity> implements View.OnClickListener {

    private List<CartItemsActivity> CartList;
    private Context mCtx;
    private Button btnRemove;
    private Button btnClear;
    private String refnum;



    /**
     * So while creating the object of this adapter class we need to give demolist and context.
     * The adapter is what actually puts the info into the dashboard in the format specified by the lv_rows layout.
     *
     * @param CartList
     * @param mCtx
     */
    public CartAdapter(List<CartItemsActivity> CartList, Context mCtx) {
        super(mCtx, R.layout.activity_cartrows, CartList);
        this.CartList = CartList;
        this.mCtx = mCtx;
    }

    /**
     * Returns the list of items
     *
     * @param position
     * @param convertView
     * @param parent
     * @return the View
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // aids in specifically placing items
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        // creating a view with our xml layout
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.activity_cartrows, null, true);
        listViewItem.setClickable(true);
//        listViewItem.setOnClickListener(myClickListener);

        // pulling the text views into the adapter
        TextView name = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView price = (TextView) listViewItem.findViewById(R.id.tvCategory);
//        TextView condition = (TextView) listViewItem.findViewById(R.id.tvCondition);
//        TextView category = (TextView) listViewItem.findViewById(R.id.tvCategory);
        TextView user = (TextView) listViewItem.findViewById(R.id.tvSeller);
        btnRemove = (Button) listViewItem.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(this);
        btnClear = (Button) listViewItem.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        // getting the specified positions for the items
        CartItemsActivity item = CartList.get(position);

        // setting each parameter to text editable boxed
        name.setText(item.getName());
        price.setText(item.getPrice());
        user.setText(item.getUser());
        refnum = item.getRefnum();


//        btnContactSeller.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(mCtx, WebSockets.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mCtx.startActivity(intent);
//            }
//        });


        //returning the list of items as a whole
        return listViewItem;

    }

    public void removeItem() {
        // make json object
        String url = Const.URL_CART_DELETE
                + "/" + refnum + "?sessid=" + UserActivity.sessionID;

        // Make post request for JSONObject using the url:
        StringRequest stringReq = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {


        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringReq, "jobj_req");

    }

    public void clearItems() {
        // make json object
        String url = Const.URL_CART_CLEAR
                + "?sessid=" + UserActivity.sessionID;

        // Make post request for JSONObject using the url:
        StringRequest stringReq = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString() + " i am queen");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {


        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringReq, "jobj_req");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRemove:
                removeItem();
                Intent intent = new Intent(mCtx, CartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
                break;
            case R.id.btnClear:
                clearItems();
                Intent intent2 = new Intent(mCtx, DashboardActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent2);
                break;
            default:
                break;

        }
    }
}

