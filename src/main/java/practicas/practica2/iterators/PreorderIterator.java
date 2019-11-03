package practicas.practica2.iterators;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Predicate;

import practicas.Position;
import practicas.Tree;

/**
 * Practica 2 Ejercicio 3 Resolucion
 *
 * @param <E>
 * @author Jose Miguel Zamora Batista.
 */
public class PreorderIterator<E> implements Iterator<Position<E>> {

    private final Deque<Position<E>> nodeStack;
    private final Tree<E> tree;
    private Predicate<Position<E>> predicate;

    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     */
    public PreorderIterator(Tree<E> tree) {
        this.nodeStack = new LinkedList<>();
        this.tree = tree;
        predicate = a -> true;
        if(!tree.isEmpty())
            this.nodeStack.add(tree.root());
    }

    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     * @param start position to start iterating.
     */
    public PreorderIterator(Tree<E> tree, Position<E> start) {
        this.nodeStack = new  LinkedList<>();
        this.tree = tree;
        this.nodeStack.add(start);
        predicate = a -> true;
    }


    /**
     * Iterators constructor.
     *
     * @param tree tree to iterate.
     * @param start position to start iterating.
     * @param predicate function to apply filtering.
     */
    public PreorderIterator(Tree<E> tree, Position<E> start, Predicate<Position<E>> predicate) {
        this.nodeStack = new  LinkedList<>();
        this.tree = tree;
        this.nodeStack.add(start);
        this.predicate = predicate;
    }

    /**
     * Function that tells if we can continue iterating.
     *
     * @return true if we can.
     */
    @Override
    public boolean hasNext() {
        return !nodeStack.isEmpty();
    }

    /**
     * Create the next steps to iterate.
     *
     * @return the next element.
     */
    @Override
    public Position<E> next() {

        Position<E> aux = nodeStack.pop();

        Deque<Position<E>> children = new LinkedList<>();

        for(Position<E> node : tree.children(aux))
            children.push(node);

        for(Position<E> node : children)
            nodeStack.push(node);

        if(!predicate.test(aux))
            if(hasNext())
                aux = next();
            else
                return null;

        return aux;
    }
}