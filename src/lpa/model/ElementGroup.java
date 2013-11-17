/*
 * @author Galaev Anton
 * Date: 12.11.2012
 */ 
package lpa.model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class that represents a collection of grid elements.
 * <p>
 * Elements of group are actually (and always) edges around
 * a cell or a vertex. Also ElementGroup is iterable.
 */
public class ElementGroup implements Iterable<Edge> {  
    
    /** Collection of elements (edges). */
    private List<Edge> elements;
    
    /** 
     * Histogram, which represents numbers of
     * each concrete type of edges in the collection.
     */
    EnumMap<EdgeState, Integer> histogram;
    
    /**
     * Public constructor without parameters.
     * Creates an empty group of elements.
     */
    public ElementGroup() {        
        elements = new ArrayList<>();
        histogram = new EnumMap<>(EdgeState.class);
        histogram.put(EdgeState.ABSENT, 0);
        histogram.put(EdgeState.PRESENT, 0);
        histogram.put(EdgeState.UNDETERMINED, 0);
    }
       
    /**
     * Adds a grid element to collection. 
     * <p>
     * If element is null reference, does not add it to the group.
     * It is a case for elements in corners or at sides of the puzzle.
     * <p>
     * Normally, just adds new element and updates histogram
     * 
     * @param element new grid element
     * @see Grid#setElementGroups()
     * @see Grid#getElement(int, int) 
     */
    public void add(Edge element) {  
        if (element == null) { // if element at the side of the puzzle
            return;
        }        
        //normally add the element to the group
        elements.add(element);
        //update histogram
        int value = histogram.get(element.getState());
        histogram.put(element.getState(), ++ value);
    }
    
    /**
     * Return the size of element collection.
     * 
     * @return number of edges in collection
     */
    public int size() {
        return elements.size();
    }
    
    /**
     * Returns iterator for the element group.
     * 
     * @return iterator for the group 
     */
    @Override
    public Iterator<Edge> iterator() {
        return new ElementGroupIterator();
    }

    /**
     * Class implementation of ElementGroupIterator 
     */
    private class ElementGroupIterator implements Iterator<Edge> {
        int index;
        
        public ElementGroupIterator() {
            
        }

        @Override
        public boolean hasNext() {
            return index < elements.size();
        }

        @Override
        public Edge next() {
            if (hasNext()) {
                Edge retValue = elements.get(index);
                index ++;
                return retValue;
            } else {
                throw new NoSuchElementException("ElementGroup.next()");
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}