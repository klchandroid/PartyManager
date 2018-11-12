package com.nickolay.partymanager.data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MyPerson extends MyItem {
    public final String ACTIVITY_TITLE = "Редактирование человека";
    public final String ACTIVITY_LIST_TITLE = "Учаснеги";
    private Set<MySpendings> inCharge = new HashSet<>();

    public MyPerson(String name, double summa, Set<MySpendings> inCharge) {
        super(name, summa);
        this.inCharge = inCharge;
    }

    public MyPerson() {
    }

    public Set<MySpendings> getInCharge() {
        return inCharge;
    }
    public  void setInCharge(Set inCharge)
    {
        this.inCharge = inCharge;
    }

    public MyItem removeInChargeElement(MySpendings element)
    {

        inCharge.remove(element);
        return element;
    }
    public int addInChargeElement(MySpendings ms)
    {
        inCharge.add(ms);
        return inCharge.size();
    }
    @Override
    public String toString() {
        return "MyPerson{" + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static  List<MyPerson> calculateItog(List<MyPerson> persons, List<MySpendings> spendings)
    {
        Map<MySpendings, Double> sPerPerson = new HashMap<>();
        List<MyPerson> itogList = new ArrayList<>();
        for(MySpendings ms: spendings)
        {
            int i = 0;
            for(MyPerson mp: persons)
            {
                if(mp.getInCharge().contains(ms))
                {
                    i++;
                }
            }
            Log.d("ITOG", ms.getName() + " " + ms.getSumma() + " " + i );
            sPerPerson.put(ms, ms.getSumma()/i);
        }
        for(MyPerson mp: persons)
        {
            double itog = 0d;
            for(MySpendings ms: mp.getInCharge())
            {
                try {
                    itog = itog + sPerPerson.get(ms);
                } catch (NullPointerException e)
                {
                    continue;
                }
            }
            MyPerson pItog = new MyPerson();
            pItog.setName(mp.getName());
            pItog.setSumma(itog);
            itogList.add(pItog);
        }
        return itogList;
    }
}
