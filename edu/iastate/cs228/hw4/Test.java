package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;

public class Test {

    public static void main(String args[]) throws FileNotFoundException {
        Point[] tempo = new Point[5];
        tempo[0] = new Point(-41,50);
        tempo[1] = new Point(12,50);
        tempo[2] = new Point(1,1);
        tempo[3] = new Point(-5,5);
        tempo[4] = new Point(-10,-10);

        GrahamScan gram = new GrahamScan(tempo);
        int hit = tempo[0].compareTo(tempo[1]);
        System.out.println(hit);
        gram.constructHull();
       // System.out.println(gram.toString());
      //  System.out.println(gram.stats());
        gram.writeHullToFile();
        gram.draw();
    }
}
