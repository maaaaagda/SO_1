package com.company;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;

import java.util.ArrayList;

/**
 * Created by Magdalena Polak on 13.03.2016.
 */
public class Wynik
{
    String a;
    double k;

    public Wynik(String a, double k)
    {
        this.a = a;
        this.k = k;
    }
    public String toString ()
    {
        return a + " " +  k;
    }
}
