/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

/**
 * Enumeration, that represents possible states of an edge.
 * Possible states are:
 * <ul>
 * <il>UNDETERMINED for maybe-edge 
 * <il>PRESENT for yes-edge 
 * <il>ABSENT for no-edge 
 * </ul>
 */
public enum EdgeState {
    UNDETERMINED, PRESENT, ABSENT;
}
