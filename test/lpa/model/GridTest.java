/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import junit.framework.TestCase;

public class GridTest extends TestCase {   
    
    // The Loop Puzzle Grid
    private String createGrid1() {
        String s = "";
        List<char[]> grid = new ArrayList<>();
        grid.add(new char[]{'+', '.', '+', '-', '+', ' ', '+', '-', '+', ' ', '+'});
        grid.add(new char[]{' ', '2', '|', '3', '|', '3', '|', '3', '|', ' ', ' '});
        grid.add(new char[]{'+', '-', '+', ' ', '+', '-', '+', ' ', '+', '-', '+'});
        grid.add(new char[]{'|', '2', ' ', ' ', ' ', ' ', ' ', '0', ' ', ' ', '|'});
        grid.add(new char[]{'+', ' ', '+', '-', '+', '-', '+', ' ', '+', '-', '+'});
        grid.add(new char[]{'|', ' ', '|', ' ', ' ', ' ', '|', '2', '|', ' ', ' '});
        grid.add(new char[]{'+', ' ', '+', ' ', '+', '-', '+', ' ', '+', '-', '+'});
        grid.add(new char[]{'|', '2', '|', ' ', '|', ' ', ' ', '0', ' ', ' ', '|'});
        grid.add(new char[]{'+', ' ', '+', '.', '+', '-', '+', ' ', '+', '-', '+'});
        grid.add(new char[]{'|', ' ', '|', ' ', ' ', ' ', '|', '3', '|', ' ', ' '});
        grid.add(new char[]{'+', '-', '+', ' ', '+', ' ', '+', '-', '+', ' ', '+'});
        for (char[] line : grid) {
            s += new String(line);
            s += "\r\n";
        }
        
        return s;
    }
    
    /**
     * Test of setElementGroups method, of class Grid.
     * Checks element group of a (1, 1) cell in grid.
     */
    public void testSetElementGroups() {
        System.out.println("setElementGroups");
        Grid instance = new Grid(new Scanner(createGrid1()));
        int expAbsent = 1;
        int expUndetermined = 1;
        int expPresent = 2;
        instance.setElementGroups();
        Cell cell = (Cell) instance.getElement(1, 1);
        int resAbsent = cell.countNoEdges();
        int resUndetermined = cell.countMaybeEdges();
        int resPresent = cell.countYesEdges();   
        assertEquals("checking result", expAbsent, resAbsent);
        assertEquals("checking result", expUndetermined, resUndetermined);
        assertEquals("checking result", expPresent, resPresent);
    }

    /**
     * Test of getElement method, of class Grid.
     * A common case. 
     * In this case a cell with "2" is expected. 
     */
    public void testGetElement1() {
        System.out.println("getElement");
        Grid instance = new Grid(new Scanner(createGrid1()));
        GridElement result = instance.getElement(1, 1);
        if (result instanceof Cell) { // must be cell 
            Cell cell = (Cell) result;
            if (cell.getDigit() == 2) { // digit must be 2
                assertTrue(true);
            } else { // no 2
                assertTrue(false);
            }
        } else { // no cell
            assertTrue(false);  
        }                  
    }
    
    /**
     * Test of getElement method, of class Grid.
     * A case for out of range calls.
     * Must return null for such cases.
     */
    public void testGetElement2() {
        System.out.println("getElement");
        Grid instance = new Grid(new Scanner(createGrid1()));
        GridElement result = instance.getElement(-1, -1);
        if (result == null) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }
    }
    

    /**
     * Test of getHeight method, of class Grid.
     * 
     */
    public void testGetHeight() {
        System.out.println("getHeight");
        Grid instance = new Grid(new Scanner(createGrid1()));
        int expResult = 11;
        int result = instance.getHeight();
        assertEquals("checking result", expResult, result);
    }

    /**
     * Test of getWidth method, of class Grid.
     */
    public void testGetWidth() {
        System.out.println("getWidth");
        Grid instance = new Grid(new Scanner(createGrid1()));
        int expResult = 11;
        int result = instance.getWidth();
        assertEquals("checking result", expResult, result);
    }

    /**
     * Test of toString method, of class Grid.
     */
    public void testToString() {
        System.out.println("toString");
        Grid instance = new Grid(new Scanner(createGrid1()));
        String expResult = createGrid1();
        String result = instance.toString();
        assertEquals("checking result", expResult, result);
    }
}