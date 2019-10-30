package practica2.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import practica2.Position;
import practica2.Tree;

public class BFSIterator<E> implements Iterator<Position<E>> {

    private final Queue<Position<E>> nodeQueue;
    private final Tree<E> tree;

    public BFSIterator(Tree<E> tree, Position<E> start) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        nodeQueue.add(start);
    }

    public BFSIterator(Tree<E> tree) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        if (!tree.isEmpty())
            nodeQueue.add(tree.root());
    }

    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    @Override
    public Position<E> next() {
        Position<E> aux = nodeQueue.remove();
        for (Position<E> node : tree.children(aux)) {
            nodeQueue.add(node);
        }
        return aux;
    }


}
