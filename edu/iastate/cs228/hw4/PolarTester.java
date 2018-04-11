
package edu.iastate.cs228.hw4;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
/**
 *
 * @author Richa Patel
 *
 */
public class PolarTester {
    private final PolarAngleComparator polaranglecompare = new PolarAngleComparator(null, false);

    @Test
    public void PolarAnglecomparetest() {
        Point r = new Point();
        PolarAngleComparator p = new PolarAngleComparator(r, false);
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 3);
        assertEquals(1, p.compare(p1, p2));
    }

    @Test
    public void PolarAnglecomparetest2() {
        Point ref = new Point();
        PolarAngleComparator p = new PolarAngleComparator(ref, false);
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 3);
        Point p3 = new Point(1, 2);
        Point p4 = new Point(2, 3);
        assertEquals(1, p.compare(p1, p2));
        assertEquals(1, p.compare(p1, p3));
        assertEquals(-1, p.compare(p2, p3));
    }

    //less than
    @Test
    public void PolarAnglecomparetest3() {
        Point ref = new Point();
        PolarAngleComparator p = new PolarAngleComparator(ref, true);
        Point p5 = new Point(-1, -2);
        Point p6 = new Point(-7, -10);
        assertEquals(1, p.compare(p5, p6));
    }

    public void PolarAnglecomparetest4() {
        Point referencepoint = new Point(0, 0);
        PolarAngleComparator p = new PolarAngleComparator(referencepoint, true);
        Point p7 = new Point(1, 1);
        Point p8 = new Point(2, 2); //checks if the cross product is greater than zero
        assertEquals(-1, p.compare(p7, p8));
    }

    @Test
    public void PolarAngleComparetest5() {
        Point referencePoint = new Point();
        PolarAngleComparator p = new PolarAngleComparator(referencePoint, true);
        Point p1 = new Point(1, 1);
        Point p2 = new Point(8, 9);
        assertEquals(-1, p.compare(p1, p2));
    }

    @Test
    public void crossprodtest() {
//((p1x-refx) * (p2y-refy))-((p2x-refx) * (p2y-refy))
        Point ref = new Point(0, 0);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 1);
        PolarAngleComparator p = new PolarAngleComparator(ref, true);
//        assertEquals(-3, p.crossProduct(p1, p2));
    }

    @Test
    public void dotproducttest() {
//((p1x-refx)*(p2-refx)) + (p1y - refy) *(p2x-refy)
        Point ref = new Point(0, 0);
        Point p1 = new Point(1, 2);
        Point p2 = new Point(2, 1);
        PolarAngleComparator p = new PolarAngleComparator(ref, true);
//        assertEquals(4, p.dotProduct(p1, p2));
    }
}