/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

/**
 * Class represents an edge, that is a grid element.
 */
public final class Edge extends GridElement {
    
    /** 
     * State of the edge.
     * @see EdgeState
     */
    private EdgeState state; 
    
    /**
     * Public constructor with 2 parameters, 
     * that are coordinates of an edge.
     * Creates an edge with UNDETERMINED state.
     * 
     * @param x column index
     * @param y row index
     * @pre {@code x >= 0 && y >= 0}
     */
    public Edge(int x, int y) {
        super(x, y);
        state = EdgeState.UNDETERMINED;
    }
    
    /**
     * Public constructor with additional state parameter.
     * Creates edge with the given state.
     * 
     * @param x column index
     * @param y row index
     * @param state given state of the edge 
     * @pre {@code x >= 0 && y >= 0 && state != null}
     */
    public Edge(int x, int y, EdgeState state) {
        super(x, y);
        this.setState(state);
    }

    /**
     * Getter for edge state.
     *
     * @return the state of the edge
     */
    public EdgeState getState() {
        return state;
    }

    /**
     * Setter for edge state.
     *
     * @param state the state to set for the edge
     * @throws NullPointerException if state is null
     * @pre {@code state != null}
     */
    public void setState(EdgeState state) 
            throws NullPointerException {
        if (state == null) {
            throw new NullPointerException();
        }
        this.state = state;
    }
    
   /**
     * Returns representation of an edge in string.
     * <ul>
     * <il>"." if edge state is undetermined
     * <il>" " if edge state is absent
     * <il>"|" if edge state is present and the edge is vertical
     * <il>"-" if edge state is present and the edge is horizontal
     * </ul>
     * 
     * @return representation of an edge in string
     */
    @Override
    public String toString() {
        if (this.getState() == EdgeState.UNDETERMINED) { // if maybe-edge
            return ".";
        }
        if (this.getState() == EdgeState.ABSENT) { // if no-edge
            return " ";
        }
        if (this.getX() % 2 == 0) { // if vertical yes-edge
            return "|";
        } else { // if horizontal yes-edge
            return "-";
        }
    }
}