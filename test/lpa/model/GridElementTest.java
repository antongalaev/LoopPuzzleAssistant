/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import junit.framework.TestCase;

public class GridElementTest extends TestCase {
    
     /**
     * Test constructor of class GridElement for exceptions.
     */
    public void testConstructor() {
        System.out.println("GridElement constructor");
        try {
            GridElement instance = new GridElement(-1, -2);
            fail("Expected exception has not happened.");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        } catch (Exception e) {
            fail("Unexpected exception has happened.");
        }
    }
    
    /**
     * Test of getX method, of class GridElement.
     */
    public void testGetX() {
        System.out.println("getX");
        GridElement instance = new GridElement(1, 2);
        int expResult = 1;
        int result = instance.getX();
        assertEquals("checking result", expResult, result);
    }

    /**
     * Test of getY method, of class GridElement.
     */
    public void testGetY() {
        System.out.println("getY");
         GridElement instance = new GridElement(1, 2);
        int expResult = 2;
        int result = instance.getY();
        assertEquals("checking result", expResult, result);
    }
}
