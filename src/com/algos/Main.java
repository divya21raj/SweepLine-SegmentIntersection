package com.algos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        File file =
                new File("/home/D2R/Documents/JavaProjects/SweepLineIntersection/src/com/algos/input/f1.txt");
        Scanner sc = new Scanner(file);

        ArrayList<LineSegment> lineSegments = new ArrayList<>();
        while (sc.hasNextLine()){
            String[] s = sc.nextLine().split(" ");
            Point p1 = new Point(Double.valueOf(s[0]), Double.valueOf(s[1]));
            Point p2 = new Point(Double.valueOf(s[2]), Double.valueOf(s[3]));
            lineSegments.add(new LineSegment(p1, p2));
        }

        Intersection test = new Intersection(lineSegments);

        System.out.println("BruteForce-------------------");
        long t1 = System.currentTimeMillis();
        test.bruteForce();
        long t2 = System.currentTimeMillis();

        test.printIntersections();
        ArrayList<Point> intersections = test.get_intersections();

        System.out.println("Intersection Points: " + intersections.size());
        System.out.println("Runtime: " + (t2 - t1) + " ms");


        System.out.println("LineSweep-------------------");
        test = new Intersection(lineSegments);

        t1 = System.currentTimeMillis();
        test.findIntersections();
        t2 = System.currentTimeMillis();

        /*test.printIntersections();*/
        intersections = test.get_intersections();

        System.out.println("Intersection Points: " + intersections.size());
        System.out.println("Runtime: " + (t2 - t1) + " ms");
    }
}
