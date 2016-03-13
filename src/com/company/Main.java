package com.company;

public class Main {

    public static void main(String[] args)
    {

        Procesy pr = new Procesy(50);
      //  System.out.println(" Nieposortowane");
      //  pr.wyswietl();
        System.out.println(" Posortowane wg cz wejscia");
        pr.wyswietlS();
        System.out.println("\nFCFS: " + pr.FCFS());
        System.out.println("\nSJF: " + pr.SJF());
        System.out.println("\nSJF2: " + pr.SJF2());
        System.out.println("\nSRTF: " + pr.SRTF());
        System.out.println("\nRR: " + pr.RR(100));



    }
}
