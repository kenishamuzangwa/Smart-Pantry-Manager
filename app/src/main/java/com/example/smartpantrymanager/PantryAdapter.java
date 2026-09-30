package com.example.smartpantrymanager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.smartpantrymanager.database.PantryItem;

import java.util.List;

public class PantryAdapter extends BaseAdapter {

    private Context context;
    private List<PantryItem> pantryItems;

    public PantryAdapter(Context context, List<PantryItem> pantryItems) {
        this.context = context;
        this.pantryItems = pantryItems;
    }

    @Override
    public int getCount() {
        return pantryItems.size();
    }

    @Override
    public Object getItem(int position) {
        return pantryItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            view = android.view.LayoutInflater.from(context)
                    .inflate(R.layout.item_pantry, parent, false);
        }

        android.widget.TextView textItemName =
                view.findViewById(R.id.textItemName);

        android.widget.TextView textItemDetails =
                view.findViewById(R.id.textItemDetails);

        PantryItem item = pantryItems.get(position);

        textItemName.setText(item.getName());

        textItemDetails.setText(
                "Qty: " + item.getQuantity()
                        + " - " + item.getCategory()
                        + " - Exp: " + item.getExpiryDate()
        );

        return view;
    }
}
