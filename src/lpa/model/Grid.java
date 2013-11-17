/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class representing the grid of the loop puzzle.
 * <p>
 * Grid can be constructed using a Scanner from text file or string.
 * Also grid is iterable.
 */
public final class Grid implements Iterable<GridElement> {  
    
    /** The grid of the puzzle. */
    private List<GridElement[]> grid;
   
    /**
     * Public constructor, that
     * creates grid from the given scanner.
     *
     * @param inp input scanner
     * @pre {@code inp != null}
     */
    public Grid(Scanner inp) {
        grid = new ArrayList<>();
        readGrid(inp);        
        setElementGroups();
    }   
    
    /**
     * Public factory method, that
     * creates empty grid of given height and width.
     * Height and width must be larger than one cell.
     *
     * @param height number of rows
     * @param width number of columns
     * @pre {@code height > 3 && width > 3}
     * @throws IllegalArgumentException if the grid is not rectangle
     *         with odd width and height
     */
    public static Grid createEmpty(int height, int width)
            throws IllegalArgumentException {
        if (! (width > 1 && height > 1)) {
            throw new IllegalArgumentException("Incorrect size.");
        }
        String emptyGrid = "";
        
        for (int i = 0; i < height; ++ i) {
            for (int j = 0; j < width; ++ j) {
                 if (j % 2 == 0 && i % 2 == 0) { // both even for vertex
                     emptyGrid += "+";
                 }
                 if (j % 2 == 1 && i % 2 == 1) { // both odd for cell
                     emptyGrid += " ";
                 }
                 if (j % 2 + i % 2 == 1) { // only one odd for edges 
                     emptyGrid += ".";
                 }
            }
            emptyGrid += "\r\n"; // new line
        }
        Grid empty = new Grid(new Scanner(emptyGrid));        
        if (! empty.isRectangle()) { // if not rectangle
            throw new IllegalArgumentException("Illegal grid: not a rectangle"
                    + " with odd number of rows and columns");
        }
        empty.setElementGroups();
        return empty;
    }
    
    /**
     * Private auxiliary method, that
     * reads grid from the given file.
     *
     * @param inp input scanner
     * @throws IllegalArgumentException if the grid is not OK,
     *         that means, that some illegal characters are met
     *         or grid is not rectangle
     */
    private void readGrid(Scanner inp) 
            throws IllegalArgumentException {
        int i = 0; // line counter
        
        while (inp.hasNext()) { // to the end of scanner
            char[] line = inp.nextLine().toCharArray(); // read a line
            GridElement[] gridLine = new GridElement[line.length];
            
            for (int j = 0; j < line.length; ++ j) { // go through a line
                switch (line[j]) {
                    case '+':
                        //both indexes must be even
                        if (j % 2 == 0 && i % 2 == 0) {
                            gridLine[j] = new Vertex(j, i);
                        } else {
                            throw new IllegalArgumentException(
                                    "Illegal grid element:" + 
                                    " bad vertex in row " + i + ", column " + j);
                        }
                        break;
                    case ' ':
                        //both indexes must be odd for cell                        
                        if (j % 2 == 1 && i % 2 == 1) {
                            gridLine[j] = new Cell(j, i);
                            break;                                                      
                        }
                        //only one of the indexes must be odd for edge
                        if (j % 2 + i % 2 == 1) {
                            gridLine[j] = new Edge(j, i, EdgeState.ABSENT);
                            break;  
                        } 
                        // if upper conditions are not satisfited,
                        // the character is illegal
                        throw new IllegalArgumentException("Illegal grid element");                       
                    case '0': case '1': case '2': case '3':
                        //both indexes must be odd for cell
                        if (j % 2 == 1 && i % 2 == 1) {
                            gridLine[j] = new Cell(j, i, line[j]);
                            break;                                                    
                        } else {
                            throw new IllegalArgumentException("Illegal grid element:"
                                    + " bad cell in row " + i + ", column " + j);
                        }                        
                    case '-': 
                        //even row and odd column for horizontal edge
                        if (j % 2 == 1 && i % 2 == 0) {
                            gridLine[j] = new Edge(j, i, EdgeState.PRESENT);
                            break;  
                        } else {
                            throw new IllegalArgumentException("Illegal grid element:"
                                    + " bad edge in row " + i + ", column " + j);
                        } 
                    case '|':
                        if (j % 2 == 0 && i % 2 == 1) {
                            gridLine[j] = new Edge(j, i, EdgeState.PRESENT);
                            break;  
                        } else {
                            throw new IllegalArgumentException("Illegal grid element:"
                                    + " bad edge in row " + i + ", column " + j);
                        }
                    case '.':
                        //only one of the indexes must be odd for edge
                        if (j % 2 + i % 2 == 1) {
                            gridLine[j] = new Edge(j, i, EdgeState.UNDETERMINED);
                            break;  
                        } else {
                            throw new IllegalArgumentException("Illegal grid element:"
                                    + " bad edge in row " + i + ", column " + j);
                        }
                    default:
                        throw new IllegalArgumentException("Illegal grid element"
                                    + " in row " + i + ", column " + j);                      
                }
            } // end of line
            grid.add(gridLine);
            ++ i;
        } // end of scanner (string)
        
        if (! isRectangle()) { // if not rectangle
            throw new IllegalArgumentException("Illegal grid: not a rectangle"
                    + " with odd number of rows and columns.");
        }
    }
    
    /** 
     * Private auxiliary method, that
     * checks that the grid is a rectangle,
     * with an odd number of rows and columns. 
     * <p>
     * Returns boolean value, which shows,
     * whether grid is OK or not.
     * If grid is not OK, method prints
     * some additional info on stderr.
     * 
     * @return boolean value (whether grid is OK)
     */
    private boolean isRectangle() {
        int nRows, nColumns;
        
        nRows = grid.size();
        nColumns = grid.get(0).length;
        if (nRows % 2 != 1) {
            return false;
        }       
        if (nColumns % 2 != 1) {
            return false;
        }
        for (int i = 0; i != nRows; ++ i) {
            if (grid.get(i).length != nColumns) {
                    return false;
                }
            }
        return true;
    }
    
    /**
     * Auxiliary method for setting element (actually edge) groups
     * for cells and vertices.
     * @see Grid#getElement(int, int)
     * @see ElementGroup#add(lpa.model.Edge) 
     */
    public void setElementGroups() {
         for (GridElement g : this) {
            if (g instanceof Cell || g instanceof Vertex) {
                //edges collection around a cell or vertex
                ElementGroup edges;
                //element that is to add to the group
                Edge adding;
                                
                edges = new ElementGroup();
                adding = (Edge) this.getElement(g.getY(), g.getX() - 1);
                edges.add(adding);
                adding = (Edge) this.getElement(g.getY(), g.getX() + 1);
                edges.add(adding);
                adding = (Edge) this.getElement(g.getY() - 1, g.getX());
                edges.add(adding);
                adding = (Edge) this.getElement(g.getY() + 1, g.getX());
                edges.add(adding);
                g.setEdges(edges);
            }
        }
    }
 
    /**
     * Returns grid element for its given coordinates.
     * If element coordinates are out of range, then return null.
     * It is a case for elements in corners or at sides of the puzzle.
     * 
     * @param y row index
     * @param x column index
     * @see Grid#setElementGroups()
     * @see ElementGroup#add(lpa.model.Edge) 
     */
    public GridElement getElement(int y, int x) {
        if (! (0 <= y && y < grid.size() && 0 <= x && x < grid.get(0).length)) {
            return null;
        }
        return grid.get(y)[x];
    }    
     
    /**
     * Returns the number of rows in the grid.
     * 
     * @return number of rows in the grid
     */
    public int getHeight() {
        return grid.size();
    }

    /**
     * Returns the number of columns in the grid.
     * 
     * @return number of columns in the grid
     */
    public int getWidth() {
        return grid.get(0).length;
    }
    
    /**
     * Returns string representation of the grid.
     * Useful for saving grid to text file or appending it
     * to the text area.
     * 
     * @return representation of the grid in string
     */
    @Override
    public String toString() {
        String result = "";
        for (GridElement g : this) {
            if (g.getX() == getWidth() - 1) {
                result += g + "\r\n";
            } else {
                result += g;
            }            
        }
        return result;
    }
    
    /**
     * Returns iterator for the grid.
     * 
     * @return iterator for the grid 
     */
    @Override
    public Iterator<GridElement> iterator() {
        return new GridIterator();
    }
    
    /**
     * Class implementation of GridIterator.
     */
    private class GridIterator implements Iterator<GridElement> {        
        private int row, column;
        
        GridIterator() {
            column = row = 0;
        }

        @Override
        public boolean hasNext() {
            return row < getHeight();
        }

        @Override
        public GridElement next() {
            if (hasNext()) {
                GridElement retValue = grid.get(row)[column];
                column ++;
                if (column == getWidth()) { // if end of row is reached
                    row ++;
                    column = 0;
                }
                return retValue;
            } else {
                throw new NoSuchElementException("GridIterator.next()");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }        
    }
}