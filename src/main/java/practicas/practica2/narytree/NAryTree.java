package practicas.practica2.narytree;

import practicas.Position;
import practicas.Tree;

public interface NAryTree<E> extends Tree<E> {
    /**
     * Modifies the element stored in a given position
     *
     * @param p the position to be modified
     * @param e the new element to be stored
     * @return the previous element stored in the position
     */
    E replace(Position<E> p, E e);

    /**
     * Swap the elements stored in two given positions
     *
     * @param p1 the first node to swap
     * @param p2 the second node to swap
     */
    void swapElements(Position<E> p1, Position<E> p2);

    /**
     * Adds a new node whose parent is pointed by a given position.
     *
     * @param element the element to be added
     * @param p       the position of the parent
     * @return the position of the new node created
     */
    Position<E> add(E element, Position<E> p);

    /**
     * Removes a node and its corresponding subtree rooted at node.
     *
     * @param p the position of the node to be removed.
     */
    void remove(Position<E> p);

    /**
     * Moves the node located at pOrig to be a new child of pDest
     *
     * @param pOrig position of the origin node
     * @param pDest position of the destination node
     * @throws RuntimeException if the destination node is a subtree of the original one
     */
    void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException;
}
