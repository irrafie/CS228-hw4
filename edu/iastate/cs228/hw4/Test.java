package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;

public class Test {

    public static void main(String args[]) throws FileNotFoundException {
        String temp = "0 0";
        Point[] tempo = new Point[5];
        tempo[0] = new Point(5,0);
        tempo[1] = new Point(3,0);
        tempo[2] = new Point(2,0);
        tempo[3] = new Point(4,0);
        tempo[4] = new Point(1,0);
        GrahamScan gram = new GrahamScan(tempo);
        gram.setUpScan();
    }
}
