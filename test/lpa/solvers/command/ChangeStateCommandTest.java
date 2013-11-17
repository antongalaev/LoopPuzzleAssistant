/*
 * @author Galaev Anton
 * Date: 24.11.2012
 */ 
package lpa.solvers.command;

import junit.framework.TestCase;
import lpa.model.Edge;
import lpa.model.EdgeState;

public class ChangeStateCommandTest extends TestCase {

    /**
     * Test of execute method, of class ChangeStateCommand.
     */
    public void testExecute() {
        System.out.println("execute");
        Edge edge = new Edge(0, 1, EdgeState.ABSENT);
        EdgeState expResult = EdgeState.PRESENT;
        ChangeStateCommand instance;
        instance = new ChangeStateCommand(edge, EdgeState.PRESENT);
        instance.execute();
        assertEquals("checking result", expResult, edge.getState());
    }

    /**
     * Test of undo method, of class ChangeStateCommand.
     */
    public void testUndo() {
        System.out.println("undo");
        Edge edge = new Edge(0, 1, EdgeState.ABSENT);
        EdgeState expResult = EdgeState.ABSENT;
        ChangeStateCommand instance;
        instance = new ChangeStateCommand(edge, EdgeState.PRESENT);
        instance.execute();
        instance.undo();
        assertEquals("checking result", expResult, edge.getState());
    }
}
