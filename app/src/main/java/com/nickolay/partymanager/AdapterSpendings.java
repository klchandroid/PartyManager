package com.nickolay.partymanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.spending_list_element, parent, false);
        }
        final MySpendings mySpendings = (MySpendings) getItem(position);
        ((TextView) view.findViewById(R.id.tvSpendingsName)).setText(mySpendings.getName());
        //Log.d("TestTest", position + " size: " +  person.getInCharge().size());
        final CheckBox chb =  (CheckBox) view.findViewById(R.id.chChecked);
        chb.setChecked(myIsChecked(position));
        chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               //Log.d("TestTest", position + " Name: " + mySpendings.getName() + " Checked: " + isChecked);
            }
        });
        chb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TestTest", position + " Name: " + mySpendings.getName() + " Clicked: " + chb.isChecked());
                if(chb.isChecked())  person.addInChargeElement(mySpendings);
                else person.removeInChargeElement(mySpendings);
            }
        });
        return view;
    }
    private boolean myIsChecked(int position)
    {
        //Log.d("TestTest", position + " Checked1: " + person.getInCharge().contains(MySpendings.AllSpendings.get(position)));
        return person.getInCharge().contains(MySpendings.AllSpendings.get(position));
    }

    private void testTest(int position) {
        String TAG = "TestTest";
        Set<MySpendings> ms = person.getInCharge();
        Log.d(TAG, "ms size: " + ms.size());
        List<MySpendings> as = MySpendings.AllSpendings;
        Log.d(TAG, "as size: " + as.size());
        for (MySpendings m : ms) {
            Log.d(TAG, "ms : " + m);
        }
        for (MySpendings m : as) {
            Log.d(TAG, "as : " + m + "ms contains as: " + ms.contains(m));
        }
    }
}
