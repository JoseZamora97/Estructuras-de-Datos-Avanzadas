package practicas.practica5.binarysearchtree;

import java.util.Comparator;

public class DefaultComparator <E> implements Comparator<E> {
    /** Compares two given elements
     *
     * @return a negative integer if <tt>a</tt> is less than <tt>b</tt>,
     * zero if <tt>a</tt> equals <tt>b</tt>, or a positive integer if
     * <tt>a</tt> is greater than <tt>b</tt>
     */

    public int compare(E a, E b) throws ClassCastException {
        return ((Comparable<E>) a).compareTo(b);
    }
}
