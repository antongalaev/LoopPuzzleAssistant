/*
 * @author Galaev Anton
 * Date: 25.11.2012
 */ 
package lpa.solvers;

import lpa.model.*;
import lpa.solvers.command.ChangeStateCommand;
import lpa.solvers.command.Command;
import lpa.solvers.command.CompoundCommand;

/**
 * Strategies to help solving Loop Puzzle.
 */
public class Strategies {

    /** The Loop Puzzle */
    private LoopPuzzle lp;
    
    /**
     * Backup variable that saves initial state of the puzzle.
     * After applying the strategies, grid is compared to this
     * initial state to see if any changes occurred.
     */
    private String init;

    /**
     * Setter for loop puzzle.
     * <p>
     * Sets a loop puzzle and saves its state to backup variable.
     * 
     * @param lp the Loop Puzzle to set
     */
    public void setPuzzle(LoopPuzzle lp) {
        this.lp = lp;        
        init = lp.getGrid().toString();
    }
  
    /**
     * A method, that repeatedly 
     * applies the strategies
     * to the loop puzzle,
     * until no further changes occur.
     * 
     * @return commands changing edges
     */
    public CompoundCommand applyStrategies() {
        CompoundCommand command = new CompoundCommand();
        do { // repeteadly applying
            init = lp.getGrid().toString();
            command.add(CycleStrategies()); // apply for all
            command.add(CornerStrategies()); // apply for corners
        } while (! init.equals(lp.getGrid().toString()));        
        
        return command;
    }

    /**
     * Strategies that are used inside
     * a cycle through all grid elements.
     * 
     * @return commands changing edges inside
     */
    private Command CycleStrategies() {
        CompoundCommand command = new CompoundCommand();
        for (GridElement ge : lp.getGrid()) {
            
            /** Cell strategies */
            if (ge instanceof Cell) {
                Cell cell = (Cell) ge;
                
                /**
                 * Cell strategy #1.
                 * If a cell containing digit d is surrounded
                 * by d yes-edges, then all its
                 * other edges can be marked as no-edges.
                 */
                if (cell.getDigit() == cell.countYesEdges()) {
                    for (Edge edge : cell.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.ABSENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
                
                /**
                 * Cell strategy #2.
                 * If a cell containing digit d is surrounded
                 * by (4 - d) no-edges, then the
                 * remaining edges can be marked as yes-edges.
                 */
                if (4 - cell.getDigit() == cell.countNoEdges()) {
                    for (Edge edge : cell.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.PRESENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }                            
            }       
            
            /** Vertex strategies */
            if (ge instanceof Vertex) {
                Vertex vertex = (Vertex) ge;
                
                /**
                 * Vertex strategy #1.
                 * If a vertex has two yes-edges,
                 * then its other edges can be marked as no-edges.
                 */
                if (vertex.countYesEdges() == 2) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.ABSENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
                
                /**
                 * Vertex strategy #2.
                 * If all but one edges around a vertex are no-edges,
                 * then the remaining edge can be marked as no-edge too.
                 */
                if (vertex.countYesEdges() + vertex.countMaybeEdges() == 1) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getState() != EdgeState.ABSENT) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.ABSENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
                
                /**
                 * Vertex strategy #3.
                 * If vertex is at side of the puzzle 
                 * and it has 1 yes-edge and 1 no-edge,
                 * then the remaining edge can be marked as yes-edge.
                 */
                if (vertex.getEdges().size() == 3 &&
                        vertex.countYesEdges() == 1 &&
                        vertex.countNoEdges() == 1) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.PRESENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
                
                /**
                 * Vertex strategy #4.
                 * Similarly for a non-side vertex.
                 * If vertex has 1 yes-edge and 2 no-edge,
                 * then the remaining edge can be marked as yes-edge.
                 */
                if (vertex.countYesEdges() == 1 &&
                        vertex.countNoEdges() == 2) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.PRESENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
                
                /**
                 * Vertex strategy #5.
                 * If vertex is in the corner of the puzzle 
                 * and it has 1 yes-edge,
                 * then the remaining edge can be marked as yes-edge.
                 */
                if (vertex.getEdges().size() == 2 &&
                        vertex.countYesEdges() == 1) {
                    for (Edge edge : vertex.getEdges()) {
                        if (edge.getState() == EdgeState.UNDETERMINED) {
                            Command c;
                            c = new ChangeStateCommand(edge, EdgeState.PRESENT);
                            c.execute();
                            command.add(c);
                        }
                    }
                }
            }
            lp.getGrid().setElementGroups(); // update links after 1 applying
        } 
        return command;
    }
    
    /**
     * Strategies for corner cells.
     * <ul>
     * <il>If corner cell contains digit 1, then 
     * its two outer edges can be marked as no-edges.
     * <il>If corner cell contains digit 3, then 
     * its two outer edges can be marked as yes-edges.
     * </ul>
     * 
     * @return commands changing edges in corners
     */
    private Command CornerStrategies() {
        CompoundCommand command = new CompoundCommand();        
              
        // go through corners:
        // using offsets to get to the corner vertices from corner cells
        for (int i = 1, iOffset = -1; i < lp.getHeight();
                i += lp.getHeight() - 3, iOffset += 2) {
            for (int j = 1, jOffset = -1; j < lp.getWidth(); 
                    j += lp.getWidth() - 3, jOffset += 2) {
                Cell cell = (Cell) lp.getGrid().getElement(i, j);
                
                /**
                 * Corner strategy #1.
                 * If cell contains 1,
                 * then corner vertex must have no yes-edges 
                 */
                if (cell.getDigit() == 1) {
                    Vertex vertex = (Vertex) 
                            lp.getGrid().getElement(i + iOffset, j + jOffset);
                    for (Edge edge : vertex.getEdges()) {
                        Command c;
                        c = new ChangeStateCommand(edge, EdgeState.ABSENT);
                        c.execute();
                        command.add(c);
                    }
                }
                
                /**
                 * Corner strategy #2.
                 * If cell contains 3,
                 * then corner vertex must have only yes-edges 
                 */
                if (cell.getDigit() == 3) {
                    Vertex vertex = (Vertex) 
                            lp.getGrid().getElement(i + iOffset, j + jOffset);
                    for (Edge edge : vertex.getEdges()) {
                        Command c;
                        c = new ChangeStateCommand(edge, EdgeState.PRESENT);
                        c.execute();
                        command.add(c);
                    }
                }            
            }
        }  
        lp.getGrid().setElementGroups(); // update links
        return command;
    }   
}