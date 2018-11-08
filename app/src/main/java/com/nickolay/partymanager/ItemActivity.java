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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        tuneNavigationMenu();
        etName = (EditText)findViewById(R.id.etItemName);
        etSumma = (EditText)findViewById(R.id.etItemSumma);
        incomeIntent = getIntent();
        itemToEdit = incomeIntent.getSerializableExtra(MyConstants.ITEM_TO_EDIT);
        itemIndex = incomeIntent.getIntExtra(MyConstants.ITEM_INDEX, -1);
        if(MyPerson.class.isInstance(itemToEdit)){
            fillMyPersons((MyPerson) itemToEdit);
        }
        else if (MySpendings.class.isInstance(itemToEdit)) {
            fillMySpendings((MySpendings) itemToEdit);
        }

    }

    public void onClick(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.btItemSave:
            case R.id.ibNext:
                intent = new Intent(this, ItemActivity.class);
                Serializable editedObject = makeObject();
                intent.putExtra(MyConstants.ITEM_TO_EDIT, editedObject);
                intent.putExtra(MyConstants.ITEM_INDEX, itemIndex);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btItemCancel:
            case R.id.ibBack:
                intent = new Intent(this, ItemActivity.class);
                intent.putExtra(MyConstants.ITEM_NAME, "");
                setResult(RESULT_OK, intent);
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
        tvTitle.setText(R.string.item_edit_title);
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
        return (MyPerson) makeMyItem();
    }

    private MySpendings makeMySpendings() {
        return (MySpendings) makeMyItem();
    }
    private MyItem makeMyItem()
    {
        MyItem myItem =(MyItem) itemToEdit;
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


}
