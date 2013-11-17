/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import junit.framework.TestCase;

public class ElementGroupTest extends TestCase {
  
    /**
     * Test of add method, of class ElementGroup.
     * Simple test for regular operation.
     */
    public void testAdd() {
        System.out.println("add");
        Edge element = new Edge(0, 0, EdgeState.PRESENT);
        ElementGroup instance = new ElementGroup();
        int expResult = instance.histogram.get(EdgeState.PRESENT) + 1;
        instance.add(element);
        int result = instance.histogram.get(EdgeState.PRESENT);
        assertEquals("checking result", expResult, result);
    }
}
