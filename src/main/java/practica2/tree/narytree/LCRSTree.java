package practica2.tree.narytree;


import java.util.Iterator;

import practica2.Position;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @param <E> the elements stored in the tree
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 */
public class LCRSTree<E> implements NAryTree<E> {

    //TODO: Practica 2 Ejercicio 2

    public static class LCRSNode<T> implements Position<T> {

        LCRSNode<T> parent;
        LCRSNode<T> firstChild;
        LCRSNode<T> sibling;

        LCRSTree<T> myTree;

        T element;

        public LCRSNode(LCRSNode<T> parent, LCRSNode<T> firstChild, LCRSNode<T> sibling, LCRSTree<T> myTree, T element) {
            this.parent = parent;
            this.firstChild = firstChild;
            this.sibling = sibling;
            this.myTree = myTree;
            this.element = element;
        }

        public LCRSNode<T> getParent() {
            return parent;
        }

        public void setParent(LCRSNode<T> parent) {
            this.parent = parent;
        }

        public LCRSNode<T> getFirstChild() {
            return firstChild;
        }

        public void setFirstChild(LCRSNode<T> firstChild) {
            this.firstChild = firstChild;
        }

        public LCRSNode<T> getSibling() {
            return sibling;
        }

        public void setSibling(LCRSNode<T> sibling) {
            this.sibling = sibling;
        }

        public LCRSTree<T> getMyTree() {
            return myTree;
        }

        public void setMyTree(LCRSTree<T> myTree) {
            this.myTree = myTree;
        }

        public void setElement(T element) {
            this.element = element;
        }

        @Override
        public T getElement() {
            return this.element;
        }
    }

    private int size;
    private LCRSNode<E> root;

    public LCRSTree () {
        this.root = null;
        this.size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> root() throws RuntimeException {
        if(root == null)
            throw new RuntimeException("The tree is empty");

        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isInternal(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public boolean isRoot(Position<E> v) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Iterator<Position<E>> iterator() {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public E replace(Position<E> p, E e) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void remove(Position<E> p) {
        throw new RuntimeException("Not yet implemented");
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        throw new RuntimeException("Not yet implemented");
    }
}
