package practica3.tree.bynarytree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import practica2.Position;

import practica3.iterators.InorderBinaryTreeIterator;
import practica3.iterators.LevelBinaryTreeIterator;

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
    public Position<E> sibling(Position<E> p) {
        BTPos<E> node = checkPosition(p);

        if(node.isLeft())
            if(tree[node.getIndex() + RIGHT] != null)
                return (Position<E>) tree[node.getIndex() + RIGHT];

        if(node.isRight())
            if(tree[node.getIndex() - RIGHT] != null)
                return (Position<E>) tree[node.getIndex() - RIGHT];

        throw new RuntimeException("No sibling");
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

        if(!hasLeft(p) && !hasRight(p))
            tree[pos.getIndex()] = null;

        // hacer subtree.

        return pos.getElement();
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BTPos<E> p1pos = checkPosition(p1);
        BTPos<E> p2pos = checkPosition(p2);

        int aux = p2pos.index;
        p2pos.index = p1pos.index;
        p1pos.index = aux;

        tree[p1pos.index] = p1;
        tree[p2pos.index] = p2;
    }

    @Override
    public BinaryTree<E> subTree(Position<E> v) {
        BTPos<E> position = checkPosition(v);
        ArrayBinaryTree<E> bTree = new ArrayBinaryTree<>();
        LevelBinaryTreeIterator<E> it = new LevelBinaryTreeIterator<>(this, v);

        bTree.tree[ROOT_POS] = position;

        while(it.hasNext()) {
            if(position != root()) {
                position = (BTPos<E>) it.next();
                if(position.isRight())
                    bTree.insertRight(position, position.element);
                if(position.isLeft())
                    bTree.insertLeft(position, position.element);
            }
        }

        for(int i=0; i<capacity; i++)
            this.tree[i] = null;

        it = new LevelBinaryTreeIterator<>(bTree);

        this.tree[ROOT_POS] = bTree.root();
        this.size = 1;

        while(it.hasNext()) {
            BTPos<E> position2 = (BTPos<E>) it.next();
            if(position2 != root()) {
                if(position2.isRight())
                    this.insertRight(position2, position2.element);
                if(position2.isLeft())
                    this.insertLeft(position2, position2.element);
            }
        }

        return bTree;
    }

    @Override
    public void attachLeft(Position<E> p, BinaryTree<E> tree)  {
        checkPosition(p);

//        LevelBinaryTreeIterator<E> itTree = new LevelBinaryTreeIterator<>(tree);
//        LevelBinaryTreeIterator<E> itThis = new LevelBinaryTreeIterator<>(this, p);
//
//        Position<E> pNextTree, pNextThis, pParentNextThis;
//
//        pParentNextThis = this.parent(p);
//
//        while(itTree.hasNext()){
//
//            pNextTree = itTree.next();
//            pNextThis = itThis.next();
//
//            if(pNextThis != null) {
//                if (!hasLeft(pNextThis))
//                    insertLeft(pNextThis, pNextTree.getElement());
//                else
//                    replace(pNextThis, pNextTree.getElement());
//
//                if (!hasRight(pNextThis))
//                    insertRight(pNextThis, pNextTree.getElement());
//                else
//                    replace(pNextThis, pNextTree.getElement());
//            }
//            else {
//                if (tree.right(tree.parent(pNextTree)) == pNextTree)
//                    insertRight(this.parent(pParentNextThis), pNextTree.getElement());
//
//                if (tree.left(tree.parent(pNextTree)) == pNextTree)
//                    insertRight(this.parent(pParentNextThis), pNextTree.getElement());
//            }
//
//            pParentNextThis = this.parent(pNextThis);
//        }

    }

    @Override
    public void attachRight(Position<E> p, BinaryTree<E> tree) {
    }

    @Override
    public boolean isComplete() {
        return isComplete((BTPos<E>)tree[ROOT_POS]);
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
        if(isEmpty()) throw new RuntimeException("The tree is empty");
        return (Position<E>) tree[ROOT_POS];
    }

    @Override
    public Position<E> parent(Position<E> v) {
        BTPos<E> node = checkPosition(v);

        if(node == root())
            throw new RuntimeException("No parent");

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
        return new InorderBinaryTreeIterator<>(this);
    }

    /**
     * If v is a good binary tree node, cast to BTPosition, else throw exception
     */
    private BTPos<E> checkPosition(Position<E> p) {
        if (!(p instanceof BTPos))
            throw new RuntimeException("The position is invalid");
        return (BTPos<E>) p;
    }

    private Position<E> getChild(Position<E> v, int side) {
        int iParent = checkPosition(v).getIndex();
        return (BTPos<E>) this.tree[2*iParent + side];
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
}