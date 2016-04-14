package com.company;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Magdalena Polak on 29.02.2016.
 */
public class Procesy {

    public double FCFS(ArrayList<Proces> procesy)
    {
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


    public double SJF(ArrayList<Proces> procesy) {
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

    public double SRTF(ArrayList<Proces> procesy)
    {
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
       // System.out.println("Ilosc wywlaszczen: " + licznikWywlaszczen / 2);
        return calkowityCzasOczekiwania / pomocnicza.size();
    }


    public double RR(int k, ArrayList<Proces> procesy)
    {
        int kwant = 0;
        int czasAktualny = 0;
        double calkowityCzasOczekiwania = 0;
        ArrayList<Proces> procesy4 = new ArrayList<Proces>();
        ArrayList<Proces> kolejka = new ArrayList<Proces>();
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



System.out.println("Priority: " + waitingPriorityTasks);
        /**
         * if pointer reaches task cylinder number its being removed
         */
        if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
        System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
        waitingPriorityTasks.remove(0);

        }
        /**
         * when new task has come or last task has been done
         */
        if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
        Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
        /**
         * if pointer wont meet deadline is rejected
         */
        if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
        System.out.println("rejection");
        rejected++;
        }
        }


        if (waitingPriorityTasks.size() != 0) {
        /**
         * if pointer is moving forwards
         */
        if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
        currentBlock++;
        }
        /**
         * if pointer is moving backwards
         */
        else {
        currentBlock--;
        }
        blocks++;
        /**
         * deadline is descanding with time
         */
        for (int i = 0; i < waitingPriorityTasks.size(); i++) {
        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
        }

        }













