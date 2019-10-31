package practica3.tree.bynarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import practica2.Position;

@SuppressWarnings("unchecked")
public class ArrayBinaryTree<E> implements BinaryTree<E> {

    private Object[] tree;
    private final int ROOT_POS = 1;
    private int capacity = 50;

    private final int LEFT = 0;
    private final int RIGHT = 1;

    private int size;

    public ArrayBinaryTree() {
        this.size = 0;
        this.tree = new Object[capacity];
    }

    private class BTPos<T> implements Position<T> {

        private T element;
        private int index;
        private ArrayBinaryTree<T> tree;

        public BTPos(T element, int index, ArrayBinaryTree<T> tree) {
            this.element = element;
            this.index = index;
            this.tree = tree;
        }

        public ArrayBinaryTree<T> getTree() {
            return tree;
        }

        public void setTree(ArrayBinaryTree<T> tree) {
            this.tree = tree;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public T getElement() {
            return element;
        }

        public BTPos<T> copy() {
            return new BTPos<>(element, index, tree);
        }

        public boolean isRight(){
            return !isLeft();
        }

        public boolean isLeft(){
            return index % 2 == 0;
        }
    }

    @Override
    public Position<E> left(Position<E> v) {
        if(!hasLeft(v))
            throw new RuntimeException("No left child");
        return getChild(v, LEFT);
    }

    private Position<E> getChild(Position<E> v, int side) {
        int iParent = checkPosition(v).getIndex();
        return (BTPos<E>) this.tree[2*iParent + side];
    }

    @Override
    public Position<E> right(Position<E> v) throws RuntimeException {
        if(!hasRight(v))
            throw new RuntimeException("No right child");

        return getChild(v, RIGHT);
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        return getChild(v, LEFT) != null;
    }

    @Override
    public boolean hasRight(Position<E> v) {
        return getChild(v, RIGHT) != null;
    }

    @Override
    public E replace(Position<E> p, E e) {
        BTPos<E> node = checkPosition(p);
        E temp = p.getElement();
        node.setElement(e);
        return temp;
    }

    @Override
    public Position<E> sibling(Position<E> p) throws RuntimeException {
        BTPos<E> node = checkPosition(p);
        Position<E> sibling;

        if(node.isLeft())
            sibling = getChild(parent(p), LEFT);
        else
            sibling = getChild(parent(p), RIGHT);

        if(sibling == null)
            throw new RuntimeException("No sibling");

        return sibling;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) throws RuntimeException {
        if(hasLeft(p)) throw new RuntimeException("Node already has a left child");
        return insert(p, e, LEFT);
    }

    private Position<E> insert(Position<E> p, E e, int side) {
        BTPos<E> node = checkPosition(p);
        Position<E> pos = getChild(p, side);

        if(pos != null)
            throw new RuntimeException("Node already has a left child");

        size++;

        BTPos<E> newNode = new BTPos<>(e, 2*node.index+side, this);
        tree[newNode.getIndex()] =  newNode;

        return newNode;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) throws RuntimeException {
        if(hasRight(p)) throw new RuntimeException("Node already has a right child");
        return insert(p, e, RIGHT);
    }

    @Override
    public E remove(Position<E> p) throws RuntimeException {
        BTPos<E> pos = checkPosition(p);
        if(hasLeft(pos) && hasRight(pos))
            throw new RuntimeException("Cannot remove node with two children");

        size--;
        removeRecursive(pos);
        return pos.getElement();
    }

    public void removeRecursive(Position <E> i) {
        this.tree[((BTPos<E>) i ) .getIndex()] = null;
        if(hasLeft(i))
            removeRecursive(left(i));
        if(hasRight(i))
            removeRecursive(right(i));
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {

    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        return null;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) throws RuntimeException {
    }

    @Override
    public boolean isComplete() {
        return isComplete((BTPos<E>)tree[ROOT_POS]);
    }

    private boolean isComplete(Position<E> node) {
        if(isInternal(node)) {
            if (!hasLeft(node) || !hasRight(node))
                return false;
            return isComplete(left(node)) && isComplete(right(node));
        }
        else
            return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Position<E> root() {
        if(isEmpty())
            throw new RuntimeException("The tree is empty");
        return (Position<E>) tree[ROOT_POS];
    }

    @Override
    public Position<E> parent(Position<E> v) {
        BTPos<E> node = checkPosition(v);

        if(node == root())
            return null;

        if(node.isLeft())
            return (Position<E>) tree[node.getIndex()/2];

        return (Position<E>) tree[(node.getIndex()-1)/2];
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {

        List<Position<E>> iterable = new ArrayList<>();

        Position<E> left = getChild(v, LEFT);
        Position<E> right = getChild(v, RIGHT);

        if(left != null)
            iterable.add(left);

        if(right != null)
            iterable.add(right);

        return iterable;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        return (!hasLeft(v) && !hasRight(v));
    }

    @Override
    public boolean isRoot(Position<E> v) {
        return checkPosition(v) == root();
    }

    @Override
    public Position<E> addRoot(E e) {
        if (!isEmpty())
            throw new RuntimeException("Tree already has a root");

        tree[ROOT_POS] = new BTPos<>(e, ROOT_POS, this);

        size = 1;

        return (BTPos<E>) tree[ROOT_POS];
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return null;
    }

    /**
     * If v is a good binary tree node, cast to BTPosition, else throw exception
     */
    private BTPos<E> checkPosition(Position<E> p) {
        if (!(p instanceof BTPos))
            throw new RuntimeException("The position is invalid");
        return (BTPos<E>) p;
    }
}