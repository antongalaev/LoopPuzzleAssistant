/*
 * @author Galaev Anton
 * Date: 25.11.2012
 */ 
package lpa.solvers;

import java.util.ArrayList;
import java.util.EventListener;
import lpa.model.Edge;
import lpa.model.EdgeState;
import lpa.model.GridElement;
import lpa.model.LoopPuzzle;
import lpa.solvers.command.ChangeStateCommand;
import lpa.solvers.command.Command;
import lpa.solvers.command.CompoundCommand;

/**
 * A class to automatically solve
 * a puzzle through backtracking.
*/
public class Backtracker {    
    
    /** Report listener interface. */
    public static interface ReportListener extends EventListener {
        
        /**
         * Reports progress.
         * 
         * @param progress  the progress indicator
         * @pre {@code 0 <= progress <= 100}
         */
        void reportProgress(int progress);
        
        /**
         * Reports a solution.
         * 
         * @param progress  the progress indicator
         * @pre {@code solution} is a solution for puzzle
         */
        void reportSolution(String solution);
        
    }
    
    /** The Loop Puzzle */
    private LoopPuzzle lp;
    
    /** Strategies */
    private Strategies strategies;
    
    /** Checker */
    private Checker checker;
    
    /** Solutions */
    private ArrayList<String> solutions;
    
    /** Flag to abort the calculation */
    private boolean abort;
    
    /** Progress listener */
    private ReportListener listener;    

    /**
     * Setter for loop puzzle that is to solve.
     * 
     * @param lp the Loop Puzzle to set
     */
    public void setPuzzle(LoopPuzzle lp) {
        this.lp = lp;
        strategies = new Strategies();
        strategies.setPuzzle(lp);
        checker = new Checker(lp.getGrid());
    }
    
    /**
     * Setter for listener, that reports progress and found solutions.
     * 
     * @param listener the listener to set
     */
    public void setListener(ReportListener listener) {
        this.listener = listener;
    }
    
    /**
     * Requests the calculation to be aborted (at a convenient moment).
     */
    public void abort() {
        this.abort = true;
    }
   
    /**
     * A wrapper method to solve the puzzle via backtracking.
     * Clears puzzle, applies strategies, then solves via backtracking.
     * Safely stops solving, if aborted.
     */
    public void solve() {
        this.abort = false;
        CompoundCommand command = new CompoundCommand();
        for (GridElement ge : lp.getGrid()) { // clear all puzzle edges
            if (ge instanceof Edge) {
                Command c = new ChangeStateCommand((Edge) ge,
                        EdgeState.UNDETERMINED);
                c.execute();
                command.add(c);
            }
        }
        lp.getGrid().setElementGroups(); // update
        command.add(strategies.applyStrategies());
        lp.getGrid().setElementGroups(); // update
        try {
            solveBacktracking(0, 100);
        } catch (InterruptedException e) {
            // aborted, ignore exception
        } finally { // undo changes
            command.undo();
            lp.getGrid().setElementGroups();
        }
    }
    
     /**
     * Solves the puzzle via backtracking.
     * <p>
     * Finds undetermined edge and sets its state to other possible states: 
     * absent and present. Then recursively calls itself. 
     * <p>
     * If changed puzzle state violates rules then goes back (return). 
     * <p>
     * If changed puzzle state is a solution, reports it.
     * 
     * @param low lowest bound of progress on current level of recursion
     * @param high highest bound of progress on current level of recursion
     * @throws InterruptedException if aborted
     */
    private void solveBacktracking(int low, int high) 
            throws InterruptedException {        
        if (! checker.checkGrid()) { // if violates rules
            return;
        } else if (checker.additionalStrictCheck()) { // if solutiion found
            listener.reportSolution(lp.getGrid().toString());
            return;
        }
        if (abort) { // if aborted
            throw new InterruptedException("Aborted by user.");
        }
        for (GridElement g : lp.getGrid()) { // go through grid
            if (g instanceof Edge) { // find undetermined edges next
                Edge edge = (Edge) g;
                if (edge.getState() == EdgeState.UNDETERMINED) {
                    Command change, strategy;
                    
                    // changing to PRESENT
                    change = new ChangeStateCommand(edge, EdgeState.PRESENT);
                    change.execute();
                    lp.getGrid().setElementGroups(); // update
                    strategy = strategies.applyStrategies(); 
                    lp.getGrid().setElementGroups(); // update
                    solveBacktracking(low, (low + high) / 2); // call again
                    strategy.undo();
                    change.undo();                    
                    lp.getGrid().setElementGroups(); // update
                    listener.reportProgress((low + high) / 2); // report
                    
                    // changing to ABSENT
                    change = new ChangeStateCommand(edge, EdgeState.ABSENT);
                    change.execute();
                    lp.getGrid().setElementGroups(); // update
                    strategy = strategies.applyStrategies();
                    lp.getGrid().setElementGroups(); // update
                    solveBacktracking((low + high) / 2, high); // call again
                    strategy.undo();
                    change.undo();                    
                    lp.getGrid().setElementGroups(); // update
                    listener.reportProgress(high); // report
                    break; // exit cycle
                }
            }
        }
    }    
}
