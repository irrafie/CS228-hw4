package edu.iastate.cs228.hw4;

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.ArrayList;

/**
 * @author Irfan Farhan Mohamad Rafie
 */
public class JarvisMarch extends ConvexHull
{
	// last element in pointsNoDuplicate(), i.e., highest of all points (and the rightmost one in case of a tie)
	private Point highestPoint; 
	
	// left chain of the convex hull counterclockwise from lowestPoint to highestPoint
	private PureStack<Point> leftChain; 
	
	// right chain of the convex hull counterclockwise from highestPoint to lowestPoint
	private PureStack<Point> rightChain; 
		

	/**
	 * Call corresponding constructor of the super class.  Initialize the variable algorithm 
	 * (from the class ConvexHull). Set highestPoint. Initialize the two stacks leftChain 
	 * and rightChain. 
	 * 
	 * @throws IllegalArgumentException  when pts.length == 0
	 */
	public JarvisMarch(Point[] pts) throws IllegalArgumentException 
	{
		super(pts); 
		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length-1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
	}

	
	/**
	 * Call corresponding constructor of the superclass.  Initialize the variable algorithm.
	 * Set highestPoint.  Initialize leftChain and rightChain.  
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	public JarvisMarch(String inputFileName) throws FileNotFoundException, InputMismatchException
	{
		super(inputFileName);
		algorithm = "Jarvis' March";
		highestPoint = pointsNoDuplicate[pointsNoDuplicate.length-1];
		leftChain = new ArrayBasedStack<Point>();
		rightChain = new ArrayBasedStack<Point>();
		lowestPoint = pointsNoDuplicate[0];
	}


	// ------------
	// Javis' march
	// ------------

	/**
	 * Calls createRightChain() and createLeftChain().  Merge the two chains stored on the stacks  
	 * rightChain and leftChain into the array hullVertices[].
	 * 
     * Two degenerate cases below must be handled: 
     * 
     *     1) The array pointsNoDuplicates[] contains just one point, in which case the convex
     *        hull is the point itself. 
     *     
     *     2) The array contains collinear points, in which case the hull is the line segment 
     *        connecting the two extreme points from them.   
	 */
	public void constructHull()
	{
		long start = System.nanoTime();

		//degenerate case 1
		if(pointsNoDuplicate.length == 1){
			hullVertices = new Point[1];
			hullVertices[0] = pointsNoDuplicate[0];
		}

		//degenerate case 2
		else if(pointsNoDuplicate.length == 2){
			hullVertices = new Point[2];
			hullVertices[0] = pointsNoDuplicate[0];
			hullVertices[1] = pointsNoDuplicate[1];
		}

		else{
			createLeftChain();
			createRightChain();

			hullVertices = new Point[rightChain.size() + leftChain.size()];

			for(int i = hullVertices.length - 1; i > rightChain.size()-1; i--){
				hullVertices[i] = leftChain.pop();
			}

			for(int i = rightChain.size() -1; i >= 0; i--){
				hullVertices[i] = rightChain.pop();
			}
			removeLinear();
			time = System.nanoTime() - start ;
		}
	}
	
	
	/**
	 * Construct the right chain of the convex hull.  Starts at lowestPoint and wrap around the 
	 * points counterclockwise.  For every new vertex v of the convex hull, call nextVertex()
	 * to determine the next vertex, which has the smallest polar angle with respect to v.  Stop 
	 * when the highest point is reached.  
	 * 
	 * Use the stack rightChain to carry out the operation.  
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createRightChain()
	{

		rightChain.push(lowestPoint);
		//while has not reached lowest point
		while(rightChain.peek().compareTo(highestPoint) != 0){
		//push new objects until it reaches highest point
			Point tempo = nextVertex(rightChain.peek());
			//tempo is not pushed, DEBUG ME
			rightChain.push(tempo);
		}
	}
	
	
	/**
	 * Construct the left chain of the convex hull.  Starts at highestPoint and continues the 
	 * counterclockwise wrapping.  Stop when lowestPoint is reached.  
	 * 
	 * Use the stack leftChain to carry out the operation. 
	 * 
	 * Ought to be private, but is made public for testing convenience. 
	 */
	public void createLeftChain()
	{
		leftChain.push(highestPoint);
		//while has not reached lowest point
		while(leftChain.peek().compareTo(lowestPoint) != 0){
			//push new objects until it reaches lowest point
			Point tempo = nextVertex(leftChain.peek());
			leftChain.push(tempo);
		}
	}
	
	
	/**
	 * Return the next vertex, which is less than all other points by polar angle with respect
	 * to the current vertex v. When there is a tie, pick the point furthest from v. Comparison 
	 * is done using a PolarAngleComparator object created by the constructor call 
	 * PolarAngleCompartor(v, false).
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param v  current vertex 
	 * @return
	 */
	public Point nextVertex(Point v)
	{
		PolarAngleComparator comp = new PolarAngleComparator(v, false);
		super.quicksorter = new QuickSortPoints(pointsNoDuplicate);
		super.quicksorter.quickSort(comp);
		int i =0;
		//find location of v
		while(!(pointsNoDuplicate[i].equals(v))){
			i++;
		}

		Point temp = v;

		for(int j = 0; j < pointsNoDuplicate.length-1; j++){
			if(comp.compare(pointsNoDuplicate[j],pointsNoDuplicate[j+1]) < 0){
				temp = pointsNoDuplicate[j];
			}
		}

		
		return temp;
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
