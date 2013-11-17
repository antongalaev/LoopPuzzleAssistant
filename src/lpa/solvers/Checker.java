/*
 * @author Galaev Anton
 * Date: 28.11.2012
 */ 
package lpa.solvers;

import lpa.model.Cell;
import lpa.model.Grid;
import lpa.model.GridElement;
import lpa.model.Vertex;


/**
 * A class for checking the grid against the rules.
 */
public class Checker {
    
    /** The grid to check */
    private Grid g;
    
    /** Coordinates of the violation */
    private int iV, jV;
    
    /** Error message */
    private String message;
      
    /**
     * Public constructor for Checker.
     * 
     * @param g the Grid to check
     */
    public Checker(Grid g) {
        this.g = g;
        iV = jV = -1;
        message = "";
    }
    
    /**
     * Checks grid against the rules, and returns the outcome.
     * 
     * @return boolean value, whether loop puzzle violates rules or not.
     * @pre {@code g != null}
     * @post {@code \result == g satisfies all rules}
     */
    public boolean checkGrid() {
        boolean gridOK = true; // anticipated
        
        //check the grid for all conditions
        if (!check1() || !check2() || !check3()) {
            gridOK = false;
        }
        return gridOK;
    } 
    
    /**
     * Checks grid strictly, and returns the outcome.
     * Should be called after checkGrid() method for
     * being sure, that the puzzle does not violate rules.
     * Returns true, if the puzzle is completely solved.
     * Otherwise, returns false.
     * 
     * @return boolean value, whether loop puzzle is solved or not.
     * @pre {@code g != null}
     * @post {@code \result == g is solved}
     */
    public boolean additionalStrictCheck() {
        for (GridElement ge : g) {
            if (ge instanceof Cell) {
                Cell cell = (Cell) ge;
                if (cell.getDigit() != -1) {
                    if (cell.countYesEdges() != cell.getDigit()) {
                        iV = cell.getY();
                        jV = cell.getX();
                        message = "Cell at row " + iV
                                + " column " + jV
                                + ": incorrect number of edges around";
                        return false;
                    }
                }
            }
            if (ge instanceof Vertex) {
                Vertex vertex = (Vertex) ge;
                 if (vertex.countYesEdges() != 2 && vertex.countYesEdges() != 0) {
                     iV = vertex.getY();
                     jV = vertex.getX();
                     message = "Vertex at row " + iV
                                + " column " + jV
                                + ": incorrect number of edges around";
                    return false;
                }
            }
        }
        return true;
    } 

    /**
     * The 1st condition.
     * Checks that the number of edges around a cell
     * equals to the number in cell.
     * @pre {@code g != null}
     * @post {@code \result == g satisfies (1) condition}
     */
    private boolean check1() {
        boolean gridOK = true;
        int rows = g.getHeight();
        int columns = g.getWidth();
        for (int i = 1; i < rows - 1; i += 2) {
            for (int j = 1; j < columns - 1; j += 2) {
                Cell c = (Cell) g.getElement(i, j);
                if (c.getDigit() != -1) {
                    if (c.countYesEdges() > c.getDigit()) {
                        iV = i;
                        jV = j;
                        message = "Cell at row " + i
                                + " column " + j
                                + ": too many edges present";
                        gridOK = false;
                    }
                    if (c.countNoEdges() > 4 - c.getDigit()) {
                        iV = i;
                        jV = j;
                        message = "Cell at row " + i
                                + " column " + j
                                + ": too many edges absent";
                        gridOK = false;
                    }
                }
            }
        }
        return gridOK;
    }

    /**
     * The 2nd condition.
     * Checks that there is no branches,
     * no more than 2 edges meet at vertex.
     * @pre {@code g != null}
     * @post {@code \result == g satisfies (2) condition}
     */
    private boolean check2() {
        boolean gridOK = true; //anticipated
        int rows = g.getHeight();
        int columns = g.getWidth();
        // Traverse all vertices
        for (int i = 0; i < rows; i += 2) {
            for (int j = 0; j < columns; j += 2) {
                Vertex v = (Vertex)  g.getElement(i, j);
                if (v.countYesEdges() > 2) {
                    iV = i;
                    jV = j;
                    message = "Vertex at row " + i +
                            " column " + j +
                            ": too many edges present";
                    gridOK = false;
                }
            }
        }
        return gridOK;
    }

    /**
     * The 3rd condition
     * Checks that there is is only one connected component.
     * @pre {@code g != null}
     * @post {@code \result == g satisfies (3) condition}
     */
    private boolean check3() {
        boolean gridOK = true;
        int rows = g.getHeight();
        int columns = g.getWidth();
        // First, determine connected components and
        // whether they are loops, using a Union/Find algorithm.
        int[] path = new int[rows * columns];
        // Component id is connected to component Math.abs(path[id])
        // Every path ends with Math.abs(path[id]) == id.
        // path[id] < 0 means the component is a loop
        // Initialize path: each element is a component by itself
        for (int id = 0; id != rows * columns; ++ id) {
            path[id] = id;
        }
        // Traverse all edges, uniting them with their vertices
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                final int e_id = i * columns + j; // id of element
                boolean edge = false;
                int[] v_id = new int[2]; // id of vertices
                if (i % 2 == 0 && j % 2 == 1 && 
                        "-".equals(g.getElement(i, j).toString())) {
                    // horizontal edge
                    edge = true;
                    v_id[0] = e_id - 1;
                    v_id[1] = e_id + 1;
                }
                else if (i % 2 == 1 && j % 2 == 0
                        && "|".equals(g.getElement(i, j).toString())) {
                    // vertical edge
                    edge = true;
                    v_id[0] = e_id - columns;
                    v_id[1] = e_id + columns;
                }
                if (edge) {
                    for (int k = 0; k != 2; ++k) {
                        while (Math.abs(path[v_id[k]]) != v_id[k]) {
                            v_id[k] = Math.abs(path[v_id[k]]);
                        }
                    }
                    // Unite vertices with edge
                    for (int k = 0; k != 2; ++k) {
                        path[v_id[k]] = e_id;
                    }
                    // Detect closing of a loop
                    if (v_id[0] == v_id[1]) {
                        path[e_id] = - e_id;
                    }
                }
            }
        }

        // Second, count the connected components and loops
        int components = 0; // to count the connected components
        int loops = 0; // to count the loops
        // Traverse all edges, uniting their vertices
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns; ++j) {
                final int e_id = i * columns + j; // id of element
                final boolean edge =
                        (i % 2 == 0 && j % 2 == 1 &&
                        "-".equals(g.getElement(i, j).toString()))
                                || (i % 2 == 1 && j % 2 == 0 && 
                        "|".equals(g.getElement(i, j).toString()));
                if (edge) {
                    if (Math.abs(path[e_id]) == e_id) {
                        ++ components;
                        if (path[e_id] < 0) {
                            ++ loops;
                        }
                    }
                }
            }
        }

        // Third, check condition (3b)
        if (loops >= 1 && components > 1) {
            message = "More than just one loop";
            gridOK = false;
        }
        return gridOK;
    }

    /**
     * Getter for row violation index.
     * 
     * @return the iV
     */
    public int getRowViolationIndex() {
        return iV;
    }

    /**
     * Getter for column violation index.
     * 
     * @return the jV
     */
    public int getColViolationIndex() {
        return jV;
    }

    /**
     * Getter for violation message.
     * 
     * @return the message of violation
     */
    public String getMessage() {
        return message;
    }
}
