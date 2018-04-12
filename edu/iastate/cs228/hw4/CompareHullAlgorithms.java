package edu.iastate.cs228.hw4;

/**
 *  
 * @author	Irfan Farhan Mohamad Rafie
 *
 */

/**
 * 
 * This class executes two convex hull algorithms: Graham's scan and Jarvis' march, over randomly
 * generated integers as well integers from a file input. It compares the execution times of 
 * these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random; 


public class CompareHullAlgorithms 
{
	/**
	 * Repeatedly take points either randomly generated or read from files. Perform Graham's scan and 
	 * Jarvis' march over the input set of points, comparing their performances.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {

		// TODO 
		// 
		// Conducts multiple rounds of convex hull construction. Within each round, performs the following: 
		// 
		//    1) If the input are random points, calls generateRandomPoints() to initialize an array 
		//       pts[] of random points. Use pts[] to create two objects of GrahamScan and JarvisMarch, 
		//       respectively.
		//
		//    2) If the input is from a file, construct two objects of the classes GrahamScan and  
		//       JarvisMarch, respectively, using the file.     
		//
		//    3) Have each object call constructHull() to build the convex hull of the input points.
		//
		//    4) Meanwhile, prints out the table of runtime statistics.
		// 
		// A sample scenario is given in Section 5 of the project description. 
		// 	
		ConvexHull[] algorithms = new ConvexHull[2];
		int RandomPoints = 0;
		Scanner userInput = new Scanner(System.in);
		int choice = 0;
		int count = 1;
		while(true) {
			System.out.print("Trial "+ count+": ");
			choice = userInput.nextInt();
			String input = "";

			switch (choice) {
				case 1:
					Random rand = new Random(1);
					System.out.print("\nEnter number of random points: ");
					RandomPoints = userInput.nextInt();
					Point[] random = generateRandomPoints(RandomPoints, rand);
					algorithms[0] = new GrahamScan(random);
					algorithms[1] = new JarvisMarch(random);
					break;
				case 2:
					System.out.print("\nPoints from a file\nFile name: ");
					input = userInput.next();
					algorithms[0] = new GrahamScan(input);
					algorithms[1] = new JarvisMarch(input);
					break;

				case 3:
					System.exit(0);
			}

			String output = "algorithm";

			System.out.println(String.format("%-15s", output) + String.format("%-5s", "size") + String.format("%-10s", "time"));
			System.out.println("---------------------------------------");
			for (int i = 0; i < 2; i++) {
				algorithms[i].constructHull();
				System.out.println(algorithms[i].stats());
				//System.out.println(algorithms[i].toString());
				algorithms[i].draw();
				//algorithms[i].writeHullToFile();
			}
			System.out.println("---------------------------------------");

			count++;


			// Within a hull construction round, have each algorithm call the constructHull() and draw()
			// methods in the ConvexHull class.  You can visually check the result. (Windows
			// have to be closed manually before rerun.)  Also, print out the statistics table
			// (see Section 5).
		}
	}
	
	
	/**
	 * This method generates a given number of random points.  The coordinates of these points are 
	 * pseudo-random numbers within the range [-50,50]  [-50,50].
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{

		Point[] randomGen = new Point[numPts];

		for(int i = 0; i < numPts; i++){
			randomGen[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		return randomGen;
	}
}
