package com.nickolay.partymanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.nickolay.partymanager.data.MyConstants;
import com.nickolay.partymanager.data.MyItem;
import com.nickolay.partymanager.data.MyPerson;
import com.nickolay.partymanager.data.MySpendings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ItemActivity extends Activity {
    public static final String TAG = "ItemActivity";
    public static final int requestCode = 1;
    Intent incomeIntent;
    Serializable itemToEdit;
    int itemIndex;
    EditText etName;
    EditText etSumma;
    ListView lv;
    AdapterSpendings as;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        etName = (EditText)findViewById(R.id.etItemName);
        etSumma = (EditText)findViewById(R.id.etItemSumma);
        incomeIntent = getIntent();
        itemToEdit = incomeIntent.getSerializableExtra(MyConstants.ITEM_TO_EDIT);
        itemIndex = incomeIntent.getIntExtra(MyConstants.ITEM_INDEX, -1);
        title = incomeIntent.getStringExtra(MyConstants.ACTIVITY_TITLE);
        tuneNavigationMenu();
        fillData();
    }

    public void onClick(View view)
    {
        Intent intent;
        intent = new Intent(this, ItemActivity.class);
        switch (view.getId())
        {
            case R.id.btItemSave:
            case R.id.ibNext:
                Serializable editedObject = makeObject();
                intent.putExtra(MyConstants.ITEM_TO_EDIT, editedObject);
                intent.putExtra(MyConstants.ITEM_INDEX, itemIndex);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btItemCancel:
            case R.id.ibBack:
                setResult(RESULT_CANCELED, intent);
                finish();
                break;
            default:
                break;
        }
    }
    private void tuneNavigationMenu()
    {
        ImageButton ibNew = (ImageButton) findViewById(R.id.ibNew);
        ibNew.setVisibility(View.INVISIBLE);
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }
    private Serializable makeObject()
    {
        Log.d(TAG, " makeObject " + itemToEdit.getClass() + " " + (MyItem.class.isInstance(itemToEdit)));
        //TODO Сделать MyItem абстрактным классом
        if(itemToEdit instanceof MyPerson){
            return makeMyPersons();
        }
        else if(MySpendings.class.isInstance(itemToEdit)){
            return makeMySpendings();
        }
        return null;
    }

    private MyPerson makeMyPersons() {
        MyPerson mp = (MyPerson) makeMyItem();
        Log.d("TestTest", "Size: " + String.valueOf(((MyPerson)itemToEdit).getInCharge().size()));
        mp.setInCharge(new HashSet<MySpendings>(((MyPerson) itemToEdit).getInCharge()));
        Log.d("TestTest", "Size2: " + String.valueOf(mp.getInCharge().size()));
        return mp;
    }

    private MySpendings makeMySpendings() {
        return (MySpendings) makeMyItem();
    }
    private MyItem makeMyItem()
    {
        MyItem myItem =(MyItem) itemToEdit;
        myItem.setId();
        myItem.setName(etName.getText().toString());
        double summa = 0d;
        try{
            myItem.setSumma(Double.parseDouble(etSumma.getText().toString()));
        } catch(IllegalArgumentException e){}
        return myItem;
    }
    private void fillMyItem(MyItem myItem)
    {
        etName.setText(myItem.getName());
        etSumma.setText(Double.toString(myItem.getSumma()));
    }

    private void fillMySpendings(MySpendings itemToEdit) {
        fillMyItem(itemToEdit);
    }
    private void fillMyPersons(MyPerson itemToEdit) {
        fillMyItem(itemToEdit);
        lv = (ListView) findViewById(R.id.lvSpendings);
        as = new AdapterSpendings(this, itemToEdit);
        lv.setAdapter(as);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable(MyConstants.ITEM_TO_EDIT, itemToEdit);
        savedInstanceState.putInt(MyConstants.ITEM_INDEX, itemIndex);
        savedInstanceState.putString(MyConstants.ACTIVITY_TITLE, title);
        savedInstanceState.putString("etName", String.valueOf(etName.getText()));
        savedInstanceState.putString("etSumma", String.valueOf(etSumma.getText()));
        makeObject();
        Log.d(TAG, String.valueOf(etName.getText()));
        Log.d(TAG, String.valueOf(etSumma.getText()));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        itemToEdit = savedInstanceState.getSerializable(MyConstants.ITEM_TO_EDIT);
        itemIndex = savedInstanceState.getInt(MyConstants.ITEM_INDEX, -1);
        title = savedInstanceState.getString(MyConstants.ACTIVITY_TITLE);
        etName.setText(savedInstanceState.getString("etName"));
        etSumma.setText(savedInstanceState.getString("etSumma"));
        Log.d(TAG, String.valueOf(etName.getText()));
        Log.d(TAG, String.valueOf(etSumma.getText()));
        tuneNavigationMenu();
        fillData();
    }
    private void fillData()
    {
        if(MyPerson.class.isInstance(itemToEdit)){
            fillMyPersons((MyPerson) itemToEdit);
        }
        else if (MySpendings.class.isInstance(itemToEdit)) {
            fillMySpendings((MySpendings) itemToEdit);
        }

    }
}
