package com.example.campusmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

/**
 * Represents the dashboard adapter for items
 */
public class DashAdapter extends ArrayAdapter<DashItemsActivity> {

    private List<DashItemsActivity> ItemList;
    private Context mCtx;

    /**
     * So while creating the object of this adapter class we need to give demolist and context.
     * The adapter is what actually puts the info into the dasboard in the format specified by the lv_rows layout.
     *
     * @param ItemList
     * @param mCtx
     */
    public DashAdapter(List<DashItemsActivity> ItemList, Context mCtx) {
        super(mCtx, R.layout.activity_lvrows, ItemList);
        this.ItemList = ItemList;
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
        @SuppressLint("ViewHolder") View listViewItem = inflater.inflate(R.layout.activity_lvrows, null, true);
        listViewItem.setClickable(true);
//        listViewItem.setOnClickListener(myClickListener);

        // pulling the text views into the adapter
        TextView name = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView price = (TextView) listViewItem.findViewById(R.id.tvPrice);
        TextView condition = (TextView) listViewItem.findViewById(R.id.tvCondition);
        TextView category = (TextView) listViewItem.findViewById(R.id.tvCategory);
        TextView user = (TextView) listViewItem.findViewById(R.id.tvSeller);
        Button btnContactSeller = (Button) listViewItem.findViewById(R.id.btnContactSeller);

        // getting the specified positions for the items
        DashItemsActivity item = ItemList.get(position);

        // setting each parameter to text editable boxed
        name.setText(item.getName());
        price.setText(item.getPrice());
        condition.setText(item.getCondition());
        category.setText(item.getCategory());
        user.setText(item.getUser());

        btnContactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mCtx, WebSockets.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mCtx.startActivity(intent);
            }

        });

        //returning the list of items as a whole
        return listViewItem;

    }
}





