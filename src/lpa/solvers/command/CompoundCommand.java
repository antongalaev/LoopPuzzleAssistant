/*
 * @author Galaev Anton
 * Date: 25.11.2012
 */ 
package lpa.solvers.command;

import java.util.ArrayList;
import java.util.List;

/**
 * The Compound Command.
 * A compound command of (already executed) 
 * edge changing commands.
 */
public class CompoundCommand extends Command {
    
    /** List of commands. */
    private List<Command> commands;
    
    /** 
     * Public Constructor.
     * Creates an empty compound command.
     */
    public CompoundCommand() {
        commands = new ArrayList<>();
        setExecuted(true);
    }
    
    /** 
     * Adding a new command to list.
     */
    public void add(Command c) {
        commands.add(c);
    }
    
    /**
     * Execute all commands in list.
     */
    @Override
    public void execute() {
        if (! isExecuted()) {
            super.execute();
            for (Command c : commands) {
                c.execute();
            }     
        }
    }
    
    /**
     * Undo all commands in list.
     */
    @Override
    public void undo() {
        if (isExecuted()) {
            super.undo();
            for (int i = commands.size() - 1; i != -1; -- i) {
                commands.get(i).undo();
            }
        }
    }
}