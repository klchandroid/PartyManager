package com.nickolay.partymanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MyPerson extends MyItem {
    private ArrayList<MyItem> inCharge;

    public MyPerson(String name, double summa, ArrayList<MyItem> inCharge) {
        super(name, summa);
        this.inCharge = inCharge;
    }

    public MyPerson() {
    }

    public List<MyItem> getInCharge() {
        return Collections.unmodifiableList((List)inCharge);
    }

    public MyItem removeInChargeElement(int i)
    {
        MyItem rem = inCharge.get(i);
        inCharge.remove(i);
        return rem;
    }
    public int addInChargeElement(MyItem mi)
    {
        inCharge.add(mi);
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
}
