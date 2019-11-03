package practicas.practica3.iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

import practicas.Position;
import practicas.practica3.bynarytree.BinaryTree;

/**
 * Level iterator for binary trees.
 *
 * @param <T>
 * @author Jose Miguel Zamora Batista.
 */
public class LevelBinaryTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> nodeQueue;
    private final BinaryTree<T> tree;


    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     */
    public LevelBinaryTreeIterator(BinaryTree<T> tree) {
        this.tree = tree;
        this.nodeQueue = new LinkedList<>();
        if(!tree.isEmpty())
            this.nodeQueue.add(tree.root());
    }

    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     * @param start position to start iterating.
     */
    public LevelBinaryTreeIterator(BinaryTree<T> tree, Position<T> start) {
        this.tree = tree;
        this.nodeQueue = new LinkedList<>();
        this.nodeQueue.addLast(start);
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
    public Position<T> next() {
        Position<T> aux = nodeQueue.remove();
        if(tree.hasLeft(aux))
            nodeQueue.addLast(tree.left(aux));
        if(tree.hasRight(aux))
            nodeQueue.addLast(tree.right(aux));
        return aux;
    }
}
