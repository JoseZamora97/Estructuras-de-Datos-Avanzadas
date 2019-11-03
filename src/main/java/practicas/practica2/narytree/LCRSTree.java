package practicas.practica2.narytree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import practicas.Position;
import practicas.practica2.iterators.BFSIterator;

/**
 * Practica 2 Ejercicio 2 Resolucion.
 *
 * @param <E> the elements stored in the tree
 * @author Jose Miguel Zamora Batista
 */
public class LCRSTree<E> implements NAryTree<E> {

    private static class LCRSNode<T> implements Position<T> {

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

        LCRSNode<T> getFirstChild() {
            return firstChild;
        }

        void setFirstChild(LCRSNode<T> firstChild) {
            this.firstChild = firstChild;
        }

        LCRSNode<T> getSibling() {
            return sibling;
        }

        void setSibling(LCRSNode<T> sibling) {
            this.sibling = sibling;
        }

        LCRSTree<T> getMyTree() {
            return myTree;
        }

        void setMyTree(LCRSTree<T> myTree) {
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

    private LCRSNode<E> checkPosition(Position<E> p) {
        if(!(p instanceof LCRSNode))
            throw new IllegalStateException("The position is invalid");

        LCRSNode<E> aux = (LCRSNode<E>) p;

        if(aux.getMyTree() != this)
            throw new IllegalStateException("The node is not from this tree");

        return aux;
    }

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
        LCRSNode<E> node  = checkPosition(v);
        Position<E> parentPos = node.getParent();
        if(parentPos == null)
            throw new RuntimeException("The node has not parent");

        return parentPos;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {

        List<Position<E>> listChildren = new LinkedList<>();
        LCRSNode<E> aChild = checkPosition(v).getFirstChild();

        while(aChild != null) {
            listChildren.add(aChild);
            aChild = aChild.getSibling();
        }

        return listChildren;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) throws RuntimeException {
        LCRSNode<E> node = checkPosition(v);
        return node.getFirstChild() == null ;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return this.root == checkPosition(v);
    }

    @Override
    public Position<E> addRoot(E e) throws RuntimeException {
        if(!isEmpty())
            throw new IllegalStateException("Tree already has a root");

        this.size=1;
        this.root = new LCRSNode<>(null, null, null, this, e);
        return this.root;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new BFSIterator<>(this);
    }

    @Override
    public E replace(Position<E> p, E e) {
        LCRSNode<E> node = checkPosition(p);
        E tmp = p.getElement();
        node.setElement(e);

        return tmp;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        LCRSNode<E> node1 = checkPosition(p1);
        LCRSNode<E> node2 = checkPosition(p2);
        E temp = p2.getElement();
        node2.setElement(p1.getElement());
        node1.setElement(temp);
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        LCRSNode<E> parent = checkPosition(p);
        LCRSNode<E> newChild = new LCRSNode<>(parent, null, null, this, element);

        addSubtree(newChild, parent);

        size += 1;

        return newChild;
    }

    @Override
    public void remove(Position<E> p) {
        LCRSNode<E> node = checkPosition(p);

        if (node.getParent() == null) {
            this.root = null;
            this.size = 0;
        }
        else {
            Iterator<Position<E>> it = new BFSIterator<>(this, p);
            int cont = 0;
            while (it.hasNext()) {
                LCRSNode<E> next = checkPosition(it.next());
                next.setMyTree(null);
                cont++;
            }
            size = size - cont;
            delete(node);
        }

        node.setMyTree(null);
    }

    @Override
    public void moveSubtree(Position<E> pOrig, Position<E> pDest) throws RuntimeException {
        LCRSNode<E> src, dst;

        src = checkPosition(pOrig);
        dst = checkPosition(pDest);

        if(isRoot(pOrig))
            throw new RuntimeException("Root node can't be moved");
        if(pOrig == pDest)
            throw new RuntimeException("Both positions are the same");

        BFSIterator<E> iterator = new BFSIterator<>(this, src);
        while(iterator.hasNext()) {
            if (iterator.next() == pDest)
                throw new RuntimeException("Target position can't be a sub tree of origin");
        }

        delete(src);
        addSubtree(src, dst);
    }

    private void addSubtree(LCRSNode<E> subNode, LCRSNode<E> destination) {

        subNode.setParent(destination);

        LCRSNode<E> aChild = destination.getFirstChild();
        if (aChild == null)
            destination.setFirstChild(subNode);

        else {
            while(aChild.getSibling() != null)
                aChild = aChild.getSibling();

            aChild.setSibling(subNode);
        }
    }

    private void delete(LCRSNode<E> node) {

        LCRSNode<E> i = node.getParent().getFirstChild();
        LCRSNode<E> j = node.getParent().getFirstChild();

        if(i == node)
            i.getParent().setFirstChild(node.getSibling());

        else {
            int cont = 0;

            while(i.getSibling() != node){
                i = i.getSibling();
                cont++;
            }

            for (int k=0; k < cont; k++)
                j = j.getSibling();

            j.setSibling(node.getSibling());
        }

        node.setParent(null);
    }
}
