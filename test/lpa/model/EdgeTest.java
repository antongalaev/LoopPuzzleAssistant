/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import junit.framework.TestCase;

public class EdgeTest extends TestCase {    
   
    /**
     * Test #1 of setState and getState methods, of class Edge.
     * Checks for a regular value.
     */
    public void testSetGetDigit1() {
        checkSetGetState(EdgeState.PRESENT);
    }
    
    /**
     * Test #2 of setState and getState methods, of class Edge.
     * Checks for a null value of edge state (looking for an exception).
     * NullPointerException is expected.
     */
    public void testSetGetDigit4() {
        checkSetGetState(null);
    }
     
    /**
     * Auxiliary method for test 
     * of setState and getState methods, 
     * of class Edge.
     */
    private void checkSetGetState(EdgeState state) {
        System.out.println("Edge setState and getState methods");
        Edge edge = new Edge(0, 1);
        if (state == null) { // check for exceptions
            try {
                edge.setState(state);
                fail("Expected exception has not happened.");
            } catch(NullPointerException e) {
                assertTrue(true);
            } catch(Exception e) {
                fail("Unexpected exception has happened.");
            }
        } else { // regular check
            edge.setState(state);
            EdgeState expResult = state;
            EdgeState result = edge.getState();
            assertEquals("checking result", expResult, result);
        }
    }

    /**
     * Test #1 of toString method, of class Cell.
     * Checks an absent edge.
     */
    public void testToString1() {
        checkToString(false, EdgeState.ABSENT);
    }
    
    /**
     * Test #2 of toString method, of class Cell.
     * Checks an undetermined edge.
     */
    public void testToString2() {
        checkToString(false, EdgeState.UNDETERMINED);
    }
    
    /**
     * Test #3 of toString method, of class Edge.
     * Checks a horizontal present edge.
     */
    public void testToString3() {
        checkToString(false, EdgeState.PRESENT);
    }
    
    /**
     * Test #4 of toString method, of class Edge.
     * Checks a horizontal present edge.
     */
    public void testToString4() {
        checkToString(true, EdgeState.PRESENT);
    }
    
    /**
     * Auxiliary method for test 
     * of toString method, 
     * of class Edge.
     */ 
    private void checkToString(boolean vertical, EdgeState state) {
        System.out.println("Edge toString");
        Edge edge;
        String expResult = "";
        if (vertical) {
            edge = new Edge(0, 1, state);
        } else {
            edge = new Edge(1, 0, state);
        }
        
        if (state == EdgeState.ABSENT) { // absent edge
            expResult = " ";
        }
        if (state == EdgeState.UNDETERMINED) { // undetermined edge
            expResult = ".";
        }
        if (state == EdgeState.PRESENT) { // present edge
            if (vertical) { // if edge is vertical 
                expResult = "|";
            } else { // if edge is horizontal
                expResult = "-";
            }
        }
        String result = edge.toString();
        assertEquals("checking result", expResult, result);
    }
}
