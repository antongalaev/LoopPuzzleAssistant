/*
 * @author Galaev Anton
 * Date: 24.11.2012
 */ 
package lpa.solvers.command;

import junit.framework.TestCase;
import lpa.model.Edge;
import lpa.model.EdgeState;

public class CompoundCommandTest extends TestCase {
    
    //Note: method add() is tested inside these 2 tests
    
    /**
     * Test of execute method, of class CompoundCommand.
     */
    public void testExecute() {
        System.out.println("execute");
        CompoundCommand instance = new CompoundCommand();
        Edge edge = new Edge(0, 1, EdgeState.ABSENT);
        EdgeState expResult = EdgeState.PRESENT;
        ChangeStateCommand c;        
        c = new ChangeStateCommand(edge, EdgeState.UNDETERMINED);
        c.execute();
        instance.add(c);
        c = new ChangeStateCommand(edge, EdgeState.PRESENT);        
        c.execute();
        instance.add(c);
        assertEquals("checking result", expResult, edge.getState());
    }

    /**
     * Test of undo method, of class CompoundCommand.
     */
    public void testUndo() {
        System.out.println("undo");
        CompoundCommand instance = new CompoundCommand();
        Edge edge = new Edge(0, 1, EdgeState.ABSENT);
        EdgeState expResult = EdgeState.ABSENT;
        ChangeStateCommand c;        
        c = new ChangeStateCommand(edge, EdgeState.UNDETERMINED);
        c.execute();
        instance.add(c);
        c = new ChangeStateCommand(edge, EdgeState.PRESENT);        
        c.execute();
        instance.add(c);
        instance.undo();
        assertEquals("checking result", expResult, edge.getState());
    }
}
