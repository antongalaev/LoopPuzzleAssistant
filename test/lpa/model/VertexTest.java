/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import junit.framework.TestCase;

public class VertexTest extends TestCase {
    
    /**
     * Test of edge collection methods, of class Vertex.
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
        System.out.println("edge collection methods in Vertex");
        Vertex vertex = new Vertex(0, 0);
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
        vertex.setEdges(edges);
        
        ElementGroup expResult = edges;
        int expAbsent = 1;
        int expUndetermined = 1;
        int expPresent = 2;
        ElementGroup result = vertex.getEdges();
        int resAbsent = vertex.countNoEdges();
        int resUndetermined = vertex.countMaybeEdges();
        int resPresent = vertex.countYesEdges();
        assertEquals("checking result", expResult, result);       
        assertEquals("checking result", expAbsent, resAbsent);
        assertEquals("checking result", expUndetermined, resUndetermined);
        assertEquals("checking result", expPresent, resPresent);
    }

    /**
     * Test of toString method, of class Cell.
     * Checks for value for a cell without digit (-1).
     */
    public void testToString() {
        System.out.println("Vertex toString");
        Vertex vertex = new Vertex(0, 0);
        String expResult = "+";
        String result = vertex.toString();
        assertEquals("checking result", expResult, result);
    }
}
