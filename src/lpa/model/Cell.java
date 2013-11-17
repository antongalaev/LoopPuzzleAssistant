/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

/**
 * Class represents a cell, that is a grid element.
 */
public final class Cell extends GridElement {
    
    /**
     * Digit value in cell.
     * Its value may vary from -1 to 3.
     * It takes -1 in case there is no digit in cell.
     */
    private int digit;
    
    /** Group of edges around the cell. */
    private ElementGroup edges;

    /**
     * Public constructor without digit parameter.
     * Creates a cell with no digit inside.
     * 
     * @param x column index
     * @param y row index
     */
    public Cell(int x, int y) {
        super(x, y);
        digit = -1;
        edges = new ElementGroup();
    }
    
    /**
     * Public constructor with digit parameter.
     * Creates cell with the given digit inside.
     * 
     * @param x column index
     * @param y row index
     * @param digit digit in the cell
     * @pre {@code x >= 0 && y >= 0 &&
     *      (digit == ' ' || (digit >= '0' && digit <= '3'))}
     */
    public Cell(int x, int y, char digit) {
        super(x, y);
        setDigit(digit - 48); // convert to int
        edges = new ElementGroup();
    }
    
    /**
     * Getter for edge collection around a cell.
     * 
     * @return the edges to set around a cell
     */
    public ElementGroup getEdges() {
        return edges;
    }
    
    /**
     * Setter for edge collection around a cell.
     * 
     * @param edges the edges to set
     */   
    @Override
    public void setEdges(ElementGroup edges) {
        this.edges = edges;
    }   
    
    /**
     * Returns the number of yes-edges around a cell.
     * 
     * @return count of yes-edges around a cell
     * @post {@code \result == number of present edges around a cell} 
     */
    public int countYesEdges() {
        return getEdges().histogram.get(EdgeState.PRESENT);
    }
    
    /**
     * Returns the number of no-edges around a cell.
     * 
     * @return count of no-edges around a cell
     * @post {@code \result == number of absent edges around a cell} 
     */
    public int countNoEdges() {
        return getEdges().histogram.get(EdgeState.ABSENT);
    }
    
    /**
     * Returns the number of maybe-edges around a cell.
     * 
     * @return count of maybe-edges around a cell
     * @post {@code \result == number of undetermined edges around a cell} 
     */
    public int countMaybeEdges() {
        return getEdges().histogram.get(EdgeState.UNDETERMINED);
    }
    
    /**
     * Getter for a digit value inside a cell.
     * 
     * @return the digit value inside a cell
     * @post {@code -1 <= \result <= 3}
     */
    public int getDigit() {
        return digit;
    }

    /**
     * Setter for a digit value inside a cell.
     * 
     * @param digit the digit value to set inside a cell
     * @pre {@code -1 <= digit <= 3}
     */
    public void setDigit(int digit) 
            throws IllegalArgumentException {
        if (digit < -1 || digit > 3) {
            throw new IllegalArgumentException("Illegal cell digit = " + digit);
        }
        this.digit = digit;
    }    
    
    /**
     * Returns " " if there is no digit inside a cell and a 
     * string representation of a digit otherwise.
     * 
     * @return representation of cell in string
     */
    @Override
    public String toString() {
        if (digit == -1) { // if no digit inside
            return " ";
        } else { // if cell has a digit
            return String.valueOf(digit);
        }        
    }   
}
