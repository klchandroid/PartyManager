package com.nickolay.partymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.nickolay.partymanager.data.MyItem;
import com.nickolay.partymanager.data.MyPerson;
import com.nickolay.partymanager.data.MySpendings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdapterSpendings extends BaseAdapter {
    private Context ctx;
    private LayoutInflater lInflater;
    private MyPerson person;
    AdapterSpendings(Context context, MyPerson person)
    {
        ctx = context;
        this.person = person;
        lInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return MySpendings.AllSpendings.size();
    }

    @Override
    public Object getItem(int position) {
        return MySpendings.AllSpendings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return MySpendings.AllSpendings.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.spending_list_element, parent, false);
        }
        MySpendings mySpendings = (MySpendings) getItem(position);
        ((TextView) view.findViewById(R.id.tvSpendingsName)).setText(mySpendings.getName());
        ((CheckBox) view.findViewById(R.id.chChecked)).setChecked(myIsChecked(position));
        return view;
    }
    private boolean myIsChecked(int position)
    {
        Set<MySpendings> ms = person.getInCharge();
        return ms.contains(MySpendings.AllSpendings.get(position));
    }
}
