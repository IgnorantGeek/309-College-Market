package com.example.campusmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Represents the dashboard adapter for items
 */
public class DashAdapter extends ArrayAdapter<DashItemsActivity> {

    private List<DashItemsActivity> ItemList;
    private Context mCtx;

    /**
     * So while creating the object of this adapter class we need to give demolist and context.
     *  The adapter is what actually puts the info into the dasboard in the format specified by the lv_rows layout.
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
        View listViewItem = inflater.inflate(R.layout.activity_lvrows, null, true);

        // pulling the text views into the adapter
        TextView name = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView price =(TextView) listViewItem.findViewById(R.id.tvPrice);
        TextView condition = (TextView) listViewItem.findViewById(R.id.tvCondition);
        TextView category = (TextView) listViewItem.findViewById(R.id.tvCategory);

        // getting the specified positions for the items
        DashItemsActivity item = ItemList.get(position);

        // setting each parameter to text editable boxed
        name.setText(item.getName());
        price.setText(item.getPrice());
        condition.setText(item.getCondition());
        category.setText(item.getCategory());

        //returning the list of items as a whole
        return listViewItem;
    }

}
