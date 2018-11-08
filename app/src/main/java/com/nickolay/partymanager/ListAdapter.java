package com.nickolay.partymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nickolay.partymanager.data.MyItem;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<MyItem> objects;
    ListAdapter(Context context, ArrayList<MyItem> things)
    {
        ctx = context;
        objects = things;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return objects.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_element, parent, false);
        }
        MyItem myItem = getMyItem(position);
        ((TextView) view.findViewById(R.id.tvItemName)).setText(myItem.getName());
        ((TextView) view.findViewById(R.id.tvItemSumma)).setText(Double.toString(myItem.getSumma()));
        return view;
    }
    private MyItem getMyItem(int position){
        return ((MyItem) getItem(position));
    }
}
