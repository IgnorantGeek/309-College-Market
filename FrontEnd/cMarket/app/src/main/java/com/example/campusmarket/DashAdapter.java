package com.example.campusmarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DashAdapter extends ArrayAdapter<DashItemsActivity> {

    private List<DashItemsActivity> ItemList;


    private Context mCtx;

    //so while creating the object of this adapter class we need to give demolist and context

    public DashAdapter(List<DashItemsActivity> ItemList, Context mCtx) {
        super(mCtx, R.layout.activity_lvrows, ItemList);
        this.ItemList = ItemList;
        this.mCtx = mCtx;
    }

    //
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.activity_lvrows, null, true);

        //getting text views
        TextView name = (TextView) listViewItem.findViewById(R.id.tvName);
        TextView price =(TextView) listViewItem.findViewById(R.id.tvPrice);
        TextView condition = (TextView) listViewItem.findViewById(R.id.tvCondition);
        TextView category = (TextView) listViewItem.findViewById(R.id.tvCategory);

        //Getting the hero for the specified position
        DashItemsActivity item = ItemList.get(position);

        //setting hero values to textviews
        name.setText(item.getName()); //item = demo
        price.setText(item.getPrice());
        condition.setText(item.getCondition()); //item = demo
        category.setText(item.getCategory()); //item = demo



        //returning the listitem
        return listViewItem;
    }

}
