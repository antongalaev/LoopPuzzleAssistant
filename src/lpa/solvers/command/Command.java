/*
 * @author Galaev Anton
 * Date: 24.11.2012
 */ 
package lpa.solvers.command;

/**
 * Base abstract class to represent an executable and undoable command,
 * applied to a Loop Puzzle.
 * Concrete command classes extend this base class,
 * by adding parameters (when needed), undo state (when needed), and
 * overriding execute() and undo().
 */
public abstract class Command {   
    
    /** Execution state */
    private boolean executed;
    
    /**
     * Constructs a command for a given receiver.
     */
    public Command() {
        this.executed = false;
    }

    /**
     * Executes the command.
     * A concrete command will override this method.
     */
    public void execute() {
        setExecuted(true);
    }

    /**
     * Undoes the command.
     * A concrete command will override this method.
     */
    public void undo() {
        setExecuted(false);
    }

    /**
     * Getter for {@code executed}.
     * Used to check in subclasses whether command is executed or not.
     * 
     * @return the executed
     */
    public boolean isExecuted() {
        return executed;
    }

    /**
     * Setter for {@code executed}.
     * Compound command are set executed in constructor.
     * 
     * @param executed the executed to set
     */
    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}