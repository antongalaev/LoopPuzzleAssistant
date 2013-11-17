/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import junit.framework.TestCase;

public class CellTest extends TestCase {
    
    /**
     * Test of edge collection methods, of class Cell.
     * <p>
     * Methods are:
     * <ul>
     * <il>getEdges
     * <il>setEdges
     * <il>testCountYesEdges
     * <il>testCountNoEdges
     * <il>testCountMaybeEdges
     * </ul>
     */
    public void testEdgeCollection() {
        System.out.println("edge collection methods in Cell");
        Cell cell = new Cell(1, 1);
        ElementGroup edges;
        //element that is to add to the group
        Edge adding;
        edges = new ElementGroup();
        adding = new Edge(0, 0, EdgeState.ABSENT);
        edges.add(adding);
        adding = new Edge(0, 0, EdgeState.UNDETERMINED);
        edges.add(adding);
        adding = new Edge(0, 0, EdgeState.PRESENT);
        edges.add(adding);
        adding = new Edge(0, 0, EdgeState.PRESENT);
        edges.add(adding);
        cell.setEdges(edges);
        
        ElementGroup expResult = edges;
        int expAbsent = 1;
        int expUndetermined = 1;
        int expPresent = 2;
        ElementGroup result = cell.getEdges();
        int resAbsent = cell.countNoEdges();
        int resUndetermined = cell.countMaybeEdges();
        int resPresent = cell.countYesEdges();
        assertEquals("checking result", expResult, result);       
        assertEquals("checking result", expAbsent, resAbsent);
        assertEquals("checking result", expUndetermined, resUndetermined);
        assertEquals("checking result", expPresent, resPresent);
    }

    /**
     * Test #1 of setDigit and getDigit methods, of class Cell.
     * Checks for a regular value.
     */
    public void testSetGetDigit1() {
        checkSetGetDigit(1);
    }

    /**
     * Test #2 of setDigit and getDigit methods, of class Cell.
     * Checks for value for a cell without digit (-1).
     */
    public void testSetGetDigit2() {
        checkSetGetDigit(-1);
    }

    /**
     * Test #3 of setDigit and getDigit methods, of class Cell.
     * Checks for value out of range (looking for an exception).
     * Value is more than upper bound.
     */
    public void testSetGetDigit3() {
        checkSetGetDigit(5);
    }
    
    /**
     * Test #4 of setDigit and getDigit methods, of class Cell.
     * Checks for value out of range (looking for an exception).
     * Value is less than lower bound.
     */
    public void testSetGetDigit4() {
        checkSetGetDigit(-5);
    }
     
    /**
     * Auxiliary method for test 
     * of setDigit and getDigit methods, 
     * of class Cell.
     */
    private void checkSetGetDigit(int digit) {
        System.out.println("setDigit and getDigit methods");
        Cell cell = new Cell(1, 1);
        if (digit < -1 || digit > 3) { // check for exceptions
            try {
                cell.setDigit(digit);
                fail("Expected exception has not happened.");
            } catch(IllegalArgumentException e) {
                assertTrue(true);
            } catch(Exception e) {
                fail("Unexpected exception has happened.");
            }
        } else { // regular check
            int expResult = digit;    
            cell.setDigit(digit);
            int result = cell.getDigit();
            assertEquals("checking result", expResult, result);
        }
    }

    /**
     * Test #1 of toString method, of class Cell.
     * Checks for a regular value.
     */
    public void testToString1() {
        checkToString(1);
    }
    
    /**
     * Test #2 of toString method, of class Cell.
     * Checks for value for a cell without digit (-1).
     */
    public void testToString2() {
        checkToString(-1);
    }
    
    /**
     * Auxiliary method for test 
     * of toString method, 
     * of class Cell.
     */ 
    private void checkToString(int digit) {
        System.out.println("Cell toString");
        Cell cell = new Cell(1, 1);
        cell.setDigit(digit);
        String expResult;
        if (digit == -1) { // empty cell
            expResult = " ";
        } else { // digit cell
            expResult = String.valueOf(digit);
        }
        String result = cell.toString();
        assertEquals("checking result", expResult, result);
    }
}
