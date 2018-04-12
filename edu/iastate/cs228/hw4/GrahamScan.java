package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.ArrayList; 

public class GrahamScan extends ConvexHull
{
	/**
	 * Stack used by Grahma's scan to store the vertices of the convex hull of the points 
	 * scanned so far.  At the end of the scan, it stores the hull vertices in the 
	 * counterclockwise order. 
	 */
	private PureStack<Point> vertexStack;  


	/**
	 * Call corresponding constructor of the super class.  Initialize two variables: algorithm 
	 * (from the class ConvexHull) and vertexStack. 
	 * 
	 * @throws IllegalArgumentException  if pts.length == 0
	 */
	public GrahamScan(Point[] pts) throws IllegalArgumentException 
	{
		super(pts);
		super.algorithm = "Graham's Scan";
		setUpScan();
		if(pointsNoDuplicate.length == 0){
			throw new IllegalArgumentException();
		}
	}
	

	/**
	 * Call corresponding constructor of the super class.  Initialize algorithm and vertexStack.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public GrahamScan(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName); 
		super.algorithm = "Graham's Scan";
		setUpScan();
	}

	
	// -------------
	// Graham's scan
	// -------------
	
	/**
	 * This method carries out Graham's scan in several steps below: 
	 * 
	 *     1) Call the private method setUpScan() to sort all the points in the array 
	 *        pointsNoDuplicate[] by polar angle with respect to lowestPoint.    
	 *        
	 *     2) Perform Graham's scan. To initialize the scan, push pointsNoDuplicate[0] and 
	 *        pointsNoDuplicate[1] onto vertexStack.  
	 * 
     *     3) As the scan terminates, vertexStack holds the vertices of the convex hull.  Pop the 
     *        vertices out of the stack and add them to the array hullVertices[], starting at index
     *        vertexStack.size() - 1, and decreasing the index toward 0.    
     *        
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains only collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points.   
	 */
	public void constructHull()
	{
	    long start = System.nanoTime();
        vertexStack = new ArrayBasedStack<Point>();
		setUpScan();
        vertexStack.push(pointsNoDuplicate[0]);
		if(pointsNoDuplicate.length == 1){
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
			return;
		}
        vertexStack.push(pointsNoDuplicate[1]);

		if(pointsNoDuplicate.length == 2){
			hullVertices = new Point[2];
			hullVertices[0] = pointsNoDuplicate[0];
			hullVertices[1] = pointsNoDuplicate[1];
			return;
		}


		vertexStack.push(pointsNoDuplicate[2]);

		PolarAngleComparator compo = new PolarAngleComparator(lowestPoint,true);
		for(int j = 3; j < pointsNoDuplicate.length; j++){
            while (compo.compareWithRef(beforeTop(), pointsNoDuplicate[j], vertexStack.peek()) != 1 && (pointsNoDuplicate[j].getY() != vertexStack.peek().getY() || pointsNoDuplicate[j].getY() != beforeTop().getY())) {  //WORKS but fails if points are linear //degenerate cases handled    //handle degenerate by adding to list
                vertexStack.pop();                                                                                                                                  //if points are collinear, they are not removed
            }
            vertexStack.push(pointsNoDuplicate[j]);
        }

		hullVertices = new Point[vertexStack.size()];
		int i = vertexStack.size()-1;
		while(!vertexStack.isEmpty()){
		    hullVertices[i] = vertexStack.pop();
		    i--;
        }
        removeLinear();
        time = System.nanoTime() - start;
	}
	
	public Point beforeTop(){
		Point temp = vertexStack.pop();
		Point toReturn = vertexStack.peek();
		vertexStack.push(temp);
		return toReturn;
	}
	/**
	 * Set the variable quicksorter from the class ConvexHull to sort by polar angle with respect 
	 * to lowestPoint, and call quickSort() from the QuickSortPoints class on pointsNoDupliate[].
	 * The argument supplied to quickSort() is an object created by the constructor call
	 * PolarAngleComparator(lowestPoint, true).
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 *
	 */
	public void setUpScan()
	{
		PolarAngleComparator comp = new PolarAngleComparator(lowestPoint, true);

		super.quicksorter = new QuickSortPoints(this.pointsNoDuplicate);
		super.quicksorter.quickSort(comp);
		pointsNoDuplicate = super.quicksorter.getPointsArray();

//      DEBUG QUICKSORT
//		for(int i = 0; i < pointsNoDuplicate.length; i++){
//			System.out.println(pointsNoDuplicate[i].getX() + ", " + pointsNoDuplicate[i].getY());
//		}
	}

	private void removeLinear(){
	    ArrayList<Point> tempora = new ArrayList<Point>();
        tempora.add(hullVertices[0]);
        for(int i = 1; i < hullVertices.length-1; i++){
            if(hullVertices[i].getY() != hullVertices[i+1].getY()){
                tempora.add(hullVertices[i]);
            }
            else if(hullVertices[i].getX() != hullVertices[i+1].getX() && hullVertices[i].getY() == hullVertices[i+1].getY()){
                tempora.add(hullVertices[i]);
            }
        }



        tempora.add(hullVertices[hullVertices.length-1]);

        hullVertices = new Point[tempora.size()];
        for(int i = 0; i < hullVertices.length; i++){
            hullVertices[i] = tempora.get(i);
        }
    }

}
