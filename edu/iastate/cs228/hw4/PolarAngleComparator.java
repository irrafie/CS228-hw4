package edu.iastate.cs228.hw4;

/**
 *
 * @author	Irfan Farhan Mohamad Rafie
 *
 */

import java.util.Comparator;

/**
 *
 * This class compares two points p1 and p2 by polar angle with respect to a reference point.
 *
 */
public class PolarAngleComparator implements Comparator<Point>
{
	private Point referencePoint;
	private boolean flag;  // used for breaking a tie between two points that have
	                       // the same polar angle with respect to referencePoint

	/**
	 *
	 * @param p reference point
	 */
	public PolarAngleComparator(Point p, boolean flag)
	{
		referencePoint = p;
		this.flag = flag;
	}

	/**
	 * Use cross product and dot product to implement this method.  Do not take square roots
	 * or use trigonometric functions. Calls private methods crossProduct() and dotProduct().
	 *
	 * Precondition: both p1 and p2 are different from referencePoint.
	 *
	 * @param p1
	 * @param p2
	 * @return  0 if p1 and p2 are the same point
	 *         -1 if one of the following three conditions holds:
	 *                a) the cross product between p1 - referencePoint and p2 - referencePoint
	 *                   is greater than zero.
	 *                b) the above cross product equals zero, flag == true, and p1 is closer to
	 *                   referencePoint than p2 is.
	 *                c) the above cross product equals zero, flag == false, and p1 is further
	 *                   from referencePoint than p2 is.
	 *          1  otherwise.
	 *
	 */
	public int compare(Point p1, Point p2)
	{

		if(p1.equals(p2)){
			return 0;
		}

		else if(crossProduct(p1,p2) > 0){										//CASE a
			return -1;
		}

		else if(crossProduct(p1,p2) == 0 && flag && dist(p1,p2) < 0){ 			//CASE b
			return -1;
		}

		else if(crossProduct(p1,p2) == 0 && !flag && dist(p1,p2) > 0){ 			//CASE c
			return -1;
		}

		else{
			return 1;
		}
	}


    /**
     *
     * @param p1
     * @param p2
     * @return cross product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    public int crossProduct(Point p1, Point p2)
    {

		int vectAX = p1.getX() - referencePoint.getX();
		int vectAY = p1.getY() - referencePoint.getY();

		int vectBX = p2.getX() - referencePoint.getX();
		int vectBY = p2.getY() - referencePoint.getY();

		int vect = (vectAX * vectBY) - (vectAY * vectBX);
    	return vect;
    }

    /**
     *
     * @param p1
     * @param p2
     * @return dot product of two vectors: p1 - referencePoint and p2 - referencePoint
     */
    public int dotProduct(Point p1, Point p2)
    {
		int vectAX = p1.getX() - referencePoint.getX();
		int vectAY = p1.getY() - referencePoint.getY();

		int vectBX = p2.getX() - referencePoint.getX();
		int vectBY = p2.getY() - referencePoint.getY();

		int product = (vectAX * vectBX) + (vectAY * vectBY);

    	return product;
    }

    public int dist(Point p1, Point p2){
    	if(dotProduct(p1,p1) == dotProduct(p2,p2)){
    		return 0;
		}
		else if(dotProduct(p1,p1) < dotProduct(p2,p2)){
    		return -1;
		}
		else{
    		return 1;
		}
	}

	private int distChoose(Point p1, Point p2, char a){
    	int temp = 0;
    	switch (a){
			case 'x':
				temp = p1.getX() - p2.getX();
				break;
			case 'y':
				temp = p1.getY() - p2.getY();
				break;
    	}
		return temp;
	}

	public int compareWithRef(Point p1, Point p2, Point p3)
	{
		int vectA = (distChoose(p1,p3,'x') * distChoose(p2,p3,'y'));
		int vectB = (distChoose(p2,p3,'x') * distChoose(p1,p3,'y'));

		if(vectA < vectB || p2.equals(referencePoint)){
			return 1;
		}
		else if(vectB < vectA || p1.equals(referencePoint)){
			return -1;
		}

		return 0;
	}

}
