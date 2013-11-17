/*
 * @author Galaev Anton
 * Date: 24.11.2012
 */ 
package lpa.solvers.command;

import lpa.model.Edge;
import lpa.model.EdgeState;

/**
 * The ChangeState Command.
 * Class represents a Command, that can change state 
 * of an edge.
 */
public class ChangeStateCommand extends Command {
    
    /** The receiving Edge */
    private final Edge receiver;
    
    /** New state of the edge */
    private final EdgeState newState;
    
    /** Old state of the edge */
    private final EdgeState oldState;
    
    /**
     * Public constructor.
     * Creates an edge state changing command.
     * 
     * @param receiver receiving edge
     * @param newState new state of the receiving edge
     */
    public ChangeStateCommand(Edge receiver, EdgeState newState) {
        super();
        this.receiver = receiver;
        this.newState = newState;
        oldState = receiver.getState();
    }
    
    /**
     * Execute a command.
     */
    @Override
    public void execute() {
        if (! isExecuted()) {
            super.execute();            
            receiver.setState(newState);
        }        
    }
    
    /**
     * Undo a command.
     */
    @Override
    public void undo() {
        if (isExecuted()) {
            super.undo();
            receiver.setState(oldState);
        }
    }  
}
