package com.nickolay.partymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.nickolay.partymanager.data.MyConstants;
import com.nickolay.partymanager.data.MyItem;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemListActivity extends Activity {
    final String TAG = "ItemListActivity";
    ListView lv;
    ArrayList<MyItem> myItems;
    Serializable exampleObject;
    ListAdapter la;
    TextView ts;
    Intent intent;
    Intent incomeIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            myItems =(ArrayList<MyItem>)  savedInstanceState.getSerializable(MyConstants.ITEM_LIST_TAG_FOR_SAVE);
        } else {
            incomeIntent = getIntent();
            myItems = (ArrayList<MyItem>) incomeIntent.getSerializableExtra(MyConstants.ITEM_LIST);
            exampleObject = incomeIntent.getSerializableExtra(MyConstants.ITEM_CLASS);
            Log.d(TAG, "Class: " + exampleObject.getClass().getCanonicalName());
            if(myItems == null) return;
        }
        setContentView(R.layout.activity_item_list);
        ts = (TextView) findViewById(R.id.tvItemItogo);
        lv = (ListView) findViewById(R.id.lvItems);
        la = new ListAdapter(this, myItems);
        ListView lvMain = (ListView) findViewById(R.id.lvItems);
        lvMain.setAdapter(la);
        //onRestore(savedInstanceState);
        tuneNavigationMenu();
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
                intent = new Intent(this, PersonsActivity.class);
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
        MyItem newItem = (MyItem) data.getSerializableExtra(MyConstants.ITEM_TO_EDIT);
        int index = data.getIntExtra(MyConstants.ITEM_INDEX, -1);
        if(index == -1) myItems.add(newItem);
        else{
             myItems.set(index, newItem);
        }
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

    private void tuneNavigationMenu()
    {
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(R.string.item_title);
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
    }
    private void onRestore(Bundle savedInstanceState)
    {
        if(savedInstanceState != null){
            myItems =(ArrayList<MyItem>)  savedInstanceState.getSerializable(MyConstants.ITEM_LIST_TAG_FOR_SAVE);
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
            intent.putExtra(MyConstants.ITEM_INDEX, index);
        } else {
            intent.putExtra(MyConstants.ITEM_TO_EDIT, myItems.get(index));
            intent.putExtra(MyConstants.ITEM_INDEX, index);
        }
        startActivityForResult(intent, MyConstants.ITEM_ACTIVITY_REQUEST_CODE);
    }

}
