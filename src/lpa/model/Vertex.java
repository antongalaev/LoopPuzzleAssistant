/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

/**
 * Class represents a vertex, that is a grid element.
 */
public final class Vertex extends GridElement {
    
    /** Group of edges around the vertex. */
    private ElementGroup edges;    
    
    /**
     * Public constructor with 2 parameters, 
     * that are coordinates of a vertex.
     * Constructs a vertex object.
     * 
     * @param x column index
     * @param y row index
     * @pre {@code x >= 0 && y >= 0} 
     */
    public Vertex(int x, int y) {
        super(x, y);
        edges = new ElementGroup();
    }
    
    /**
     * Getter for edge collection around a vertex.
     *
     * @return the edges around a vertex
     */
    public ElementGroup getEdges() {
        return edges;
    }
    
    /**
     * Setter for edge collection around a vertex.
     * 
     * @param edges the edges to set around a vertex
     */    
    @Override
    public void setEdges(ElementGroup edges) {
        this.edges = edges;
    }   

    /**
     * Returns the number of yes-edges around a vertex.
     * 
     * @return count of yes-edges around a vertex
     * @post {@code \result == number of present edges around a vertex} 
     */
    public int countYesEdges() {
        return edges.histogram.get(EdgeState.PRESENT);
    }
    
    /**
     * Returns the number of no-edges around a vertex.
     * 
     * @return count of no-edges around a vertex
     * @post {@code \result == number of absent edges around a vertex} 
     */
    public int countNoEdges() {
        return edges.histogram.get(EdgeState.ABSENT);
    }
    
    /**
     * Returns the number of maybe-edges around a vertex.
     * 
     * @return count of maybe-edges around a vertex
     * @post {@code \result == number of undetermined edges around a cell} 
     */
    public int countMaybeEdges() {
        return edges.histogram.get(EdgeState.UNDETERMINED);
    }
    
    /**
     * Returns "+" - string representation of vertex
     * 
     * @return representation of vertex in string
     */
    @Override
    public String toString() {
        return "+";
    }
    
}
