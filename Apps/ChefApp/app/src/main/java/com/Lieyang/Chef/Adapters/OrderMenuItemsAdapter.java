package com.Lieyang.Chef.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.Lieyang.Chef.Models.MenuItem;
import com.Lieyang.Chef.R;

import java.util.List;

public class OrderMenuItemsAdapter extends ArrayAdapter<MenuItem> {
    public OrderMenuItemsAdapter(Context context, List<MenuItem> MenuItems) {
        super(context, 0, MenuItems);
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {

        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.view_order_menuitems, parent, false);
        }

        MenuItem currentMenuItem = getItem(position);

        TextView orderId = itemView.findViewById(R.id.order_menuItem_name);
        TextView guestId = itemView.findViewById(R.id.order_menuItem_price);


        orderId.setText(currentMenuItem.mName);
        guestId.setText(currentMenuItem.mPrice);

        return itemView;
    }
}
