package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 *
 * @author	Irfan Farhan Mohamad Rafie
 *
 * This class sorts an array of Point objects using a provided Comparator.  For the purpose
 * you may adapt your implementation of quicksort from Project 2.
 */

public class QuickSortPoints
{
	private Point[] points;  	// Array of points to be sorted.


	/**
	 * Constructor takes an array of Point objects.
	 *
	 * @param pts
	 */
	QuickSortPoints(Point[] pts)
	{
		points = pts;
	}


	/**
	 * Copy the sorted array to pts[].
	 *
	 * @param pts  array to copy onto
	 */
	void getSortedPoints(Point[] pts)
	{
		ArrayList<Point> temp = new ArrayList<>();
		for(int x = 1; x < pts.length-1; x++){
			if(pts[x-1].compareTo(pts[x]) != 0){
				temp.add(pts[x]);
			}
		}

		Point[] tempo = new Point[temp.size()];
		for(int x = 0; x < tempo.length; x++){
			tempo[x] = temp.get(x);
		}

	}


	/**
	 * Perform quicksort on the array points[] with a supplied comparator.
	 *
	 * @param comp
	 */
	public void quickSort(Comparator<Point> comp)
	{
		try{
			if(points == null){
				throw new NullPointerException("Null Pointer");
			}
			quickSortRec( 0, points.length - 1, comp);
		}
		catch (NullPointerException e){
			e.printStackTrace();
		}
	}


	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 *
	 * @param first  firsting index of the subarray
	 * @param last   last
	 *                 ing index of the subarray
	 */
	private void quickSortRec(int first, int last, Comparator<Point> comp)
	{
		int index = partition(first,last, comp);

		if(first < index -1){
			quickSortRec(first, index - 1, comp);
		}

		if(index < last){
			quickSortRec(index, last, comp);
		}
	}


	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 *
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last, Comparator<Point> comp)
	{
		Random rand = new Random(100);
		int i = first;
		int j = last;

		Point pivot = points[(first+last)/2];

		while(i <= j){
			while(comp.compare(points[i], pivot) == -1){
				i++;
			}

			while(comp.compare(points[j], pivot) == 1){
				j--;
			}

			if(i <= j){
				swap(i,j);
				i++;
				j--;
			}

		}

		return i;
	}

	public Point[] getPointsArray(){
		return points;

	}

	private void swap(int i, int j){
		Point temp;

		temp = points[i];
		points[i] = points[j];
		points[j] = temp;
	}
}







