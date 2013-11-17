/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

/**
 * Class, that represents elements of the grid.
 * Possible grid elements (subclasses) are:
 * <ul>
 * <il>edge
 * <il>cell
 * <il>vertex
 * </ul>
 */
public class GridElement {  
    
    /** Element row index */
    private final int Y;
    
    /** Element column index */
    private final int X;
    
    /**
     * Protected constructor with 2 parameters, 
     * that are coordinates of a grid element.
     * Creates GridElement with given coordinates.
     * Constructor used only in subclasses.
     * 
     * @param x column index
     * @param y row index
     * @throws IllegalArgumentException if indexes are negative
     * @pre {@code x >= 0 && y >= 0}
     */
    protected GridElement(int x, int y) 
            throws IllegalArgumentException {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Illegal indexes for element");
        }
        this.X = x;
        this.Y = y;
    }
    
    /**
     * Getter for column index (X coordinate) of a grid element.
     * 
     * @return column index of an element
     */
    public int getX() {
        return X;
    }

    /**
     * Getter for row index (Y coordinate) of a grid element.
     * 
     * @return row index of an element
     */
    public int getY() {
        return Y;
    }
    
    /**
     * A method, needed for Cell and Vertex objects.
     * Method is overriden in classes Cell and Vertex. 
     * It was done just for convenient calling of overriden methods
     * directly from a base class reference. 
     * Calling for edge it does nothing.
     * 
     * @param edges the edges to set
     * @see Grid#setElementGroups() 
     */
    public void setEdges(ElementGroup edges) {
        // do nothing here
    }
}
