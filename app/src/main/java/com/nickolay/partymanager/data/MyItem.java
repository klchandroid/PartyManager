package com.nickolay.partymanager.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MyItem implements Serializable {
    public final String ACTIVITY_TITLE = "";
    public final String ACTIVITY_LIST_TITLE = "";
    private String id;
    private String name;
    private double summa;
    public Class myClass = MyItem.class;
    public MyItem(String name, double summa) {
        this.name = name;
        this.summa = summa;
    }

    public MyItem() {
        setId();
        name = "";
        summa = 0d;
    }

    public void setId()
    {
        id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSumma() {
        return summa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    @Override
    public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
           MyItem myItem = (MyItem) o;
            return Objects.equals(id, myItem.id);
        }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MyItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", summa=" + summa +
                '}';
    }

    public static double calculateMyItemsSumma(List<? extends MyItem> items)
    {
        if (items == null) return  0d;
        double s = 0d;
        for(MyItem i: items)
        {
            s = s + i.getSumma();
        }
        return s;
    }
}
