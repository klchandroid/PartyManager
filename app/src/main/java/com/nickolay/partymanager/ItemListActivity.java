package com.nickolay.partymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nickolay.partymanager.data.MyConstants;
import com.nickolay.partymanager.data.MyItem;
import com.nickolay.partymanager.data.MyPerson;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemListActivity extends Activity {
    final String TAG = "ItemListActivity";
    ListView lv;
    ArrayList<MyItem> myItems;
    Serializable exampleObject;
    AdapterListItems la;
    TextView ts;
    Intent intent;
    Intent incomeIntent;
    private String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            myItems =(ArrayList<MyItem>)  savedInstanceState.getSerializable(MyConstants.ITEM_LIST_TAG_FOR_SAVE);
            exampleObject = (MyItem) savedInstanceState.getSerializable(MyConstants.ITEM_CLASS);
        } else {
            incomeIntent = getIntent();
            myItems = (ArrayList<MyItem>) incomeIntent.getSerializableExtra(MyConstants.ITEM_LIST);
            exampleObject = incomeIntent.getSerializableExtra(MyConstants.ITEM_CLASS);
            title = incomeIntent.getStringExtra(MyConstants.ACTIVITY_TITLE);
            Log.d(TAG, "Class: " + exampleObject.getClass().getCanonicalName());
            if(myItems == null) return;
        }
        setContentView(R.layout.activity_item_list);
        ts = (TextView) findViewById(R.id.tvItemItogo);
        lv = (ListView) findViewById(R.id.lvItems);
        la = new AdapterListItems(this, myItems);
        ListView lvMain = (ListView) findViewById(R.id.lvItems);
        lvMain.setAdapter(la);
        //onRestore(savedInstanceState);
        tuneNavigationMenu(title);
        registerForContextMenu(lvMain);
        ifDataChanged();
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.ibNew:
                /*intent = new Intent(this, ItemActivity.class);
                startActivityForResult(intent, MyConstants.ITEM_ACTIVITY_REQUEST_CODE);*/
                editItem(-1);
                break;
            case R.id.ibNext:
            case R.id.ibBack:
                intent = new Intent(this, MainActivity.class);
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) myItems);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("TestTest", "Result");
        if(resultCode != RESULT_OK) return;
        MyItem newItem = (MyItem) data.getSerializableExtra(MyConstants.ITEM_TO_EDIT);
        int index = data.getIntExtra(MyConstants.ITEM_INDEX, -1);
        if(index == -1) myItems.add(newItem);
        else{
             myItems.set(index, newItem);
        }
    //    Log.d("TestTest", "Size3: " + String.valueOf(((MyPerson)newItem).getInCharge().size()));
        ifDataChanged();
    }
    public void fillData() throws InterruptedException //test
    {
        for(int i = 0; i < 100; i++)
        {
            MyItem mi = new MyItem("nick", i);
            myItems.add(mi);
            //la.notifyDataSetChanged();
            ifDataChanged();
         //   Thread.sleep(1000);
        }
    }
    private void ifDataChanged()
    {
        ts.setText(Double.toString(MyItem.calculateMyItemsSumma(myItems)));
        la.notifyDataSetChanged();
    }

    private void tuneNavigationMenu(String title)
    {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu");
        menu.add(0, 1, 0, R.string.delete_item);
        menu.add(0, 2, 0, R.string.edit_item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d(TAG, "onContextItemSelected");
        if (item.getItemId() == 1) {
            // получаем инфу о пункте списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // удаляем Map из коллекции, используя позицию пункта в списке
            myItems.remove(acmi.position);
            // уведомляем, что данные изменились
            ifDataChanged();
            return true;
        }
        else if(item.getItemId() == 2)
        {
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            editItem(acmi.position);
        }
        return super.onContextItemSelected(item);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable(MyConstants.ITEM_LIST_TAG_FOR_SAVE, myItems);
        savedInstanceState.putSerializable(MyConstants.ITEM_CLASS, exampleObject);
        savedInstanceState.putString(MyConstants.ACTIVITY_TITLE, title);
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        if(savedInstanceState != null){
            myItems =(ArrayList<MyItem>)  savedInstanceState.getSerializable(MyConstants.ITEM_LIST_TAG_FOR_SAVE);
            title = savedInstanceState.getString(MyConstants.ACTIVITY_TITLE);
            tuneNavigationMenu(title);
            ifDataChanged();
        } else {
            try {
                fillData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void editItem(int index)
    {
        intent = new Intent(this, ItemActivity.class);
        if(index == -1) {
            intent.putExtra(MyConstants.ITEM_TO_EDIT, exampleObject);

        } else {
            intent.putExtra(MyConstants.ITEM_TO_EDIT, myItems.get(index));
        }
        intent.putExtra(MyConstants.ITEM_INDEX, index);
        intent.putExtra(MyConstants.ACTIVITY_TITLE, ((MyItem)exampleObject).ACTIVITY_TITLE);
        startActivityForResult(intent, MyConstants.ITEM_ACTIVITY_REQUEST_CODE);
    }

}
//TODO: сохранять exampleObject