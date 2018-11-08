package com.nickolay.partymanager.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MyPerson extends MyItem {
    private Set<MySpendings> inCharge = new HashSet<>();

    public MyPerson(String name, double summa, Set<MySpendings> inCharge) {
        super(name, summa);
        this.inCharge = inCharge;
    }

    public MyPerson() {
    }

    public Set<MySpendings> getInCharge() {
        return Collections.unmodifiableSet(inCharge);
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
}
