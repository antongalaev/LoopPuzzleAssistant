/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import java.util.Scanner;

/**
 * Class Loop Puzzle is a class that acts as a facade for grid.
 */
public class LoopPuzzle {
    
    /** Name of a puzzle */
    private final String name;
    
    /** Grid of a puzzle */
    private final Grid grid;
    
    /**
     * Public constructor for Loop Puzzle.
     * Creates a Loop Puzzle with given name and 
     * a grid, constructed from a given scanner.
     * 
     * @param name name of the puzzle
     * @param inp scanner for grid constructor
     */
    public LoopPuzzle(String name, Scanner inp) {
        this.name = name;
        this.grid = new Grid(inp);
    }
    
    /**
     * Getter for name of the puzzle.
     * 
     * @return the name of the puzzle
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for grid of the puzzle.
     * 
     * @return the grid of the puzzle
     */
    public Grid getGrid() {
        return grid;
    }
    
    /**
     * Wrapping getter for the width of the puzzle.
     * 
     * @return number of columns in the puzzle
     */
    public int getWidth() {
        return grid.getWidth();
    }

    /**
     * Wrapping getter for the height of the puzzle.
     * 
     * @return number of rows in the puzzle 
     */
    public int getHeight() {        
        return grid.getHeight();
    }
}
