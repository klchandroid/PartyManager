package com.nickolay.partymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nickolay.partymanager.data.MyConstants;
import com.nickolay.partymanager.data.MyItem;
import com.nickolay.partymanager.data.MyPerson;
import com.nickolay.partymanager.data.MySpendings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    Intent intent;
    List<MySpendings> spendings = new ArrayList<>();
    List<MyPerson> persons = new ArrayList<>();
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Test();
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btItems:
                intent = new Intent(this, ItemListActivity.class);
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) spendings);
                intent.putExtra(MyConstants.ITEM_CLASS, new MySpendings());
                startActivityForResult(intent, MyConstants.SPENDINGS_LIST_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.btPersons:
                intent = new Intent(this, ItemListActivity.class);
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) persons);
                intent.putExtra(MyConstants.ITEM_CLASS, new MyPerson());
                startActivityForResult(intent, MyConstants.PERSONS_LIST_ACTIVITY_REQUEST_CODE);
                break;
            default:
                break;
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (data == null) return;
        switch (requestCode){
            case MyConstants.SPENDINGS_LIST_ACTIVITY_REQUEST_CODE:
                spendings = (ArrayList<MySpendings>) data.getSerializableExtra(MyConstants.ITEM_LIST);
                //TODO Убрать эту хрень при первой возможности
                MySpendings.AllSpendings = spendings;
                break;
            case MyConstants.PERSONS_LIST_ACTIVITY_REQUEST_CODE:
                persons = (ArrayList<MyPerson>) data.getSerializableExtra(MyConstants.ITEM_LIST);
                break;
        }
    }

    public void Test()
    {
        Log.d("TEST", String.valueOf(MyItem.class.isInstance(new MyItem())));
        Log.d("TEST", String.valueOf(MyItem.class));
    }
}
