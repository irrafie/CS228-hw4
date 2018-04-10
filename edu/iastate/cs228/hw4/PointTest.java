package edu.iastate.cs228.hw4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PointTest {

    private Point p;
    private Point q;
    private Point r;
    private Point s;

    @Before
    public void setUp() {
        p = new Point();
        q = new Point(3,5);
        r = new Point(99,45);
        s = new Point(r);
    }

    @Test
    public void testPoint() {
        assertEquals(p.getX(), 0);
        assertEquals(p.getY(), 0);

        assertEquals(q.getX(), 3);
        assertEquals(q.getY(), 5);

        assertEquals(r.getX(), 99);
        assertEquals(r.getY(), 45);

        assertEquals(s.getX(), 99);
        assertEquals(s.getY(), 45);
    }

    @Test
    public void testEquals() {
        assertFalse(p.equals(q));

        assertTrue(r.equals(s));

        assertFalse(q.equals(r));
        assertFalse(q.equals(s));
    }

    @Test
    public void testCompareTo() {
        assertEquals(p.compareTo(q), -1);
        assertEquals(p.compareTo(r), -1);
        assertEquals(p.compareTo(s), -1);

        assertEquals(q.compareTo(r), -1);
        assertEquals(r.compareTo(q), 1);

        assertEquals(r.compareTo(s), 0);
    }

    @Test
    public void testToString() {
        assertEquals(p.toString(), "(0, 0)");
        assertEquals(q.toString(), "(3, 5)");
        assertEquals(r.toString(), "(99, 45)");
        assertEquals(s.toString(), "(99, 45)");
    }
}