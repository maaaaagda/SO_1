package com.company;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Magdalena Polak on 29.02.2016.
 */
public class Procesy {
    ArrayList<Proces> procesy;

    public Procesy(int liczba) {
        procesy = new ArrayList<Proces>();
   /*     procesy.add(new Proces(0, 0, 50, 50, 0));
        procesy.add(new Proces(1, 0, 15, 15, 0));
        procesy.add(new Proces(2, 16, 6, 6, 0));
         procesy.add(new Proces(2, 60, 10, 10, 0));
        procesy.add(new Proces(2, 61, 2, 2, 0));

        procesy = new ArrayList<Proces>();
        procesy.add(new Proces(0, 0, 10));
        procesy.add(new Proces(1, 4, 5));
        procesy.add(new Proces(2, 5, 2));
      */  for(int i = 0; i<liczba; i++)
        {
            Random r = new Random();

            int n = i;
            int d =  r.nextInt(100);
            int m = 1 + r.nextInt(100);
            procesy.add(new Proces(n, d, m, m, 0));
        }
    }


    public void wyswietl() {

        for (Proces p : procesy)
            System.out.print(p + "\n");

    }

    public void wyswietlS() {
        Collections.sort(procesy, Proces.Comparators.ComparatorMomentWejscia);
        for (Proces p : procesy)
            System.out.print(p + " \n");
    }


    public double FCFS() {
        ArrayList<Proces> procesy1 = new ArrayList<Proces>(procesy.size());
     /*   for (Proces p : procesy) {
            procesy1.add(new Proces(p));
        } */
        for (int i = 0; i < procesy.size(); i++) {
            procesy1.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy1, Proces.Comparators.ComparatorMomentWejscia);
        double czasCalkowityOczekiwania = 0;
        int czasZakonczeniaCalkowity = (procesy1.get(0)).getDlugoscFazy();
        for (int i = 1; i < procesy1.size(); i++) {
            if (czasZakonczeniaCalkowity <= procesy1.get(i).getMomentWejscia())
                czasZakonczeniaCalkowity = procesy1.get(i).getMomentWejscia() + (procesy1.get(i)).getDlugoscFazy();

            else {
                czasCalkowityOczekiwania += (czasZakonczeniaCalkowity - procesy1.get(i).getMomentWejscia());
                czasZakonczeniaCalkowity += (procesy1.get(i)).getDlugoscFazy();
            }

        }
        return czasCalkowityOczekiwania / procesy.size();
    }

    public double SJF() {
        Collections.sort(procesy, Proces.Comparators.ComparatorMomentWejscia);

        double czasCalkowityOczekiwania = 0;
        int czasZakonczeniaCalkowity = (procesy.get(0)).getDlugoscFazy();
        for (int i = 1; i < procesy.size(); i++) {
             if (czasZakonczeniaCalkowity <= procesy.get(i).getMomentWejscia())
                czasZakonczeniaCalkowity = procesy.get(i).getMomentWejscia() + (procesy.get(i)).getDlugoscFazy();

            else if (czasZakonczeniaCalkowity > procesy.get(i).getMomentWejscia()) {
                int a = i;
                ArrayList<Proces> kolejka = new ArrayList<Proces>();
                while (a < procesy.size() && czasZakonczeniaCalkowity > (procesy.get(a)).getMomentWejscia())

                {
                    kolejka.add(procesy.get(a));
                    a++;
                }


                Collections.sort(kolejka, Proces.Comparators.ComparatorDlugoscFazy);
                int d = i;
                for (int p = 0; p < kolejka.size(); p++) {

                    procesy.set(d, kolejka.get(p));
                    d++;
                }
                czasCalkowityOczekiwania += (czasZakonczeniaCalkowity - procesy.get(i).getMomentWejscia());
                czasZakonczeniaCalkowity += (procesy.get(i)).getDlugoscFazy();
            }

        }
        return czasCalkowityOczekiwania / procesy.size();
    }

    public double SJF2() {
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        ArrayList<Proces> procesy2 = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();
        for (int i = 0; i < procesy.size(); i++) {
            procesy2.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy2, Proces.Comparators.ComparatorMomentWejscia);

        do {
            for (int i = 0; i < procesy2.size(); i++)

            {
                if (czasAktualny == (procesy2.get(i)).getMomentWejscia()) {

                    kolejka.add(new Proces(0, 0, 0, (procesy2.get(i)).getCzasPozostaly(), 0));
                }


            }
            if (kolejka.size() == 0) {
                Collections.sort(kolejka, Proces.Comparators.ComparatorCzasPozostaly);
            } else if ((kolejka.get(0)).getCzasPozostaly() == 0) {
                calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania();
                kolejka.remove(0);
                Collections.sort(kolejka, Proces.Comparators.ComparatorCzasPozostaly);

            }
            czasAktualny++;

            if (kolejka.size() != 0) {
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - 1);
                for (int j = 1; j < kolejka.size(); j++) {
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + 1);
                }

            }
        }
        while (czasAktualny != 100000);
        return calkowityCzasOczekiwania / procesy2.size();

    }

    public double SRTF() {
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        int licznikWywlaszczen = 0;
        ArrayList<Proces> pomocnicza = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();
        for (int i = 0; i < procesy.size(); i++) {
            pomocnicza.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(pomocnicza, Proces.Comparators.ComparatorMomentWejscia);

        do {
            for (int i = 0; i < pomocnicza.size(); i++) {
                if (czasAktualny == (pomocnicza.get(i)).getMomentWejscia()) {
                    if (kolejka.size() != 0 && ((kolejka.get(0)).getCzasPozostaly() < (pomocnicza.get(i)).getCzasPozostaly())) {
                        licznikWywlaszczen++;
                    }
                    kolejka.add(new Proces(0, 0, 0, (pomocnicza.get(i)).getCzasPozostaly(), 0));
                    Collections.sort(kolejka, Proces.Comparators.ComparatorCzasPozostaly);


                }


            }
            czasAktualny++;

            if (kolejka.size() != 0) {
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - 1);


                for (int j = 1; j < kolejka.size(); j++) {
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + 1);
                }
                if ((kolejka.get(0)).getCzasPozostaly() == 0) {
                    calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania();
                    kolejka.remove(0);
                }

            }
        }
        while (czasAktualny != 100000);
        System.out.println("Ilosc wywlaszczen: " + licznikWywlaszczen / 2);
        return calkowityCzasOczekiwania / pomocnicza.size();
    }


    public double RR(int k) {
        int kwant = 0;
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        ArrayList<Proces> procesy4 = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();
      //  ArrayList<Proces> pomocnicza = new ArrayList<Proces>();

        for (int i = 0; i < procesy.size(); i++) {
            procesy4.add(new Proces((procesy.get(i)).getNumerProcesu(),
                    (procesy.get(i)).getMomentWejscia(), (procesy.get(i)).getDlugoscFazy(), (procesy.get(i)).getDlugoscFazy(), 0));
        }
        Collections.sort(procesy4, Proces.Comparators.ComparatorMomentWejscia);

        do {

            for (int i = 0; i < procesy4.size(); i++)

            {
                if (czasAktualny == (procesy4.get(i)).getMomentWejscia())
                {
                     kolejka.add(new Proces(0, (procesy4.get(i)).getMomentWejscia(), 0, (procesy4.get(i)).getCzasPozostaly(), 0));
                }
            }

            if(kwant <= 0 && kolejka.size() !=0)
            {
                if ((kolejka.get(0)).getCzasPozostaly() == 0) {
                   // System.out.println("kwant zerowy");
                    calkowityCzasOczekiwania += (kolejka.get(0)).getCzasOczekiwania();
                    kolejka.remove(0);
                    Collections.sort(kolejka, Proces.Comparators.ComparatorPriorytetWejscie);
                    if (kolejka.size() != 0)
                    {
                        (kolejka.get(0)).setNumerProcesu((kolejka.get(0)).getNumerProcesu() + 1);
                        if ((kolejka.get(0)).getCzasPozostaly() >= k) {
                            kwant = k;
                        } else {
                          //  System.out.println("mniejszy kwant");
                            kwant = (kolejka.get(0)).getCzasPozostaly();
                        }
                    }

                }

                else
                {
                    if(kolejka.size()==1)
                    {
                        (kolejka.get(0)).setNumerProcesu((kolejka.get(0)).getNumerProcesu() + 1);

                    }
                    else
                    {
                        kolejka.add(kolejka.get(0));
                        kolejka.remove(0);
                        Collections.sort(kolejka, Proces.Comparators.ComparatorPriorytetWejscie);
                        (kolejka.get(0)).setNumerProcesu((kolejka.get(0)).getNumerProcesu() + 1);


                    }
                    if((kolejka.get(0)).getCzasPozostaly()>=k)
                    {
                        kwant = k;
                    }
                    else {
                      //  System.out.println("mniejszy kwant");
                        kwant = (kolejka.get(0)).getCzasPozostaly();
                    }
                }

            }



            if (kolejka.size() != 0) {
               // System.out.println(kolejka);
                (kolejka.get(0)).setCzasPozostaly((kolejka.get(0)).getCzasPozostaly() - 1);
                for (int j = 1; j < kolejka.size(); j++) {
                    (kolejka.get(j)).setCzasOczekiwania((kolejka.get(j)).getCzasOczekiwania() + 1);
                }

            }

            czasAktualny++;
            kwant--;

        }
        while (czasAktualny != 100000);
        return calkowityCzasOczekiwania / procesy4.size();

    }
}

















