package practica3.iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import practica2.Position;
import practica3.tree.bynarytree.BinaryTree;

public class LevelBinaryTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeQueue = new LinkedList<>();
    private final BinaryTree<T> tree;

    public LevelBinaryTreeIterator(BinaryTree<T> tree) {
        this.tree = tree;
        this.nodeQueue = new LinkedList<>();
    }

    public LevelBinaryTreeIterator(BinaryTree<T> tree, Position<T> start) {
        this.tree = tree;
        this.nodeQueue = new LinkedList<>();
        this.nodeQueue.addLast(start);
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    @Override
    public Position<T> next() {
        Position<T> aux = nodeQueue.remove();
        if(tree.hasLeft(aux))
            nodeQueue.addLast(tree.left(aux));
        if(tree.hasRight(aux))
            nodeQueue.addLast(tree.right(aux));
        return aux;
    }
}
