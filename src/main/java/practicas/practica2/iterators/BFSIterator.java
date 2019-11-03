package practicas.practica2.iterators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import practicas.Position;
import practicas.Tree;

public class BFSIterator<E> implements Iterator<Position<E>> {

    private final Queue<Position<E>> nodeQueue;
    private final Tree<E> tree;


    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     * @param start position to start iterating.
     */
    public BFSIterator(Tree<E> tree, Position<E> start) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        nodeQueue.add(start);
    }

    /**
     * Iterators constructor.
     *
     * @param tree
     */
    public BFSIterator(Tree<E> tree) {
        nodeQueue = new ArrayDeque<>();
        this.tree = tree;
        if (!tree.isEmpty())
            nodeQueue.add(tree.root());
    }

    /**
     * Function that tells if we can continue iterating.
     *
     * @return true if we can.
     */
    @Override
    public boolean hasNext() {
        return (nodeQueue.size() != 0);
    }

    /**
     * Create the next steps to iterate.
     *
     * @return the next element.
     */
    @Override
    public Position<E> next() {
        Position<E> aux = nodeQueue.remove();
        for (Position<E> node : tree.children(aux))
            nodeQueue.add(node);
        return aux;
    }


}
