package com.nickolay.partymanager;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    List<MyPerson> itog = new ArrayList<>();
    Button btSpendings;
    Button btPersons;
    Button btItog;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btSpendings = (Button) findViewById(R.id.btItems);
        btPersons = (Button) findViewById(R.id.btPersons);
        btItog = (Button) findViewById(R.id.btItog);
        btItog.setEnabled(false);
        Test();
        updateButtons();
    }
    public void onClick(View view)
    {
        intent = new Intent(this, ItemListActivity.class);
        switch (view.getId())
        {
            case R.id.btItems:
                MySpendings ms = new MySpendings();
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) spendings);
                intent.putExtra(MyConstants.ITEM_CLASS, ms);
                intent.putExtra(MyConstants.ACTIVITY_TITLE, ms.ACTIVITY_LIST_TITLE);
                startActivityForResult(intent, MyConstants.SPENDINGS_LIST_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.btPersons:
                MyPerson mp = new MyPerson();
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) persons);
                intent.putExtra(MyConstants.ITEM_CLASS, mp);
                intent.putExtra(MyConstants.ACTIVITY_TITLE, mp.ACTIVITY_LIST_TITLE);
                startActivityForResult(intent, MyConstants.PERSONS_LIST_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.btItog:
                MyPerson mp2 = new MyPerson();
                intent.putExtra(MyConstants.ITEM_LIST, (Serializable) itog);
                intent.putExtra(MyConstants.ITEM_CLASS, mp2);
                intent.putExtra(MyConstants.ACTIVITY_TITLE, "Итого");
                startActivityForResult(intent, MyConstants.ITOGO_LIST_ACTIVITY_REQUEST_CODE);
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
                //TODO Заголовки окон!
                persons = (ArrayList<MyPerson>) data.getSerializableExtra(MyConstants.ITEM_LIST);
                break;
        }
        updateButtons();
    }

    private void updateButtons()
    {
        double diff = (MyPerson.calculateMyItemsSumma(spendings ) - MySpendings.calculateMyItemsSumma(persons ));
        btSpendings.setText(getResources().getText(R.string.item_title) + " ( " + getResources().getText(R.string.item_title) + ": " + spendings.size() + ", " + getResources().getText(R.string.summa) + ": " + MySpendings.calculateMyItemsSumma(spendings ) + ")");
        btPersons.setText(getResources().getText(R.string.person_title) + " ( " + getResources().getText(R.string.person_title) + ": " + persons.size() + ", " + getResources().getText(R.string.summa) + ": " + MyPerson.calculateMyItemsSumma(persons ) + ")");
        btItog.setText(getResources().getText(R.string.itog_title) + " ( " +  diff + " ) ");
        itog = MyPerson.calculateItog(persons, spendings);
        Log.d("ITOG", String.valueOf(itog.size()));
        btItog.setEnabled(diff < 0.001d);
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putSerializable(MyConstants.PERSONS, (Serializable) persons);
        savedInstanceState.putSerializable(MyConstants.SPENDINGS, (Serializable) spendings);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        spendings = (List<MySpendings>) savedInstanceState.getSerializable(MyConstants.SPENDINGS);
        MySpendings.AllSpendings = spendings;
        persons = (List<MyPerson>) savedInstanceState.getSerializable(MyConstants.PERSONS);
        updateButtons();
    }

    public void Test()
    {
        MySpendings ms1 = new MySpendings();
        ms1.setName("q");
        ms1.setSumma(1000);
        MySpendings ms2 = new MySpendings();
        ms2.setName("w");
        ms2.setSumma(2000);
        MySpendings ms3 = new MySpendings();
        ms3.setName("r");
        ms3.setSumma(3000);
        spendings.add(ms1);
        spendings.add(ms2);
        spendings.add(ms3);
        MyPerson mp1 = new MyPerson();
        mp1.setName("e");
        mp1.setSumma(1500);
        mp1.addInChargeElement(ms3);
        persons.add(mp1);
        MySpendings.AllSpendings = spendings;

    }
}
