package edu.iastate.cs228.hw4;

/**
 *  
 * @author	Irfan Farhan Mohamad Rafie
 *
 */

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.InputMismatchException; 
import java.io.PrintWriter;
import java.util.Random; 
import java.util.Scanner;



/**
 * 
 * This class implements construction of the convex hull of a finite number of points. 
 *
 */

public abstract class ConvexHull 
{
	// ---------------
	// Data Structures 
	// ---------------
	protected String algorithm;  // Its value is either "Graham's scan" or "Jarvis' march". 
	                             // Initialized by a subclass.
	
	protected long time;         // execution time in nanoseconds
	
	/**
	 * The array points[] holds an input set of Points, which may be randomly generated or 
	 * input from a file.  Duplicates are possible. 
	 */
	private Point[] points;    
	

	/**
	 * Lowest point from points[]; and in case of a tie, the leftmost one of all such points. 
	 * To be set by a constructor. 
	 */
	protected Point lowestPoint; 

	
	/**
	 * This array stores the same set of points from points[] with all duplicates removed. 
	 * These are the points on which Graham's scan and Jarvis' march will be performed. 
	 */
	protected Point[] pointsNoDuplicate; 
	
	
	/**
	 * Vertices of the convex hull in counterclockwise order are stored in the array 
	 * hullVertices[], with hullVertices[0] storing lowestPoint. 
	 */
	protected Point[] hullVertices;
	
	
	protected QuickSortPoints quicksorter;  // used (and reset) by this class and its subclass GrahamScan

	
	
	// ------------
	// Constructors
	// ------------
	
	
	/**
	 * Constructor over an array of points.  
	 * 
	 *    1) Store the points in the private array points[].
	 *    
	 *    2) Initialize quicksorter. 
	 *    
	 *    3) Call removeDuplicates() to store distinct points from the input in pointsNoDuplicate[].
	 *    
	 *    4) Set lowestPoint to pointsNoDuplicate[0]. 
	 * 
	 * @param pts
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public ConvexHull(Point[] pts) throws IllegalArgumentException 
	{
		this.points = pts;
		quicksorter = new QuickSortPoints(this.points);
		removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
	}
	
	
	/**
	 * Read integers from an input file.  Every pair of integers represent the x- and y-coordinates 
	 * of a point.  Generate the points and store them in the private array points[]. The total 
	 * number of integers in the file must be even.
	 * 
	 * You may declare a Scanner object and call its methods such as hasNext(), hasNextInt() 
	 * and nextInt(). An ArrayList may be used to store the input integers as they are read in 
	 * from the file.  
	 * 
	 * Perform the operations 1)-4) described for the previous constructor. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public ConvexHull(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		Scanner scanInput = new Scanner(new FileReader(inputFileName));
		int count = 0;
		int x = 0;
		int y = 0;
		int pointCount = 0;
		ArrayList<Point> temp = new ArrayList<Point>();
		while(scanInput.hasNext()){
			String tempo = scanInput.next();
			if(count % 2 == 0) {
				x = Integer.parseInt(tempo);
				count++;
			}
			else if(count % 2 != 0){
				y = Integer.parseInt(tempo);
				count++;
				temp.add(new Point(x,y));
			}
		}

		points = new Point[temp.size()];
		for(int i = 0; i < temp.size(); i++){
			points[i] = temp.get(i);
		}

		if(count % 2 != 0){
			throw new InputMismatchException();
		}

		quicksorter = new QuickSortPoints(this.points);
		removeDuplicates();
		lowestPoint = pointsNoDuplicate[0];
		}

	
	/**
	 * Construct the convex hull of the points in the array pointsNoDuplicate[]. 
	 */
	public abstract void constructHull(); 

	
		
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <convex hull algorithm> <size>  <time>
	 *
	 * For instance,
	 *
	 * Graham's scan   1000	  9200867
	 *
	 * Use the spacing in the sample run in Section 5 of the project description.
	 */
	public String stats()
	{
		return String.format("%-15s", algorithm) + String.format("%-5s", points.length) + String.format("%-10s", time);
	}
	
	/**
	 * The string displays the convex hull with vertices in counterclockwise order starting at  
	 * lowestPoint.  When printed out, it will list five points per line with three blanks in 
	 * between. Every point appears in the format "(x, y)".  
	 * 
	 * For illustration, the convex hull example in the project description will have its 
	 * toString() generate the output below: 
	 * 
	 * (-7, -10)   (0, -10)   (10, 5)   (0, 8)   (-10, 0)   
	 * 
	 * lowestPoint is listed only ONCE. 
	 *  
	 * Called only after constructHull().  
	 */
	public String toString()
	{
		String temp = "";
		int lineCount = 0;

		while(lineCount < hullVertices.length){
			for(int i = 0; i < 5; i++){
				temp += hullVertices[lineCount] + "   ";
				lineCount++;
				if(lineCount >= hullVertices.length){
					return temp;
				}
			}
			temp = temp + "\n";
		}
		return temp;
	}
	
	
	/** 
	 * 
	 * Writes to the file "hull.txt" the vertices of the constructed convex hull in counterclockwise 
	 * order.  These vertices are in the array hullVertices[], starting with lowestPoint.  Every line
	 * in the file displays the x and y coordinates of only one point.  
	 * 
	 * For instance, the file "hull.txt" generated for the convex hull example in the project 
	 * description will have the following content: 
	 * 
     *  -7 -10 
     *  0 -10
     *  10 5
     *  0  8
     *  -10 0
	 * 
	 * The generated file is useful for debugging as well as grading. 
	 * 
	 * Called only after constructHull().  
	 * 
	 * 
	 * @throws IllegalStateException  if hullVertices[] has not been populated (i.e., the convex 
	 *                                   hull has not been constructed)
	 */
	public void writeHullToFile() throws IllegalStateException, FileNotFoundException {
		String temp = "";
		for(int i = 0; i < hullVertices.length; i++){
			temp += hullVertices[i].getX() + " " + hullVertices[i].getY() + "\n";
		}

		PrintWriter output = new PrintWriter("hull.txt");
		output.println(temp);
		output.close();
	}
	

	/**
	 * Draw the points and their convex hull.  This method is called after construction of the 
	 * convex hull.  You just need to make use of hullVertices[] to generate a list of segments 
	 * as the edges. Then create a Plot object to call the method myFrame().  
	 */
	public void draw()
	{		
		int numSegs = 0;  // number of segments to draw

		// Based on Section 4, generate the line segments to draw for display of the convex hull.
		// Assign their number to numSegs, and store them in segments[] in the order. 
		Segment[] segments = new Segment[hullVertices.length];

		for(numSegs = 0; numSegs < hullVertices.length-1; ++numSegs){
			segments[numSegs] = new Segment(hullVertices[numSegs],hullVertices[numSegs+1]);
		}

		segments[hullVertices.length-1] = new Segment(hullVertices[hullVertices.length-1],hullVertices[0]);
		Plot tempora = new Plot();
		// The following statement creates a window to display the convex hull.
		tempora.myFrame(pointsNoDuplicate, segments, getClass().getName());
		
	}

		
	/**
	 * Sort the array points[] by y-coordinate in the non-decreasing order.  Have quicksorter 
	 * invoke quicksort() with a comparator object which uses the compareTo() method of the Point 
	 * class. Copy the sorted sequence onto the array pointsNoDuplicate[] with duplicates removed.
	 *     
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void removeDuplicates()
	{
		Point temp = new Point();
		Arrays.sort(points);

		ArrayList<Point> tempo = new ArrayList<>();
		tempo.add(points[0]);
		for(int x = 1; x < points.length; x++){
			if(points[x-1].compareTo(points[x]) != 0){
				tempo.add(points[x]);
			}
		}

		Point[] tempop = new Point[tempo.size()];
		for(int x = 0; x < tempop.length; x++){
			tempop[x] = tempo.get(x);
		}
		pointsNoDuplicate = tempop;
	}
}
